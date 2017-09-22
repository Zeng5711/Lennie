package lennie.org.lennie.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

//游戏进场背景色条
public class LineBGView extends View {
    
    private Paint mPaint ;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mDrawWidth;
    
    public LineBGView(Context context) {
        super(context);
        init();
    }
    
    public LineBGView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public LineBGView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mWidth = defWidth/2+1;
        mHeight = defHeight/2-defHeight/3;
        this.setMeasuredDimension(mWidth, mHeight);
    }
    
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ViewAcceleration.viewAcceleration(this);
        if(mDrawWidth<mWidth){
	        canvas.drawRect(0, 0, mDrawWidth, mHeight, mPaint);
        }else{
        	canvas.drawColor(0xffcccccc);
        }
        ViewAcceleration.hardwareAccelerated(this);
        
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);// 去除锯齿
        mPaint.setColor(0xffcccccc);
        mPaint.setStyle(Style.FILL);
    }
}
