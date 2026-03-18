package fr.sae.chuzzle;

import android.view.MotionEvent;
import android.view.View;

public class EcouteurCase implements View.OnTouchListener {

    private final ActiviteJeu activite;
    private final int ligne;
    private final int colonne;
    private final int distanceMin;

    private float departX;
    private float departY;

    public EcouteurCase(ActiviteJeu activite, int ligne, int colonne, int distanceMin) {
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
                return true;

            case MotionEvent.ACTION_UP:
                float deltaX = event.getX() - departX;
                float deltaY = event.getY() - departY;

                if (Math.abs(deltaX) < distanceMin && Math.abs(deltaY) < distanceMin) {
                    return true;
                }

                activite.jouerGlissement(ligne, colonne, deltaX, deltaY);
                return true;

            case MotionEvent.ACTION_CANCEL:
                return false;

            default:
                return true;
        }
    }
}
