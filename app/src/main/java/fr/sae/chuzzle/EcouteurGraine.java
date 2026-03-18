package fr.sae.chuzzle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class EcouteurGraine implements TextView.OnEditorActionListener {
    private static final String TAG = "EcouteurGraine";

    private final EditText champGraine;
    private final ActiviteGraine activite;

    public EcouteurGraine(ActiviteGraine activite, EditText champGraine) {
        this.activite = activite;
        this.champGraine = champGraine;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean actionDone = actionId == EditorInfo.IME_ACTION_DONE;
        boolean enterPressed = keyEvent != null
                && keyEvent.getAction() == KeyEvent.ACTION_DOWN
                && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER;
        Log.d(TAG, "Action clavier actionId=" + actionId + " enterPressed=" + enterPressed);

        if (!actionDone && !enterPressed) {
            return false;
        }

        String texte = champGraine.getText().toString().trim();
        Log.d(TAG, "Texte graine saisi='" + texte + "'");
        if (texte.isEmpty()) {
            Log.d(TAG, "Saisie vide refusee");
            champGraine.setError("Entrez une graine");
            return true;
        }

        try {
            long seed = Long.parseLong(texte);
            Log.d(TAG, "Graine valide=" + seed);
            activite.demarrerPartieDestinee(seed);
        } catch (NumberFormatException e) {
            Log.d(TAG, "Graine invalide");
            champGraine.setError("Graine invalide");
        }

        return true;
    }
}
