package fr.sae.chuzzle;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class GraineListener implements TextView.OnEditorActionListener {

    private final EditText champGraine;
    private final SeedActivity activity;

    public GraineListener(SeedActivity activity, EditText champGraine) {
        this.activity = activity;
        this.champGraine = champGraine;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean actionDone = actionId == EditorInfo.IME_ACTION_DONE;
        boolean enterPressed = keyEvent != null
                && keyEvent.getAction() == KeyEvent.ACTION_DOWN
                && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER;

        if (!actionDone && !enterPressed) {
            return false;
        }

        String texte = champGraine.getText().toString().trim();
        if (texte.isEmpty()) {
            champGraine.setError("Entrez une graine");
            return true;
        }

        try {
            long seed = Long.parseLong(texte);
            activity.demarrerPartieDestinee(seed);
        } catch (NumberFormatException e) {
            champGraine.setError("Graine invalide");
        }

        return true;
    }
}
