package com.jcdecaux.jcdroid.tictactoe.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class RobotoTextView extends TextView {

    public RobotoTextView(Context context) {
        super(context);
        setTypeface(context);
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
        setTypeface(face);
    }

}
