package fr.sae.chuzzle;

import android.content.Intent;

public class MenuController {

    private final MenuActivity activity;
    public MenuController(MenuActivity activity){
        this.activity = activity;
    }

    public void demarrerPartieNormale() {
        Intent intent =new Intent(activity, JeuActivity.class);
        activity.startActivity(intent);
    }

    public void demarrerPartieDestinee() {
        Intent intent = new Intent(activity, SeedActivity.class);
        activity.startActivity(intent);
    }

    public void ouvrirOptions() {
        Intent intent = new Intent(activity, OptionsActivity.class);
        activity.startActivity(intent);
    }

    public void quitterJeu() {
        activity.finish();
    }
}
