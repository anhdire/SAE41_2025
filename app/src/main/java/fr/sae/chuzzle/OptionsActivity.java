package fr.sae.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Switch;

public class OptionsActivity extends Activity {

    private Switch switchHardMode;
    private OptionsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        controller = new OptionsController(this);

        switchHardMode = findViewById(R.id.switchHardMode);

        switchHardMode.setChecked(controller.isHardMode());

        switchHardMode.setOnCheckedChangeListener(
                new HardModeListener(controller)
        );
    }
}