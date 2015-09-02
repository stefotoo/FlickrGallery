package com.dision.android.flickrgallery.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dision.android.flickrgallery.R;
import com.dision.android.flickrgallery.enums.TypefaceEnum;
import com.dision.android.flickrgallery.utils.TypefaceUtil;

public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta; /*Container for an array of values that were retrieved with AttributeSet*/

        ta = null;
        //attrs - a collection of attributes, as found associated with a tag in an XML document
        if (attrs != null) {
            ta = context.obtainStyledAttributes(attrs, R.styleable.Text);
        }

        //setTextIsSelectable(true);
        setIncludeFontPadding(false);
        setTypeface(ta);
    }

    public void setTypeface(TypedArray a) {
        TypefaceEnum typefaceEnum;

        if (a != null) {
            int typefaceKey = a.getInteger(R.styleable.Text_font, 2);

            typefaceEnum = TypefaceEnum.findByIndex(typefaceKey);
        } else {
            typefaceEnum = TypefaceEnum.ROBOTO_REGULAR;
        }

        setTypeface(typefaceEnum);
    }

    public void setTypeface(TypefaceEnum typefaceEnum) {
        setTypeface(TypefaceUtil.getTypeface(getContext(), typefaceEnum));
    }
}
