package com.dision.android.flickrgallery.utils;

import android.content.Context;

public class TypeUtil {

    public static int convertDpiToPixels(Context context, double dpi) {
        int result;
        final float density;

        density = context.getResources().getDisplayMetrics().density;
        result = (int) ((dpi * density) + 0.5f);

        return result;
    }
}
