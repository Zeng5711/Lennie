package com.hxyd.dyt.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by win7 on 2017/6/13.
 */

public class RetoursLigneTextView extends android.support.v7.widget.AppCompatTextView {
    public RetoursLigneTextView(Context context) {
        this(context,null);
    }

    public RetoursLigneTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public RetoursLigneTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
