package fr.sae.chuzzle;

public class JeuController {
    private MoteurJeu moteur;
    private JeuActivity activity;

    public JeuController(JeuActivity activity) {
        this.activity = activity;
        long seed = System.currentTimeMillis();
        moteur = new MoteurJeu();
    }

    public MoteurJeu getMoteur() {
        return moteur;
    }
}