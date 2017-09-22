package com.hxyd.dyt.login.presenter;

import android.text.TextUtils;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.JsonUtil;
import com.hxyd.dyt.common.uitl.Sign;
import com.hxyd.dyt.common.uitl.ThreeDES;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.login.modle.UpdataPasswordM;
import com.hxyd.dyt.login.modle.in.UpdataPasswordMI;
import com.hxyd.dyt.login.presenter.in.UpdataPasswordPI;
import com.hxyd.dyt.login.view.in.UpdataPasswordVI;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/3/7.
 */

public class UpdataPasswordP implements UpdataPasswordPI {

    private UpdataPasswordMI M;
    private UpdataPasswordVI V;

    public UpdataPasswordP(UpdataPasswordVI v) {
        this.V = v;
        M = new UpdataPasswordM();
    }

    @Override
    public void submit(String lod, String new_one, String new_tow) {
        if (M != null && V != null) {
            if (TextUtils.isEmpty(lod)) {
                V.onErr(1, "原密码不能为空");
                return;
            }

            if (TextUtils.isEmpty(new_one)) {
                V.onErr(2, "新密码不能为空");
                return;
            }

            if (new_one.length() < 8) {
                V.onErr(2, "新密码长度不得小于8位数");
                return;
            }
//        if(new_one.length()>20){
//            V.onErr(2, "新密码长度不得小于20位数");
//            return;
//        }

            if (TextUtils.isEmpty(new_tow)) {
                V.onErr(3, "重复密码不能为空");
                return;
            }

            if (new_tow.length() < 8) {
                V.onErr(3, "重复密码长度不得小于8位数");
                return;
            }
//        if(new_tow.length()>20){
//            V.onErr(3, "新密码长度不得小于20位数");
//            return;
//        }

            if (!new_one.equals(new_tow)) {
                V.onErr(4, "两次输入新密码不一致");
                return;
            }

            if (new_one.length() < 8) {
                V.onErr(4, "新密码至少要8个字符");
                return;
            }

            if (new_tow.length() < 8) {
                V.onErr(4, "重复密码至少要8个字符");
                return;
            }

            if (!isLetterDigit(new_one)) {
                V.onErr(4, "新密码请使用字母加数字");
                return;
            }


            V.onShowProgressBar();

            try {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
                m.put("oldPassword", lod);
                m.put("newPassword", new_one);
                m = Constant.addMap(m);
                String encoding = ThreeDES.encrypt(JsonUtil.packageJsonObject(m), Constant.DES_KEY);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("encoding", encoding);
                map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
                map = Constant.addMap(map);
                M.submit(map)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new FilterSubscriber<Object>() {
                            @Override
                            public void onNext(Object data) {
                                loginOut();
                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(String str) {
                                if(V!=null) {
                                    Logger.e(str);
                                    V.onCloseProgressBar();
                                    V.onShowDialog(str);
                                }
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

    @Override
    public void loginOut() {
        if (M != null && V != null) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("token", Constant.getToken());
            m = Constant.addMap(m);

            M.logOut(m)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<Object>() {
                        @Override
                        public void onNext(Object data) {
                            if(V!=null) {
                                V.onCloseProgressBar();
                                V.onSuccess();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if(V!=null) {
                                V.onCloseProgressBar();
                                V.onShowDialog(str);
                                Logger.e(str);
                            }
                        }
                    });
        }
    }

    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }
}
