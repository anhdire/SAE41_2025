package fr.sae.chuzzle;

import android.graphics.Color;

public enum TypeElement {
    ROUGE("#D55E00"),
    BLEU_OCEAN("#0072B2"),
    VERT("#009E73"),
    ORANGE("#E69F00"),
    VIOLET("#CC79A7"),
    JAUNE("#F0E442"),
    BLEU_CIEL("#56B4E9");
    //selection de couleur adapter au type de daltonisme de type protanopie et deutéranopie

    private final String hexColor;

    TypeElement(String hexColor) {
        this.hexColor = hexColor;
    }
    public int getColor() {
        return Color.parseColor(hexColor);
    }
}
