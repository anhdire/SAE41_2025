package fr.sae.chuzzle;

import java.util.List;

public class ControleJeu {
    private final MoteurJeu moteur;

    public ControleJeu(long seed) {
        moteur = new MoteurJeu(seed);
    }

    public List<EtatJeu> jouerGlissement(int ligne, int colonne, float deltaX, float deltaY) {
        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            int decalage = deltaX > 0 ? -1 : 1;
            return moteur.jouerCoupAnime(true, ligne, decalage);
        }

        int decalage = deltaY > 0 ? 1 : -1;
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
