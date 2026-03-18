package fr.sae.chuzzle;

import android.os.Handler;
import android.util.Log;

import java.util.List;

public class AnimationJeu implements Runnable {
    private static final String TAG = "AnimationJeu";

    private static final long DELAI = 380L;

    private final ActiviteJeu activite;
    private final Handler handler;
    private final List<EtatJeu> etapes;
    private int index;

    public AnimationJeu(ActiviteJeu activite, Handler handler, List<EtatJeu> etapes) {
        this.activite = activite;
        this.handler = handler;
        this.etapes = etapes;
        this.index = 0;
    }

    @Override
    public void run() {
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
