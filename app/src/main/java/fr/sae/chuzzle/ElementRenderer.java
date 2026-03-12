package fr.sae.chuzzle;

import android.graphics.drawable.Drawable;

public interface ElementRenderer {
    Drawable getDrawable(TypeElement type);
}