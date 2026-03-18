package fr.sae.chuzzle;

import java.util.ArrayList;
import java.util.List;

public class GestionVerrou {

    private static final int CHANCE_MIN = 15;
    private static final int CHANCE_MAX = 60;
    private static final int AUGMENTATION_PAR_COUP = 3;

    private final Generateur generateur;

    public GestionVerrou(long seed) {
        this.generateur = new Generateur(seed + 1);
    }

    public void ajouterVerrouSiNecessaire(Grille grille, int nbCoups) {
        if (grille == null || nbCoups <= 0) {
            return;
        }

        /* Pour chaque coup joué, on augmente la chance d'ajouter un verrou de Augmentation_Par_Coup% (jusqu'à un max de CHANCE_MAX%) */
        int chance = CHANCE_MIN + ((nbCoups - 1) * AUGMENTATION_PAR_COUP);
        chance = Math.max(CHANCE_MIN, Math.min(CHANCE_MAX, chance));
        
        if (generateur.entierAleatoire(100) >= chance) {
            return;
        }

        List<Case> candidates = new ArrayList<>();
        Case[][] cases = grille.getCases();

        for (Case[] ligne : cases) {
            for (Case caseCourante : ligne) {
                if (caseCourante.getType() != TypeElement.NAN && !caseCourante.isVerrouille()) {
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

}
