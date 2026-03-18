package fr.sae.chuzzle;

import android.app.Activity;
import android.os.Bundle;
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
    private long seed;
    private int distanceMin;

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
        int tailleCase = getResources().getDimensionPixelSize(R.dimen.taille_case_grille);
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
                grid.addView(vueCase);
            }
        }
    }

    private void mettreAJourScoreEtCoups() {
        txtScore.setText("Score : " + controle.getScore());
        txtCoups.setText("Coups : " + controle.getNbCoup());
    }

    private void afficherGrille(){
        Case[][] cases = controle.getCases();
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
        mettreAJourScoreEtCoups();
        afficherGrille();
        verifierFinPartie();
    }

    private void verifierFinPartie() {
        if (controle.estFinie()) {
            txtSeed.setVisibility(View.VISIBLE);
            txtSeed.setText("Seed : " + seed);
        } else {
            txtSeed.setVisibility(View.GONE);
        }
    }
}
