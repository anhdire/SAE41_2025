package fr.sae.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Switch;

public class ActiviteOptions extends Activity {
    private Switch switchHardMode;
    private ControleOptions controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        controle = new ControleOptions(this);

        switchHardMode = findViewById(R.id.switchHardMode);

        switchHardMode.setChecked(controle.isHardMode());

        switchHardMode.setOnCheckedChangeListener(
                new EcouteurModeDur(controle)
        );
    }
}
