package com.hxyd.dyt.common.uitl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.hxyd.dyt.MyAppliaction;

import static android.content.ContentValues.TAG;

/**
 * Created by win7 on 2017/3/2.
 */

public class Tools {

    public static void makeText(String str) {
        Toast.makeText(MyAppliaction.getContext(), str, Toast.LENGTH_SHORT).show();
    }

    public static String getString(int id) {
        return MyAppliaction.getContext().getResources().getString(id);
    }

    public static String getVersionName() {
        String version = "";
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = MyAppliaction.getContext().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(MyAppliaction.getContext().getPackageName(), 0);
            version = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    public static int getVersionCode() {
        int version = 1;
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = MyAppliaction.getContext().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(MyAppliaction.getContext().getPackageName(), 0);
            version = packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    public static void inputMethodManager() {
        InputMethodManager imm = (InputMethodManager) MyAppliaction.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void inputMethodManager(View view) {
        InputMethodManager imm = (InputMethodManager) MyAppliaction.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    public static float getDisplayDensity(Context context) {
        float density = 1.0f;
        if (context != null) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            density = metrics.density;
        }
        return density;
    }

    public static int getwidthPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static boolean isSupportABI() {
        boolean isSupportABI = true;
        try {
            String CPU_ABI = android.os.Build.CPU_ABI;
            if ("mips".equals(CPU_ABI)
                    || "mips64".equals(CPU_ABI)
                    || "x86".equals(CPU_ABI)
                    || "x86_64".equals(CPU_ABI)) {
                isSupportABI = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isSupportABI;
    }
}
