package fr.sae.chuzzle;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.GridLayout;
import android.widget.TextView;

public class ActiviteJeu extends Activity {
    public static final String EXTRA_SEED = "fr.sae.chuzzle.SEED";

    private ControleJeu controle;
    private VueCase[][] vuesCases;
    private AfficheurElement afficheur;
    private TextView txtScore;
    private TextView txtCoups;
    private TextView txtSeed;
    private View overlayFin;
    private TextView txtOverlayFinTitre;
    private TextView txtOverlayFinSeed;
    private long seed;
    private boolean modeDur;
    private int distanceMin;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        initialiserPartie();
        configurerBarreAction();
        initialiserChamps();
        initialiserGrille();
        actualiserJeu();
    }

    @Override
    protected void onPause() {
        controle.sauvegarder(this);
        super.onPause();
    }

    private long recupererSeed() {
        return getIntent().getLongExtra(
                EXTRA_SEED,
                System.currentTimeMillis()
        );
    }

    private void initialiserPartie() {
        seed = recupererSeed();
        modeDur = new ControleOptions(this).isHardMode();
        controle = new ControleJeu(seed, modeDur);

        if (MoteurJeu.aUneSauvegarde(this) && controle.restaurer(this)) {
            seed = controle.getSeed();
            modeDur = controle.isModeDur();
        }

        if (grilleEstVide(controle.getCases())) {
            MoteurJeu.effacerSauvegarde(this);
            controle = new ControleJeu(seed, modeDur);
        }
    }

    private void initialiserChamps() {
        afficheur = new AfficheurNormal();
        txtSeed = findViewById(R.id.txtSeed);
        txtScore = findViewById(R.id.txtScore);
        txtCoups = findViewById(R.id.txtCoups);
        overlayFin = findViewById(R.id.overlayFin);
        txtOverlayFinTitre = findViewById(R.id.txtOverlayFinTitre);
        txtOverlayFinSeed = findViewById(R.id.txtOverlayFinSeed);
        distanceMin = ViewConfiguration.get(this).getScaledTouchSlop();
    }

    private void configurerBarreAction() {
        ActionBar barreAction = getActionBar();

        if (barreAction == null) {
            return;
        }

        barreAction.setTitle(R.string.titre_jeu);
    }

    private void initialiserGrille() {
        GridLayout grilleJeu = findViewById(R.id.grilleJeu);
        int taille = MoteurJeu.TAILLE_GRILLE;
        int tailleCase = calculerTailleCase();
        int margeCase = getResources().getDimensionPixelSize(R.dimen.marge_case_grille);

        vuesCases = new VueCase[taille][taille];

        for(int i=0;i<taille;i++){
            for(int j=0;j<taille;j++){
                VueCase vueCase = creerVueCase(i, j, tailleCase, margeCase);
                vuesCases[i][j] = vueCase;
                grilleJeu.addView(vueCase);
            }
        }
    }

    private VueCase creerVueCase(int ligne, int colonne, int tailleCase, int margeCase) {
        VueCase vueCase = new VueCase(this);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();

        params.rowSpec = GridLayout.spec(ligne);
        params.columnSpec = GridLayout.spec(colonne);
        params.width = tailleCase;
        params.height = tailleCase;
        params.setMargins(margeCase, margeCase, margeCase, margeCase);
        vueCase.setLayoutParams(params);
        vueCase.setOnTouchListener(
                new EcouteurCase(controle, this, ligne, colonne, distanceMin)
        );
        return vueCase;
    }

    private int calculerTailleCase() {
        DisplayMetrics metriques = getResources().getDisplayMetrics();
        int largeurEcran = metriques.widthPixels;
        int paddingContenu = getResources().getDimensionPixelSize(R.dimen.padding_contenu_jeu_h);
        int paddingCadre = getResources().getDimensionPixelSize(R.dimen.padding_cadre_grille);
        int margeCase = getResources().getDimensionPixelSize(R.dimen.marge_case_grille);
        int tailleBase = getResources().getDimensionPixelSize(R.dimen.taille_case_grille);

        int largeurDisponible = largeurEcran - (paddingContenu * 2) - (paddingCadre * 2);
        int tailleMax = (largeurDisponible / MoteurJeu.TAILLE_GRILLE) - (margeCase * 2);

        return Math.min(tailleBase, tailleMax);
    }

    private void mettreAJourScoreEtCoups() {
        txtScore.setText(getString(R.string.jeu_score, controle.getScore()));
        txtCoups.setText(getString(R.string.jeu_coups, controle.getNbCoup()));
        txtSeed.setText(getString(R.string.jeu_seed, seed));
    }

    private void afficherGrille(){
        afficherGrille(controle.getCases());
    }

    private void afficherGrille(Case[][] cases){
        int taille = MoteurJeu.TAILLE_GRILLE;

        for(int i=0;i<taille;i++){
            for(int j=0;j<taille;j++){
                afficherCase(i, j, cases[i][j]);
            }
        }
    }

    private void afficherCase(int ligne, int colonne, Case caseCourante) {
        TypeElement type = caseCourante.getType();

        vuesCases[ligne][colonne].setType(type);
        vuesCases[ligne][colonne].setDrawable(afficheur.getDrawable(type));
        vuesCases[ligne][colonne].setVerrouillee(caseCourante.isVerrouille());
        vuesCases[ligne][colonne].setMystere(caseCourante.isMystere());

        if (type == null) {
            vuesCases[ligne][colonne].setVisibility(View.INVISIBLE);
        } else {
            vuesCases[ligne][colonne].setVisibility(View.VISIBLE);
        }
    }

    private boolean grilleEstVide(Case[][] cases) {
        for (Case[] ligne : cases) {
            for (Case caseCourante : ligne) {
                if (caseCourante.getType() != null) {
                    return false;
                }
            }
        }

        return true;
    }

    public void actualiserJeu() {
        mettreAJourScoreEtCoups();
        afficherGrille();
        verifierFinPartie();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_graine, menu);
        return true;
    }

    private void verifierFinPartie() {
        if (controle.estFinie()) {
            overlayFin.setVisibility(View.VISIBLE);
            txtOverlayFinTitre.setText(R.string.overlay_fin_titre);
            txtOverlayFinSeed.setText(getString(R.string.jeu_fin_partie, seed));
        } else {
            overlayFin.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.revenir_menu) {
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
