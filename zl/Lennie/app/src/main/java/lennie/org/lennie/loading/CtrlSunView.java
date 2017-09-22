package lennie.org.lennie.loading;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.ViewHelper;


import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import lennie.org.lennie.R;


//自动弹球界面
public class CtrlSunView extends FrameLayout {

    private final String TAG = "CtrlSunView";
    private ObjectAnimator mAa; // 圆盘自转动画
    
    private float mBulletGap;
    
    private int mSpeedTime = 900;

    private boolean isCrash = false;
    
    private TargetView mCenterSunCircle;
    
    private SunView small_sunView;
    
    private BulletView mBullet1;
    
    private BulletView mBullet2;
    
    private BulletView mBullet3;
    
    private TextView mGameTip, mGameClick;
    
    private boolean isStart = false;  //是否开始游戏
    
    private boolean isVISIBLE = true;
    
    private boolean isShowFinishTip = false;    //倒计时点击控制
    
    private Timer mTimer;

    private TimerTask mTask;



    public void destroyCtrlSunView(){
        if(null!=mCenterSunCircle){
            mCenterSunCircle = null;
        }
        if(null!=small_sunView){
            small_sunView = null;
        }
        if(null!=mBullet1){
            mBullet1 = null;
        }
        if(null!=mBullet2){
            mBullet2 = null;
        }
        if(null!=mBullet3){
            mBullet3 = null;
        }
        if(null!=mGameTip){
            mGameTip = null;
        }
        if(null!=mGameClick){
            mGameClick = null;
        }
        if(null!=mTimer){
            mTimer.cancel();
            mTimer = null;
        }
        if(null!=mTask){

        }
    }

    public void stopAnia() {
        if (null != mAa) {
            mAa.removeAllListeners();
            mAa.removeAllUpdateListeners();
            mAa.end();
            mAa = null;
        }

        if(null!=small_sunView){
            ViewHelper.setRotation( small_sunView,0);
        }

        if (null != mTimer) {
            mTimer.cancel();
            mTask = null;
            mTimer = null;
        }

        isStart = false;
        isVISIBLE = true;
        isShowFinishTip = false;
    }
    
    public CtrlSunView(Context context) {
        super(context);
    }
    
    public CtrlSunView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public CtrlSunView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void onEventMainThread(BusEvent evt) {
        switch (evt.type) {
            case BusEvent.EVENT_SCALE_ALPHA:
                if (isVISIBLE) {
                    isVISIBLE = false;
                    startLineRun();
                    this.setVisibility(View.VISIBLE);
                    startScale();
                    startTimer();
                    // 游戏引导埋点
                    ADGameManager.getInstance().setGuideShowTD();
                    EventBus.getDefault().post(new BusEvent(BusEvent.EVENT_WEBVIEW_NAVI_IS_SHOW, "n", "game"));
                }
                break;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setBackgroundColor(ADGameManager.BG_COLOR);


        mCenterSunCircle = (TargetView)findViewById(R.id.center_sun_circle);
        small_sunView = (SunView)findViewById(R.id.small_sunView);
        
        mBullet1 = (BulletView)findViewById(R.id.bullet1);
        mBullet2 = (BulletView)findViewById(R.id.bullet2);
        mBullet3 = (BulletView)findViewById(R.id.bullet3);
        
        mGameTip = (TextView)findViewById(R.id.rym_game_tip);
        mGameClick = (TextView)findViewById(R.id.rym_game_click);
        
        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "引导界面点击----倒计时点击 1");
                if (isShowFinishTip) {
                    Log.i(TAG, "引导界面点击----倒计时点击 1");
                    if (null != mAa) {
                        mAa.removeAllListeners();
                        mAa.removeAllUpdateListeners();
                        mAa.end();
                        ViewHelper.setRotation(small_sunView, 0);
                    }
                    isShowFinishTip = false;
                    if (null != mTimer) {
                        mTimer.cancel();
                        mTask = null;
                        mTimer = null;
                    }
                    CtrlSunView.this.setVisibility(View.GONE);
                    EventBus.getDefault().post(new BusEvent(BusEvent.LOADING_SATRT_ANIA, null));
                    // 游戏引导点击
                    ADGameManager.getInstance().setGuideClickTD();
                }
            }
        });
        
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(null!= mBullet1 && null!= mBullet2) {
            mBulletGap = mBullet2.getTop() - mBullet1.getTop();
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

    private void isClick() {
        if (null != mBullet1 ){
            // 射击动画
            mBullet1.resetPos();
            final float cury = ViewHelper.getY(mBullet1);
            final ObjectAnimator oa = ObjectAnimator.ofFloat(mBullet1, "y", cury, cury - 300);
            oa.setInterpolator(new LinearInterpolator());
            oa.setDuration(500);
            oa.addUpdateListener(new AnimatorUpdateListener() {
                                     @Override
                                     public void onAnimationUpdate(ValueAnimator animation) {

                                         if ((Float) animation.getAnimatedValue() > cury - 180
                                                 && (Float) animation.getAnimatedValue() < cury - 120) {
                                             if (null != small_sunView) {
                                                 float f = ViewHelper.getRotation(small_sunView);
                                                 if (f >= 30 || f <= 6) {
                                                     isCrash = true;
                                                     // 播放反弹动画
                                                     float x = ViewHelper.getX(mBullet1);
                                                     float y = ViewHelper.getY(mBullet1);
                                                     oa.cancel();
                                                     mBullet1.clearAnimation();
                                                     ViewHelper.setX(mBullet1, x);
                                                     ViewHelper.setY(mBullet1, y);
                                                     AnimatorSet animSet = new AnimatorSet();
                                                     ObjectAnimator animator = ObjectAnimator.ofFloat(mBullet1, "x", x - 400);
                                                     animator.setDuration(300);
                                                     ObjectAnimator animator2 = ObjectAnimator.ofFloat(mBullet1, "y", y + 200);
                                                     animator2.setDuration(300);
                                                     ObjectAnimator animator3 = ObjectAnimator.ofFloat(mBullet1, "Alpha", 1f, 0f);
                                                     animator3.setDuration(300);
                                                     animSet.play(animator).with(animator2).with(animator3);
                                                     animSet.addListener(new AnimatorListener() {
                                                         @Override
                                                         public void onAnimationStart(Animator animation) {
                                                         }

                                                         @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                                                         @Override
                                                         public void onAnimationEnd(Animator animation) {
                                                             if (null != mBullet1) {
                                                                 mBullet1.setAlpha(1f);
                                                             }
                                                             // 添加子弹
                                                             addBullet(200, 200, 400);
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
                                 }

            );
                oa.addListener(new

                                       AnimatorListener() {

                                           @Override
                                           public void onAnimationStart(Animator animation) {
                                           }

                                           @Override
                                           public void onAnimationEnd(Animator animation) {

                                               // 添加子弹
                                               if (!isCrash) {
                                                   cricleScaleAnimation();
                                                   addBullet(200, 200, 400);
                                               }
                                               isCrash = false;
                                           }

                                           @Override
                                           public void onAnimationCancel(Animator animation) {
                                           }

                                           @Override
                                           public void onAnimationRepeat(Animator animation) {
                                           }

                                       }

                );
                oa.start();
    }
    }
    
    // 球碰撞到中心圆，中心圆变大
    private void cricleScaleAnimation() {
        final ValueAnimator animator = ValueAnimator.ofFloat(1f, 1.03f);
        animator.setDuration(200);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                try {
                    if(null!= mCenterSunCircle) {
                        Object object = arg0.getAnimatedValue();
                        if (null != object) {
                            float i = Float.parseFloat(object.toString());
                            ViewHelper.setScaleX(mCenterSunCircle, i);
                            ViewHelper.setScaleY(mCenterSunCircle, i);
                        }
                    }
                }catch (Exception e){
                    Log.e(TAG,e.getMessage());
                }
            }
        });
        animator.start();
        
    }
    
    // 添加子弹动画
    private void addBullet(int time1, int time2, int time3) {
        if(null != mBullet1 && null != mBullet2 && null!=mBullet3) {
            mBullet1.resetPos();
            TranslateAnimation ta1 = new TranslateAnimation(0, 0, mBulletGap, 0);
            ta1.setInterpolator(new LinearInterpolator());
            ta1.setFillAfter(true);
            ta1.setDuration(time1);

            mBullet2.resetPos();
            TranslateAnimation ta2 = new TranslateAnimation(0, 0, mBulletGap, 0);
            ta2.setInterpolator(new LinearInterpolator());
            ta2.setFillAfter(true);
            ta2.setDuration(time2);

            mBullet3.resetPos();
            TranslateAnimation ta3 = new TranslateAnimation(0, 0, mBulletGap, 0);
            ta3.setInterpolator(new LinearInterpolator());
            ta3.setFillAfter(true);
            ta3.setDuration(time3);

            mBullet1.startAnimation(ta1);
            mBullet2.startAnimation(ta2);
            mBullet3.startAnimation(ta3);

            isShowFinishTip = true;
        }
    }
    

    
    private void startTimer() {

            mTimer = new Timer();
            mTask = new TimerTask() {

                @Override
                public void run() {
                    if (isStart) {
                        handler.sendMessage(handler.obtainMessage());
                    }

                }
            };
            mTimer.schedule(mTask, 1000, 2000);

    }

    private void startScale() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(600);
        animator.start();
        animator.addUpdateListener(new AnimatorUpdateListener() {
            
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {

                float f = (Float)arg0.getAnimatedValue();
                if (small_sunView!=null && small_sunView.getVisibility() != View.VISIBLE) {
                    small_sunView.setVisibility(View.VISIBLE);
                }
                if(small_sunView !=null){
                    small_sunView.setScaleX(f);
                    small_sunView.setScaleY(f);
                    small_sunView.setAlpha(f);
                }

                if (mCenterSunCircle!=null && mCenterSunCircle.getVisibility() != View.VISIBLE) {
                    mCenterSunCircle.setVisibility(View.VISIBLE);
                }
                if(mCenterSunCircle != null){
                    mCenterSunCircle.setScaleX(f);
                    mCenterSunCircle.setScaleY(f);
                    mCenterSunCircle.setAlpha(f);
                }


            }
        });
        animator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                if (null != mBullet1 && null!= mBullet2 && null!= mBullet3 && null!= mGameTip && null!=mGameClick ) {
                    mBullet1.setVisibility(View.VISIBLE);
                    mBullet2.setVisibility(View.VISIBLE);
                    mBullet3.setVisibility(View.VISIBLE);
                    isStart = true;
                    addBullet(100, 300, 500);
                    mGameTip.setVisibility(View.VISIBLE);
                    mGameClick.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator arg0) {

            }
        });
    }
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            isClick();
        };
    };

    private void startLineRun() {
        if (null!= small_sunView) {
            // 圆盘自转动画
            mAa = ObjectAnimator.ofFloat(small_sunView, "rotation", 36f);
            mAa.setDuration(mSpeedTime);
            mAa.setInterpolator(new LinearInterpolator());
            mAa.setRepeatCount(-1);
            mAa.setRepeatMode(ObjectAnimator.RESTART);
            mAa.start();
        }
    }
    
}
