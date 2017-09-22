package lennie.org.lennie.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
//引导界面中心线环
public class SunView extends View {
    
    private float mCx = 0;
    private float mCy = 0;
    private float mRadius1 = 0;
    private float mRadius2 = 0;
    private Paint mPaint ;
    
    public SunView(Context context) {
        super(context);
        init();
    }
    
    public SunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public SunView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    public float getOuterRaius(){
    	return mRadius2;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int defHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        mCx = defWidth/2.0f;
        mCy = defHeight/2.0f;
        mRadius1 = defHeight/3.0f+15;
        mRadius2 = defHeight/2.0f-15;
        this.setMeasuredDimension(defWidth, defHeight);
    }
    
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ViewAcceleration.viewAcceleration(this);
        for(int i=0; i<360; i+=36){
            canvas.drawLine( 
                mCx+(float)Math.sin(Math.toRadians(i))*mRadius1,
                mCy-(float)Math.cos(Math.toRadians(i))*mRadius1,
                mCx+(float)Math.sin(Math.toRadians(i))*mRadius2,
                mCy-(float)Math.cos(Math.toRadians(i))*mRadius2,
                mPaint);
        }
        ViewAcceleration.hardwareAccelerated(this);
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);// 去除锯齿
        mPaint.setColor(0xff000000);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(5f);
        mPaint.setStrokeCap(Cap.ROUND);
    }
}
