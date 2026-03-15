package fr.sae.chuzzle;

public enum TypeElement {
    ROUGE(0xFFD55E00),
    BLEU_OCEAN(0xFF0072B2),
    VERT(0xFF009E73),
    ORANGE(0xFFE69F00),
    VIOLET(0xFFCC79A7),
    JAUNE(0xFFF0E442),
    BLEU_CIEL(0xFF56B4E9),
    NAN(0x00000000); // pour les cases vides (transparent)

    private final int couleur;

    TypeElement(int couleur) {
        this.couleur = couleur;
    }

    public int getCouleur() {
        return couleur;
    }
}