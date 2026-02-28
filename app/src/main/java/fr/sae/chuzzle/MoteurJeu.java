package fr.sae.chuzzle;

public class MoteurJeu {
    private Grille grille;
   // private ScoreManager scoreManager;
    // private VerrouManager verrouManager;
    private Generateur generateur;
    private int score;
    private int nbCoup;
    private boolean finie;
    long int seed;


    public void jouerCoup(boolean estLigne,int index,int decalage){
        if (finie) return;

        Case[] sauv;


        if (estLigne) {
            sauv = grille.glisserLigne(index, decalage);
        } else {
            sauv = grille.glisserColonne(index, decalage);
        }

        if (sauv == null) {
            return;
        }

        List<Serie> series = grille.detecterSeries();

        if (series.isEmpty()) {

            Case[][] cases = grille.getCases();

            if (estLigne) {
                for (int j = 0; j < sauv.length; j++) {
                    cases[index][j] = sauv[j];
                }
            } else {
                for (int i = 0; i < sauv.length; i++) {
                    cases[i][index] = sauv[i];
                }
            }

            return;
        }


        this.nbCoup++;

        if (!grille.existeCoupPossible()) {
            finie = true;
        }
    }

    public boolean estFinie(){
        return this.finie;
    }

    public int getScore(){
        return this.score;
    }

    public int getNbCoup(){
        return this.nbCoup;
    }


    public Grille getGrille(){
        return this.grille;

    }

}
