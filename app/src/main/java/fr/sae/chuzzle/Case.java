package fr.sae.chuzzle;

public class Case {
    private TypeElement type;
    private boolean verrouille;

    public Case(TypeElement type) {
        this.type = type;
        this.verrouille = false;
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

    public void verrouiller() {
        this.verrouille = true;
    }
    public void deverrouiller() {
        this.verrouille = false;
    }
}
