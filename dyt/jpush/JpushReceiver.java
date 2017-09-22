package com.hxyd.dyt.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.hxyd.dyt.MyAppliaction;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.main.view.MainActivity;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;


/**
 * 极光广播
 * <p>
 * <br> Author: 叶青
 * <br> Version: 3.0.0
 * <br> Date: 2017/2/8
 */
public class JpushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Logger.e("接收Registration Id:" + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Logger.e("接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Logger.e("接收到推送下来的通知的 Id:" + notifactionId);
            // TODO 消息处理
            Constant.setMessage(true);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Logger.e("[MyReceiver] 用户点击打开了通知");
            // TODO 消息处理
            Intent intent1 = new Intent(MyAppliaction.getContext(),MainActivity.class);
            intent1.putExtras(bundle);
//            intent1.putExtra("ishowMeaage",true);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            MyAppliaction.getContext().startActivity(intent1);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Logger.e("用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            // 接收到了消息，未生成通知
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Logger.e("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);

        }
    }

    /**
     * 处理通知栏的消息的
     * <p>
     * <br> Version: 3.0.0
     * <br> CreateAuthor: 叶青
     * <br> CreateTime: 2017/2/8 17:55
     * <br> UpdateAuthor: 叶青
     * <br> UpdateTime: 2017/2/8 17:55
     * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     *         上下文
     * @param reIntent
     *         收到的intent
     */
    private void handleNotificationOpened(Context context, Intent reIntent) {
        Bundle reBundle = reIntent.getExtras();
        String message = reBundle.getString(JPushInterface.EXTRA_MESSAGE);

        String extras = reBundle.getString(JPushInterface.EXTRA_EXTRA);
        String title = reBundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        int notificationId = reBundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        String alert = reBundle.getString(JPushInterface.EXTRA_ALERT);
        String msgId = reBundle.getString(JPushInterface.EXTRA_MSG_ID);

        if (!TextUtils.isEmpty(extras)) {
            try {
                //解析数据
                JSONObject json = new JSONObject(extras);
                String type = "";
                String id = "";
                String content = "";
                if (json.has("data")) {
                    JSONObject dataJson = new JSONObject(json.optString("data"));
                    if (dataJson.has("type")) {
                        type = dataJson.optString("type");
                    }
                    if (dataJson.has("content")) {
                        content = dataJson.optString("content");
                    }
                    if (dataJson.has("id")) {
                        id = dataJson.optString("id");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e( "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
