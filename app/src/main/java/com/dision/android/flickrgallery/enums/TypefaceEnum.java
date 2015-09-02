package com.dision.android.flickrgallery.enums;

import com.dision.android.flickrgallery.utils.Util;

public enum TypefaceEnum {
    ROBOTO_BLACK(1, "Roboto-Black.ttf"),
    ROBOTO_REGULAR(2, "Roboto-Regular.ttf"),
    ROBOTO_BOLD(3, "Roboto-Bold.ttf"),
    ROBOTO_MEDIUM(4, "Roboto-Medium.ttf");

    private int index;
    private String path;

    private TypefaceEnum(int index, String path) {
        this.index = index;
        this.path = path;
    }

    public int getIndex() {
        return index;
    }

    public String getAssetsPath() {
        return Util.stringsToPath("fonts", path);
    }

    public static TypefaceEnum findByIndex(int index) {
        switch (index) {
            case 1: {
                return ROBOTO_BLACK;
            }
            case 2: {
                return ROBOTO_REGULAR;
            }
            case 3: {
                return ROBOTO_BOLD;
            }
            case 4: {
                return ROBOTO_MEDIUM;
            }
            default: {
                return null;
            }
        }
    }
}
