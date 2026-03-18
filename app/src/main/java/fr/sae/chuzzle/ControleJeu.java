package fr.sae.chuzzle;

public class ControleJeu {
    private MoteurJeu moteur;

    public ControleJeu(long seed) {
        moteur = new MoteurJeu(seed);
    }

    public MoteurJeu getMoteur(){
        return this.moteur;
    }
}
