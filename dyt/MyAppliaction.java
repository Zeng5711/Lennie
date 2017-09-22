package com.hxyd.dyt;

import android.annotation.TargetApi;
import android.app.ActivityManager;
//import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.db.DBHelp;
import com.hxyd.dyt.common.uitl.AndroidLogAdapter;
import com.hxyd.dyt.common.uitl.Files;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.main.presenter.ApplictionP;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by win7 on 2017/3/2.
 */

public class MyAppliaction extends DefaultApplicationLike {

    public MyAppliaction(Application application, int tinkerFlags,
                         boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                         long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        String processName = getProcessName(getApplication(), android.os.Process.myPid());
        if (processName != null) {
            boolean defaultProcess = processName.equals("com.hxyd.dyt");
            if (defaultProcess) {
                mContext = getApplication();
                try {
                    Class.forName("android.os.AsyncTask");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                }

                boolean isDebug = true;// 设置开启日志,发布时请关闭日志
                LogLevel logLevel = LogLevel.FULL;//FULL 开 ，NONE 关；
                if (!BuildConfig.LOG_DEBUG) {
                    isDebug = false;
                    logLevel = LogLevel.NONE;
                }
                DBHelp.getInstance().initDB(mContext);
                LeakCanary.install(getApplication());
                // 初始化极光推送
                JPushInterface.setDebugMode(isDebug);
                // 初始化 JPush
                JPushInterface.init(mContext);
                Logger.init(Constant.TAG)
                        .methodCount(3)
                        .hideThreadInfo()
                        .logLevel(logLevel)
                        .methodOffset(2)
                        .logAdapter(new AndroidLogAdapter());

                initBugly(isDebug);
                initMobclickAgent(isDebug);
                double size = Files.getAutoFileOrFilesSize(Files.getStorageDirectory());
                if (size > Constant.CACHE_SIZE) {
                    Files.deleteExtraFiles(Files.getStorageDirectory());
                }


            }
        }
    }

    private void initMobclickAgent(boolean isDebug) {
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(mContext, BuildConfig.MOBCLICKAGENT_APPID, "dyt"));
        MobclickAgent.setDebugMode(isDebug);
    }

    private void initBugly(boolean isDebug) {
        // 获取当前包名
        String packageName = mContext.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mContext);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppChannel("hxyd_dyt");  //设置渠道
        strategy.setAppVersion(Tools.getVersionName());      //App的版本
        strategy.setAppPackageName(packageName);  //App的包名
        // 初始化Bugly
        Bugly.init(mContext, BuildConfig.BUGLY_APPID, isDebug, strategy);
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }


    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader;
        reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

}
