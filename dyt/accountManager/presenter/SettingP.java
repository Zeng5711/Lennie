package com.hxyd.dyt.accountManager.presenter;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.hxyd.dyt.MyAppliaction;
import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.AccountManagerM;
import com.hxyd.dyt.accountManager.modle.entity.Version;
import com.hxyd.dyt.accountManager.modle.in.AccountManagerMI;
import com.hxyd.dyt.accountManager.presenter.in.SettingPI;
import com.hxyd.dyt.accountManager.view.in.AccountManagerVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.DownloadProgressHandler;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ProgressHelper;
import com.hxyd.dyt.common.uitl.Files;
import com.hxyd.dyt.common.uitl.Sign;
import com.hxyd.dyt.common.uitl.Tools;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/4/17.
 */

public class SettingP implements SettingPI {

    private AccountManagerVI V;
    private AccountManagerMI M;
    private static final String VERCODE_KEY = "verCode";
    private static final String TYPE_KEY = "type";

    public SettingP(AccountManagerVI vi) {
        this.V = vi;
        M = new AccountManagerM();
    }

    @Override
    public void onLoginOut() {
        if (M != null && V != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
            map = Constant.addMap(map);

            M.logOut(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<Object>() {
                        @Override
                        public void onNext(Object data) {
                            if (V != null) {
                                Tools.makeText("退出成功");
                                V.onStartActivity(null);
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                if (!str.equals("登录已过期，请重新登录！")) {
                                    V.onStartActivity(null);
                                    Tools.makeText("退出成功");
                                    Logger.e(str);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void checkVersion() {
        if (M != null && V != null) {
            V.onShowDownDialog(Tools.getString(R.string.check_version_message), false);
            String verCode = Tools.getVersionName();
            String type = Constant.APP_TYPE;
            Map<String, Object> map = new HashMap<>();
            map.put(VERCODE_KEY, verCode);
            map.put(TYPE_KEY, type);
            map = Constant.addMap(map);

            M.checkVersion(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<Version>() {
                        @Override
                        public void onNext(Version data) {
                            if (V != null) {
                                V.onCloseDownDialog();
                                checkCode(data);
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                V.onCloseDownDialog();
                                V.onPrompt(-1, Tools.getString(R.string.check_version_err_message));
                                Logger.e(str);
                            }
                        }
                    });
        }
    }

    @Override
    public void downAPK(String url) {
        if (V != null) {
            V.onShowDownDialog(Tools.getString(R.string.down_message), true);
            Call<ResponseBody> response = GitHubAPI.craetRetrofitProgress().downAPK(url);
            ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
                @Override
                protected void onProgress(long bytesRead, long contentLength, boolean done) {
                    if (V != null) {
                        V.onSetDownDialogStr((int) (contentLength / 1024), (int) (bytesRead / 1024));
                        if (done) {
                            V.onCloseDownDialog();
                        }
                    }
                }
            });


            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        String path = Files.getStorageDirectory() + "dyt.apk";
                        Files.writeResponseBodyToDisk(response.body(), path);
                        installApk(path);
                        if (V != null) {
                            V.onCloseDownDialog();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (V != null) {
                        V.onCloseDownDialog();
                        V.onPrompt(-1, "更新APK失败！");
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        M.onDestroy();
        V = null;
        M = null;
    }

    private void checkCode(Version version) {
        if (V != null && version != null) {
            if (!TextUtils.isEmpty(version.getVerCode()) && Tools.getVersionCode() < Integer.parseInt(version.getVerCode())) {
                boolean isForce = false;
                String str = "";
                if (version.getForce().equals("1")) {
                    isForce = true;
                    str = "是否强制更新";
                } else {
                    isForce = false;
                    str = "是否更新";
                }
                V.onShowUpdataAPK(str, version.getUrl(), isForce);
            } else {
                V.onPrompt(-1, Tools.getString(R.string.check_version_no_message));
            }
        }
    }

    private void installApk(String filePath) {
        if (V != null) {
            try {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse("file://" + filePath),
                        "application/vnd.android" + ".package-archive");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyAppliaction.getContext().startActivity(i);
                V.onCloseDownDialog();
            } catch (Exception e) {
                Logger.e("安装失败");
                e.printStackTrace();
            }
        }
    }
}
