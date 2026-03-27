package fr.sae.chuzzle;

public class Case {
    private TypeElement type;
    private boolean verrouille;
    private boolean mystere;

    public Case(TypeElement type) {
        this.type = type;
        this.verrouille = false;
        this.mystere = false;
    }

    public TypeElement getType() {
        return type;
    }

    public void setType(TypeElement type) {
        this.type = type;
    }

    public boolean isVerrouille() {
        return verrouille;
    }

    public boolean isMystere() {
        return mystere;
    }

    public void verrouiller() {
        this.verrouille = true;
    }

    public void deverrouiller() {
        this.verrouille = false;
    }

    public void rendreMystere() {
        this.mystere = true;
    }

    public void enleverMystere() {
        this.mystere = false;
    }
}
