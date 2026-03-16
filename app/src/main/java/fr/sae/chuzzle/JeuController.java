package fr.sae.chuzzle;

public class JeuController {
    private MoteurJeu moteur;

    public JeuController(long seed) {
        moteur = new MoteurJeu(seed);
    }

    public MoteurJeu getMoteur(){
        return this.moteur;
    }
}
