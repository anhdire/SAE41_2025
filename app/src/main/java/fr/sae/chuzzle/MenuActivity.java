package fr.sae.chuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuActivity extends Activity {

    private Button btnPlay;
    private Button btnOptions;
    private Button btnQuit;

    private MenuController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // je cree le controlleur car en android c'est la vue qui est creer en premier par l'os
        this.controller = new MenuController(this);

        btnPlay = findViewById(R.id.btnPlay);
        btnOptions = findViewById(R.id.btnOptions);
        btnPartieDestinee = findViewById(R.id.btnPartieDestinee);
        btnQuit = findViewById(R.id.btnQuit);

        btnPlay.setOnClickListener(
            new MenuClickListener(controller, MenuClickListener.ACTION_PLAY)
        );

        btnOptions.setOnClickListener(
            new MenuClickListener(controller, MenuClickListener.ACTION_OPTIONS)
        );

        btnPartieDestinee.setOnClickListener(
            new MenuClickListener(controller, MenuClickListener.ACTION_SEED)
        );

        btnQuit.setOnClickListener(
            new MenuClickListener(controller, MenuClickListener.ACTION_QUIT)
        );
    }
}
