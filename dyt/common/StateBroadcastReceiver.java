package com.hxyd.dyt.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hxyd.dyt.common.uitl.Tools;

/**
 * Created by win7 on 2017/3/20.
 */

public class StateBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifiState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();//获取wifi网络状态
        NetworkInfo.State mobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();//获取移动数据网络状态
        //没有执行return,则说明当前无网络连接
        if (wifiState == NetworkInfo.State.DISCONNECTED && mobileState == NetworkInfo.State.DISCONNECTED) {
            Tools.makeText("当前网络不可用，请重新检查网络设置");
        }
    }
}
