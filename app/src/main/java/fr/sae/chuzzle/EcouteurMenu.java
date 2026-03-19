package fr.sae.chuzzle;

import android.view.View;

public class EcouteurMenu implements View.OnClickListener {
    public static final int ACTION_PLAY = 0;
    public static final int ACTION_SEED = 1;
    public static final int ACTION_QUIT = 2;

    private ControleMenu controle;
    private int action;

    public EcouteurMenu(ControleMenu controle, int action) {
        this.controle = controle;
        this.action = action;
    }

    @Override
    public void onClick(View v) {
        if (action == ACTION_PLAY) {
            controle.demarrerPartieNormale();
        } else if (action == ACTION_SEED) {
            controle.demarrerPartieDestinee();
        } else if (action == ACTION_QUIT) {
            controle.quitterJeu();
        }
    }
}
