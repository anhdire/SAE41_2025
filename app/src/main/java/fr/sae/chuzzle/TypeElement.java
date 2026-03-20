package fr.sae.chuzzle;

public enum TypeElement {
    ROUGE(0xFFE63946),
    BLEU_OCEAN(0xFF457B9D),
    VERT(0xFF2A9D8F),
    ORANGE(0xFFF77F00),
    VIOLET(0xFF9D4EDD),
    JAUNE(0xFFE9C46A),
    BLEU_CIEL(0xFFFF4D6D),
    NAN(0x00000000); // pour les cases vides (transparent)

    private final int couleur;

    TypeElement(int couleur) {
        this.couleur = couleur;
    }

    public int getCouleur() {
        return couleur;
    }
}
