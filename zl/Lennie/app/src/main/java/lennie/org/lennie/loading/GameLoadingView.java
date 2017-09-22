package lennie.org.lennie.loading;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;


import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import lennie.org.lennie.R;


//游戏界面及其游戏逻辑控制
public class GameLoadingView extends FrameLayout {

    private final String TAG = "GameLoadingView";

    private RingView mRingView;

    private View mCtrlView;

    private View mCtrlSunView;

    private View mLoadingFinish;

    private View mLoadingFialure;

    private TextView mOfficial;

    private TextView mLoadingFinishNumber;

    private static int mCriclex;

    private static int mCricley = -1;

    private static int mRingR;

    // private boolean isPlayGame = true; // 是否开启玩游戏的小菊花

    private boolean isLoadingFinish = false;

    private final static int LOADING_DOWN = 1;

    private final static int LOADING_FINISH = 2;

    private boolean isloadingFialure = false;

    private int mGameTime = 0; // 游戏时间，埋点

    private long mGameStartTime = 0;

    private long mGameEndTime = 0;

    private ImageButton mImgBtn;

    private int mCloseRight;

    // private boolean isCK = false;

    private Timer mTimer;

    private int mNum;

    private Activity mActivity;

    private Context mContext;

    private boolean isaddBaseView = false;

    // public static boolean isAPI19 = true;

    public final static int RING_MODE = 0; // 小菊花模式

    public final static int RING_AND_GAME_MODE = 1; // 小菊花+游戏模式

    public final static int F_MODE = 2; // F模式

    public final static int RING_AND_OffICAL_MODE = 3;// 小菊花模式+文案提示

    public final static int RING_AND_OffICAL_H5_MODE = 4;// H5小菊花模式+文案提示

    public final static int RING_AND_OffICAL_AND_GAME_MODE = 5;// 小菊花模式+文案提示+游戏

    public GameLoadingView(Context context) {
        super(context);
        init(context);
    }

    // public GameLoadingView(Context context, int openMode) {
    // super(context);
    // ADGameManager.getInstance().setgLoadingMode(openMode);
    // init(context);
    // }

    public GameLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 根据loading模式来开启loading动画
     *
     * @param mode
     */
    public void startLoading(int mode) {
        startLoading(mode, null);
    }

    /**
     * 根据loading模式来开启loading动画
     *
     * @param mode
     * @param value loadingwenan
     */
    public void startLoading(int mode, String value) {
        stopGameLoading();
        // 小于4.0使用默认小菊花模式
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mode = RING_MODE;
            // isAPI19 = false;
        }
        // 保存loading模式
        ADGameManager.getInstance().setgLoadingMode(mode);
        startMode(mode, value);
    }

    private void startMode(int mode, String value) {
        Log.i("startGameView", "=====>" + mode);
        if (this.getVisibility() != View.VISIBLE) {
            this.setVisibility(View.VISIBLE);
        }
        switch (mode) {
            case RING_MODE:
                // 小菊花模式
                startRingModeLoading();
                break;
            case RING_AND_GAME_MODE:
                // 小菊花+游戏模式
                startRingAndGagmeModeLoading();
                break;
            case F_MODE:
                // F模式
                startRingAndGagmeModeLoading();
                break;
            case RING_AND_OffICAL_MODE:
                // 小菊花模式+文案提示
                startOfficialLoading(value, false);
                break;
            case RING_AND_OffICAL_H5_MODE:
                // H5小菊花模式+文案提示
                break;
            case RING_AND_OffICAL_AND_GAME_MODE:
                // 小菊花模式+文案提示+游戏
                startOfficialLoading(value, true);
                break;
        }
    }

    /**
     * 小菊花动画
     */
    private void startRingModeLoading() {
        startRingAnia(true);
    }

    /**
     * 是否开启长loading
     *
     * @param islongAnia
     */
    private void startRingAnia(boolean islongAnia) {
        checkViewStart();
        if (null != mRingView) {
            if (islongAnia) {
                mRingView.startAnia();
            } else {
                mRingView.startRotationAnia();
            }

        }
    }

    /**
     * 小菊花+游戏模式动画
     */
    private void startRingAndGagmeModeLoading() {
        startRingAnia(false);
        if (!isaddBaseView && RING_AND_GAME_MODE == ADGameManager.getInstance().getLoadingnMode()
                || F_MODE == ADGameManager.getInstance().getLoadingnMode()) {
            addBaseView(mActivity);
        }

    }

    /**
     * 检查游戏前View的状态
     */
    private void checkViewStart() {

        if (mRingView.getVisibility() != View.VISIBLE) {
            mRingView.setVisibility(View.VISIBLE);
        }
        if (null != mLoadingFinish && mLoadingFinish.getVisibility() != View.GONE) {
            mLoadingFinish.setVisibility(View.GONE);
        }
        if (null != mLoadingFialure && mLoadingFialure.getVisibility() != View.GONE) {
            mLoadingFialure.setVisibility(View.GONE);
        }

        if (null != mCtrlView && mCtrlView.getVisibility() != View.GONE) {
            ((CtrlView) mCtrlView).stopAnia();
            mCtrlView.setVisibility(View.GONE);
        }

        if (null != mCtrlSunView && mCtrlSunView.getVisibility() != View.GONE) {
            ((CtrlSunView) mCtrlSunView).stopAnia();
            mCtrlSunView.setVisibility(View.GONE);
        }

        if (null != mImgBtn && mImgBtn.getVisibility() != View.GONE) {
            mImgBtn.setVisibility(View.GONE);
        }

        if (null != mOfficial && mOfficial.getVisibility() != View.GONE) {
            mOfficial.setVisibility(View.GONE);
        }

        canelTimer();
    }

    /**
     * 启动文案loading
     *
     * @param value
     * @param isPlayGame
     */
    private void startOfficialLoading(String value, boolean isPlayGame) {

        if (isPlayGame) {
            startRingAndGagmeModeLoading();
        } else {
            startRingAnia(true);
        }

        if (null == mOfficial) {
            addRingMode(mContext);
        }
        if (null != mOfficial && mOfficial.getVisibility() != View.VISIBLE) {
            mOfficial.setVisibility(View.VISIBLE);
        }

        setOfficialText(value);
    }

    /**
     * loadingView加载完成
     */
    public void showLoadingFinish() {
        if (null != mRingView && mRingView.getVisibility() != View.VISIBLE && null != mLoadingFinish
                && mLoadingFinish.getVisibility() != View.VISIBLE && null != mCtrlSunView && null != mCtrlView
                && (mCtrlSunView.getVisibility() == View.VISIBLE || mCtrlView.getVisibility() == View.VISIBLE)) {

            if (null != mLoadingFialure && mLoadingFialure.getVisibility() != View.GONE) {
                mLoadingFialure.setVisibility(View.GONE);
            }
            mLoadingFinish.setVisibility(View.VISIBLE);
            isLoadingFinish = true;
            mNum = 3;
            canelTimer();
            startTimer();
            ADGameManager.getInstance().setGameOverTD();
        } else {
            stopGameLoading();
        }
    }

    /**
     * TODO loadingView加载超时
     */
    public void showLoadingFialure() {
        isloadingFialure = true;
        if (null != mLoadingFinish && mLoadingFinish.getVisibility() != View.GONE) {
            mLoadingFinish.setVisibility(View.GONE);
        }
        if (null != mLoadingFialure && mLoadingFialure.getVisibility() != View.VISIBLE) {
            mLoadingFialure.setVisibility(View.VISIBLE);
        }
        canelTimer();
    }

    /**
     * TODO 关闭按钮事件
     */
    public void closeButton() {
        Log.i("mImgBtn", "===closeButton=============>1");
        if (null != mRingView
                && mRingView.getVisibility() != View.VISIBLE
                && (null != mCtrlSunView && mCtrlSunView.getVisibility() == View.VISIBLE || null != mCtrlView
                && mCtrlView.getVisibility() == View.VISIBLE)) {// &&
            // !isloadingFialure
            // isPlayGame = false;
            stopGameLoading();
            if (!isLoadingFinish) {
                Log.i("mImgBtn", "====closeButton============>2");
                // startGameLoading();
                startMode(RING_MODE, null);
                if (null != mLoadingFialure && mLoadingFialure.getVisibility() == View.VISIBLE
                        && "y".equals(ADGameManager.getInstance().getIsShowNaviBar())
                        && (RING_MODE == ADGameManager.getInstance().getLoadingnMode() || RING_AND_GAME_MODE ==
                        ADGameManager.getInstance().getLoadingnMode())) {
                    EventBus.getDefault().post(new BusEvent(BusEvent.EVENT_WEBVIEW_NAVI_IS_SHOW, "y", "game"));
                }
            }
            Log.i("mImgBtn", "====closeButton============>3");
        }
        // else {
        // if (RING_MODE == ADGameManager.getInstance().getLoadingnMode()||RING_AND_GAME_MODE ==
        // ADGameManager.getInstance().getLoadingnMode()) {
        // ADGameManager.getInstance().isCloseWebView = true;
        // if (null != mRingView) {
        // mRingView.destoryAnimation();
        // }
        // // 关闭WebView
        // EventBus.getDefault().post(new BusEvent(BusEvent.EVENT_CLOSE_WEBVIEW, null));
        // }
        // }
        // 游戏关闭按钮埋点
        ADGameManager.getInstance().setGameCloseButton();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isLoadingFinish) {
            return true;
        } else {
            return false;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isLoadingFinish) {
            loadingFinishClick();
        }
        return true;
    }

    /**
     * TODO 小菊花是否显示
     *
     * @return boolean
     */
    public boolean isShowRingView() {
        if (null != mRingView && mRingView.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }

    // /**
    // * 开启长loadingView
    // */
    // public void startLongLoading(String value) {
    //
    // if(null == mOfficial){
    // addRingMode(mContext);
    // }
    //
    // if (this.getVisibility() != View.VISIBLE) {
    // this.setVisibility(View.VISIBLE);
    // }
    //
    // if (null != mRingView) {
    // mRingView.startAnia();
    // }
    //
    // setOfficialText(value);
    // }
    //

    /**
     * 设置文案
     *
     * @param value
     */
    private void setOfficialText(String value) {
        if (null != mOfficial && !TextUtils.isEmpty(value)) {
            Log.e(TAG, "value = " + value.length());
            if (value.length() > 15) {
                value = value.substring(0, 14);
            }
            mOfficial.setText(value);
        }
    }

    // public void startGameLoading(){
    // startGameLoading(null);
    // }
    //
    // /**
    // * 开启loadingView
    // */
    // public void startGameLoading(String value) {
    // ObjectAnimator.setFrameDelay(0);
    //
    // if (RING_MODE == ADGameManager.getInstance().getLoadingnMode() || F_MODE ==
    // ADGameManager.getInstance().getLoadingnMode()) {
    // addBaseView(mActivity);
    // }
    //
    // if(!TextUtils.isEmpty(value) && null == mOfficial){
    // addRingMode(mContext);
    // setOfficialText(value);
    // }
    //
    // if (null != mLoadingFinish && mLoadingFinish.getVisibility() != View.GONE) {
    // mLoadingFinish.setVisibility(View.GONE);
    // }
    // if (null != mLoadingFialure && mLoadingFialure.getVisibility() != View.GONE) {
    // mLoadingFialure.setVisibility(View.GONE);
    // }
    //
    // if (null != mCtrlView) {
    // ((CtrlView) mCtrlView).stopAnia();
    // mCtrlView.setVisibility(View.GONE);
    // Log.i("destroyGameLoading", "mCtrlView===>");
    // }
    //
    // if(null != mCtrlSunView){
    // ((CtrlSunView) mCtrlSunView).stopAnia();
    // mCtrlSunView.setVisibility(View.GONE);
    // Log.i("destroyGameLoading", "mCtrlSunView===>");
    // }
    //
    //
    // if (this.getVisibility() != View.VISIBLE) {
    // this.setVisibility(View.VISIBLE);
    // }
    //
    // if (null != mRingView && !isCK) {
    // isCK = true;
    // if (mRingView.getVisibility() != View.VISIBLE) {
    // mRingView.setVisibility(View.VISIBLE);
    // }
    // canelTimer();
    //
    // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && isPlayGame) {
    // mRingView.startRotationAnia();
    // } else {
    // mRingView.startAnia();
    // if(null!= mOfficial){
    // mOfficial.setVisibility(View.VISIBLE);
    // }
    // }
    //
    // }
    //
    // }

    /**
     * 销毁loading
     */
    public void destroyLoadingView() {

        if (null != mRingView) {
            mRingView = null;
        }

        if (null != mCtrlView) {
            ((CtrlView) mCtrlView).destroyCtrlView();
            mCtrlView = null;
        }

        if (null != mCtrlSunView) {
            ((CtrlSunView) mCtrlSunView).destroyCtrlSunView();
            mCtrlSunView = null;
        }

        if (null != mLoadingFinish) {
            mLoadingFinish = null;
        }

        if (null != mLoadingFialure) {
            mLoadingFialure = null;
        }

        if (null != mOfficial) {
            mOfficial = null;
        }

        if (null != mLoadingFinishNumber) {
            mLoadingFinishNumber = null;
        }

        if (null != mActivity) {
            mActivity = null;
        }
        if (null != mContext) {
            mContext = null;
        }
        isaddBaseView = false;
    }

    /**
     * 停止loading动画
     */
    public void stopGameLoading() {

        if (null != mRingView && mRingView.getVisibility() != View.VISIBLE) {
            mGameEndTime = System.currentTimeMillis();
            // 埋点记录游戏时长
            if (mGameStartTime != 0) {
                mGameTime = (int) ((mGameEndTime - mGameStartTime) / 1000);
            }
            ADGameManager.getInstance().setGameTimeTD(String.valueOf(mGameTime));
        }

        if (null != mCtrlView) {
            ((CtrlView) mCtrlView).stopAnia();
            mCtrlView.setVisibility(View.GONE);
            Log.i("destroyGameLoading", "mCtrlView===>");
        }

        if (null != mCtrlSunView) {
            ((CtrlSunView) mCtrlSunView).stopAnia();
            mCtrlSunView.setVisibility(View.GONE);
            Log.i("destroyGameLoading", "mCtrlSunView===>");
        }

        if (null != mRingView) {
            mRingView.destoryAnimation();
            if (mRingView.getVisibility() != View.GONE) {
                mRingView.setVisibility(View.GONE);
            }
            // isCK = false;
        }
        if (null != mLoadingFinish && mLoadingFinish.getVisibility() != View.GONE) {
            mLoadingFinish.setVisibility(View.GONE);
        }
        if (null != mLoadingFialure && mLoadingFialure.getVisibility() != View.GONE) {
            mLoadingFialure.setVisibility(View.GONE);
        }

        if (null != mImgBtn && mImgBtn.getVisibility() != View.GONE) {
            mImgBtn.setVisibility(View.GONE);
        }
        if (this.getVisibility() != View.GONE) {
            this.setVisibility(View.GONE);
        }
        if (null != mOfficial && mOfficial.getVisibility() != View.GONE) {
            mOfficial.setVisibility(View.GONE);
        }
        canelTimer();
        // 如果H5隐藏了关闭按钮,则把setIsShowCloseButton设为默认值“y”
        // if ("y".equals(ADGameManager.getInstance().getIsShowCloseButton())) {
        // // 显示webView关闭按钮
        // EventBus.getDefault().post(new BusEvent(BusEvent.EVENT_WEBVIEW_NAVI_IS_SHOW, "y"));
        // } else {
        // ADGameManager.getInstance().setIsShowCloseButton("y");
        // }

        ADGameManager.getInstance().setgLoadingMode(RING_MODE);
        ObjectAnimator.setFrameDelay(33);

        isLoadingFinish = false;
        isloadingFialure = false;
        EventBus.getDefault().post(new BusEvent(BusEvent.EVENT_WEBVIEW_NAVI_IS_SHOW, "y", "game"));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                loadingFinishClick();
            }
        });

    }

    private void loadingFinishClick() {
        if (isLoadingFinish) {
            stopGameLoading();
            // 倒计时点击埋点,todo
            ADGameManager.getInstance().setGameClickTD();
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
                mGameStartTime = System.currentTimeMillis();
                break;
            case BusEvent.LOADING_SHOW_CLOSEBUTTON:

                if (null != mOfficial && mOfficial.getVisibility() != View.GONE) {
                    mOfficial.setVisibility(View.GONE);
                }

                if (null != mImgBtn && mImgBtn.getVisibility() != View.VISIBLE) {
                    mImgBtn.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void init(Context context) {
//        Resources resources = context.getResources();
//        if (null == context || null == resources) {
//            return;
//        }

        Activity activity = (Activity) context;
        this.setBackgroundColor(ADGameManager.BG_COLOR);
        if (null != activity) {

            mActivity = activity;
            mContext = context;

            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            if (null != manager) {
                if (null != manager.getDefaultDisplay()) {
                    manager.getDefaultDisplay().getMetrics(dm);
                }
            }

            if (null != dm) {
                mCriclex = dm.widthPixels / 2;
                // mCricley = (int) (JarUtils.getResources().getDimension(R.dimen.rym_cricler_y));
                // mRingR = (int) (JarUtils.getResources().getDimension(R.dimen.rym_ring_r));
            }

            if (-1 == mCricley && context != null) {
                mCricley = (int) (context.getResources().getDimension(R.dimen.rym_cricler_y));
                mRingR = (int) (context.getResources().getDimension(R.dimen.rym_ring_r));
                // rym_bule_ball = (int) (resources.getColor(R.color.rym_bule_ball));
            } else {
                mCricley = 196;//Tools.dip2px(context, 196);
                mRingR = 30;//Tools.dip2px(context, 30);
            }

            // 小菊花
            mRingView = new RingView(context, mCriclex, mCricley, mRingR, Color.BLACK);
            LayoutParams ringLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            ringLayout.gravity = Gravity.CENTER_HORIZONTAL;
            this.addView(mRingView, ringLayout);

        }
    }

    private void addBaseView(Activity activity) {
        if (null == activity) {
            return;
        }
        LayoutParams viewFlow =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LayoutInflater inflater =  LayoutInflater.from(activity);

        // 小于4.0不执行 游戏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {



            mCtrlSunView = inflater.inflate( R.layout.rym_ctrl_sun_view, null);
            LayoutParams viewFlowLp =
                    new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (null != mCtrlSunView) {
//                this.addView(mCtrlSunView, viewFlowLp);
                activity.getWindow().addContentView(mCtrlSunView, viewFlowLp);
                mCtrlSunView.setVisibility(View.GONE);
            }

            mCtrlView = inflater.inflate( R.layout.rym_game_view, null);
            if (null != mCtrlView) {
//                this.addView(mCtrlView, viewFlowLp);
                activity.getWindow().addContentView(mCtrlView, viewFlowLp);
                mCtrlView.setVisibility(View.GONE);
            }

            mLoadingFinish = inflater.inflate( R.layout.rym_loading_finish, null);
            if (null != mLoadingFinish) {
//                this.addView(mLoadingFinish, viewFlow);
                activity.getWindow().addContentView(mLoadingFinish, viewFlow);
                (mLoadingFinish.findViewById(R.id.loading_finish)).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingFinishClick();
                    }
                });
                mLoadingFinishNumber = (TextView) mLoadingFinish.findViewById(R.id.rym_loading_finish_number);
                mLoadingFinish.setVisibility(View.GONE);
            }

        }

        mLoadingFialure = inflater.inflate( R.layout.rym_loading_failure, null);
        if (null != mLoadingFialure) {
//            this.addView(mLoadingFialure, viewFlow);
            activity.getWindow().addContentView(mLoadingFialure, viewFlow);
            mLoadingFialure.setVisibility(View.GONE);
            (mLoadingFialure.findViewById(R.id.loading_failure)).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // isloadingFialure = false;
                    mLoadingFialure.setVisibility(View.GONE);
                    // 发送EventBus给webView，重新加载
                    // stopGameLoading();
                    EventBus.getDefault().post(new BusEvent(BusEvent.EVENT_WEBVIEW_RETRY, null));
                }
            });
        }

        // if (isShowCloseButton) {
//        int closeWidth = (int) (JarUtils.getResources().getDimension(R.dimen.rym_webview_closebtn_width));
//        mCloseRight = (int) (JarUtils.getResources().getDimension(R.dimen.rym_webview_closebtn_marginRight));
//        int closeTop = (int) (JarUtils.getResources().getDimension(R.dimen.rym_webview_closebtn_marginTop));
//        LayoutParams closelp = new LayoutParams(closeWidth, closeWidth);
//        closelp.gravity = Gravity.RIGHT | Gravity.TOP;
//        closelp.topMargin = closeTop;
//        closelp.rightMargin = mCloseRight;
//        mImgBtn = new ImageButton(activity);
//        mImgBtn.setContentDescription("closeButton"); // 自动化测试标签
//        mImgBtn.setBackgroundDrawable((JarUtils.getResources().getDrawable(R.drawable.rym_anydoor_webview_close_btn)));
//        mImgBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("mImgBtn", "================>");
//                closeButton();
//            }
//        });
//        this.addView(mImgBtn, closelp);
//        activity.getWindow().addContentView(mImgBtn, closelp);
//        mImgBtn.setVisibility(View.GONE);
        // }
        isaddBaseView = true;

    }

    private void addRingMode(Context context) {
        if (null != context ) {
            LayoutParams offLP =
                    new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            offLP.topMargin =
                    mCricley + mRingR + (int) context.getResources().getDimension(R.dimen.rym_gameview_official_to_top);
            mOfficial = new TextView(context);
            mOfficial.setTextColor(Color.BLACK);
            int textSize = (int) context.getResources().getDimension(R.dimen.rym_gameview_official_text_size);

            textSize = px2sp(context, textSize);

            mOfficial.setTextSize(textSize);
            mOfficial.setGravity(Gravity.CENTER);
            this.addView(mOfficial, offLP);
        }

    }

    private int px2sp(Context context, float pxValue) {
        if (null == context) {
            return 0;
        }
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        if (null == metrics) {
            return 0;
        }
        final float fontScale = metrics.scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case LOADING_DOWN:
                    if (null != mLoadingFinishNumber && mNum >= 0) {
                        mLoadingFinishNumber.setText("" + mNum);
                        mNum--;
                    }

                    break;
                case LOADING_FINISH:
                    isLoadingFinish = false;
                    stopGameLoading();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    private void canelTimer() {
        if (null != mTimer) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private void startTimer() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (mNum >= 0) {
                    handler.sendMessage(handler.obtainMessage(LOADING_DOWN));
                } else {
                    handler.sendMessage(handler.obtainMessage(LOADING_FINISH));
                }
            }
        }, 0, 1000);
    }

    public static void onRestoreInstanceState(Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            mCriclex = savedInstanceState.getInt("mCriclex", -1);
            mCricley = savedInstanceState.getInt("mCricley", -1);
            mRingR = savedInstanceState.getInt("mRingR", -1);
        }
    }

    public Bundle onSaveInstanceState(Bundle outState) {

        outState.putInt("mCriclex", mCriclex);
        outState.putInt("mCricley", mCricley);
        outState.putInt("mRingR", mRingR);
        return outState;
    }

}
