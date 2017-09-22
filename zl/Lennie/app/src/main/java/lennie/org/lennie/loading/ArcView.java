package lennie.org.lennie.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

import lennie.org.lennie.R;
//import com.pingan.anydoor.R;
//import com.pingan.anydoor.common.utils.JarUtils;


public class ArcView extends View {
    
    private final int ANGLE = -100; //初始化偏移角度
    private final int STARTANGLE = 290; //开始绘制的角度
    private final int STARTANGLE2 = 310; //尾部白色不透明绘制起始角度
    private final int ENDANGLE = 42; ////尾部白色不透明绘制结束角度
    private float mCx = 0;
    private float mCy = 0;
    private float mRadius1 ;
    private float mRadius2 ;
    private float mR;
    private Paint mPaint;
    private int mDrawAngle;
    private SweepGradient mSweepGradient;
    private int mEndColor;
    private RectF mRectF;
    
    
    
    public ArcView(Context context) {
        super(context);
        init(context);
    }
    
    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
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
        mR = mRadius2-mRadius1;
        mRectF = new RectF(mR/2, mR/2, mCx*2-mR/2, mCy*2-mR/2);
        mSweepGradient = new SweepGradient(mCx, mCy, new int[] {0xffcccccc, mEndColor}, null);
        this.setMeasuredDimension(defWidth, defHeight);
    }

    public  void isDraw(boolean isDraw){
        this.isDraw = isDraw;
    }
    private boolean isDraw = false;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if(isDraw) {
            mPaint.setStrokeWidth(mR);
            mPaint.setShader(mSweepGradient);
            canvas.drawArc(mRectF, STARTANGLE, mDrawAngle, false, mPaint);

            mPaint.setColor(Color.WHITE);
            mPaint.setShader(null);
            canvas.drawArc(mRectF, STARTANGLE2, ENDANGLE, false, mPaint);
//        }
    }

    private void init(Context context){
        ViewHelper.setRotation(this,ANGLE);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);// 去除锯齿
        mPaint.setStyle(Style.STROKE);
        mEndColor = context.getResources().getColor(R.color.rym_loading_arcview_end);
    }
}
