package lennie.org.lennie.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class CircleView extends View {

    private final int ANGLE = -95;
    private float mCx = 0;
    private float mCy = 0;
    private float mRadius1 = 100;
    private float mRadius2 = 100;
    private Paint mPaint ;
    private SweepGradient mSweepGradient;
    private RectF mRectF;
    private int mDrawAngle;
    
    public CircleView(Context context) {
        super(context);
        init();
    }
    
    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    public void setDrawAngle(int drawAngle){
    	mDrawAngle = drawAngle;
    	invalidate();
    }
    public int getDrawAngle(){
    	return mDrawAngle;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int defHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        mCx = defWidth/2.0f;
        mCy = defHeight/2.0f;
        mRadius1 = defHeight/3.0f;
        mRadius2 = defHeight/2.0f;
        mSweepGradient = new SweepGradient(mCx, mCy, new int[] {0xffcccccc,0x00222222}, null);
        mPaint.setStrokeWidth(mRadius2-mRadius1);
        mPaint.setShader(mSweepGradient);
        mRectF = new RectF((mRadius2-mRadius1)/2, (mRadius2-mRadius1)/2, mCx*2-(mRadius2-mRadius1)/2, mCy*2-(mRadius2-mRadius1)/2);
        this.setMeasuredDimension(defWidth, defHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ViewAcceleration.viewAcceleration(this);
        canvas.drawArc(mRectF, 0, mDrawAngle, false, mPaint);
        ViewAcceleration.hardwareAccelerated(this);
        
    }

    private void init(){
        ViewHelper.setRotation(this,ANGLE);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);// 去除锯齿
        mPaint.setStyle(Style.STROKE);
    }
}
