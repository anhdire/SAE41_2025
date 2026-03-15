package fr.sae.chuzzle;

public class NormlRenderer implements ElementRenderer {
    
    @Override
    public Drawable getDrawable(TypeElement type) {
        
        if(type == null) {
            return new ColorDrawable(0x00000000);
        }

        return new ColorDrawable(type.getColor());
    }
}