package com.hxyd.dyt.common.uitl;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权工具类
 * <p>
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2017/1/15
 */
public class PermissionUtils {

    // private static final String TAG = "123456";
    /**
     * 请求授权 录音 返回码。等同于跳转页面的resultcode
     */
    public static final int CODE_RECORD_AUDIO = 0;
    /**
     * 请求授权 通讯录 返回码。等同于跳转页面的resultcode
     */
    public static final int CODE_READ_CONTACTS = 1;
    /**
     * 请求授权 手机状态 返回码。等同于跳转页面的resultcode
     */
    public static final int CODE_READ_PHONE_STATE = 2;
    /**
     * 请求授权 手机状态 返回码。等同于跳转页面的resultcode
     */
    public static final int CODE_READ_CALL_LOG = 3;
    /**
     * 请求授权 相机 返回码。等同于跳转页面的resultcode
     */
    public static final int CODE_CAMERA = 4;
    /**
     * 请求授权 定位 返回码。等同于跳转页面的resultcode
     */
    public static final int CODE_ACCESS_FINE_LOCATION = 5;
    /**
     * 请求授权 定位 返回码。等同于跳转页面的resultcode
     */
    public static final int CODE_ACCESS_COARSE_LOCATION = 6;
    /**
     * 请求授权 存储 返回码。等同于跳转页面的resultcode
     */
    public static final int CODE_READ_EXTERNAL_STORAGE = 7;
    /**
     * 请求授权 存储 返回码。等同于跳转页面的resultcode
     */
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 8;
    /**
     * 请求授权 多个权限 返回码。等同于跳转页面的resultcode
     */
    public static final int CODE_MULTI_PERMISSION = 100;

    //    6.0权限的基本知识，以下是需要单独申请的权限，共分为9组，每组只要有一个权限申请成功了，就默认整组权限都可以使用了。
    //    group:android.permission-group.CALENDAR
    //    permission:android.permission.READ_CALENDAR
    //    permission:android.permission.WRITE_CALENDAR
    //
    //    group:android.permission-group.SENSORS
    //    permission:android.permission.BODY_SENSORS

    //    group:android.permission-group.MICROPHONE
    //    permission:android.permission.RECORD_AUDIO
    //
    //    group:android.permission-group.SMS
    //    permission:android.permission.READ_SMS
    //    permission:android.permission.RECEIVE_WAP_PUSH
    //    permission:android.permission.RECEIVE_MMS
    //    permission:android.permission.RECEIVE_SMS
    //    permission:android.permission.SEND_SMS
    //    permission:android.permission.READ_CELL_BROADCASTS
    //
    //    group:android.permission-group.CONTACTS
    //    permission:android.permission.WRITE_CONTACTS
    //    permission:android.permission.GET_ACCOUNTS
    //    permission:android.permission.READ_CONTACTS
    //
    //    group:android.permission-group.PHONE
    //    permission:android.permission.READ_CALL_LOG
    //    permission:android.permission.READ_PHONE_STATE
    //    permission:android.permission.CALL_PHONE
    //    permission:android.permission.WRITE_CALL_LOG
    //    permission:android.permission.USE_SIP
    //    permission:android.permission.PROCESS_OUTGOING_CALLS
    //    permission:com.android.voicemail.permission.ADD_VOICEMAIL
    //
    //    group:android.permission-group.CAMERA
    //    permission:android.permission.CAMERA
    //
    //    group:android.permission-group.LOCATION
    //    permission:android.permission.ACCESS_FINE_LOCATION
    //    permission:android.permission.ACCESS_COARSE_LOCATION
    //
    //    group:android.permission-group.STORAGE
    //    permission:android.permission.READ_EXTERNAL_STORAGE
    //    permission:android.permission.WRITE_EXTERNAL_STORAGE
    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String PERMISSION_READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static final String PERMISSION_MOUNT_UNMOUNT_FILESYSTEMS = Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS;

    private static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO,
            PERMISSION_READ_CONTACTS,
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_READ_CALL_LOG,
            PERMISSION_CAMERA,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE
    };

    /**
     * 权限开启成功失败回调
     */
    public interface PermissionGrant {
        /**
         * 权限开启
         *
         * @param requestCode CODE_RECORD_AUDIO = 0; CODE_READ_CONTACTS = 1; CODE_READ_PHONE_STATE = 2;
         * @param isSuccess   isSuccess=true 授權成功
         */
        void onPermissionGranted(int requestCode, boolean isSuccess);
    }

    /**
     * 单例
     */
//    private static PermissionUtils instance;
    /**
     * 权限开启成功失败回调
     */
    private static PermissionGrant mPermissionGrant;

    private static class Singletonholder {
        private static PermissionUtils INSTANCE = new PermissionUtils();
    }


    public static synchronized PermissionUtils getInstance() {
        return  Singletonholder.INSTANCE;
    }

    /**
     * 申请授权
     *
     * @param activity    activity
     * @param requestCode request code,  if you need request CAMERA permission,parameters is PermissionUtils.CODE_CAMERA
     */
    public void requestPermission(Activity activity, int requestCode,PermissionGrant permissionGrant) {
        if (activity == null || requestCode < 0 || requestCode >= requestPermissions.length) {
            return;
        }

        this.mPermissionGrant = permissionGrant;
        // 如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。permissionGrant.onPermissionGranted(requestCode);
        //        if (Build.VERSION.SDK_INT < 23) {
        //            permissionGrant.onPermissionGranted(requestCode);
        //            return;
        //        }
        final String requestPermission = requestPermissions[requestCode];
        int checkSelfPermission;
        try {
            //检查是否有读取XXXX权限
            checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
        } catch (RuntimeException e) {
            Logger.e(e.getMessage());
            return;
        }

        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            //没有这个权限 请求授权
            ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
        } else {
            //有这个权限
            mPermissionGrant.onPermissionGranted(requestCode, true);
        }
    }

    /**
     * 一次申请多个权限
     */
    public void requestMultiPermissions(Activity activity, String[] permissionsList,PermissionGrant permissionGrant) {
        if (permissionsList == null) {
            return;
        }

        this.mPermissionGrant = permissionGrant;

        if (permissionsList.length > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList, CODE_MULTI_PERMISSION);
        } else {
            mPermissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION, true);
        }
    }

    /**
     * 授权结果处理
     *
     * @param activity     activity
     * @param requestCode  CODE_RECORD_AUDIO = 0; CODE_READ_CONTACTS = 1; CODE_READ_PHONE_STATE = 2; CODE_READ_CALL_LOG = 3; CODE_CAMERA = 4;
     *                     CODE_ACCESS_FINE_LOCATION = 5; CODE_ACCESS_COARSE_LOCATION = 6; CODE_READ_EXTERNAL_STORAGE = 7; CODE_WRITE_EXTERNAL_STORAGE = 8;
     * @param permissions  权限数组
     * @param grantResults 授权结果处理
     */
    public static void requestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (activity == null) {
            return;
        }

        if (requestCode == CODE_MULTI_PERMISSION) {
            requestMultiResult(activity, permissions, grantResults);
            return;
        }

        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            // Log.w(TAG, "requestPermissionsResult illegal requestCode:" + requestCode);
            return;
        }

        //  权限开启结果检查 grantResults[0]==0=PackageManager.PERMISSION_GRANTED的话 权限开启成功
        if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 授权成功 统一处理 permissionGrant.onPermissionGranted回掉方法。
            mPermissionGrant.onPermissionGranted(requestCode, true);

        } else {
            //  权限开启结果检查 grantResults[0]==-1=PackageManager.PERMISSION_DENIED 权限开启失败
            // 授权失败 统一处理 弹出对话框。
            mPermissionGrant.onPermissionGranted(requestCode, false);
        }

    }

    /**
     * 一次申请多个授权的 结果处理
     * <p>
     *
     * @param activity     Activity
     * @param permissions  权限数组
     * @param grantResults 授权结果处理
     */
    private static void requestMultiResult(Activity activity, String[] permissions, int[] grantResults) {

        if (activity == null) {
            return;
        }

        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permissions[i]);
            }
        }

        if (notGranted.size() == 0) {
            mPermissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION, true);
        } else {
            mPermissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION, false);
        }

//        for (int i = 0; i < permissions.length; i++) {
//            for (int j = 0; j < requestPermissions.length; j++) {
//                if (requestPermissions[j].equals(permissions[i])) {
//                    instance.mPermissionGrant.onPermissionGranted(j, grantResults[i] == PackageManager.PERMISSION_GRANTED);
//                }
//            }
//        }
    }
}