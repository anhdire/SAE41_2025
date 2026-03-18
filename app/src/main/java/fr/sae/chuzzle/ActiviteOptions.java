package fr.sae.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

public class ActiviteOptions extends Activity {
    private static final String TAG = "ActiviteOptions";

    private Switch switchHardMode;
    private ControleOptions controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Log.d(TAG, "onCreate options");

        controle = new ControleOptions(this);

        switchHardMode = findViewById(R.id.switchHardMode);

        switchHardMode.setChecked(controle.isHardMode());

        switchHardMode.setOnCheckedChangeListener(
                new EcouteurModeDur(controle)
        );
    }
}
