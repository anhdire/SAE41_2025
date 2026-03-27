package fr.sae.chuzzle;

import java.util.ArrayList;
import java.util.List;

public class GestionVerrou {

    private static final int CHANCE_MIN_NORMALE = 10;
    private static final int CHANCE_MAX_NORMALE = 60;
    private static final int AUGMENTATION_NORMALE = 2;
    private static final int MAX_VERROUS_NORMALE = 3;
    private static final int CHANCE_MIN_DURE = 20;
    private static final int CHANCE_MAX_DURE = 80;
    private static final int AUGMENTATION_DURE = 4;
    private static final int MAX_VERROUS_DURE = 4;

    private final Generateur generateur;
    private final boolean modeDur;

    public GestionVerrou(long seed, boolean modeDur) {
        this.generateur = new Generateur(seed + 1);
        this.modeDur = modeDur;
    }

    public void ajouterVerrouSiNecessaire(Grille grille, int nbCoups) {
        int chanceMin;
        int chanceMax;
        int augmentation;
        int maxVerrous;

        if (grille == null || nbCoups <= 0) {
            return;
        }

        if (modeDur) {
            chanceMin = CHANCE_MIN_DURE;
            chanceMax = CHANCE_MAX_DURE;
            augmentation = AUGMENTATION_DURE;
            maxVerrous = MAX_VERROUS_DURE;
        } else {
            chanceMin = CHANCE_MIN_NORMALE;
            chanceMax = CHANCE_MAX_NORMALE;
            augmentation = AUGMENTATION_NORMALE;
            maxVerrous = MAX_VERROUS_NORMALE;
        }

        if (compterVerrous(grille) >= maxVerrous) {
            return;
        }

        /* Pour chaque coup joue, on augmente la chance d'ajouter un verrou. */
        int chance = chanceMin + ((nbCoups - 1) * augmentation);
        chance = Math.max(chanceMin, Math.min(chanceMax, chance));

        if (generateur.entierAleatoire(100) >= chance) {
            return;
        }

        List<Case> candidates = new ArrayList<>();
        Case[][] cases = grille.getCases();

        for (Case[] ligne : cases) {
            for (Case caseCourante : ligne) {
                if (caseCourante.getType() != null && !caseCourante.isVerrouille()) {
                    candidates.add(caseCourante);
                }
            }
        }

        if (candidates.isEmpty()) {
            return;
        }

        int index = generateur.entierAleatoire(candidates.size());
        candidates.get(index).verrouiller();
    }

    private int compterVerrous(Grille grille) {
        int nbVerrous = 0;

        for (Case[] ligne : grille.getCases()) {
            for (Case caseCourante : ligne) {
                if (caseCourante.isVerrouille()) {
                    nbVerrous++;
                }
            }
        }

        return nbVerrous;
    }
}
