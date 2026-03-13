package fr.sae.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class JeuActivity extends Activity {
    private TextView txtScore;
    private TextView txtCoups;
    private JeuController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);
        controller = new JeuController(this);

        txtScore = findViewById(R.id.txtScore);
        txtCoups = findViewById(R.id.txtCoups);

        this.mettreAJourInfos();

    }

    private void mettreAJourInfos(){
        txtScore.setText("Score : " + controller.getMoteur().getScore());
        txtCoups.setText("Coups : " + controller.getMoteur().getNbCoup());
    }
}
