package lennie.org.lennie.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.Toast;

import lennie.org.lennie.R;

/**
 * Created by win7 on 2017/7/4.
 */

public class CView extends LinearLayout {

    private final String TAG = "CView";

    private Scroller mScroller;

    private View view;

    public CView(Context context) {
        this(context, null);
    }

    public CView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "CView == >");
//        setBackgroundColor(Color.RED);

        mScroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
         view = LayoutInflater.from(context).inflate(R.layout.cview, null);
        this.addView(view, layoutParams);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "CView_Click", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 手势是否在View的范围内
     * @param localX
     * @param localY
     * @return
     */
    public boolean pointInViews(float localX, float localY) {
        int top = this.getTop();
        return  localY <= view.getBottom() + top && localY >= view.getTop() + top;

    }

    /**
     * 是否往右边移动
     * @return
     */
    public boolean isScrolling() {
        int scrollX = getScrollX();
        int width = getWidth();
        return scrollX < width && scrollX > 0;
    }

    /**
     * 是否全屏显示
     * @return
     */
    public boolean isAllDisplaying() {
        int scrollX = getScrollX();
        return scrollX == 0;
    }


    /**
     * 是否超过屏幕的一半
     * @return
     */
    public boolean isHalfDisplaying() {
        int scrollX = getScrollX();
        int width = getWidth();
        return scrollX < width / 2;
    }

    /**
     * 以动画方式滑动到某一个位置（翻屏操作）
     * @param newX
     * @param duration
     */
    public void snapTo(int newX, int duration) {
        int x = getScrollX();
        int delta = newX - x;
        if (duration < 0) {
            duration = 300;
        }
        mScroller.startScroll(x, 0, delta, 0, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (null != mScroller && mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
