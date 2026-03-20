package fr.sae.chuzzle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class EcouteurCase implements View.OnTouchListener {
    private static final String TAG = "EcouteurCase";

    private final ControleJeu controle;
    private final ActiviteJeu activite;
    private final int ligne;
    private final int colonne;
    private final int distanceMin;

    private float departX;
    private float departY;

    public EcouteurCase(ControleJeu controle, ActiviteJeu activite, int ligne, int colonne, int distanceMin) {
        this.controle = controle;
        this.activite = activite;
        this.ligne = ligne;
        this.colonne = colonne;
        this.distanceMin = distanceMin;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                departX = event.getX();
                departY = event.getY();
                activite.relancerAideAnalys();
                Log.d(TAG, "ACTION_DOWN grille=(" + ligne + "," + colonne + ")"
                        + " touch=(" + departX + "," + departY + ")");
                return true;

            case MotionEvent.ACTION_UP:
                float deltaX = event.getX() - departX;
                float deltaY = event.getY() - departY;
                Log.d(TAG, "ACTION_UP grille=(" + ligne + "," + colonne + ")"
                        + " arriveeTouch=(" + event.getX() + "," + event.getY() + ")"
                        + " deltaX=" + deltaX + " deltaY=" + deltaY);

                if (Math.abs(deltaX) < distanceMin && Math.abs(deltaY) < distanceMin) {
                    Log.d(TAG, "Geste ignore car trop petit. seuil=" + distanceMin);
                    return true;
                }

                controle.jouerGlissement(
                        ligne,
                        colonne,
                        deltaX,
                        deltaY,
                        view.getWidth(),
                        view.getHeight()
                );
                activite.actualiserJeu();
                activite.relancerAideAnalys();
                return true;

            case MotionEvent.ACTION_CANCEL:
                return false;

            default:
                return true;
        }
    }
}
