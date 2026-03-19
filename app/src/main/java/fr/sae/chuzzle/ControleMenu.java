package fr.sae.chuzzle;

import android.content.Intent;

public class ControleMenu {
    private final ActiviteMenu activite;

    public ControleMenu(ActiviteMenu activite){
        this.activite = activite;
    }

    public void demarrerPartieNormale() {
        long seed = System.currentTimeMillis();
        Intent intent = new Intent(activite, ActiviteJeu.class);

        MoteurJeu.effacerSauvegarde(activite);
        intent.putExtra(ActiviteJeu.EXTRA_SEED, seed);
        activite.startActivity(intent);
    }

    public void demarrerPartieDestinee() {
        Intent intent = new Intent(activite, ActiviteGraine.class);
        activite.startActivity(intent);
    }

    public void ouvrirParametres() {
        Intent intent = new Intent(activite, ActiviteOptions.class);
        activite.startActivity(intent);
    }

    public void quitterJeu() {
        activite.finish();
    }
}
