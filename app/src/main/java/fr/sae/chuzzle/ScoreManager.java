package fr.sae.chuzzle;

import java.util.List;

public class ScoreManager {
    public int calculerScore(List<Serie> serie) {


        int totalBase = 0;

        for (Serie s : serie) {
            int longueur = s.longueur();
            int points = 0;

            if (longueur == 3) {
                points = 8;
            } else if (longueur == 4) {
                points = 16;
            } else if (longueur == 5) {
                points = 32;
            } else if (longueur >= 6) {
                points = 64;
            }

            totalBase += points;
        }
        //bonnus sera egale a 0 si il y a une seul serie
        double bonus = (serie.size() - 1) * 0.5;
        //multiplication des bonnus pour arriver au resultat final
        double scoreFinal = totalBase * (1 + bonus);

        return (int) scoreFinal;
    }
}
