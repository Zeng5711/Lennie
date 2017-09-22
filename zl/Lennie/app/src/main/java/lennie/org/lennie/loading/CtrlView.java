package lennie.org.lennie.loading;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import de.greenrobot.event.EventBus;
import lennie.org.lennie.R;


//玩游戏界面及其逻辑
public class CtrlView extends FrameLayout {

    private final String TAG = "CtrlView";
    private ObjectAnimator mAa; // 圆盘自转动画
    private float mBulletGap;

    private boolean isCrash = false; // 是否有撞出球

    private boolean isClickAble = false; // 是否可点击，防止暴力点击

    private long mLastClickTime = 0;

    private int mMaxNumber = 0;

    private ObjectAnimator mLvbgOa;

    private ObjectAnimator mCircleViewOa;

    private ObjectAnimator mArcViewOa;

    private int mHitBall = 0;

    private int mMaxTime = 1300; // 游戏最大时间

    private int mMinTime = 360; // 游戏最小时间

    private int mSpeedTime = 900;

    private int[] mColors;

    private int mColorNum = 1;

    private View lineView;

    private LineBGView lvbg;

    private BulletView bullet1;

    private BulletView bullet2;

    private BulletView bullet3;

    private View center_circle;

    private TextView tv,max_number;

    private CircleView circleView;

    private ArcView arcView;

    private SunView sunView;

    public void destroyCtrlView(){
        if(null != lineView){
            lineView = null;
        }
        if(null != lvbg){
            lvbg = null;
        }
        if(null != bullet1){
            bullet1 = null;
        }
        if(null != bullet2){
            bullet2 = null;
        }
        if(null != bullet3){
            bullet3 = null;
        }
        if(null != center_circle){
            center_circle = null;
        }
        if(null!=sunView){
            sunView = null;
        }
        if(null!=arcView){
            arcView = null;
        }
        if(null!= circleView){
            circleView = null;
        }
        if(null!=tv){
            tv = null;
        }
        if(null!=max_number){
            max_number = null;
        }


    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void stopAnia() {
        isCrash = false;
        isClickAble = false;
        if (null != mAa) {
            mAa.removeAllListeners();
            mAa.removeAllUpdateListeners();
            mAa.end();
            mAa = null;
        }

        if(null != sunView){
            sunView.setRotation(0);
        }
        if (null != mLvbgOa) {
            mLvbgOa.removeAllListeners();
            mLvbgOa.removeAllUpdateListeners();
            mLvbgOa.end();
            mLvbgOa = null;
        }
        if(null!=findViewById(R.id.lineBGView)){
            ((LineBGView) findViewById(R.id.lineBGView)).setDrawWidth(0);
        }
        if (null != mCircleViewOa) {
            mCircleViewOa.removeAllListeners();
            mCircleViewOa.removeAllUpdateListeners();
            mCircleViewOa.end();
            mCircleViewOa = null;
        }
        if(null!=circleView){
            circleView.setDrawAngle(0);
        }
        if (null != mArcViewOa) {
            mArcViewOa.removeAllListeners();
            mArcViewOa.removeAllUpdateListeners();
            mArcViewOa.end();
            mArcViewOa = null;
        }
        if(null != arcView){
            arcView.setDrawAngle(0);
        }

        if(null!= center_circle && null!=mColors && mColors.length>0) {
            ((TargetView) center_circle).setColor(mColors[0]);
        }

    }

    public CtrlView(Context context) {
        super(context);
    }

    public CtrlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CtrlView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setBackgroundColor(Color.WHITE);

        lineView = findViewById(R.id.lineView);
        lvbg = (LineBGView) findViewById(R.id.lineBGView);

        bullet1 = (BulletView) findViewById(R.id.bullet1);
        bullet2 = (BulletView) findViewById(R.id.bullet2);
        bullet3 = (BulletView) findViewById(R.id.bullet3);

        center_circle = findViewById(R.id.center_circle);
        circleView = (CircleView)findViewById(R.id.circleView);
        arcView = (ArcView)findViewById(R.id.arcView);
        sunView =  (SunView)findViewById(R.id.sunView);

        tv = (TextView) findViewById(R.id.tv_number);
        max_number = ((TextView) findViewById(R.id.max_number));

        ((TextView) findViewById(R.id.max_tv)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.max_number)).setTextColor(Color.BLACK);

        mColors =
                new int[]{0xff4a90e2, 0xff468dab, 0xff04b78b, 0xffb8e986, 0xfff8e71c, 0xfff5a623, 0xffd00252, 0xffc301cb,
                        0xff9013fe};
        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log.i(TAG, "游戏界面点击----倒计时点击1 ");
                if (isFastDoubleClick(400)) {
                    return;
                }
                if (isClickAble) {
                    Log.i(TAG, "引导界面点击----倒计时点击 2");
                    isClick();
                }

            }
        });

        arcView.setVisibility(View.GONE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mBulletGap = findViewById(R.id.bullet2).getTop() - findViewById(R.id.bullet1).getTop();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setRotateSpeed() {
        upTextView(mHitBall);

        //游戏点击埋点
        ADGameManager.getInstance().setGameClickTD(mHitBall + "");

        if (mHitBall <= 30) {
            if (mSpeedTime != 0) {
                mSpeedTime = 1000 / (40 + mHitBall * 2) * 36;
            } else {
                mSpeedTime = mMaxTime;
            }
        } else {
            mSpeedTime = mMinTime;
        }
        if (mAa != null) {
            mAa.setDuration(mSpeedTime);
        }
    }

    public void startArcRun() {

        // 轨道进场
        // 进场轨道动画
        if (null != lvbg) {
            lvbg.postDelayed(new Runnable() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void run() {
                    ViewAcceleration.viewAcceleration(lvbg);
                    mLvbgOa = ObjectAnimator.ofInt(lvbg, "drawWidth", lvbg.getMeasuredWidth());
                    mLvbgOa.setDuration(500);
                    mLvbgOa.addUpdateListener(new AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            if ((Integer) animation.getAnimatedValue() == lvbg.getMeasuredWidth()) {

                                if (null != lineView) {
                                    lineView.setVisibility(View.VISIBLE);
                                }
                                if (null != circleView) {
                                    circleView.setVisibility(View.VISIBLE);
                                    mCircleViewOa = ObjectAnimator.ofInt(circleView, "drawAngle", 315);
                                    mCircleViewOa.setDuration(2000);
                                    mCircleViewOa.addUpdateListener(new AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            if ((Integer) animation.getAnimatedValue() == 315) {
                                                if (null != arcView) {
                                                    mArcViewOa = ObjectAnimator.ofInt(arcView, "drawAngle", 60);
                                                    mArcViewOa.addUpdateListener(new AnimatorUpdateListener(){

                                                        @Override
                                                        public void onAnimationUpdate(ValueAnimator animation) {
                                                            if ((Integer) animation.getAnimatedValue() >= 3) {
                                                                if(arcView.getVisibility()!=View.VISIBLE) {
                                                                    arcView.setVisibility(View.VISIBLE);
                                                                }
                                                            }
                                                        }
                                                    });
                                                    mArcViewOa.setDuration(100);
                                                    mArcViewOa.start();
                                                }
                                            }
                                        }
                                    });
                                    mCircleViewOa.start();
                                }
                            }
                        }
                    });
                    mLvbgOa.addListener(new AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            isClickAble = false;
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isClickAble = true;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            isClickAble = true;

                        }
                    });
                    mLvbgOa.start();
                }
            }, 100);

        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void isClick() {
        if(null!= bullet1) {
            // 射击动画
            bullet1.resetPos();
            final float cury = bullet1.getY();
            final ObjectAnimator oa = ObjectAnimator.ofFloat(bullet1, "y", cury, cury - 480);
            oa.setInterpolator(new LinearInterpolator());
            oa.setDuration(250);
            oa.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float fValue = (Float) animation.getAnimatedValue();
                    if (fValue > cury - 320
                            && fValue < cury - 180) {
                        if (null!=sunView) {
                            float f = sunView.getRotation();
//                    Log.i(TAG, "---弹出---1 " + f);
                            if (f >= 24 || f <= 6) {
//                        Log.i(TAG, "---弹出---2 " + f);
                                isCrash = true;
                                mHitBall = 0;
                                // 重置速率
                                setRotateSpeed();
                                // 播放反弹动画
                                float x = bullet1.getX();
                                float y = bullet1.getY();
                                oa.cancel();
                                bullet1.clearAnimation();
                                bullet1.setX(x);
                                bullet1.setY(y);
                                AnimatorSet animSet = new AnimatorSet();
                                ObjectAnimator animator = ObjectAnimator.ofFloat(bullet1, "x", x - 500);
                                animator.setDuration(400);
                                ObjectAnimator animator2 = ObjectAnimator.ofFloat(bullet1, "y", y + 450);
                                animator2.setDuration(400);
                                ObjectAnimator animator3 = ObjectAnimator.ofFloat(bullet1, "alpha", 1f, 0f);
                                animator2.setDuration(400);
                                animSet.play(animator).with(animator2).with(animator3);
                                animSet.addListener(new AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        if (null != bullet1) {
                                            bullet1.setAlpha(1f);
                                        }
                                        // 添加子弹
                                        addBullet();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                });
                                animSet.start();
                            }
                        }
                    }
                }
            });
            oa.addListener(new AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    isCrash = false;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    // 加分,并加快圆盘转动速度
                    if (!isCrash) {
                        mHitBall++;
                        cricleScaleAnimation();
                        setCricleViewColor();
                    }
                    setRotateSpeed();
                    // 添加子弹
                    addBullet();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }

            });
            oa.start();
        }
    }


    // 添加子弹动画
    private void addBullet() {
        if(null != bullet1 && null != bullet2 && null!=bullet3) {

        bullet1.resetPos();
        TranslateAnimation ta1 = new TranslateAnimation(0, 0, mBulletGap, 0);
        ta1.setInterpolator(new LinearInterpolator());
        ta1.setFillAfter(true);
        ta1.setDuration(100);

        bullet2.resetPos();
        TranslateAnimation ta2 = new TranslateAnimation(0, 0, mBulletGap, 0);
        ta2.setInterpolator(new LinearInterpolator());
        ta2.setFillAfter(true);
        ta2.setDuration(100);

        bullet3.resetPos();
        TranslateAnimation ta3 = new TranslateAnimation(0, 0, mBulletGap, 0);
        ta3.setInterpolator(new LinearInterpolator());
        ta3.setFillAfter(true);
        ta3.setDuration(200);
        bullet1.startAnimation(ta1);
        bullet2.startAnimation(ta2);
        bullet3.startAnimation(ta3);

        }
    }

    /**
     * TODO改变击球后圆的颜色
     *
     * @return void
     * @throw
     */
    private void setCricleViewColor() {
        if (mColorNum == mColors.length) {
            mColorNum = 1;
        } else {
            mColorNum++;
        }
        if(null!=center_circle) {
            ((TargetView) center_circle).setColor(mColors[mColorNum - 1]);
        }
    }

    private void upTextView(final int num) {
        if(null!= tv) {
            tv.setText("" + num);
            if (num > mMaxNumber) {
                mMaxNumber = num;
                setMaxNumber();
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(BusEvent evt) {
        switch (evt.type) {
            case BusEvent.LOADING_SATRT_ANIA:
                this.setVisibility(View.VISIBLE);
                scrle();
                startLineRun();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void scrle() {
        ViewAcceleration.viewAcceleration(this);
        ValueAnimator animator = ValueAnimator.ofFloat(0.6f, 1f);
        animator.setDuration(300);
        animator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                float f = (Float) arg0.getAnimatedValue();

                if(null!= lineView && null!=center_circle && null!= bullet1 && null!= bullet2 && null!= bullet3) {
                    lineView.setScaleX(f);
                    lineView.setScaleY(f);

                    center_circle.setScaleX(f);
                    center_circle.setScaleY(f);

                    bullet1.setScaleX(f);
                    bullet1.setScaleY(f);

                    bullet2.setScaleX(f);
                    bullet2.setScaleY(f);

                    bullet3.setScaleX(f);
                    bullet3.setScaleY(f);
                }
            }
        });
        animator.start();
        animator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                startArcRun();

            }

            @Override
            public void onAnimationCancel(Animator arg0) {

            }
        });

    }

    // 球碰撞到中心圆，中心圆变大
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void cricleScaleAnimation() {
        if(null!= center_circle) {
            ViewAcceleration.viewAcceleration(this);
            final ValueAnimator animator = ValueAnimator.ofFloat(1f, 1.03f);
            animator.setDuration(200);
            animator.setTarget(((TargetView) center_circle));
            animator.addUpdateListener(new AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator arg0) {
                    try {
                        Object object = arg0.getAnimatedValue();
                        if (null != object && null != center_circle) {
                            float i = Float.parseFloat(object.toString());
                            ViewHelper.setScaleX(((TargetView) center_circle), i);
                            ViewHelper.setScaleY(((TargetView) center_circle), i);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            });
            animator.start();
        }

    }


    /**
     * 是否在设定时间内双击
     *
     * @param intervalTime 设定的双击有效时间
     * @return
     */
    private boolean isFastDoubleClick(long intervalTime) {
        long time = System.currentTimeMillis();
        long timeD = time - mLastClickTime;
        if (0 < timeD && timeD < intervalTime) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }


    private void setMaxNumber() {
        if(null!= max_number) {
            max_number .setText("" + mMaxNumber);
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void startLineRun() {
        if(null!=lineView && null != sunView) {
            lineView.setVisibility(View.INVISIBLE);
            // 圆盘自转动画
            ViewAcceleration.viewAcceleration(this);
            mAa = ObjectAnimator.ofFloat(sunView, "rotation", 36f);
            mAa.setDuration(mSpeedTime);
            mAa.setInterpolator(new LinearInterpolator());
            mAa.setRepeatCount(-1);
            mAa.setRepeatMode(1);
            mAa.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (null != sunView && null != lineView) {
                        float or = ((SunView) sunView).getOuterRaius();
                        int w =
                                (int) (Math.sin(Math.toRadians((Float) animation.getAnimatedValue())) * or);
                        // 圆盘转动时上边的竖线跟着横向移动
                        ((LineView) lineView).scrollTo(-w, 0);
                    }

                }
            });
            mAa.start();
        }
    }

}
