package fr.sae.chuzzle;

import android.app.Activity;
import android.os.Bundle;

public class JeuActivity extends Activity {
    private JeuController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        controller = new JeuController(this);

    }
}
