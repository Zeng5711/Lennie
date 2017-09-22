package lennie.org.lennie.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

// 中心圆
public class TargetView extends View {
    
    private float mCx = 0;
    
    private float mCy = 0;
    
    private float mRadius1 = 140.0f;
    
    private Paint mPaint;
    
    public TargetView(Context context) {
        super(context);
        init();
    }
    
    public TargetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public TargetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setColor(int color) {
        if (null != mPaint) {
            mPaint.setColor(color);
            this.invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int defHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        mCx = defWidth / 2.0f;
        mCy = defHeight / 2.0f;
        mRadius1 = defHeight / 2.0f;
        this.setMeasuredDimension(defWidth, defHeight);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ViewAcceleration.viewAcceleration(this);
        canvas.drawCircle(mCx, mCy, mRadius1, mPaint);
        ViewAcceleration.hardwareAccelerated(this);
    }
    
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);// 去除锯齿
        mPaint.setStyle(Style.FILL);
        mPaint.setStrokeWidth(2f);
        mPaint.setColor(0xff4A90E2);
    }
}
