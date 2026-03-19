package fr.sae.chuzzle;

import android.util.Log;

import java.util.List;

public class ControleJeu {
    private static final String TAG = "ControleJeu";
    private final MoteurJeu moteur;
    private boolean dernierEstLigne;
    private int dernierIndex;
    private int dernierDecalage;

    public ControleJeu(long seed) {
        moteur = new MoteurJeu(seed);
    }

    public List<EtatJeu> jouerGlissement(int ligne, int colonne, float deltaX, float deltaY,
                                         int largeurCase, int hauteurCase) {
        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            int nbCases = calculerNbCases(Math.abs(deltaX), largeurCase);
            int decalage = deltaX > 0 ? -nbCases : nbCases;
            String direction = deltaX > 0 ? "droite" : "gauche";
            dernierEstLigne = true;
            dernierIndex = ligne;
            dernierDecalage = decalage;
            Log.d(TAG, "Glissement horizontal detecte ligne=" + ligne
                    + " direction=" + direction + " nbCases=" + nbCases
                    + " decalage=" + decalage);
            return moteur.jouerCoupAnime(true, ligne, decalage);
        }

        int nbCases = calculerNbCases(Math.abs(deltaY), hauteurCase);
        int decalage = deltaY > 0 ? nbCases : -nbCases;
        String direction = deltaY > 0 ? "bas" : "haut";
        dernierEstLigne = false;
        dernierIndex = colonne;
        dernierDecalage = decalage;
        Log.d(TAG, "Glissement vertical detecte colonne=" + colonne
                + " direction=" + direction + " nbCases=" + nbCases
                + " decalage=" + decalage);
        return moteur.jouerCoupAnime(false, colonne, decalage);
    }

    private int calculerNbCases(float distance, int tailleCase) {
        int nbCases;

        if (tailleCase <= 0) {
            return 1;
        }

        nbCases = Math.round(distance / (float) tailleCase);

        if (nbCases < 1) {
            nbCases = 1;
        }

        if (nbCases >= MoteurJeu.TAILLE_GRILLE) {
            nbCases = MoteurJeu.TAILLE_GRILLE - 1;
        }

        return nbCases;
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

    public boolean getDernierEstLigne() {
        return dernierEstLigne;
    }

    public int getDernierIndex() {
        return dernierIndex;
    }

    public int getDernierDecalage() {
        return dernierDecalage;
    }
}
