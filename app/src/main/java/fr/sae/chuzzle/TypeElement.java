package fr.sae.chuzzle;

public enum TypeElement {
    ROUGE(0xFFE76F51),
    BLEU_OCEAN(0xFF2D6CDF),
    VERT(0xFF2BB07F),
    ORANGE(0xFFF4A425),
    VIOLET(0xFF8D72E1),
    JAUNE(0xFFF4DD5B),
    BLEU_CIEL(0xFF62C4EE),
    NAN(0x00000000); // pour les cases vides (transparent)

    private final int couleur;

    TypeElement(int couleur) {
        this.couleur = couleur;
    }

    public int getCouleur() {
        return couleur;
    }
}
