package fr.sae.chuzzle;
import android.view.View;

public class MenuClickListener implements View.OnClickListener {

    public static final int ACTION_PLAY = 0;
    public static final int ACTION_SEED = 1;
    public static final int ACTION_QUIT = 2;

    private MenuController controller;
    private int action;

    public MenuClickListener(MenuController controller, int action) {
        this.controller = controller;
        this.action = action;
    }

    @Override
    public void onClick(View v) {
        if (action == ACTION_PLAY) {
            controller.demarrerPartieNormale();
        } else if (action == ACTION_SEED) {
            controller.demarrerPartieDestinee();
        } else if (action == ACTION_QUIT) {
            controller.quitterJeu();
        }
    }
}
