package fr.sae.chuzzle;

import android.util.Log;

public class ControleJeu {
    private static final String TAG = "ControleJeu";
    private final MoteurJeu moteur;

    public ControleJeu(long seed) {
        moteur = new MoteurJeu(seed);
    }

    public void jouerGlissement(int ligne, int colonne, float deltaX, float deltaY,
                                int largeurCase, int hauteurCase) {
        int decalageNormalise;

        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            int nbCases = calculerNbCases(Math.abs(deltaX), largeurCase);
            int decalage = deltaX > 0 ? -nbCases : nbCases;
            int colonneArrivee;

            decalageNormalise = normaliserDecalage(decalage);
            colonneArrivee = normaliserIndex(colonne - decalageNormalise);
            Log.d(TAG, "GLISSEMENT_LIGNE"
                    + " depart=(" + ligne + "," + colonne + ")"
                    + " arrivee=(" + ligne + "," + colonneArrivee + ")"
                    + " deltaX=" + deltaX
                    + " nbCases=" + nbCases
                    + " decalage=" + decalage);
            moteur.jouerCoupAvecTrace(true, ligne, decalage, ligne, colonne, ligne, colonneArrivee);
            return;
        }

        int nbCases = calculerNbCases(Math.abs(deltaY), hauteurCase);
        int decalage = deltaY > 0 ? nbCases : -nbCases;
        int ligneArrivee;

        decalageNormalise = normaliserDecalage(decalage);
        ligneArrivee = normaliserIndex(ligne + decalageNormalise);
        Log.d(TAG, "GLISSEMENT_COLONNE"
                + " depart=(" + ligne + "," + colonne + ")"
                + " arrivee=(" + ligneArrivee + "," + colonne + ")"
                + " deltaY=" + deltaY
                + " nbCases=" + nbCases
                + " decalage=" + decalage);
        moteur.jouerCoupAvecTrace(false, colonne, decalage, ligne, colonne, ligneArrivee, colonne);
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

    private int normaliserDecalage(int decalage) {
        return ((decalage % MoteurJeu.TAILLE_GRILLE) + MoteurJeu.TAILLE_GRILLE) % MoteurJeu.TAILLE_GRILLE;
    }

    private int normaliserIndex(int index) {
        return ((index % MoteurJeu.TAILLE_GRILLE) + MoteurJeu.TAILLE_GRILLE) % MoteurJeu.TAILLE_GRILLE;
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
