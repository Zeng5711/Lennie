package lennie.org.lennie.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class BulletView extends View {
    
    private float mCx = 0;
    private float mCy = 0;
    private float mRadius = 100;
    private Paint mPaint ;
    
    private float mOrinX = 0;
    private float mOrinY = 0;
    
    public BulletView(Context context) {
        super(context);
        init();
    }
    
    public BulletView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public BulletView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    public void resetPos(){
    	ViewHelper.setX(this,mOrinX);
        ViewHelper.setY(this,mOrinY);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int defHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        mCx = defWidth/2.0f;
        mCy = defHeight/2.0f;
        mRadius = defHeight/2.0f;
        this.setMeasuredDimension(defWidth, defHeight);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
    		int bottom) {
    	super.onLayout(changed, left, top, right, bottom);
    	mOrinX = ViewHelper.getX(this);
    	mOrinY = ViewHelper.getY(this);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ViewAcceleration.viewAcceleration(this);
        canvas.drawCircle(mCx, mCy, mRadius, mPaint);
        ViewAcceleration.hardwareAccelerated(this);

    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);// 去除锯齿
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(0xff000000);
    }
}
