package fr.sae.chuzzle;

import android.widget.CompoundButton;

public class EcouteurModeDur implements CompoundButton.OnCheckedChangeListener {
    private final ControleOptions controle;

    public EcouteurModeDur(ControleOptions controller){
        this.controle = controller;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        controle.setHardMode(b);
    }
}
