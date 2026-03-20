package fr.sae.chuzzle;

public class RappelAideAnalys implements Runnable {
    private final ActiviteJeu activite;

    public RappelAideAnalys(ActiviteJeu activite) {
        this.activite = activite;
    }

    @Override
    public void run() {
        activite.aideAnalys();
    }
}
