package fr.sae.chuzzle;
import java.util.ArrayList;
import java.util.Arrays;

import kotlin.collections.ArrayDeque;

public class Grille {

    private Case[][] cases;
    private Generateur generateur;

    public Grille(int taille, long seed) {
        this.cases = new Case[taille][taille];
        this.generateur = new Generateur(seed);
    }

    public void initialiser() {
        for (int ligne = 0; ligne < cases.length; ligne++) {
            for (int colonne = 0; colonne < cases[ligne].length; colonne++) {

                TypeElement type;
                boolean invalide;
                /* tant que une ligne ou colone a plus de 2 cases de meme type 
                je regénère un type pour la case */
                do {
                    type = generateur.typeAleatoire();
                    invalide = false;

                    // je verifie horizontale : éviter 3 identiques
                    if (colonne >= 2) {
                        TypeElement t1 = cases[ligne][colonne - 1].getType();
                        TypeElement t2 = cases[ligne][colonne - 2].getType();
                        if (t1 == type && t2 == type) {
                            invalide = true;
                        }
                    }

                    // je fais pareil pour la verticale :
                    if (ligne >= 2) {
                        TypeElement t1 = cases[ligne - 1][colonne].getType();
                        TypeElement t2 = cases[ligne - 2][colonne].getType();
                        if (t1 == type && t2 == type) {
                            invalide = true;
                        }
                    }

                } while (invalide);

                cases[ligne][colonne] = new Case(type);
            }
        }
    }

    public Case[] glisserLigne(int index , int decalage){
        int taille = cases[index].length;

        // Verification si une case est vérouillé :
        for(int j = 0 ; j < taille ; j++){
            if(cases[index][j].isVerrouille()){
                return null; 
            }
        }

        int dec = ((decalage % taille) + taille) % taille;

        // Sauvegarde de la ligne s'il n'y a pas de séries :
        Case[] temp = Arrays.copyOf(cases[index], cases[index].length);

        if(dec == 0){
            return temp;
        }
        
        // Creation de la nouvelle ligne décalé :
        Case[] nouvelleLigne = new Case[taille];
        for(int j = 0 ; j < taille ; j++){
            int n = (j - dec + taille) % taille; // - car vers la gauche
            nouvelleLigne[n] = cases[index][j];
        }
        
        // Mise à jour de la ligne en question
        cases[index] = nouvelleLigne;
        
        // pour la verifiaction si ya une serie (supprimer) le faire dans le moteur de jeu
        return temp;
    }

    public Case[] glisserColonne(int index , int decalage){
        
        int length = cases.length;

        // je veifie si une case est vérouillé :
        for(int i = 0 ; i < length ; i++){
            if(cases[i][index].isVerrouille()){
                return null; 
            }
        }


        // je normalise le decalage et renvoie une copie
        int dec = ((decalage % length) + length) % length;
        Case[] temp = new Case[length];
        for (int i = 0; i < length; i++) {
            temp[i] = cases[i][index];
        }
        if(dec == 0){
            return temp;
        }
                

        // je cree la nouvelle colonne décalé :
        Case[] nouvelleColonne = new Case[length];

        for (int i = 0; i < length; i++) {
            nouvelleColonne[(i + dec) % length] = cases[i][index]; // + car vers le bas 
        }

        // mis à a jour de la vraie colonne
        for (int i = 0; i < length; i++) {
            cases[i][index] = nouvelleColonne[i];
        }

        return nouvelleColonne;
    }

    public List<Serie> detecterSeries(){
        ArrayList<Serie> liste = new ArrayList<>();

        int length = cases.length;

        // Detection des séries horizontales
        for (int i = 0; i < length; i++) {
            int compteur = 1;

            for (int j = 1; j < length; j++) {
                TypeElement caseCourant = cases[i][j].getType();
                TypeElement casePrecedent = cases[i][j - 1].getType();

                if (caseCourant == casePrecedent) {
                    compteur++;
                } else {
                   if(compteur >= 3){
                        Serie serie =new Serie();
                        for(int k = 0; k < compteur ; k++){
                            serie.addPosition(new Position(i, j - 1 - k));
                        }
                        liste.add(serie);
                    }
                    compteur = 1;
                }
            }
        }

        // je veriife aussi la fin de ligne
        if (compteur >= 3) {
            Serie serie = new Serie();
            for (int k = 0; k < compteur; k++) {
                serie.addPosition(new Position(i, length - 1 - k));
            }
            liste.add(serie);
        }

        // Détection des séries verticales
        for(int j = 0 ; j < length ; j++){
            int compteur = 1;

            for(int i = 1 ; i < length ; i++){
                TypeElement caseCourant = cases[i][j].getType();
                TypeElement casePrecedent = cases[i - 1][j].getType();

                if(caseCourant == casePrecedent){
                    compteur++;
                }else{
                    if(compteur >= 3){
                        Serie serie = new Serie();
                        for(int k = 0 ; k < compteur ; k++){
                            serie.addPosition(new Position(i - 1 - k, j));
                        }
                        liste.add(serie);
                    }
                    compteur = 1;
                }
            }

            // je veriife aussi la fin de colonne
            if (compteur >= 3) {
                Serie serie = new Serie();
                for (int k = 0; k < compteur; k++) {
                    serie.addPosition(new Position(length - 1 - k, j));
                }
                liste.add(serie);
            }
        }

        return liste;
    }

    public void supprimerSeries(List<Serie> series){

    }

    public void appliquerGravite(){

    }

    public void remplir(){

    }

    public boolean estStable(){
        return true;
    }

    public boolean existeCoupPossible(){
        return true;
    }
}
