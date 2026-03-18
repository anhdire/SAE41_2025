package fr.sae.chuzzle;

import android.util.Log;

import java.util.List;

public class ControleJeu {
    private static final String TAG = "ControleJeu";
    private final MoteurJeu moteur;

    public ControleJeu(long seed) {
        moteur = new MoteurJeu(seed);
    }

    public List<EtatJeu> jouerGlissement(int ligne, int colonne, float deltaX, float deltaY) {
        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            int decalage = deltaX > 0 ? -1 : 1;
            String direction = deltaX > 0 ? "droite" : "gauche";
            Log.d(TAG, "Glissement horizontal detecte ligne=" + ligne
                    + " direction=" + direction + " decalage=" + decalage);
            return moteur.jouerCoupAnime(true, ligne, decalage);
        }

        int decalage = deltaY > 0 ? 1 : -1;
        String direction = deltaY > 0 ? "bas" : "haut";
        Log.d(TAG, "Glissement vertical detecte colonne=" + colonne
                + " direction=" + direction + " decalage=" + decalage);
        return moteur.jouerCoupAnime(false, colonne, decalage);
    }

    public Case[][] getCases() {
        return moteur.getGrille().getCases();
    }

    public int getScore() {
        return moteur.getScore();
    }

    public int getNbCoup() {
        return moteur.getNbCoup();
    }

    public boolean estFinie() {
        return moteur.estFinie();
    }
}
