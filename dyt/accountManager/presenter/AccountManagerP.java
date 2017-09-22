package com.hxyd.dyt.accountManager.presenter;


import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.text.TextUtils;

import com.hxyd.dyt.MyAppliaction;
import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.AccountManagerM;
import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.accountManager.modle.entity.Version;
import com.hxyd.dyt.accountManager.modle.in.AccountManagerMI;
import com.hxyd.dyt.accountManager.presenter.in.AccountManagerPI;
import com.hxyd.dyt.accountManager.view.in.AccountManagerVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.DownloadProgressHandler;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ProgressHelper;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Files;
import com.hxyd.dyt.common.uitl.Sign;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.login.modle.entity.UserInfo;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/3/3.
 */

public class AccountManagerP implements AccountManagerPI {

    private AccountManagerVI V;
    private AccountManagerMI M;

    public AccountManagerP(AccountManagerVI vi) {
        this.V = vi;
        M = new AccountManagerM();
    }


    @Override
    public void onDestroy() {
        M.onDestroy();
        V = null;
        M = null;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onLoginOut() {

    }

    @Override
    public void onSetData() {
        if (M != null && V != null) {
            UserInfo userInfo = M.getUserInfo();
            if (null != userInfo) {
                V.setUser(userInfo.getUserName(), userInfo.getRoleName(), -1);
            } else {
                V.setUser("", "", 0);
            }
        }
    }

    @Override
    public void checkVersion() {
        if (M != null && V != null) {
            V.onShowDownDialog(Tools.getString(R.string.check_version_message), false);
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
                            Logger.e(data.toString());
                            if(V!=null) {
                                V.onCloseDownDialog();
                                checkCode(data);
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if(V!=null) {
                                V.onCloseDownDialog();
                                V.onPrompt(-1, Tools.getString(R.string.check_version_err_message));
                                Logger.e(str);
                            }
                        }
                    });
        }
    }

    @Override
    public void downAPK(final String url) {
        if (M != null && V != null) {
            V.onShowDownDialog(Tools.getString(R.string.down_message), true);
            Call<ResponseBody> response = GitHubAPI.craetRetrofitProgress().downAPK(url);
            ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
                @Override
                protected void onProgress(long bytesRead, long contentLength, boolean done) {
                    V.onSetDownDialogStr((int) (contentLength / 1024), (int) (bytesRead / 1024));
                    if (done) {
                        V.onCloseDownDialog();
                    }
                }
            });
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful() && V != null) {
                        String path = Files.getStorageDirectory() + "dyt.apk";
                        Files.writeResponseBodyToDisk(response.body(), path);
                        V.installApk(path);
                        V.onCloseDownDialog();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (V != null) {
                        V.onCloseDownDialog();
                        V.onPrompt(-2, "更新APK失败！");
                    }
                }
            });
        }
    }


    @Override
    public void getCountTotal() {
        if (M != null && V != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
            map = Constant.addMap(map);
            M.mainInfo(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<CountTotal>() {
                        @Override
                        public void onNext(CountTotal data) {
                            if (V != null) {
                                Logger.e("getCountTotal=======>" + data.toString());
                                V.onSetCountTotal(data.getCountTotal());
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            Logger.e(str);
                        }
                    });
        }
    }

    private void checkCode(Version version) {
        if ( V != null && version!=null) {
            if (!TextUtils.isEmpty(version.getVerCode()) && Tools.getVersionCode() < Double.parseDouble(version.getVerCode())) {
                boolean isForce = false;
                String str = "";
                if (version.getForce().equals("1")) {
                    isForce = true;
                    str = "请下载最新的版本，否则会导致无法正常使用贷业通！";
                } else {
                    isForce = false;
                    str = "请下载最新的版本";
                }
                V.onShowUpdataAPK(str, version.getUrl(), isForce);
            } else {
                V.onPrompt(2, Tools.getString(R.string.check_version_no_message));
            }
        }
    }


}
