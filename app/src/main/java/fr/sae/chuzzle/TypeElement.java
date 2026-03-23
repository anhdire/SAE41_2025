package fr.sae.chuzzle;

public enum TypeElement {
    ROUGE(0xFFE76F51),
    BLEU_OCEAN(0xFF3A86FF),
    VERT(0xFF2A9D8F),
    ORANGE(0xFFFB8500),
    VIOLET(0xFF8338EC),
    JAUNE(0xFFFFBE0B),
    BLEU_CIEL(0xFF4CC9F0),
    NAN(0x00000000); // pour les cases vides (transparent)

    private final int couleur;

    TypeElement(int couleur) {
        this.couleur = couleur;
    }

    public int getCouleur() {
        return couleur;
    }
}
