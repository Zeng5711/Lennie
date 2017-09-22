package com.hxyd.dyt.gpsinstallation.modle;

import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.gpsinstallation.modle.in.InstallationMI;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/18.
 */

public class InstallationM implements InstallationMI {

    @Override
    public Observable<Object> teardown(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().teardown(map).map(new ResponseFunc<Object>());
    }

    @Override
    public Observable<Object> completeTask(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().completeTask(map).map(new ResponseFunc<>());
    }

}
