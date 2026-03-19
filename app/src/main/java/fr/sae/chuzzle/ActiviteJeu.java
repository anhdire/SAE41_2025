package fr.sae.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.GridLayout;
import android.widget.TextView;

public class ActiviteJeu extends Activity {
    public static final String EXTRA_SEED = "fr.sae.chuzzle.SEED";

    private ControleJeu controle;
    private VueCase[][] vuesCases;
    private AfficheurElement afficheur;
    private TextView txtScore;
    private TextView txtCoups;
    private TextView txtSeed;
    private TextView txtMessageFin;
    private long seed;
    private boolean modeDur;
    private int distanceMin;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        seed = recupererSeed();
        modeDur = new ControleOptions(this).isHardMode();
        controle = new ControleJeu(seed, modeDur);
        afficheur = new AfficheurNormal();
        txtSeed = findViewById(R.id.txtSeed);
        txtScore = findViewById(R.id.txtScore);
        txtCoups = findViewById(R.id.txtCoups);
        txtMessageFin = findViewById(R.id.txtMessageFin);
        distanceMin = ViewConfiguration.get(this).getScaledTouchSlop();

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
        GridLayout grilleJeu = findViewById(R.id.grilleJeu);
        int taille = MoteurJeu.TAILLE_GRILLE;
        int tailleCase = calculerTailleCase();
        int margeCase = getResources().getDimensionPixelSize(R.dimen.marge_case_grille);

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
                grilleJeu.addView(vueCase);
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
        txtScore.setText(getString(R.string.jeu_score, controle.getScore()));
        txtCoups.setText(getString(R.string.jeu_coups, controle.getNbCoup()));
        txtSeed.setText(getString(R.string.jeu_seed, seed));
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
                vuesCases[i][j].setVerrouillee(cases[i][j].isVerrouille());
            }
        }
    }

    public void actualiserJeu() {
        mettreAJourScoreEtCoups();
        afficherGrille();
        verifierFinPartie();
    }

    private void verifierFinPartie() {
        if (controle.estFinie()) {
            txtMessageFin.setVisibility(View.VISIBLE);
            txtMessageFin.setText(getString(R.string.jeu_fin_partie, seed));
        } else {
            txtMessageFin.setVisibility(View.GONE);
        }
    }
}
