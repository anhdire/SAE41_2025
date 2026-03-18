package fr.sae.chuzzle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

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
                Log.d(TAG, "ACTION_DOWN ligne=" + ligne + " colonne=" + colonne
                        + " x=" + departX + " y=" + departY);
                return true;

            case MotionEvent.ACTION_UP:
                float deltaX = event.getX() - departX;
                float deltaY = event.getY() - departY;
                Log.d(TAG, "ACTION_UP ligne=" + ligne + " colonne=" + colonne
                        + " deltaX=" + deltaX + " deltaY=" + deltaY);

                if (activite.estAnimationEnCours()) {
                    Log.d(TAG, "Geste ignore car animation en cours");
                    return true;
                }

                if (Math.abs(deltaX) < distanceMin && Math.abs(deltaY) < distanceMin) {
                    Log.d(TAG, "Geste trop petit ignore. seuil=" + distanceMin);
                    return true;
                }

                List<EtatJeu> etapes = controle.jouerGlissement(ligne, colonne, deltaX, deltaY);
                Log.d(TAG, "Nombre d'etapes recues=" + etapes.size());
                activite.lancerAnimation(etapes);
                return true;

            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "ACTION_CANCEL ligne=" + ligne + " colonne=" + colonne);
                return false;

            default:
                return true;
        }
    }
}
