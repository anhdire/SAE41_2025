package fr.sae.chuzzle;

import android.util.Log;
import android.widget.CompoundButton;

public class EcouteurModeDur implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "EcouteurModeDur";
    public EcouteurModeDur(ControleOptions controller){

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Log.d(TAG, "Switch mode dur change valeur=" + b);
    }
}
