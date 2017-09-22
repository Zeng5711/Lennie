package com.hxyd.dyt.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.hxyd.dyt.common.uitl.Tools;
import com.orhanobut.logger.Logger;

/**
 * Created by win7 on 2017/4/10.
 */

public class ItemImageView extends android.support.v7.widget.AppCompatImageView {
    public ItemImageView(Context context) {
        super(context);
    }

    public ItemImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidth = (Tools.getwidthPixels(this.getContext()) - (int) (50 * 2 * Tools.getDisplayDensity(this.getContext()))) / 3;
        setMeasuredDimension(mWidth, mWidth);
    }
}
