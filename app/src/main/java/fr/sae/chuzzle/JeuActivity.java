package fr.sae.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridLayout;

public class JeuActivity extends Activity {

    private MoteurJeu moteur;
    private CaseView[][] caseViews;

    private ElementRenderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        long seed = System.currentTimeMillis();
        moteur = new MoteurJeu(seed);

        renderer = new NormlRenderer();

        GridLayout grid = findViewById(R.id.grilleJeu);

        int taille = MoteurJeu.TAILLE;

        caseViews = new CaseView[taille][taille];

        for(int i=0;i<taille;i++){
            for(int j=0;j<taille;j++){

                CaseView v = new CaseView(this);

                GridLayout.LayoutParams params =
                        new GridLayout.LayoutParams();

                params.width = 120;
                params.height = 120;

                v.setLayoutParams(params);

                caseViews[i][j] = v;

                grid.addView(v);
            }
        }

        afficherGrille();
    }

    private void afficherGrille(){

        Case[][] cases = moteur.getGrille().getCases();

        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){

                TypeElement type = cases[i][j].getType();

                caseViews[i][j].setDrawable(
                        renderer.getDrawable(type)
                );
            }
        }
    }
}