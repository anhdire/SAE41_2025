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

    void lancerAnimation(List<EtatJeu> etapes) {
        if (etapes.isEmpty()) {
            Log.d(TAG, "Aucune animation a jouer, rafraichissement direct");
            actualiserJeu();
            return;
        }

        animationEnCours = true;
        Log.d(TAG, "Animation lancee avec " + etapes.size() + " etapes");
        handler.removeCallbacksAndMessages(null);
        handler.post(new AnimationJeu(this, handler, etapes));
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

    void finAnimation() {
        animationEnCours = false;
        Log.d(TAG, "Animation terminee");
        actualiserJeu();
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
}
