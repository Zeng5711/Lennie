package com.hxyd.dyt.accountManager.modle;

import com.google.gson.JsonArray;
import com.hxyd.dyt.accountManager.modle.entity.JingZhengu;
import com.hxyd.dyt.accountManager.modle.in.JingZhenGuMI;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.common.http.ResponseFuncT;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/5/4.
 */

public class JingZhenGuM implements JingZhenGuMI {
    @Override
    public Observable<JsonArray> getJingZhenGuData(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().getJingZhenGuData(map).map(new ResponseFunc<JsonArray>());
    }

    @Override
    public Observable<JingZhengu> getJingZhenGuObjectData(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().getJingZhenGuObjectData(map).map(new ResponseFunc<JingZhengu>());
    }

    @Override
    public void onDestroy() {

    }
}
