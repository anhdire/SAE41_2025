package fr.sae.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MenuActivity extends Activity {

    private Button btnPlay;
    private Button btnQuit;
    private Button btnPartieDestinee;

    private MenuController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // je cree le controlleur car en android c'est la vue qui est creer en premier par l'os
        this.controller = new MenuController(this);

        btnPlay = findViewById(R.id.btnPlay);
        btnPartieDestinee = findViewById(R.id.btnPartieDestinee);
        btnQuit = findViewById(R.id.btnQuit);

        btnPlay.setOnClickListener(
               new MenuClickListener(controller, MenuClickListener.ACTION_PLAY)
        );

        btnPartieDestinee.setOnClickListener(
            new MenuClickListener(controller, MenuClickListener.ACTION_SEED)
        );

        btnQuit.setOnClickListener(
            new MenuClickListener(controller, MenuClickListener.ACTION_QUIT)
        );
    }
}
