package com.hxyd.dyt.main.presenter;

import android.text.TextUtils;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.accountManager.modle.entity.Version;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.Sign;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.main.CallDownAPKListener;
import com.hxyd.dyt.main.model.MainM;
import com.hxyd.dyt.main.model.in.MainMI;
import com.hxyd.dyt.main.presenter.in.MainPI;
import com.hxyd.dyt.main.view.in.MainVI;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/5/11.
 */

public class MainP implements MainPI {

    private static MainMI M;
    private static MainVI V;

    public static final int ERROR_PROMPT = 1;
    public static String DOWNURL = "";

    public MainP(MainVI mainVI) {
        this.V = mainVI;
        this.M = new MainM();
    }

    @Override
    public void checkVersion() {
        if (M != null && V != null) {
            V.onShowDialog("提示", "正在检查新版本...");

            String verCode = Tools.getVersionName();
            String type = Constant.APP_TYPE;
            Map<String, Object> map = new HashMap<>();
            map.put(Constant.VERCODE_KEY, verCode);
            map.put(Constant.TYPE_KEY, type);
            map = Constant.addMap(map);

            M.checkVersion(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<Version>() {

                        @Override
                        public void onNext(Version data) {
                            V.onCloseDialog();
                            checkCode(data);
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                V.onCloseDialog();
                                V.onPrompt(ERROR_PROMPT, str);
                            }
                        }
                    });
        }
    }

    @Override
    public void mainInfo() {
        if (M != null && V != null) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
            m = Constant.addMap(m);

            M.mainInfo(m)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<CountTotal>() {
                        @Override
                        public void onNext(CountTotal data) {
                            if (V != null && data != null) {
                                V.onSetCountTotal(data.getCountTotal());
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                V.onPrompt(ERROR_PROMPT, str);
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
//        M.onDestroy();
        V = null;
        M = null;
    }

    @Override
    public void downAPK() {
        if (M != null && V != null) {
            V.onShowProgress("提示", "正在下载中，请稍后...");
            M.downAPK(DOWNURL, new CallDownAPKListener() {
                @Override
                public void onSetDownDialogStr(int max, int Progress) {
                    if (V != null) {
                        V.onSetProgress(max, Progress);
                    }
                }

                @Override
                public void onCloseDownDialog() {
                    V.onCloseProgress();
                }

                @Override
                public void onSuccess(String path) {
                    V.installApk(path);
                }

                @Override
                public void onErr() {
                    if (V != null) {
                        V.onPrompt(ERROR_PROMPT, "下载APK失败！");
                        V.onCloseProgress();
                    }
                }
            });
        }
    }

    private void checkCode(Version version) {
        if (V != null && version != null) {
            if (!TextUtils.isEmpty(version.getVerCode()) && Tools.getVersionCode() < Double.parseDouble(version.getVerCode())) {
                String str = "";
                boolean isForce = false;
                if (version.getForce().equals("1")) {
                    isForce = true;
                    str = "请下载最新的版本，否则会导致无法正常使用贷业通！";
                } else {
                    str = "请下载最新的版本";
                }
                DOWNURL = version.getUrl();
                V.onShowAlertDialog("贷业通已经更新版本", str, isForce);
            } else {
//                V.onPrompt(ERROR_PROMPT, Tools.getString(R.string.check_version_no_message));
            }
        }
    }
}
