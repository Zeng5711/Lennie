package com.hxyd.dyt.login.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.hxyd.dyt.MyAppliaction;
import com.hxyd.dyt.R;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.JsonUtil;
import com.hxyd.dyt.common.uitl.MD5Coder;
import com.hxyd.dyt.common.uitl.SharedPrefsUtil;
import com.hxyd.dyt.common.uitl.Sign;
import com.hxyd.dyt.common.uitl.ThreeDES;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.jpush.JPushUtil;
import com.hxyd.dyt.login.LoginListener;
import com.hxyd.dyt.login.modle.LoginM;
import com.hxyd.dyt.login.modle.entity.UserInfo;
import com.hxyd.dyt.login.modle.in.LoginMI;
import com.hxyd.dyt.login.presenter.in.LoginPI;
import com.hxyd.dyt.login.view.in.LoginVI;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/3/2.
 */

public class LoginP implements LoginPI {

    private LoginVI V;
    private LoginMI M;

    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String ENCODING_KEY = "encoding";


    public LoginP(LoginVI loginVI) {
        this.V = loginVI;
        M = new LoginM();
    }


    @Override
    public void submit(final String userName, String password) {
        if (M != null && V != null) {
            //
            JPushUtil.getInstance(MyAppliaction.getContext()).setAlias(null);

            if (TextUtils.isEmpty(userName)) {
                V.onPrompt(1, Tools.getString(R.string.login_username_err));
                return;
            }
            if (TextUtils.isEmpty(password)) {
                V.onPrompt(2, "密码不能为空");
                return;
            }

//        if(password.length()<8){
//            V.onPrompt(2, "密码不得小于八位");
//            return;
//        }

            V.onShowProgressBar();

            try {


                Map<String, Object> m = new HashMap<String, Object>();
                m.put(USERNAME_KEY, userName);
                m.put(PASSWORD_KEY, password);
                m = Constant.addMap(m);
                String encoding = ThreeDES.encrypt(JsonUtil.packageJsonObject(m), Constant.DES_KEY);


                Map<String, Object> map = new HashMap<String, Object>();
                map.put(ENCODING_KEY, encoding);
                map = Constant.addMap(map);
                Logger.e("login map = >" + map.toString());
                M.submit(map)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new FilterSubscriber<UserInfo>() {

                            @Override
                            public void onNext(final UserInfo userInfo) {
                                Logger.e("data" + userInfo.toString());
                                try {
                                    if (!TextUtils.isEmpty(userInfo.getToken())) {
                                        Constant.setToken(userInfo.getToken());
                                        Logger.e("getAlias = " + userInfo.getAlias());
                                        JPushUtil.getInstance(MyAppliaction.getContext()).setAlias(userInfo.getAlias());
                                        MobclickAgent.onProfileSignIn(userInfo.getUserId());
                                        CrashReport.setUserId(userInfo.getUserId());
                                        SharedPrefsUtil.putValue(null, Constant.USER, userName);
                                        SharedPrefsUtil.putValue(null, Constant.USER_ID, userInfo.getUserId());
                                        SharedPrefsUtil.putValue(null, Constant.USER_NAME, userInfo.getUserName());
                                        SharedPrefsUtil.putValue(null, Constant.ROLE_NAME, userInfo.getRoleName());
                                        SharedPrefsUtil.putValue(null, Constant.IS_GPS_PERMISSION, Boolean.parseBoolean(userInfo.getIsGpsPermission()));
                                        SharedPrefsUtil.putValue(null, Constant.ACCOUNT_NAME, userInfo.getAccountName());
                                        SharedPrefsUtil.putValue(Constant.MENUPE_RMISSION_BEAN,Constant.MENUPE_RMISSION_BEAN,userInfo.getMenuPermission());
                                        onLoginSuccess(userInfo.getUserName(), userInfo.getRoleName(), "");
                                    } else {
                                        onLoginErr("登录失败，请重新登录!");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    onLoginErr("登录失败，请重新登录!");
                                }
                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(String str) {
                                Logger.e("str = " + str);
                                onLoginErr(str);
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        M.onDestroy();
        M = null;
        V = null;
    }


    private void onLoginSuccess(String username, String roleName, String orderTotal) {
        if (V != null) {
            V.onCloseProgressBar();
            V.onLoginSuccess(username, roleName, orderTotal);
        }
    }


    private void onLoginErr(String str) {
        if (V != null) {
            V.onCloseProgressBar();
            V.onLoginErr(str);
        }
    }

    private static String getNowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

}
