package fr.sae.chuzzle;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ControleOptions {
    public static final String CLE_MODE_DUR = "mode_dur";

    private final SharedPreferences preferences;

    public ControleOptions(Context context){
        PreferenceManager.setDefaultValues(context, R.xml.preferences, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isHardMode(){
        return preferences.getBoolean(CLE_MODE_DUR, false);
    }

    public void setHardMode(boolean actif) {
        preferences.edit()
                .putBoolean(CLE_MODE_DUR, actif)
                .apply();
    }
}
