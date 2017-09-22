package lennie.org.lennie.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
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

public class AView extends LinearLayout {

    private final String TAG = "AView";
    private View view;
    private Scroller mScroller;

    public AView(Context context) {
        this(context, null);
    }

    public AView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        setBackgroundColor(Color.WHITE);

        mScroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view = LayoutInflater.from(context).inflate(R.layout.aview, null);
        this.addView(view, layoutParams);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "AView_Click", Toast.LENGTH_SHORT).show();
            }
        });

//        mScroller.startScroll();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public boolean isScrolling() {
        int scrollX = getScrollX();
        return scrollX != 0;
    }

    public boolean isAllDisplaying() {
        int scrollX = getScrollX();
        return scrollX == 0 ;
    }

    public boolean pointInViews(float localX, float localY) {

        int top = this.getTop();
        boolean r;
        r = localX <= view.getRight() && localX >= view.getLeft() && localY <= view.getBottom() + top && localY >= view.getTop() + top;
        return r;
    }

    public void snapTo(int newX, int duration) {
        int x = getScrollX();
        int delta = newX - x;

//        if (delta < 0) {
//            delta = 0;
//        }

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
