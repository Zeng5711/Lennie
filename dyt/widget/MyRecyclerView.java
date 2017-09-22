package com.hxyd.dyt.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.orhanobut.logger.Logger;

/**
 * Created by win7 on 2017/9/1.
 */

public class MyRecyclerView extends RecyclerView {

    private OnBottomCallback mOnBottomCallback;

    public interface OnBottomCallback {
        void onBottom();
    }

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnBottomCallback(OnBottomCallback onBottomCallback) {
        this.mOnBottomCallback = onBottomCallback;
    }

    @Override
    public void onScrolled(int dx, int dy) {

        if (isSlideToBottom() &&  this.computeVerticalScrollOffset() >0) {
            mOnBottomCallback.onBottom();
        }

        int i = this.computeVerticalScrollExtent();

        int i2 = this.computeVerticalScrollOffset();

        int i3 = this.computeVerticalScrollRange();
    }



    public boolean isSlideToBottom() {
        return this != null
                && this.computeVerticalScrollExtent() + this.computeVerticalScrollOffset()
                >= this.computeVerticalScrollRange();
    }


}
