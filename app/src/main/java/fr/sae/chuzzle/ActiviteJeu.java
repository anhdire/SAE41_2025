package fr.sae.chuzzle;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.List;

public class ActiviteJeu extends Activity {
    private static final String TAG = "ActiviteJeu";

    public static final String EXTRA_SEED = "fr.sae.chuzzle.SEED";

    private ControleJeu controle;
    private VueCase[][] vuesCases;
    private AfficheurElement afficheur;
    private TextView txtScore;
    private TextView txtCoups;
    private TextView txtSeed;
    private long seed;
    private int distanceMin;
    private Handler handler;
    private boolean animationEnCours;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        seed = recupererSeed();
        controle = new ControleJeu(seed);
        afficheur = new AfficheurNormal();
        txtSeed = findViewById(R.id.txtSeed);
        txtScore = findViewById(R.id.txtScore);
        txtCoups = findViewById(R.id.txtCoups);
        distanceMin = ViewConfiguration.get(this).getScaledTouchSlop();
        handler = new Handler(Looper.getMainLooper());
        animationEnCours = false;
        Log.d(TAG, "onCreate seed=" + seed + " distanceMin=" + distanceMin);

        initialiserGrille();
        actualiserJeu();
    }

    private long recupererSeed() {
        return getIntent().getLongExtra(
                EXTRA_SEED,
                System.currentTimeMillis()
        );
    }

    private void initialiserGrille() {
        GridLayout grid = findViewById(R.id.grilleJeu);
        int taille = MoteurJeu.TAILLE_GRILLE;
        int tailleCase = calculerTailleCase();
        int margeCase = getResources().getDimensionPixelSize(R.dimen.marge_case_grille);
        Log.d(TAG, "Initialisation grille taille=" + taille + " tailleCase=" + tailleCase + " margeCase=" + margeCase);

        vuesCases = new VueCase[taille][taille];

        for(int i=0;i<taille;i++){
            for(int j=0;j<taille;j++){
                VueCase vueCase = new VueCase(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();

                params.rowSpec = GridLayout.spec(i);
                params.columnSpec = GridLayout.spec(j);
                params.width = tailleCase;
                params.height = tailleCase;
                params.setMargins(margeCase, margeCase, margeCase, margeCase);

                vueCase.setLayoutParams(params);
                vueCase.setOnTouchListener(
                        new EcouteurCase(controle, this, i, j, distanceMin)
                );

                vuesCases[i][j] = vueCase;
                grid.addView(vueCase);
            }
        }
    }

    private int calculerTailleCase() {
        DisplayMetrics metriques = getResources().getDisplayMetrics();
        int largeurEcran = metriques.widthPixels;
        int paddingContenu = getResources().getDimensionPixelSize(R.dimen.padding_contenu_jeu_h);
        int paddingCadre = getResources().getDimensionPixelSize(R.dimen.padding_cadre_grille);
        int margeCase = getResources().getDimensionPixelSize(R.dimen.marge_case_grille);
        int tailleBase = getResources().getDimensionPixelSize(R.dimen.taille_case_grille);

        int largeurDisponible = largeurEcran - (paddingContenu * 2) - (paddingCadre * 2);
        int tailleMax = (largeurDisponible / MoteurJeu.TAILLE_GRILLE) - (margeCase * 2);

        return Math.min(tailleBase, tailleMax);
    }

    private void mettreAJourScoreEtCoups() {
        txtScore.setText("Score : " + controle.getScore());
        txtCoups.setText("Coups : " + controle.getNbCoup());
    }

    private void afficherGrille(){
        afficherGrille(controle.getCases());
    }

    private void afficherGrille(Case[][] cases){
        int taille = MoteurJeu.TAILLE_GRILLE;

        for(int i=0;i<taille;i++){
            for(int j=0;j<taille;j++){
                vuesCases[i][j].setTranslationX(0f);
                vuesCases[i][j].setTranslationY(0f);
                TypeElement type = cases[i][j].getType();
                vuesCases[i][j].setDrawable(
                        afficheur.getDrawable(type)
                );
            }
        }
    }

    public void actualiserJeu() {
        Log.d(TAG, "actualiserJeu score=" + controle.getScore()
                + " coups=" + controle.getNbCoup()
                + " finie=" + controle.estFinie());
        mettreAJourScoreEtCoups();
        afficherGrille();
        verifierFinPartie();
    }

    public void lancerAnimation(List<EtatJeu> etapes, boolean estLigne, int index, int decalage) {
        if (etapes.isEmpty()) {
            Log.d(TAG, "Aucune animation a jouer, rafraichissement direct");
            actualiserJeu();
            return;
        }

        animationEnCours = true;
        Log.d(TAG, "Animation lancee avec " + etapes.size() + " etapes");
        handler.removeCallbacksAndMessages(null);
        handler.post(new AnimationJeu(this, handler, etapes, estLigne, index, decalage));
    }

    void afficherEtat(EtatJeu etat) {
        Log.d(TAG, "afficherEtat score=" + etat.getScore()
                + " coups=" + etat.getNbCoups()
                + " finie=" + etat.estFinie());
        txtScore.setText("Score : " + etat.getScore());
        txtCoups.setText("Coups : " + etat.getNbCoups());
        afficherGrille(convertirEnCases(etat.getTypes()));

        if (etat.estFinie()) {
            txtSeed.setVisibility(View.VISIBLE);
            txtSeed.setText("Seed : " + seed);
        } else {
            txtSeed.setVisibility(View.GONE);
        }
    }

    public void finAnimation() {
        animationEnCours = false;
        Log.d(TAG, "Animation terminee");
        actualiserJeu();
    }

    void animerGlissement(boolean estLigne, int index, int decalage) {
        float translation;

        if (decalage == 0) {
            return;
        }

        translation = calculerDistanceGlissement(estLigne, Math.abs(decalage));

        if (estLigne) {
            if (decalage < 0) {
                translation = Math.abs(translation);
            } else {
                translation = -Math.abs(translation);
            }
            animerLigne(index, translation);
            return;
        }

        if (decalage > 0) {
            translation = Math.abs(translation);
        } else {
            translation = -Math.abs(translation);
        }
        animerColonne(index, translation);
    }

    boolean estAnimationEnCours() {
        return animationEnCours;
    }

    private void verifierFinPartie() {
        if (controle.estFinie()) {
            txtSeed.setVisibility(View.VISIBLE);
            txtSeed.setText("Seed : " + seed);
        } else {
            txtSeed.setVisibility(View.GONE);
        }
    }

    private Case[][] convertirEnCases(TypeElement[][] types) {
        int taille = types.length;
        Case[][] cases = new Case[taille][taille];

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cases[i][j] = new Case(types[i][j]);
            }
        }
        return cases;
    }

    private float calculerDistanceGlissement(boolean estLigne, int nbCases) {
        VueCase vue = vuesCases[0][0];
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) vue.getLayoutParams();
        int tailleCase;
        int marge;

        if (estLigne) {
            tailleCase = vue.getWidth();
            marge = params.leftMargin + params.rightMargin;
        } else {
            tailleCase = vue.getHeight();
            marge = params.topMargin + params.bottomMargin;
        }

        return (tailleCase + marge) * nbCases;
    }

    private void animerLigne(int ligne, float translationX) {
        int taille = MoteurJeu.TAILLE_GRILLE;

        for (int colonne = 0; colonne < taille; colonne++) {
            vuesCases[ligne][colonne].animate()
                    .translationX(translationX)
                    .setDuration(AnimationJeu.DUREE_GLISSEMENT)
                    .start();
        }
    }

    private void animerColonne(int colonne, float translationY) {
        int taille = MoteurJeu.TAILLE_GRILLE;

        for (int ligne = 0; ligne < taille; ligne++) {
            vuesCases[ligne][colonne].animate()
                    .translationY(translationY)
                    .setDuration(AnimationJeu.DUREE_GLISSEMENT)
                    .start();
        }
    }
}
