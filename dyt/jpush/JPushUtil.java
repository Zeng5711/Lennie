package com.hxyd.dyt.jpush;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.hxyd.dyt.MyAppliaction;
import com.orhanobut.logger.Logger;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 极光推送 设置别名和Tags
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/1/15
 */
public class JPushUtil {

    private Context mContext;

    private static volatile JPushUtil instance;

    private JPushUtil(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public static JPushUtil getInstance(Context mContext) {
        if (instance == null) {
            synchronized (JPushUtil.class) {
                if (instance == null) {
                    instance = new JPushUtil(mContext);
                }
            }
        }
        return instance;
    }

    public void setTag(String tag) {
        if (JPushInterface.isPushStopped(mContext)) {
            JPushInterface.resumePush(mContext);
        }
        try {
            // 检查 tag 的有效性
            if (TextUtils.isEmpty(tag)) {
                return;
            }

            // ","隔开的多个 转换成 Set
            String[] sArray = tag.split(",");
            Set<String> tagSet = new LinkedHashSet<String>();
            for (String sTagItme : sArray) {
                if (!isValidTagAndAlias(sTagItme)) {
                    return;
                }
                tagSet.add(sTagItme);
            }

            //调用JPush API设置Tag
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setAlias(String alias) {
//        if (TextUtils.isEmpty(alias)) {
//            return;
//        }
//
//        if (!isValidTagAndAlias(alias)) {
//            return;
//        }
        //调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, TextUtils.isEmpty(alias)?"":alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            try {
                String logs = "";
                switch (code) {
                    case 0:
                        logs = "Set tag and alias success " + alias;
                        Logger.d(logs);
                        break;

                    case 6002:
                        logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                        Logger.d(logs);
//                        if (isConnected(mContext)) {
                            mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
//                        } else {
//                            Logger.d("No network");
//                        }
                        break;

                    default:
                        logs = "Failed with errorCode = " + code;
                        Logger.e(logs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            try {
                String logs = "";
                switch (code) {
                    case 0:
                        logs = "Set tag and alias success";
                        Logger.d(logs);
                        break;

                    case 6002:
                        logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                        Logger.d(logs);
                        if (isConnected(mContext)) {
                            mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                        } else {
                            Logger.d("No network");
                        }
                        break;

                    default:
                        logs = "Failed with errorCode = " + code;
                        Logger.e(logs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    /** 指定用户发送推送消息 */
    private static final int MSG_SET_ALIAS = 1001;
    /** 指定TAG发送推送消息 */
    private static final int MSG_SET_TAGS = 1002;

    /*private static class MyHandler extends Handler {
        private WeakReference<JPushUtil> activityWeakReference;

        public MyHandler(JPushUtil activity) {
            activityWeakReference = new WeakReference<JPushUtil>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JPushUtil activity = activityWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case MSG_SET_ALIAS:
                        Logger.d("Set alias in handler.");
                        JPushInterface.setAliasAndTags(activity.mContext, (String) msg.obj, null, activity.mAliasCallback);
                        break;

                    case MSG_SET_TAGS:
                        LogUtil.d("Set tags in handler.");
                        JPushInterface.setAliasAndTags(activity.mContext, null, (Set<String>) msg.obj, activity.mTagsCallback);
                        break;

                    default:
                        LogUtil.d("Unhandled msg - " + msg.what);
                }
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(instance);*/

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Logger.d("Set alias in handler." + (String) msg.obj);
                    JPushInterface.setAliasAndTags(MyAppliaction.getContext(), (String) msg.obj , null, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    Logger.d("Set tags in handler.");
                    JPushInterface.setAliasAndTags(MyAppliaction.getContext(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;

                default:
                    Logger.d("Unhandled msg - " + msg.what);
            }
        }
    };

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]*$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public void closeJPush() {
        if (!JPushInterface.isPushStopped(mContext)) {
            JPushInterface.stopPush(mContext);
        }
        JPushInterface.onKillProcess(mContext);
    }

    /**
     *设置通知提示方式 - 基础属性
     */
    /*private void setStyleBasic(){
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(mContext);
        builder.statusBarDrawable = R.drawable.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
        JPushInterface.setPushNotificationBuilder(1, builder);
        Toast.makeText(mContext, "Basic Builder - 1", Toast.LENGTH_SHORT).show();
    }*/


    /**
     *设置通知栏样式 - 定义通知栏Layout
     */
    /*private void setStyleCustom(){
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(mContext,R.layout.customer_notitfication_layout,R.id.icon, R.id.title, R.id.text);
        builder.layoutIconDrawable = R.drawable.ic_launcher;
        builder.developerArg0 = "developerArg2";
        JPushInterface.setPushNotificationBuilder(2, builder);
        Toast.makeText(mContext,"Custom Builder - 2", Toast.LENGTH_SHORT).show();
    }*/
}
