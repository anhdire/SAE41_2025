package fr.sae.chuzzle;

import android.os.Handler;
import android.util.Log;

import java.util.List;

public class AnimationJeu implements Runnable {
    private static final String TAG = "AnimationJeu";

    static final long DUREE_GLISSEMENT = 160L;
    private static final long DELAI = 380L;

    private final ActiviteJeu activite;
    private final Handler handler;
    private final List<EtatJeu> etapes;
    private final boolean estLigne;
    private final int indexGlissement;
    private final int decalage;
    private int index;
    private boolean glissementAffiche;

    public AnimationJeu(ActiviteJeu activite, Handler handler, List<EtatJeu> etapes,
                        boolean estLigne, int indexGlissement, int decalage) {
        this.activite = activite;
        this.handler = handler;
        this.etapes = etapes;
        this.estLigne = estLigne;
        this.indexGlissement = indexGlissement;
        this.decalage = decalage;
        this.index = 0;
        this.glissementAffiche = false;
    }

    @Override
    public void run() {
        if (!glissementAffiche && !etapes.isEmpty()) {
            glissementAffiche = true;
            Log.d(TAG, "Animation glissement index=" + indexGlissement
                    + " decalage=" + decalage + " estLigne=" + estLigne);
            activite.animerGlissement(estLigne, indexGlissement, decalage);
            handler.postDelayed(this, DUREE_GLISSEMENT);
            return;
        }

        Log.d(TAG, "Lecture etape index=" + index + "/" + (etapes.size() - 1));
        activite.afficherEtat(etapes.get(index));

        if (index >= etapes.size() - 1) {
            Log.d(TAG, "Derniere etape atteinte");
            activite.finAnimation();
            return;
        }

        index++;
        handler.postDelayed(this, DELAI);
    }
}
