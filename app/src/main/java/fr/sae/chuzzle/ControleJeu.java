package fr.sae.chuzzle;

import android.content.Context;

public class ControleJeu {
    private final MoteurJeu moteur;

    public ControleJeu(long seed, boolean modeDur) {
        moteur = new MoteurJeu(seed, modeDur);
    }

    public void jouerGlissement(int ligne, int colonne, float deltaX, float deltaY,
                                int largeurCase, int hauteurCase) {
        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            jouerGlissementLigne(ligne, deltaX, largeurCase);
            return;
        }

        jouerGlissementColonne(colonne, deltaY, hauteurCase);
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

    private void jouerGlissementLigne(int ligne, float deltaX, int largeurCase) {
        int nbCases = calculerNbCases(Math.abs(deltaX), largeurCase);
        int decalage;

        if (deltaX > 0) {
            decalage = -nbCases;
        } else {
            decalage = nbCases;
        }

        moteur.jouerCoup(true, ligne, decalage);
    }

    private void jouerGlissementColonne(int colonne, float deltaY, int hauteurCase) {
        int nbCases = calculerNbCases(Math.abs(deltaY), hauteurCase);
        int decalage;

        if (deltaY > 0) {
            decalage = nbCases;
        } else {
            decalage = -nbCases;
        }

        moteur.jouerCoup(false, colonne, decalage);
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

    public String getAide() {
        return moteur.getGrille().getAide();
    }

    public void sauvegarder(Context contexte) {
        moteur.sauvegarder(contexte);
    }

    public boolean restaurer(Context contexte) {
        return moteur.restaurer(contexte);
    }

    public long getSeed() {
        return moteur.getSeed();
    }

    public boolean isModeDur() {
        return moteur.isModeDur();
    }
}
