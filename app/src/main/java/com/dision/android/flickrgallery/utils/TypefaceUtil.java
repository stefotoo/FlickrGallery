package com.dision.android.flickrgallery.utils;

import android.content.Context;
import android.graphics.Typeface;

import com.dision.android.flickrgallery.enums.TypefaceEnum;

public class TypefaceUtil {

    // The Typeface class specifies the typeface and intrinsic style of a font.
    // This is used in the paint, along with optionally Paint settings like textSize,
    // textSkewX, textScaleX to specify how text appears when drawn (and measured).
    public static Typeface getTypeface(
            Context context,
            TypefaceEnum typefaceEnum) {
        Typeface typeface;

        try {
            typeface = Typeface.createFromAsset(context.getAssets(), typefaceEnum.getAssetsPath());
        } catch (Exception e) {
            e.printStackTrace();

            typeface = Typeface.DEFAULT;
        }

        return typeface;
    }
}
