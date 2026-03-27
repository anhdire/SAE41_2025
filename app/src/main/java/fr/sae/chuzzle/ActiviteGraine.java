package fr.sae.chuzzle;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

public class ActiviteGraine extends Activity {
    private EditText champGraine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar barreAction;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed);

        champGraine = findViewById(R.id.champ_graine);
        champGraine.setOnEditorActionListener(new EcouteurGraine(this, champGraine));

        barreAction = getActionBar();
        if (barreAction != null) {
            barreAction.setDisplayHomeAsUpEnabled(true);
            barreAction.setTitle(R.string.titre_graine);
        }
    }

    public void demarrerPartieDestinee(long seed) {
        Intent intent = new Intent(this, ActiviteJeu.class);

        MoteurJeu.effacerSauvegarde(this);
        intent.putExtra(ActiviteJeu.EXTRA_SEED, seed);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ouvrirMenu();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        ouvrirMenu();
    }

    private void ouvrirMenu() {
        Intent intent = new Intent(this, ActiviteMenu.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
