package fr.sae.chuzzle;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class MoteurJeu {
    public static final int TAILLE_GRILLE = 6;
    private static final String NOM_SAUVEGARDE = "sauvegarde_partie";
    private static final String CLE_ACTIVE = "partie_active";
    private static final String CLE_GRILLE = "grille";
    private static final String CLE_SCORE = "score";
    private static final String CLE_NB_COUPS = "nb_coups";
    private static final String CLE_FINIE = "finie";
    private static final String CLE_SEED = "seed";
    private static final String CLE_MODE_DUR = "mode_dur";

    private Grille grille;
    private GestionScore scoreManager;
    private GestionVerrou verrouManager;
    private Generateur generateur;
    private int score;
    private int nbCoup;
    private boolean finie;
    private boolean modeDur;
    long seed;

    public MoteurJeu(long seed, boolean modeDur) {
        this.seed = seed;
        this.modeDur = modeDur;

        this.generateur = new Generateur(seed);
        this.grille = new Grille(TAILLE_GRILLE, seed);
        this.scoreManager = new GestionScore();
        this.verrouManager = new GestionVerrou(seed, modeDur);
        this.score = 0;
        this.nbCoup = 0;
        this.finie = false;

        grille.initialiser();
        mettreAJourFinPartie();
    }

    public void jouerCoup(boolean estLigne, int index, int decalage) {
        Case[] sauvegarde;
        List<Serie> series;

        if (finie) {
            return;
        }

        sauvegarde = glisserCases(estLigne, index, decalage);
        if (sauvegarde == null) {
            return;
        }

        // Un coup ne compte que s'il cree au moins une serie.
        series = grille.detecterSeries();
        if (series.isEmpty()) {
            restaurerCases(estLigne, index, sauvegarde);
            return;
        }

        this.nbCoup++;
        resoudreSeries(series);
        verrouManager.ajouterVerrouSiNecessaire(grille, nbCoup);
        mettreAJourFinPartie();
    }

    public void setSeed(long seed) {
        this.seed = seed;
        this.generateur = new Generateur(seed);
        this.grille = new Grille(6, seed);
        this.verrouManager = new GestionVerrou(seed, modeDur);
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

    public boolean isModeDur() {
        return modeDur;
    }

    public void sauvegarder(Context contexte) {
        SharedPreferences preferences = getPreferencesSauvegarde(contexte);

        preferences.edit()
                .putBoolean(CLE_ACTIVE, true)
                .putString(CLE_GRILLE, grille.versTexte())
                .putInt(CLE_SCORE, score)
                .putInt(CLE_NB_COUPS, nbCoup)
                .putBoolean(CLE_FINIE, finie)
                .putLong(CLE_SEED, seed)
                .putBoolean(CLE_MODE_DUR, modeDur)
                .apply();
    }

    public boolean restaurer(Context contexte) {
        SharedPreferences preferences = getPreferencesSauvegarde(contexte);
        String texteGrille = preferences.getString(CLE_GRILLE, "");

        if (!preferences.getBoolean(CLE_ACTIVE, false) || texteGrille == null || texteGrille.isEmpty()) {
            return false;
        }

        try {
            seed = preferences.getLong(CLE_SEED, seed);
            modeDur = preferences.getBoolean(CLE_MODE_DUR, modeDur);
            generateur = new Generateur(seed);
            grille = new Grille(TAILLE_GRILLE, seed);
            scoreManager = new GestionScore();
            verrouManager = new GestionVerrou(seed, modeDur);
            score = preferences.getInt(CLE_SCORE, 0);
            nbCoup = preferences.getInt(CLE_NB_COUPS, 0);
            finie = preferences.getBoolean(CLE_FINIE, false);
            grille.chargerDepuisTexte(texteGrille);

            if (grille.estVide()) {
                throw new IllegalArgumentException("Grille sauvegardee vide");
            }

            return true;
        } catch (IllegalArgumentException e) {
            effacerSauvegarde(contexte);
            return false;
        }
    }

    public static boolean aUneSauvegarde(Context contexte) {
        SharedPreferences preferences = getPreferencesSauvegarde(contexte);
        String texteGrille = preferences.getString(CLE_GRILLE, "");

        return preferences.getBoolean(CLE_ACTIVE, false)
                && texteGrille != null
                && !texteGrille.isEmpty();
    }

    public static void effacerSauvegarde(Context contexte) {
        getPreferencesSauvegarde(contexte)
                .edit()
                .clear()
                .apply();
    }

    void mettreAJourFinPartie() {
        this.finie = !grille.existeCoupPossible();
    }

    private Case[] glisserCases(boolean estLigne, int index, int decalage) {
        if (estLigne) {
            return grille.glisserLigne(index, decalage);
        }

        return grille.glisserColonne(index, decalage);
    }

    private void restaurerCases(boolean estLigne, int index, Case[] sauvegarde) {
        Case[][] cases = grille.getCases();
        int position;

        if (estLigne) {
            for (position = 0; position < sauvegarde.length; position++) {
                cases[index][position] = sauvegarde[position];
            }
            return;
        }

        for (position = 0; position < sauvegarde.length; position++) {
            cases[position][index] = sauvegarde[position];
        }
    }

    private void resoudreSeries(List<Serie> series) {
        int niveauCascade = 0;

        while (!series.isEmpty()) {
            int points = scoreManager.calculerScore(series, niveauCascade);
            score = score + points;
            grille.supprimerSeries(series);
            grille.appliquerGravite();
            grille.remplir();
            niveauCascade++;
            series = grille.detecterSeries();
        }
    }

    private static SharedPreferences getPreferencesSauvegarde(Context contexte) {
        return contexte.getSharedPreferences(NOM_SAUVEGARDE, Context.MODE_PRIVATE);
    }
}
