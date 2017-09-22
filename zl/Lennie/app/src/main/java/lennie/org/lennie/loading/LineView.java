package lennie.org.lennie.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

//绘制游戏出来的线条
public class LineView extends View {
    
    private Paint mPaint;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mLineGap = 0;
    private int mDrawWidth;
    
    public LineView(Context context) {
        super(context);
        init();
    }
    
    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    public void setDrawWidth(int drawWidth){
    	mDrawWidth = drawWidth;
    	invalidate();
    }
    public int getDrawWidth(){
    	return mDrawWidth;
    }
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int defHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        mWidth = defWidth/2;
        mHeight = defHeight/2-defHeight/3;
        mLineGap = (int)(Math.sin(Math.toRadians(36))*(defHeight/2-15));
        this.setMeasuredDimension(mWidth, mHeight);
    }
    
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = mWidth;
        ViewAcceleration.viewAcceleration(this);
        while(x > -mLineGap){
        	canvas.drawLine(x, 15, x, mHeight-15, mPaint);
        	x -= mLineGap;
        }
        ViewAcceleration.hardwareAccelerated(this);
        
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);// 去除锯齿
        mPaint.setColor(0xff000000);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(5f);
    }
}
