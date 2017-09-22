package com.hxyd.dyt.main.model;

import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.accountManager.modle.entity.Version;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.DownloadProgressHandler;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ProgressHelper;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.common.uitl.Files;
import com.hxyd.dyt.main.CallDownAPKListener;
import com.hxyd.dyt.main.model.in.MainMI;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by win7 on 2017/5/11.
 */

public class MainM implements MainMI {
    @Override
    public Observable<Version> checkVersion(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().check(map).map(new ResponseFunc<Version>());
    }

    @Override
    public Observable<CountTotal> mainInfo(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().mainInfo(map).map(new ResponseFunc<CountTotal>());
    }

    @Override
    public void downAPK(String url, final CallDownAPKListener listener) {
        Call<ResponseBody> response = GitHubAPI.craetRetrofitProgress().downAPK(url);
        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                listener.onSetDownDialogStr((int) (contentLength / 1024), (int) (bytesRead / 1024));
                if (done) {
                    listener.onCloseDownDialog();
                }
            }
        });
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String path = Files.getDeleteDirectory() + Constant.APK_NAME + Constant.APK_SUFFIX;
                    Files.writeResponseBodyToDisk(response.body(), path);
                    listener.onSuccess(path);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onErr();
            }
        });
    }

    @Override
    public void onDestroy() {

    }
}
