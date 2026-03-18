package fr.sae.chuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;

public class VueCase extends View {

    private Drawable drawable;
    private final Paint fondCase;
    private final Paint contourCase;
    private final Paint ombreCase;
    private final Paint tuile;
    private final Paint contourTuile;
    private final Paint refletTuile;
    private final RectF zone = new RectF();

    public VueCase(Context context) {
        super(context);
        fondCase = new Paint(Paint.ANTI_ALIAS_FLAG);
        contourCase = new Paint(Paint.ANTI_ALIAS_FLAG);
        ombreCase = new Paint(Paint.ANTI_ALIAS_FLAG);
        tuile = new Paint(Paint.ANTI_ALIAS_FLAG);
        contourTuile = new Paint(Paint.ANTI_ALIAS_FLAG);
        refletTuile = new Paint(Paint.ANTI_ALIAS_FLAG);

        fondCase.setColor(Color.parseColor("#6626354D"));
        contourCase.setStyle(Paint.Style.STROKE);
        contourCase.setStrokeWidth(dp(1.2f));
        contourCase.setColor(Color.parseColor("#26FFFFFF"));

        ombreCase.setColor(Color.parseColor("#55000000"));

        contourTuile.setStyle(Paint.Style.STROKE);
        contourTuile.setStrokeWidth(dp(1.4f));

        refletTuile.setStyle(Paint.Style.STROKE);
        refletTuile.setStrokeWidth(dp(1.1f));
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float rayonCase = dp(16);
        float rayonTuile = dp(14);
        float marge = dp(3);
        float profondeur = dp(3);

        zone.set(marge, marge, getWidth() - marge, getHeight() - marge);
        canvas.drawRoundRect(
                new RectF(zone.left, zone.top + profondeur, zone.right, zone.bottom + profondeur),
                rayonCase,
                rayonCase,
                ombreCase
        );
        canvas.drawRoundRect(zone, rayonCase, rayonCase, fondCase);
        canvas.drawRoundRect(zone, rayonCase, rayonCase, contourCase);

        int couleur = recupererCouleur();
        if (Color.alpha(couleur) == 0) {
            return;
        }

        RectF zoneTuile = new RectF(
                zone.left + dp(2),
                zone.top + dp(1),
                zone.right - dp(2),
                zone.bottom - dp(4)
        );

        tuile.setShader(new LinearGradient(
                0,
                zoneTuile.top,
                0,
                zoneTuile.bottom,
                eclaircir(couleur, 0.18f),
                assombrir(couleur, 0.16f),
                Shader.TileMode.CLAMP
        ));
        contourTuile.setColor(assombrir(couleur, 0.28f));
        refletTuile.setColor(eclaircir(couleur, 0.38f));

        canvas.drawRoundRect(zoneTuile, rayonTuile, rayonTuile, tuile);
        canvas.drawRoundRect(zoneTuile, rayonTuile, rayonTuile, contourTuile);

        RectF zoneReflet = new RectF(
                zoneTuile.left + dp(4),
                zoneTuile.top + dp(3),
                zoneTuile.right - dp(4),
                zoneTuile.top + dp(13)
        );
        canvas.drawRoundRect(zoneReflet, dp(10), dp(10), refletTuile);
        tuile.setShader(null);
    }

    private int recupererCouleur() {
        if (drawable == null) {
            return Color.TRANSPARENT;
        }

        if (drawable instanceof ColorDrawable) {
            return ((ColorDrawable) drawable).getColor();
        }

        return Color.TRANSPARENT;
    }

    private float dp(float valeur) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                valeur,
                getResources().getDisplayMetrics()
        );
    }

    private int eclaircir(int couleur, float ratio) {
        int rouge = melanger(Color.red(couleur), 255, ratio);
        int vert = melanger(Color.green(couleur), 255, ratio);
        int bleu = melanger(Color.blue(couleur), 255, ratio);
        return Color.argb(Color.alpha(couleur), rouge, vert, bleu);
    }

    private int assombrir(int couleur, float ratio) {
        int rouge = melanger(Color.red(couleur), 0, ratio);
        int vert = melanger(Color.green(couleur), 0, ratio);
        int bleu = melanger(Color.blue(couleur), 0, ratio);
        return Color.argb(Color.alpha(couleur), rouge, vert, bleu);
    }

    private int melanger(int depart, int arrivee, float ratio) {
        return (int) (depart + ((arrivee - depart) * ratio));
    }
}
