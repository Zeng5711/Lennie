package com.hxyd.dyt.accountManager.modle;

import com.hxyd.dyt.accountManager.modle.entity.Information;
import com.hxyd.dyt.accountManager.modle.in.InformationMI;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/4/20.
 */

public class InformationM implements InformationMI {

    @Override
    public Observable<Information> readImageSample(Map<String,Object> map) {
        return GitHubAPI.craetRetrofit().readImageSample(map).map(new ResponseFunc<Information>());
    }
}
