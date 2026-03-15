package fr.sae.chuzzle;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText$InspectionCompanion;

public class GraineListener implements TextView.OnEditorActionListener{

    private EditText champ_graine;
    private SeedActivity activity;

    private int seed;

    public GraineListener(SeedActivity s, EditText e){
        this.activity = s;
        this.champ_graine = e;
    }



    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if(i == EditorInfo.IME_ACTION_DONE){
           String text = champ_graine.getText().toString();
           try{
               seed = Integer.parseInt(text);
           }catch(NumberFormatException e){
                return false;
           }
        }
        return true;
    }

    public int getGraine(){
        return this.seed;
    }


}
