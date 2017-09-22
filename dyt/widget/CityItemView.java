package com.hxyd.dyt.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.hxyd.dyt.R;

/**
 * Created by win7 on 2017/6/1.
 */

public class CityItemView extends RelativeLayout {

    private static final int[] STATE_MESSAGE_READED = {R.attr.state_check};
    private boolean isChecked = false;

    public CityItemView(Context context) {
        this(context, null);
    }

    public CityItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CityItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundResource(R.drawable.city_item_drawable);
    }

    public void isChecked(boolean isChecked) {
        if (this.isChecked != isChecked) {
            this.isChecked = isChecked;
            refreshDrawableState();
        }
    }


    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (isChecked) {
            final int[] drawableState = super
                    .onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, STATE_MESSAGE_READED);
            return drawableState;
        }
        return super.onCreateDrawableState(extraSpace);
    }
}
