package fr.sae.chuzzle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.MenuItem;

public class ActiviteOptions extends PreferenceActivity {
    private SharedPreferences preferences;
    private ObservateurPreferences observateur;
    private ControleOptions controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        addPreferencesFromResource(R.xml.preferences);
        controle = new ControleOptions(this);
        preferences = getPreferenceScreen().getSharedPreferences();
        observateur = new ObservateurPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(observateur);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setTitle(R.string.parametres);
        }

        actualiserResumeModeDur();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ouvrirMenu();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void actualiserResumeModeDur() {
        SwitchPreference preference;

        preference = (SwitchPreference) findPreference(ControleOptions.CLE_MODE_DUR);
        if (preference == null) {
            return;
        }

        if (controle.isHardMode()) {
            preference.setSummary(getString(R.string.mode_dur_active));
        } else {
            preference.setSummary(getString(R.string.mode_dur_inactif));
        }
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
