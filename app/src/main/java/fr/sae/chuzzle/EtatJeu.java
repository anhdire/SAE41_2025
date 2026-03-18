package fr.sae.chuzzle;

public class EtatJeu {

    private final TypeElement[][] types;
    private final int score;
    private final int nbCoups;
    private final boolean finie;

    public EtatJeu(Case[][] cases, int score, int nbCoups, boolean finie) {
        int taille = cases.length;
        this.types = new TypeElement[taille][taille];

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                this.types[i][j] = cases[i][j].getType();
            }
        }

        this.score = score;
        this.nbCoups = nbCoups;
        this.finie = finie;
    }

    public TypeElement[][] getTypes() {
        return types;
    }

    public int getScore() {
        return score;
    }

    public int getNbCoups() {
        return nbCoups;
    }

    public boolean estFinie() {
        return finie;
    }
}
