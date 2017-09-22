package com.hxyd.dyt.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hxyd.dyt.R;

/**
 * Created by win7 on 2017/5/12.
 */

public class AmountToBeApprovedView extends View {

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    private String mMoneyText;
    private int mMoneyTextColor;
    private int mMoneyTextSize;

    private String mUnitpriceText;
    private int mUnitpriceTextColor;
    private int mUnitpriceTextSize;

    private Rect mBound;
    private Paint mPaint;

    private int mRadiusMaxLefit;
    private int mRadiusMinLefit;

    public AmountToBeApprovedView(Context context) {
        this(context, null);
    }

    public AmountToBeApprovedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AmountToBeApprovedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AmountToBeApprovedView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.AmountToBeApprovedView_titleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.AmountToBeApprovedView_titleTextColor:
                    // 默认颜色设置为黑色
                    mTitleTextColor = a.getColor(attr, context.getResources().getColor(R.color.default_black));
                    break;
                case R.styleable.AmountToBeApprovedView_titleTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.AmountToBeApprovedView_moneyText:
                    mMoneyText = a.getString(attr);
                    break;
                case R.styleable.AmountToBeApprovedView_moneyTextColor:
                    // 默认颜色设置为黑色
                    mMoneyTextColor = a.getColor(attr, context.getResources().getColor(R.color.default_black));
                    break;
                case R.styleable.AmountToBeApprovedView_moneyTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mMoneyTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.AmountToBeApprovedView_unitpriceText:
                    mUnitpriceText = a.getString(attr);
                    break;
                case R.styleable.AmountToBeApprovedView_unitpriceTextColor:
                    // 默认颜色设置为黑色
                    mUnitpriceTextColor = a.getColor(attr, context.getResources().getColor(R.color.default_black));
                    break;
                case R.styleable.AmountToBeApprovedView_unitpriceTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mUnitpriceTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.AmountToBeApprovedView_radiusMaxLefit:
                    mRadiusMaxLefit = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 50, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.AmountToBeApprovedView_radiusMinLefit:
                    mRadiusMinLefit = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 30, getResources().getDisplayMetrics()));
                    break;
            }

        }
        a.recycle();

        mPaint = new Paint();
        mBound = new Rect();
        mPaint.setAntiAlias(true);
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cx = getWidth() / 2;
        int cy = cx;
        int radius = cx - mRadiusMaxLefit;

        mPaint.setColor(getResources().getColor(R.color.circularView_background));
        canvas.drawCircle(cx, cy, radius, mPaint);
        mPaint.setColor(getResources().getColor(R.color.default_whit));
        canvas.drawCircle(cx, cy, radius - mRadiusMinLefit, mPaint);

        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mPaint.setTypeface(font);
        mPaint.setTextSize(mTitleTextSize);
        mPaint.setColor(mTitleTextColor);
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
        canvas.drawText(mTitleText, cx - mBound.width() / 2, getWidth() + mRadiusMaxLefit, mPaint);

        mPaint.setTextSize(mUnitpriceTextSize);
        mPaint.setColor(mUnitpriceTextColor);
        mPaint.getTextBounds(mUnitpriceText, 0, mUnitpriceText.length(), mBound);
        canvas.drawText(mUnitpriceText, cx - mBound.width() / 2, cy + radius / 2, mPaint);

        Typeface font2 = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mPaint.setTypeface(font2);
        mPaint.setTextSize(mMoneyTextSize);
        mPaint.setColor(mMoneyTextColor);
        if (!TextUtils.isEmpty(mMoneyText) && !mMoneyText.equals("null")) {
            mPaint.getTextBounds(mMoneyText, 0, mMoneyText.length(), mBound);
//        float moneyTextW = mPaint.measureText(mMoneyText);
            canvas.drawText(mMoneyText, cx - mBound.width() / 2, cy, mPaint);
        }


    }

    public void setMoneyText(String moneyText) {
        this.mMoneyText = moneyText;
        invalidate();
    }
}
