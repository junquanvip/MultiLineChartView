package com.example.aron.multilinechartview.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * @author aron
 * @data 2017/12/27
 */

public class CustomType {

    private static Typeface sTypeface = null;

    private CustomType() {
    }

    public static Typeface getTypeface() {
        return sTypeface;
    }

    public static Paint getPaint() {
        return getPaint(0,true);
    }

    public static Paint getPaint(int textSize, boolean isCustomTypeface) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(1);
        paint.setStrokeWidth(1);
        paint.setTextSize(textSize);
        paint.setTypeface(isCustomTypeface?CustomType.getTypeface():null);
        paint.setColor(Color.BLACK);
        return paint;
    }

    public static void setTypeface(Typeface typeface) {
        sTypeface = typeface;
    }

}
