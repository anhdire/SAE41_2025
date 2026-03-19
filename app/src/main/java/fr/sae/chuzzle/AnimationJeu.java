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
    private final boolean retourSimple;
    private final boolean estLigne;
    private final int indexGlissement;
    private int index;
    private boolean retourFait;

    public AnimationJeu(ActiviteJeu activite, Handler handler, List<EtatJeu> etapes) {
        this.activite = activite;
        this.handler = handler;
        this.etapes = etapes;
        this.retourSimple = false;
        this.estLigne = false;
        this.indexGlissement = -1;
        this.index = 0;
        this.retourFait = false;
    }

    public AnimationJeu(ActiviteJeu activite, Handler handler, boolean estLigne, int indexGlissement) {
        this.activite = activite;
        this.handler = handler;
        this.etapes = null;
        this.retourSimple = true;
        this.estLigne = estLigne;
        this.indexGlissement = indexGlissement;
        this.index = 0;
        this.retourFait = false;
    }

    @Override
    public void run() {
        if (retourSimple) {
            if (!retourFait) {
                retourFait = true;
                Log.d(TAG, "Retour animation simple estLigne=" + estLigne
                        + " index=" + indexGlissement);
                activite.animerRetourGlissement(estLigne, indexGlissement);
                handler.postDelayed(this, DUREE_GLISSEMENT);
                return;
            }

            Log.d(TAG, "Fin animation simple");
            activite.finAnimation();
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
