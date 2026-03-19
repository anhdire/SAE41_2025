package fr.sae.chuzzle;

public class ControleJeu {
    private final MoteurJeu moteur;

    public ControleJeu(long seed) {
        moteur = new MoteurJeu(seed);
    }

    public void jouerGlissement(int ligne, int colonne, float deltaX, float deltaY,
                                int largeurCase, int hauteurCase) {
        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            int nbCases = calculerNbCases(Math.abs(deltaX), largeurCase);
            int decalage = deltaX > 0 ? -nbCases : nbCases;
            moteur.jouerCoup(true, ligne, decalage);
            return;
        }

        int nbCases = calculerNbCases(Math.abs(deltaY), hauteurCase);
        int decalage = deltaY > 0 ? nbCases : -nbCases;
        moteur.jouerCoup(false, colonne, decalage);
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
}
