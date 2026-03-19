package fr.sae.chuzzle;

import android.content.SharedPreferences;

public class ObservateurPreferences implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final ActiviteOptions activite;

    public ObservateurPreferences(ActiviteOptions activite) {
        this.activite = activite;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String cle) {
        if (ControleOptions.CLE_MODE_DUR.equals(cle)) {
            activite.actualiserResumeModeDur();
        }
    }
}
