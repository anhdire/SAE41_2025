package fr.sae.chuzzle;
import java.util.ArrayList;
import java.util.Arrays;

import kotlin.collections.ArrayDeque;

public class Grille {

    private Case[][] cases;
    private Generateur generateur;
    private List<Serie> liste;

    public void initialiser(){

    }

    public void glisserLigne(int index , int decalage){
        int taille = cases[index].length;

        // Verification si une case est vérouillé :

        for(int j = 0 ; j < taille ; j++){
            if(cases[index][j].isVerrouille()){
                return;
            }
        }


        int decalageMod = decalage % taille;
        int decalagePositif = decalageMod + taille;
        int dec = decalagePositif % taille;
        decalage = dec;

        // Sauvegarde de la ligne s'il n'y a pas de séries :
        Case[] temp = Arrays.copyOf(cases[index], cases[index].length);

        // Creation de la nouvelle ligne décalé :
        Case[] ligneDecale = new Case[taille];
        for(int j = 0 ; j < taille ; j++){
            int n = (j + decalage) % taille;
            ligneDecale[n] = cases[index][j];
        }
        // Mise à jour de la ligne en question
        cases[index] = ligneDecale;

        // Gerer le cas ou il n'y a pas de serie , autrement dit si la liste est vide :

        if(this.liste.isEmpty()){
            cases[index] = temp; // aucune mise à jour
        }

    }

    public void glisserColonne(int index , int decalage){

    }

    public List<Serie> detecterSeries(){
        this.liste = new ArrayList<>();


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
