package fr.sae.chuzzle;

import android.content.Intent;

public class MenuController {

    private final MenuActivity activity;
    public MenuController(MenuActivity activity){
        this.activity = activity;
    }

    public void demarrerPartieNormale() {
        long seed = System.currentTimeMillis();
        Intent intent = new Intent(activity, JeuActivity.class);
        intent.putExtra(JeuActivity.EXTRA_SEED, seed);
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
