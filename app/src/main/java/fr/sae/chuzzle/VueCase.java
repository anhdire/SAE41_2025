package fr.sae.chuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;

public class VueCase extends View {

    private static Drawable imageCadenasPartagee;

    private TypeElement type;
    private Drawable drawable;
    private final Drawable imageCadenas;
    private final Paint fondCase;
    private final Paint contourCase;
    private final Paint ombreCase;
    private final Paint tuile;
    private final Paint contourTuile;
    private final Paint refletTuile;
    private final Paint voileVerrou;
    private final RectF zone = new RectF();
    private final Path forme = new Path();
    private boolean verrouillee;

    public VueCase(Context context) {
        super(context);
        chargerImageCadenas(context);
        imageCadenas = imageCadenasPartagee;
        type = null;
        fondCase = new Paint(Paint.ANTI_ALIAS_FLAG);
        contourCase = new Paint(Paint.ANTI_ALIAS_FLAG);
        ombreCase = new Paint(Paint.ANTI_ALIAS_FLAG);
        tuile = new Paint(Paint.ANTI_ALIAS_FLAG);
        contourTuile = new Paint(Paint.ANTI_ALIAS_FLAG);
        refletTuile = new Paint(Paint.ANTI_ALIAS_FLAG);
        voileVerrou = new Paint(Paint.ANTI_ALIAS_FLAG);
        verrouillee = false;

        fondCase.setColor(Color.parseColor("#FFF3EADB"));
        contourCase.setStyle(Paint.Style.STROKE);
        contourCase.setStrokeWidth(dp(1.2f));
        contourCase.setColor(Color.parseColor("#FFCDBFA8"));

        ombreCase.setColor(Color.parseColor("#22000000"));

        contourTuile.setStyle(Paint.Style.STROKE);
        contourTuile.setStrokeWidth(dp(1.4f));

        refletTuile.setStyle(Paint.Style.FILL);

        voileVerrou.setColor(Color.parseColor("#AAE2E5E9"));
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        invalidate();
    }

    public void setType(TypeElement type) {
        this.type = type;
        invalidate();
    }

    public void setVerrouillee(boolean verrouillee) {
        this.verrouillee = verrouillee;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int couleur = recupererCouleur();
        float rayonCase = dp(16);
        float marge = dp(3);
        float profondeur = dp(3);

        if (type == null || Color.alpha(couleur) == 0) {
            return;
        }

        zone.set(marge, marge, getWidth() - marge, getHeight() - marge);
        canvas.drawRoundRect(
                new RectF(zone.left, zone.top + profondeur, zone.right, zone.bottom + profondeur),
                rayonCase,
                rayonCase,
                ombreCase
        );
        canvas.drawRoundRect(zone, rayonCase, rayonCase, fondCase);
        canvas.drawRoundRect(zone, rayonCase, rayonCase, contourCase);

        RectF zoneTuile = new RectF(
                zone.left + dp(5),
                zone.top + dp(4),
                zone.right - dp(5),
                zone.bottom - dp(7)
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

        dessinerForme(canvas, zoneTuile);
        tuile.setShader(null);

        if (verrouillee) {
            dessinerVerrou(canvas, zoneTuile);
        }
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

    private void dessinerForme(Canvas canvas, RectF zoneTuile) {
        if (type == TypeElement.ROUGE) {
            dessinerRond(canvas, zoneTuile);
            return;
        }

        if (type == TypeElement.BLEU_OCEAN) {
            dessinerCarre(canvas, zoneTuile);
            return;
        }

        if (type == TypeElement.VERT) {
            dessinerTriangle(canvas, zoneTuile);
            return;
        }

        if (type == TypeElement.ORANGE) {
            dessinerLosange(canvas, zoneTuile);
            return;
        }

        if (type == TypeElement.VIOLET) {
            dessinerEtoile(canvas, zoneTuile);
            return;
        }

        if (type == TypeElement.JAUNE) {
            dessinerPentagone(canvas, zoneTuile);
            return;
        }

        if (type == TypeElement.BLEU_CIEL) {
            dessinerCroix(canvas, zoneTuile);
        }
    }

    private void dessinerRond(Canvas canvas, RectF zoneTuile) {
        float centreX = zoneTuile.centerX();
        float centreY = zoneTuile.centerY();
        float rayon = Math.min(zoneTuile.width(), zoneTuile.height()) / 2f;

        canvas.drawCircle(centreX, centreY, rayon, tuile);
        canvas.drawCircle(centreX, centreY, rayon, contourTuile);
        canvas.drawCircle(centreX - dp(7), centreY - dp(7), dp(5), refletTuile);
    }

    private void dessinerCarre(Canvas canvas, RectF zoneTuile) {
        float rayon = dp(8);
        RectF zoneReflet;

        canvas.drawRoundRect(zoneTuile, rayon, rayon, tuile);
        canvas.drawRoundRect(zoneTuile, rayon, rayon, contourTuile);

        zoneReflet = new RectF(
                zoneTuile.left + dp(6),
                zoneTuile.top + dp(5),
                zoneTuile.right - dp(16),
                zoneTuile.top + dp(14)
        );
        canvas.drawRoundRect(zoneReflet, dp(6), dp(6), refletTuile);
    }

    private void dessinerTriangle(Canvas canvas, RectF zoneTuile) {
        forme.reset();
        forme.moveTo(zoneTuile.centerX(), zoneTuile.top);
        forme.lineTo(zoneTuile.right, zoneTuile.bottom);
        forme.lineTo(zoneTuile.left, zoneTuile.bottom);
        forme.close();

        canvas.drawPath(forme, tuile);
        canvas.drawPath(forme, contourTuile);
        canvas.drawCircle(zoneTuile.centerX(), zoneTuile.top + dp(12), dp(4), refletTuile);
    }

    private void dessinerLosange(Canvas canvas, RectF zoneTuile) {
        forme.reset();
        forme.moveTo(zoneTuile.centerX(), zoneTuile.top);
        forme.lineTo(zoneTuile.right, zoneTuile.centerY());
        forme.lineTo(zoneTuile.centerX(), zoneTuile.bottom);
        forme.lineTo(zoneTuile.left, zoneTuile.centerY());
        forme.close();

        canvas.drawPath(forme, tuile);
        canvas.drawPath(forme, contourTuile);
        canvas.drawCircle(zoneTuile.centerX(), zoneTuile.centerY() - dp(8), dp(4), refletTuile);
    }

    private void dessinerEtoile(Canvas canvas, RectF zoneTuile) {
        creerEtoile(zoneTuile);
        canvas.drawPath(forme, tuile);
        canvas.drawPath(forme, contourTuile);
        canvas.drawCircle(zoneTuile.centerX(), zoneTuile.top + dp(10), dp(4), refletTuile);
    }

    private void dessinerPentagone(Canvas canvas, RectF zoneTuile) {
        creerPolygone(zoneTuile, 5, -90f);
        canvas.drawPath(forme, tuile);
        canvas.drawPath(forme, contourTuile);
        canvas.drawCircle(zoneTuile.centerX(), zoneTuile.top + dp(10), dp(4), refletTuile);
    }

    private void dessinerCroix(Canvas canvas, RectF zoneTuile) {
        float epaisseur = zoneTuile.width() / 3.4f;
        float demi = epaisseur / 2f;
        float centreX = zoneTuile.centerX();
        float centreY = zoneTuile.centerY();

        forme.reset();
        forme.moveTo(centreX - demi, zoneTuile.top);
        forme.lineTo(centreX + demi, zoneTuile.top);
        forme.lineTo(centreX + demi, centreY - demi);
        forme.lineTo(zoneTuile.right, centreY - demi);
        forme.lineTo(zoneTuile.right, centreY + demi);
        forme.lineTo(centreX + demi, centreY + demi);
        forme.lineTo(centreX + demi, zoneTuile.bottom);
        forme.lineTo(centreX - demi, zoneTuile.bottom);
        forme.lineTo(centreX - demi, centreY + demi);
        forme.lineTo(zoneTuile.left, centreY + demi);
        forme.lineTo(zoneTuile.left, centreY - demi);
        forme.lineTo(centreX - demi, centreY - demi);
        forme.close();

        canvas.drawPath(forme, tuile);
        canvas.drawPath(forme, contourTuile);
        canvas.drawCircle(centreX - dp(8), zoneTuile.top + dp(10), dp(4), refletTuile);
    }

    private void creerPolygone(RectF zoneTuile, int cotes, float angleDepart) {
        int index;
        float centreX = zoneTuile.centerX();
        float centreY = zoneTuile.centerY();
        float rayon = Math.min(zoneTuile.width(), zoneTuile.height()) / 2f;

        forme.reset();

        for (index = 0; index < cotes; index++) {
            float angle = (float) Math.toRadians(angleDepart + ((360f / cotes) * index));
            float x = centreX + ((float) Math.cos(angle) * rayon);
            float y = centreY + ((float) Math.sin(angle) * rayon);

            if (index == 0) {
                forme.moveTo(x, y);
            } else {
                forme.lineTo(x, y);
            }
        }

        forme.close();
    }

    private void creerEtoile(RectF zoneTuile) {
        int index;
        float centreX = zoneTuile.centerX();
        float centreY = zoneTuile.centerY();
        float rayonExterieur = Math.min(zoneTuile.width(), zoneTuile.height()) / 2f;
        float rayonInterieur = rayonExterieur * 0.45f;

        forme.reset();

        for (index = 0; index < 10; index++) {
            float angle = (float) Math.toRadians((-90f) + (36f * index));
            float rayon;
            float x;
            float y;

            if (index % 2 == 0) {
                rayon = rayonExterieur;
            } else {
                rayon = rayonInterieur;
            }

            x = centreX + ((float) Math.cos(angle) * rayon);
            y = centreY + ((float) Math.sin(angle) * rayon);

            if (index == 0) {
                forme.moveTo(x, y);
            } else {
                forme.lineTo(x, y);
            }
        }

        forme.close();
    }

    private void dessinerVerrou(Canvas canvas, RectF zoneTuile) {
        canvas.drawRoundRect(zoneTuile, dp(12), dp(12), voileVerrou);

        if (imageCadenas == null) {
            return;
        }

        float taille = Math.min(zoneTuile.width(), zoneTuile.height()) * 0.42f;
        int gauche = (int) (zoneTuile.right - taille - dp(4));
        int haut = (int) (zoneTuile.top + dp(4));

        imageCadenas.setBounds(
                gauche,
                haut,
                (int) (gauche + taille),
                (int) (haut + taille)
        );

        imageCadenas.draw(canvas);
    }

    private static void chargerImageCadenas(Context context) {
        if (imageCadenasPartagee != null) {
            return;
        }

        imageCadenasPartagee = context.getDrawable(R.drawable.lock);
    }
}

