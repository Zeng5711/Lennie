package lennie.org.lennie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;


/**
 * Created by win7 on 2017/7/4.
 */

public class BaseViewGroup extends ViewGroup {

    private final String TAG = "BaseViewGroup";

    private AView centerView;
    private BView rigthView;
    private CView leftView;
    private VelocityTracker mVelocityTracker; //手势加速器

    private final int SCREEN_LEFT = 1;  // 左屏
    private final int SCREEN_CENTER = 2; //中屏
    private final int SCREEN_RIGHT = 3;//右屏
    private final int mUnits = 1000;//手势滑动速度
    private final int mDuration = -1;
    private int mTouchSlop; //滑动阀值
    private int mMaximumVelocity; //最大移动速度
    private int mActivePointerId;//活动触摸点
    private int mCurrentScreen = 2; //当前所处屏
    private int mScreenWidth; //屏幕宽度
    private float mLastMotionX; //上一次移动点的位置

    private boolean mPointInViews; //触摸点是否在指定范围内
    private boolean mScrolled; //是否滑动


    public BaseViewGroup(Context context) {
        this(context, null);
    }

    public BaseViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;


        LinearLayout.LayoutParams aLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        centerView = new AView(context);
        this.addView(centerView, aLayoutParams);

        rigthView = new BView(context);
        this.addView(rigthView, layoutParams);


        leftView = new CView(context);
        this.addView(leftView, layoutParams);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int defaultWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int defaultHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

//        MeasureSpec.AT_MOST;  // 父容器已经为子容器确定的大小，子容器应该遵守
//        MeasureSpec.EXACTLY;  // 父容器已经为子容器确定的大小，子容器应该遵守
//        MeasureSpec.UNSPECIFIED;// 父容器对子容器没有做任何限制，子容器可以任意大小

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
//            child.measure(width, height);
        }

        //保存测量结果
        setMeasuredDimension(defaultWidth, defaultHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = this.getWidth();
        int height = this.getHeight();


        centerView.layout(0, 0, width, height);
        rigthView.layout(0, 0, width, height);
        leftView.layout(0, 0, width, height);

        rigthView.scrollTo(-width, 0);
        leftView.scrollTo(width, 0);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断是否在子View上
     * @param ev
     * @return
     */
    public boolean pointInViews(MotionEvent ev) {
        boolean rigthView = false;
        float x = ev.getX();
        float y = ev.getY();
        if (mCurrentScreen == SCREEN_CENTER) {
            return centerView.pointInViews(x, y);
        } else if (mCurrentScreen == SCREEN_LEFT) {
            return leftView.pointInViews(x, y);
        } else if (mCurrentScreen == SCREEN_RIGHT) {
            return this.rigthView.pointInViews(x, y);
        }
        return rigthView;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mPointInViews = pointInViews(ev);
        if (!mPointInViews) {
            return false;
        }
        final float x = ev.getX();
        final float y = ev.getY();
        // 防止事件冲突
        getParent().requestDisallowInterceptTouchEvent(true);
        boolean handle = super.onInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                if (ev.getPointerCount() > 0) {
                    mActivePointerId = ev.getPointerId(0);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(x - mLastMotionX) >= mTouchSlop) {
                    handle = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return handle;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!mPointInViews) {
            return false;
        }

        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        if (null != mVelocityTracker) {
            mVelocityTracker.addMovement(event);
        }

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                mLastMotionX = event.getX();
                mScrolled = false;
                break;
            case MotionEvent.ACTION_MOVE:

                final int activePointerIndex = event.getPointerCount() > mActivePointerId ? event.findPointerIndex(mActivePointerId) : -1;
                if (activePointerIndex == -1) {
                    break;
                }
                int x = (int) event.getX(activePointerIndex);
                int move = (int) mLastMotionX - x;
                if (mScrolled) {
                    mLastMotionX = x;
                    onTouchMove(move);
                } else if (Math.abs(x - mLastMotionX) >= mTouchSlop) {
                    mScrolled = true;
                    onTouchMove(move);
                }

                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                if (mActivePointerId < 0) {
                    break;
                }
                final int activeUpPointerIndex = event.getPointerCount() > mActivePointerId ? event.findPointerIndex(mActivePointerId) : -1;
                if (activeUpPointerIndex == -1) {
                    break;
                }

                if (event.getPointerId(event.getActionIndex()) != mActivePointerId) {
                    break;
                }

                mVelocityTracker.computeCurrentVelocity(mUnits, mMaximumVelocity);
                float mVelocityX = mVelocityTracker.getXVelocity();
                mActivePointerId = -1;
                scrollToNextScreen(mVelocityX);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_CANCEL:
                releaseTracker();

        }

        return true;
    }

    /**
     * 释放速率捕捉器
     */
    private void releaseTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


    private void onTouchMove(int move) {
        if (mCurrentScreen == SCREEN_LEFT) {
            leftView.scrollBy(move, 0);
            centerView.scrollBy(move, 0);
        } else if (mCurrentScreen == SCREEN_CENTER) {
            centerView.scrollBy(move, 0);
            leftView.scrollBy(move, 0);
            rigthView.scrollBy(move, 0);
        } else if (mCurrentScreen == SCREEN_RIGHT) {
            rigthView.scrollBy(move, 0);
            centerView.scrollBy(move, 0);
        }
    }


    /**
     * @param
     */
    private void scrollToNextScreen(float mVelocityX) {
        boolean isScrollToNextScreen = false;
        if (mVelocityX > mUnits) {
            isScrollToNextScreen = false;
        } else if (mVelocityX < -mUnits) {
            isScrollToNextScreen = true;
        } else {
            if (leftView.isHalfDisplaying()) {
                // left
                snapTo(SCREEN_LEFT, mScreenWidth);
            } else if (rigthView.isHalfDisplaying()) {
                // right
                snapTo(SCREEN_RIGHT, mScreenWidth);
            } else {
                snapTo(SCREEN_CENTER, mScreenWidth);
            }

            return;
        }
        int nextScreen;
        if (leftView.isScrolling() || leftView.isAllDisplaying()) {
            nextScreen = SCREEN_LEFT;
        } else if (rigthView.isAllDisplaying()) {
            nextScreen = SCREEN_RIGHT;
        } else {
            nextScreen = SCREEN_CENTER;
        }

        if (isScrollToNextScreen) {
            nextScreen++;
        } else {
            if (nextScreen != SCREEN_LEFT && mCurrentScreen != SCREEN_RIGHT) {
                nextScreen--;
            }
        }

        if (nextScreen > SCREEN_RIGHT) {
            snapTo(nextScreen, mScreenWidth);
        } else if (nextScreen == SCREEN_RIGHT) {
            snapTo(nextScreen, mScreenWidth);
        } else if (nextScreen <= SCREEN_RIGHT && nextScreen >= SCREEN_LEFT) {
            snapTo(nextScreen, mScreenWidth);
        }
    }

    /**
     * 滑动到某一屏
     *
     * @param toCurrent
     * @param x
     */
    private void snapTo(int toCurrent, int x) {

        if (SCREEN_LEFT == toCurrent) {

            leftView.snapTo(0, mDuration);
            centerView.snapTo(-x, mDuration);
            rigthView.snapTo(-x, 0);
            mCurrentScreen = 1;

        } else if (SCREEN_CENTER == toCurrent) {

            centerView.snapTo(0, mDuration);
            rigthView.snapTo(-x, mDuration);
            leftView.snapTo(x, mDuration);
            mCurrentScreen = SCREEN_CENTER;

        } else if (SCREEN_RIGHT == toCurrent) {

            rigthView.snapTo(0, mDuration);
            centerView.snapTo(x, mDuration);
            leftView.snapTo(x, mDuration);
            mCurrentScreen = SCREEN_RIGHT;

        }
    }

}
