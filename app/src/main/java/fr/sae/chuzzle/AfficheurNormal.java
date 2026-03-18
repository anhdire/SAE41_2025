package fr.sae.chuzzle;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

public class AfficheurNormal implements AfficheurElement {

    @Override
    public Drawable getDrawable(TypeElement type) {
        if (type == null) {
            return new ColorDrawable(0x00000000);
        }

        return new ColorDrawable(type.getCouleur());
    }
}
