package fr.sae.chuzzle;

import java.util.List;

public class MoteurJeu {

    public static final int TAILLE_GRILLE = 6;

    private Grille grille;
    private GestionScore scoreManager;
    private GestionVerrou verrouManager;
    private Generateur generateur;
    private int score;
    private int nbCoup;
    private boolean finie;
    long seed;

    public MoteurJeu(long seed) {
        this.seed = seed;

        this.generateur = new Generateur(seed);
        this.grille = new Grille(TAILLE_GRILLE, seed);
        this.scoreManager = new GestionScore();
        this.verrouManager = new GestionVerrou(seed);
        this.score = 0;
        this.nbCoup = 0;
        this.finie = false;

        grille.initialiser();
        mettreAJourFinPartie();
    }

    public void jouerCoup(boolean estLigne, int index, int decalage) {
        if (finie) {
            return;
        }

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
        int niveau = 0;

        while (!series.isEmpty()) {
            int points = scoreManager.calculerScore(series, niveau);
            score = score + points;
            grille.supprimerSeries(series);
            grille.appliquerGravite();
            grille.remplir();
            niveau++;
            series = grille.detecterSeries();
        }

        verrouManager.ajouterVerrouSiNecessaire(grille, nbCoup);

        mettreAJourFinPartie();
    }

    public void setSeed(long seed) {
        this.seed = seed;
        this.generateur = new Generateur(seed);
        this.grille = new Grille(6, seed);
        this.verrouManager = new GestionVerrou(seed);
        this.score = 0;
        this.nbCoup = 0;
        this.finie = false;
        this.grille.initialiser();
        mettreAJourFinPartie();
    }

    public boolean estFinie() {
        return this.finie;
    }

    public int getScore() {
        return this.score;
    }

    public int getNbCoup() {
        return this.nbCoup;
    }

    public Grille getGrille() {
        return this.grille;
    }

    public long getSeed() {
        return this.seed;
    }

    void mettreAJourFinPartie() {
        this.finie = !grille.existeCoupPossible();
    }
}
