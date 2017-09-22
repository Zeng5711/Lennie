package com.hxyd.dyt.gpsinstallation.modle;

import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.gpsinstallation.entity.ImeiInfo;
import com.hxyd.dyt.gpsinstallation.entity.Loading;
import com.hxyd.dyt.gpsinstallation.modle.in.LoadingMI;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by win7 on 2017/9/14.
 */

public class LoadingM implements LoadingMI {
    @Override
    public Observable<Object> uploadImg(String url, Map<String, Object> map, Map<String, RequestBody> params) {
        return GitHubAPI.craetRetrofit().uploadImg(url, map, params).map(new ResponseFunc<>());
    }

    @Override
    public Observable<Object> deleteImg(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().deleteImg(map).map(new ResponseFunc<>());
    }

    @Override
    public Observable<Loading> queryAllImgByParams(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().queryAllImgByParams(map).map(new ResponseFunc<Loading>());
    }

    @Override
    public Observable<ImeiInfo> updateImeiInfo(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().updateImeiInfo(map).map(new ResponseFunc<ImeiInfo>());
    }

    @Override
    public Observable<Object> updateLoanAudiByBussinessId(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().updateLoanAudiByBussinessId(map).map(new ResponseFunc<>());
    }

    @Override
    public Observable<Object> saveCarEvaluation(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().saveCarEvaluation(map).map(new ResponseFunc<>());
    }
}
