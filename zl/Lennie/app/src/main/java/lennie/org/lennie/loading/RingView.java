package lennie.org.lennie.loading;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.os.Build;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import de.greenrobot.event.EventBus;


//小菊花界面
public class RingView extends View {

    private final String TAG = "RingView";
    private int mR;
    private int mColor;

    private Paint mPaint;

    private ObjectAnimator mRotationAnimator;

    private static boolean isStart = false;

    public RingView(Context context, int x, int y, int r, int color) {
        super(context);
        mR = r;
        mColor = color;
        ViewHelper.setY(this,y - r);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);// 去除锯齿
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Cap.ROUND);

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setRotation(float rotation) {
        super.setRotation(rotation);
//        HFLogger.i(TAG, "" + rotation);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.setMeasuredDimension(mR * 2, mR * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ViewAcceleration.viewAcceleration(this);
        for (int i = 0; i < 360; i += 36) {
            canvas.drawLine(mR + (float)Math.sin(Math.toRadians(i)) * mR / 3, mR - (float)Math.cos(Math.toRadians(i))
                    * mR / 3, mR + (float)Math.sin(Math.toRadians(i)) * mR / 3 * 2, mR - (float)Math.cos(Math.toRadians(i))
                    * mR / 3 * 2, mPaint);
        }
        ViewAcceleration.hardwareAccelerated(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        destoryAnimation();
    }

    public void startRotationAnia() {
        if(!isStart) {
            isStart = true;
            mRotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 360f);
            mRotationAnimator.setDuration(5000);
            mRotationAnimator.setInterpolator(new LinearInterpolator());
            mRotationAnimator.addListener(new AnimatorListener() {

                @Override
                public void onAnimationStart(Animator arg0) {

                }

                @Override
                public void onAnimationRepeat(Animator arg0) {

                }

                @Override
                public void onAnimationEnd(Animator arg0) {
                    RingView.this.setVisibility(View.GONE);
                    isStart = false;
                    if (!ADGameManager.getInstance().isCloseWebView) {
                        EventBus.getDefault().post(new BusEvent(BusEvent.EVENT_SCALE_ALPHA, null));
                        EventBus.getDefault().post(new BusEvent(BusEvent.LOADING_SHOW_CLOSEBUTTON, null));
                        //自己打开
//                        if (GameLoadingView.F_MODE != ADGameManager.getInstance().getLoadingnMode()) {
//                            // 隐藏webView关闭按钮
//                            EventBus.getDefault().post(new BusEvent(BusEvent.EVENT_WEBVIEW_NAVI_IS_SHOW, "n"));
//                        }
                    }
                    destoryAnimation();
                }

                @Override
                public void onAnimationCancel(Animator arg0) {
                }
            });
            mRotationAnimator.start();
        }
    }

    public void destoryAnimation() {
        if (null != mRotationAnimator) {
            mRotationAnimator.removeAllListeners();
            mRotationAnimator.removeAllUpdateListeners();
            mRotationAnimator.end();
            isStart = false;
            // if (!ADGameManager.getInstance().isCloseWebView) {
            ViewHelper.setRotation(this, 0);
            // }
//            HFLogger.i(TAG, "RingView====> destory animation");
        }

    }

    /**
     * TODO系统小于4.0启动此动画
     *
     * @throw
     * @return void
     */
    public void startAnia() {
        if(!isStart) {
            isStart = true;
            mRotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 360f);
            mRotationAnimator.setDuration(5000);
            mRotationAnimator.setInterpolator(new LinearInterpolator());
            mRotationAnimator.setRepeatCount(-1);
            mRotationAnimator.setRepeatMode(ObjectAnimator.RESTART);
            mRotationAnimator.start();
        }
    }

}
