package fr.sae.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class JeuActivity extends Activity {

    public static final String EXTRA_SEED = "fr.sae.chuzzle.SEED";

    private MoteurJeu moteur;
    private CaseView[][] caseViews;
    private ElementRenderer renderer;
    private TextView txtScore;
    private TextView txtCoups;
    private TextView txtSeed;
    private long seed;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        seed = recupererSeed();
        moteur = new MoteurJeu(seed);
        renderer = new NormalRenderer();
        txtSeed = findViewById(R.id.txtSeed);
        txtScore = findViewById(R.id.txtScore);
        txtCoups = findViewById(R.id.txtCoups);

        initialiserGrille();
        mettreAJourScoreEtCoups();
        afficherGrille();
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

        caseViews = new CaseView[taille][taille];

        for(int i=0;i<taille;i++){
            for(int j=0;j<taille;j++){
                CaseView caseView = new CaseView(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();

                params.width = tailleCase;
                params.height = tailleCase;

                caseView.setLayoutParams(params);

                caseViews[i][j] = caseView;
                grid.addView(caseView);
            }
        }
    }

    private void mettreAJourScoreEtCoups() {
        txtScore.setText("Score : " + moteur.getScore());
        txtCoups.setText("Coups : " + moteur.getNbCoup());
    }

    private void afficherGrille(){
        Case[][] cases = moteur.getGrille().getCases();
        int taille = MoteurJeu.TAILLE_GRILLE;

        for(int i=0;i<taille;i++){
            for(int j=0;j<taille;j++){
                TypeElement type = cases[i][j].getType();
                caseViews[i][j].setDrawable(
                        renderer.getDrawable(type)
                );
            }
        }
    }
    private void verifierFinPartie() {
        if (moteur.estFinie()) {
            txtSeed.setVisibility(View.VISIBLE);
            txtSeed.setText("Seed : " + seed);
        }
    }
}
