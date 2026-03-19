package fr.sae.chuzzle;

import android.util.Log;

import java.util.List;

public class MoteurJeu {
    private static final String TAG = "MoteurJeu";
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
        jouerCoupAvecTrace(estLigne, index, decalage, -1, -1, -1, -1);
    }

    public void jouerCoupAvecTrace(boolean estLigne, int index, int decalage,
                                   int ligneDepart, int colonneDepart,
                                   int ligneArrivee, int colonneArrivee) {
        Case casePrise;

        if (finie) {
            Log.d(TAG, "COUP_REFUSE partie deja finie");
            return;
        }

        casePrise = getCaseSiValide(ligneDepart, colonneDepart);
        if (casePrise != null) {
            Log.d(TAG, "CASE_PRISE depart=(" + ligneDepart + "," + colonneDepart + ")"
                    + " type=" + decrireCase(casePrise));
        }

        Case[] sauv;

        if (estLigne) {
            sauv = grille.glisserLigne(index, decalage);
        } else {
            sauv = grille.glisserColonne(index, decalage);
        }

        if (sauv == null) {
            Log.d(TAG, "COUP_REFUSE glissement impossible ou verrou sur la ligne/colonne");
            return;
        }

        List<Serie> series = grille.detecterSeries();

        if (positionValide(ligneArrivee, colonneArrivee)) {
            Log.d(TAG, "CASE_APRES_GLISSEMENT arrivee=(" + ligneArrivee + "," + colonneArrivee + ")"
                    + " type=" + decrireCase(grille.getCases()[ligneArrivee][colonneArrivee]));
            Log.d(TAG, "VOISINAGE_ARRIVEE\n" + decrireVoisinage(ligneArrivee, colonneArrivee));
        }

        if (series.isEmpty()) {
            Log.d(TAG, "SERIE_CREEE=false");
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

            Log.d(TAG, "COUP_ANNULE retour a l'etat precedent");
            return;
        }

        Log.d(TAG, "SERIE_CREEE=true nbSeries=" + series.size());
        Log.d(TAG, "DETAIL_SERIES\n" + decrireSeries(series));

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

    private boolean positionValide(int ligne, int colonne) {
        return ligne >= 0
                && colonne >= 0
                && ligne < TAILLE_GRILLE
                && colonne < TAILLE_GRILLE;
    }

    private Case getCaseSiValide(int ligne, int colonne) {
        if (!positionValide(ligne, colonne)) {
            return null;
        }

        return grille.getCases()[ligne][colonne];
    }

    private String decrireCase(Case caseCourante) {
        if (caseCourante == null) {
            return "null";
        }

        return caseCourante.getType().name() + " verrou=" + caseCourante.isVerrouille();
    }

    private String decrireVoisinage(int ligneCentre, int colonneCentre) {
        StringBuilder texte = new StringBuilder();
        Case[][] cases = grille.getCases();

        for (int ligne = ligneCentre - 1; ligne <= ligneCentre + 1; ligne++) {
            for (int colonne = colonneCentre - 1; colonne <= colonneCentre + 1; colonne++) {
                if (!positionValide(ligne, colonne)) {
                    continue;
                }

                texte.append("(")
                        .append(ligne)
                        .append(",")
                        .append(colonne)
                        .append(")=")
                        .append(cases[ligne][colonne].getType().name())
                        .append(" verrou=")
                        .append(cases[ligne][colonne].isVerrouille())
                        .append("\n");
            }
        }

        return texte.toString();
    }

    private String decrireSeries(List<Serie> series) {
        StringBuilder texte = new StringBuilder();
        Case[][] cases = grille.getCases();
        int indexSerie = 1;

        for (Serie serie : series) {
            texte.append("serie ")
                    .append(indexSerie)
                    .append(" : ");

            for (Position position : serie.getPositions()) {
                texte.append("(")
                        .append(position.getLigne())
                        .append(",")
                        .append(position.getColonne())
                        .append(")=")
                        .append(cases[position.getLigne()][position.getColonne()].getType().name())
                        .append(" ");
            }

            texte.append("\n");
            indexSerie++;
        }

        return texte.toString();
    }
}
