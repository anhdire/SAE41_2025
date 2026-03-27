package fr.sae.chuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class ActiviteMenu extends Activity {
    private Button btnPlay;
    private Button btnQuit;
    private Button btnPartieDestinee;

    private ControleMenu controle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // je cree le controlleur car en android c'est la vue qui est creer en premier
        this.controle = new ControleMenu(this);

        btnPlay = findViewById(R.id.btnPlay);
        btnPartieDestinee = findViewById(R.id.btnPartieDestinee);
        btnQuit = findViewById(R.id.btnQuit);

        btnPlay.setOnClickListener(
               new EcouteurMenu(controle, EcouteurMenu.ACTION_PLAY)
        );

        btnPartieDestinee.setOnClickListener(
            new EcouteurMenu(controle, EcouteurMenu.ACTION_SEED)
        );

        btnQuit.setOnClickListener(
            new EcouteurMenu(controle, EcouteurMenu.ACTION_QUIT)
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_parametres) {
            controle.ouvrirParametres();
            return true;
        }

        if (item.getItemId() == R.id.menu_quitter) {
            controle.quitterJeu();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
