package lennie.org.lennie.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by win7 on 2017/7/12.
 */

public class TestView extends View {

    private final String TAG = "TestView";
    private Paint mPaint;
    private Rect mRect;
    private String text = "123456";

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(100);
        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        Log.v(TAG, "宽的模式:"+widthMode);
        Log.i(TAG, "高的模式:"+heightMode);
        Log.d(TAG, "宽的尺寸:"+widthSize);
        Log.e(TAG, "高的尺寸:"+heightSize);


        int widhtModel = MeasureSpec.getMode(widthMeasureSpec);

        getMeasureSpec(widhtModel, widthMeasureSpec);

    }

    private int getMeasureSpec(int model, int size) {

//        MeasureSpec.AT_MOST;  // 父容器已经为子容器确定的大小，子容器应该遵守
//        MeasureSpec.EXACTLY;  // 父控件决定给孩子一个精确的尺寸
//        MeasureSpec.UNSPECIFIED;// 父容器对子容器没有做任何限制，子容器可以任意大小
        int defSize = 0;
        switch (model) {
            case MeasureSpec.EXACTLY:
                Log.e(TAG, "EXACTLY ===>");
                break;
            case MeasureSpec.AT_MOST:
                Log.e(TAG, "AT_MOST ===>");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.e(TAG, "UNSPECIFIED ===>");
                break;
        }
        return defSize;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.getTextBounds(text, 0, text.length(), mRect);
        canvas.drawText(text, getWidth() / 2 - mRect.width() / 2, getHeight() / 2 + mRect.height() / 2, mPaint);

    }
}
