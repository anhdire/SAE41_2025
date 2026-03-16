package fr.sae.chuzzle;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toolbar;

public class SeedActivity extends Activity {

    private EditText champ_graine;
    private JeuController jeuController;

    private Toolbar barre_action;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_seed);
        this.champ_graine = findViewById(R.id.champ_graine);
        GraineListener l = new GraineListener(this,champ_graine);
        this.champ_graine.setOnEditorActionListener(l);
        int seed = l.getGraine();
        this.jeuController = new JeuController(null);
        jeuController.getMoteur().setSeed(seed);

       this.barre_action = findViewById(R.id.toolbar);
        if(barre_action != null) {
            setActionBar(barre_action);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_graine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.revenir_menu) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







}
