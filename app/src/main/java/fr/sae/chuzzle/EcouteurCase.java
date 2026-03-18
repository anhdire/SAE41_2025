package fr.sae.chuzzle;

import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class EcouteurCase implements View.OnTouchListener {

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
                return true;

            case MotionEvent.ACTION_UP:
                float deltaX = event.getX() - departX;
                float deltaY = event.getY() - departY;

                if (activite.estAnimationEnCours()) {
                    return true;
                }

                if (Math.abs(deltaX) < distanceMin && Math.abs(deltaY) < distanceMin) {
                    return true;
                }

                List<EtatJeu> etapes = controle.jouerGlissement(ligne, colonne, deltaX, deltaY);
                activite.lancerAnimation(etapes);
                return true;

            case MotionEvent.ACTION_CANCEL:
                return false;

            default:
                return true;
        }
    }
}
