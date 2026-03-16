package fr.sae.chuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toolbar;

public class SeedActivity extends Activity {

    private EditText champGraine;
    private Toolbar barreAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed);

        champGraine = findViewById(R.id.champ_graine);
        champGraine.setOnEditorActionListener(new GraineListener(this, champGraine));

        barreAction = findViewById(R.id.toolbar);
        if (barreAction != null) {
            setActionBar(barreAction);
        }
    }

    public void demarrerPartieDestinee(long seed) {
        Intent intent = new Intent(this, JeuActivity.class);
        intent.putExtra(JeuActivity.EXTRA_SEED, seed);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_graine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.revenir_menu) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
