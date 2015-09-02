package com.dision.android.flickrgallery.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.dision.android.flickrgallery.R;

import java.util.List;

public class Util {
    public static void hideKeyboard(Activity activity,
                                    IBinder binder) {
        InputMethodManager imm;

        imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(binder, 0);
    }

    public static void showNotification(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongNotification(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showSafeNotification(Fragment fragment, String msg) {
        if (isActivityAlive(fragment)) {
            showNotification(fragment.getActivity(), msg);
        }
    }

    public static void showSafeLongNotification(Fragment fragment, String msg) {
        if (isActivityAlive(fragment)) {
            showLongNotification(fragment.getActivity(), msg);
        }
    }

    public static String getCurrLanguage(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage();
    }

    public static boolean isActivityAlive(Fragment fragment) {
        return !fragment.isDetached() && fragment.getActivity() != null;
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String stringsToPath(String... strs) {
        String res = "";

        for (int i = 0; i < strs.length; i++) {
            res += strs[i] + "/";
        }

        return res.length() > 0 ? res.substring(0, res.length() - 1) : res;
    }

    public static boolean isListNotEmpty(List list) {
        return list != null && !list.isEmpty();
    }

    public static boolean isStringNotNull(String str) {
        return str != null && !str.equals("");
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();

        return s;
    }

    public static String getFileExtension(String filePath) {
        int lastIndex = filePath.lastIndexOf('.');

        if (lastIndex > -1) {
            return filePath.substring(lastIndex + 1, filePath.length());
        } else {
            return null;
        }
    }

    public static Point getScreenDimensions(Activity activity) {
        Point point = new Point();

        activity.getWindowManager().getDefaultDisplay().getSize(point);

        return point;
    }

    public static int convertDpiToPixels(Context context, double dpi) {
        int result;
        final float density;

        density = context.getResources().getDisplayMetrics().density;
        result = (int) ((dpi * density) + 0.5f);

        return result;
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }
}
