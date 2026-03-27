package fr.sae.chuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grille {

    private final Case[][] cases;
    private final Generateur generateur;

    public Grille(int taille, long seed) {
        this.cases = new Case[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                // Une case vide est representee par un type nul.
                cases[i][j] = new Case(null);
            }
        }
        this.generateur = new Generateur(seed);
    }

    public void initialiser() {
        for (int ligne = 0; ligne < cases.length; ligne++) {
            for (int colonne = 0; colonne < cases[ligne].length; colonne++) {
                TypeElement type;
                boolean invalide;

                do {
                    type = generateur.typeAleatoire();
                    invalide = false;

                    if (colonne >= 2) {
                        TypeElement typeGauche = cases[ligne][colonne - 1].getType();
                        TypeElement typeEncoreGauche = cases[ligne][colonne - 2].getType();
                        if (typeGauche == type && typeEncoreGauche == type) {
                            invalide = true;
                        }
                    }

                    if (ligne >= 2) {
                        TypeElement typeHaut = cases[ligne - 1][colonne].getType();
                        TypeElement typeEncoreHaut = cases[ligne - 2][colonne].getType();
                        if (typeHaut == type && typeEncoreHaut == type) {
                            invalide = true;
                        }
                    }
                } while (invalide);

                cases[ligne][colonne] = new Case(type);
            }
        }
    }

    public Case[] glisserLigne(int index, int decalage) {
        int taille = cases[index].length;
        int decalageNormalise;
        Case[] sauvegarde;

        if (!ligneEstGlissable(cases, index)) {
            return null;
        }

        decalageNormalise = normaliserDecalage(decalage, taille);
        sauvegarde = Arrays.copyOf(cases[index], cases[index].length);

        if (decalageNormalise == 0) {
            return sauvegarde;
        }

        appliquerGlissementLigne(cases, index, decalageNormalise);
        return sauvegarde;
    }

    public Case[] glisserColonne(int index, int decalage) {
        int taille = cases.length;
        int decalageNormalise;
        Case[] sauvegarde;

        if (!colonneEstGlissable(cases, index)) {
            return null;
        }

        decalageNormalise = normaliserDecalage(decalage, taille);
        sauvegarde = new Case[taille];
        for (int i = 0; i < taille; i++) {
            sauvegarde[i] = cases[i][index];
        }

        if (decalageNormalise == 0) {
            return sauvegarde;
        }

        appliquerGlissementColonne(cases, index, decalageNormalise);
        return sauvegarde;
    }

    public List<Serie> detecterSeries() {
        return detecterSeries(cases);
    }

    private List<Serie> detecterSeries(Case[][] plateau) {
        ArrayList<Serie> liste = new ArrayList<>();
        detecterSeriesLignes(plateau, liste);
        detecterSeriesColonnes(plateau, liste);

        return liste;
    }

    public void supprimerSeries(List<Serie> series) {
        for (Serie serie : series) {
            for (Position pos : serie.getPositions()) {
                int ligne = pos.getLigne();
                int colonne = pos.getColonne();
                cases[ligne][colonne].setType(null);
                cases[ligne][colonne].deverrouiller();
            }
        }
    }

    public void appliquerGravite() {
        int taille = cases.length;
        for (int col = 0; col < taille; col++) {
            int ligneVide = taille - 1;
            for (int ligne = taille - 1; ligne >= 0; ligne--) {
                if (cases[ligne][col].getType() != null) {
                    cases[ligneVide][col].setType(cases[ligne][col].getType());
                    ligneVide--;
                }
            }

            for (int ligne = ligneVide; ligne >= 0; ligne--) {
                cases[ligne][col].setType(null);
            }
        }
    }

    public void remplir() {
        for (int col = 0; col < cases.length; col++) {
            for (int ligne = 0; ligne < cases.length; ligne++) {
                if (cases[ligne][col].getType() == null) {
                    cases[ligne][col].setType(generateur.typeAleatoire());
                }
            }
        }
    }

    public boolean estStable() {
        return detecterSeries().isEmpty();
    }

    public boolean estVide() {
        for (Case[] ligne : cases) {
            for (Case caseCourante : ligne) {
                if (caseCourante.getType() != null) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean existeCoupPossible() {
        int taille = cases.length;

        for (int index = 0; index < taille; index++) {
            if (ligneEstGlissable(cases, index) && existeSerieApresGlissementLigne(index)) {
                return true;
            }

            if (colonneEstGlissable(cases, index) && existeSerieApresGlissementColonne(index)) {
                return true;
            }
        }

        return false;
    }

    public String getAide() {
        int index;

        for (index = 0; index < cases.length; index++) {
            String aideLigne;
            String aideColonne;

            if (ligneEstGlissable(cases, index)) {
                aideLigne = getAideLigne(index);
                if (aideLigne != null) {
                    return aideLigne;
                }
            }

            if (colonneEstGlissable(cases, index)) {
                aideColonne = getAideColonne(index);
                if (aideColonne != null) {
                    return aideColonne;
                }
            }
        }

        return "Aucun coup";
    }

    public Case[][] getCases() {
        return this.cases;
    }

    public String versTexte() {
        StringBuilder texte = new StringBuilder();

        for (int ligne = 0; ligne < cases.length; ligne++) {
            if (ligne > 0) {
                texte.append(";");
            }

            for (int colonne = 0; colonne < cases[ligne].length; colonne++) {
                String verrou;

                if (colonne > 0) {
                    texte.append(",");
                }

                if (cases[ligne][colonne].isVerrouille()) {
                    verrou = "1";
                } else {
                    verrou = "0";
                }

                if (cases[ligne][colonne].getType() == null) {
                    texte.append("VIDE");
                } else {
                    texte.append(cases[ligne][colonne].getType().name());
                }

                texte.append(":")
                        .append(verrou);
            }
        }

        return texte.toString();
    }

    public void chargerDepuisTexte(String texte) {
        String[] lignes;

        if (texte == null || texte.trim().isEmpty()) {
            throw new IllegalArgumentException("Grille vide");
        }

        lignes = texte.split(";");
        if (lignes.length != cases.length) {
            throw new IllegalArgumentException("Nombre de lignes invalide");
        }

        for (int ligne = 0; ligne < lignes.length; ligne++) {
            String[] colonnes = lignes[ligne].split(",");

            if (colonnes.length != cases[ligne].length) {
                throw new IllegalArgumentException("Nombre de colonnes invalide");
            }

            for (int colonne = 0; colonne < colonnes.length; colonne++) {
                chargerCaseDepuisTexte(colonnes[colonne], ligne, colonne);
            }
        }
    }

    private boolean existeSerieApresGlissementLigne(int index) {
        for (int decalage = 1; decalage < cases.length; decalage++) {
            Case[][] copie = copierCases(cases);
            appliquerGlissementLigne(copie, index, decalage);
            if (!detecterSeries(copie).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    private boolean existeSerieApresGlissementColonne(int index) {
        for (int decalage = 1; decalage < cases.length; decalage++) {
            Case[][] copie = copierCases(cases);
            appliquerGlissementColonne(copie, index, decalage);
            if (!detecterSeries(copie).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    private String getAideLigne(int index) {
        int decalage;

        for (decalage = 1; decalage < cases.length; decalage++) {
            Case[][] copie = copierCases(cases);
            appliquerGlissementLigne(copie, index, decalage);

            if (!detecterSeries(copie).isEmpty()) {
                return formaterCoordonnee(index, 0)
                        + " vers "
                        + formaterCoordonnee(index, decalage);
            }
        }

        return null;
    }

    private String getAideColonne(int index) {
        int decalage;

        for (decalage = 1; decalage < cases.length; decalage++) {
            Case[][] copie = copierCases(cases);
            appliquerGlissementColonne(copie, index, decalage);

            if (!detecterSeries(copie).isEmpty()) {
                return formaterCoordonnee(0, index) + " vers " + formaterCoordonnee(decalage, index);
            }
        }

        return null;
    }

    private String formaterCoordonnee(int ligne, int colonne) {
        String[] lettres = {"A", "B", "C", "D", "E", "F"};

        return lettres[colonne] + (ligne + 1);
    }

    private Case[][] copierCases(Case[][] source) {
        int taille = source.length;
        Case[][] copie = new Case[taille][taille];

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                copie[i][j] = new Case(source[i][j].getType());
                if (source[i][j].isVerrouille()) {
                    copie[i][j].verrouiller();
                }
            }
        }

        return copie;
    }

    private void appliquerGlissementLigne(Case[][] plateau, int index, int decalage) {
        int taille = plateau[index].length;
        int dec = normaliserDecalage(decalage, taille);
        Case[] nouvelleLigne = new Case[taille];

        for (int j = 0; j < taille; j++) {
            int destination = (j - dec + taille) % taille;
            nouvelleLigne[destination] = plateau[index][j];
        }

        plateau[index] = nouvelleLigne;
    }

    private void appliquerGlissementColonne(Case[][] plateau, int index, int decalage) {
        int taille = plateau.length;
        int dec = normaliserDecalage(decalage, taille);
        Case[] nouvelleColonne = new Case[taille];

        for (int i = 0; i < taille; i++) {
            int destination = (i + dec) % taille;
            nouvelleColonne[destination] = plateau[i][index];
        }

        for (int i = 0; i < taille; i++) {
            plateau[i][index] = nouvelleColonne[i];
        }
    }

    private boolean ligneEstGlissable(Case[][] plateau, int index) {
        for (Case caseCourante : plateau[index]) {
            if (caseCourante.isVerrouille()) {
                return false;
            }
        }

        return true;
    }

    private boolean colonneEstGlissable(Case[][] plateau, int index) {
        for (Case[] ligne : plateau) {
            if (ligne[index].isVerrouille()) {
                return false;
            }
        }

        return true;
    }

    private int normaliserDecalage(int decalage, int taille) {
        return ((decalage % taille) + taille) % taille;
    }

    private void chargerCaseDepuisTexte(String texteCase, int ligne, int colonne) {
        String[] morceaux = texteCase.split(":");
        TypeElement type;

        if (morceaux.length != 2) {
            throw new IllegalArgumentException("Case invalide");
        }

        if ("VIDE".equals(morceaux[0])) {
            type = null;
        } else {
            type = TypeElement.valueOf(morceaux[0]);
        }

        cases[ligne][colonne].setType(type);

        if ("1".equals(morceaux[1])) {
            cases[ligne][colonne].verrouiller();
        } else {
            cases[ligne][colonne].deverrouiller();
        }
    }

    private void detecterSeriesLignes(Case[][] plateau, List<Serie> liste) {
        int taille = plateau.length;

        for (int ligne = 0; ligne < taille; ligne++) {
            detecterSerieLigne(plateau, liste, taille, ligne);
        }
    }

    private void detecterSerieLigne(Case[][] plateau, List<Serie> liste,
                                    int taille, int ligne) {
        int compteur = 1;

        for (int colonne = 1; colonne < taille; colonne++) {
            TypeElement typeCourant = plateau[ligne][colonne].getType();
            TypeElement typePrecedent = plateau[ligne][colonne - 1].getType();

            if (typeCourant != null && typeCourant == typePrecedent) {
                compteur++;
            } else {
                ajouterSerieLigne(liste, ligne, colonne - 1, compteur);
                compteur = 1;
            }
        }

        ajouterSerieLigne(liste, ligne, taille - 1, compteur);
    }

    private void detecterSeriesColonnes(Case[][] plateau, List<Serie> liste) {
        int taille = plateau.length;

        for (int colonne = 0; colonne < taille; colonne++) {
            detecterSerieColonne(plateau, liste, taille, colonne);
        }
    }

    private void detecterSerieColonne(Case[][] plateau, List<Serie> liste,
                                      int taille, int colonne) {
        int compteur = 1;

        for (int ligne = 1; ligne < taille; ligne++) {
            TypeElement typeCourant = plateau[ligne][colonne].getType();
            TypeElement typePrecedent = plateau[ligne - 1][colonne].getType();

            if (typeCourant != null && typeCourant == typePrecedent) {
                compteur++;
            } else {
                ajouterSerieColonne(liste, ligne - 1, colonne, compteur);
                compteur = 1;
            }
        }

        ajouterSerieColonne(liste, taille - 1, colonne, compteur);
    }

    private void ajouterSerieLigne(List<Serie> liste, int ligne,
                                   int colonneFin, int compteur) {
        Serie serie;

        if (compteur < 3) {
            return;
        }

        serie = new Serie();
        for (int decalage = 0; decalage < compteur; decalage++) {
            serie.addPosition(new Position(ligne, colonneFin - decalage));
        }
        liste.add(serie);
    }

    private void ajouterSerieColonne(List<Serie> liste, int ligneFin,
                                     int colonne, int compteur) {
        Serie serie;

        if (compteur < 3) {
            return;
        }

        serie = new Serie();
        for (int decalage = 0; decalage < compteur; decalage++) {
            serie.addPosition(new Position(ligneFin - decalage, colonne));
        }
        liste.add(serie);
    }
}
