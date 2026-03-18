package fr.sae.chuzzle;

import android.content.Intent;
import android.util.Log;

public class ControleMenu {
    private static final String TAG = "ControleMenu";

    private final ActiviteMenu activite;

    public ControleMenu(ActiviteMenu activite){
        this.activite = activite;
    }

    public void demarrerPartieNormale() {
        long seed = System.currentTimeMillis();
        Log.d(TAG, "Demarrage partie normale seed=" + seed);
        Intent intent = new Intent(activite, ActiviteJeu.class);
        intent.putExtra(ActiviteJeu.EXTRA_SEED, seed);
        activite.startActivity(intent);
    }

    public void demarrerPartieDestinee() {
        Log.d(TAG, "Ouverture ecran graine");
        Intent intent = new Intent(activite, ActiviteGraine.class);
        activite.startActivity(intent);
    }

    public void quitterJeu() {
        Log.d(TAG, "Fermeture du jeu depuis le menu");
        activite.finish();
    }
}
