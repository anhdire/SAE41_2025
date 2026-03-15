package fr.sae.chuzzle;
import java.util.List;
public class MoteurJeu {
    private Grille grille;
    private ScoreManager scoreManager;
    // private VerrouManager verrouManager;
    private Generateur generateur;
    private int score;
    private int nbCoup;
    private boolean finie;
    long seed;


    public MoteurJeu(long seed){
        this.seed = seed;

        this.generateur = new Generateur(seed);
        this.grille = new Grille(6, seed);

        this.scoreManager = new ScoreManager();

        this.score = 0;
        this.nbCoup = 0;
        this.finie = false;

        grille.initialiser();
    }

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
        // niveau de cascade = 0
        //si le coup peut etre jouer alors on augmente le nombre de coups jouer

        int niveau = 0;

        // verification qu'il reste encore ou pas des séries :
        while(!series.isEmpty()){
            // calcul du score grace à la méthode CalculerScore dans la classe ScoreManager
            int points = scoreManager.calculerScore(series,niveau);
            score = score + points;
            // supression des series
            grille.supprimerSeries(series);
            // aplication de la gravité
            grille.appliquerGravite();
            // remplissage des cases vides :
            grille.remplir();
            // Niveau de cascade incremente :
            niveau++;

            // verifier si apres cela il ya encore des séries :
            series = grille.detecterSeries();
        }

        if (!grille.existeCoupPossible()) {
            finie = true;
        }
    }

    public void setSeed(long seed){
        this.seed = seed;
        this.generateur = new Generateur(seed);
        this.grille = new Grille(6, seed);
        this.grille.initialiser();
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
