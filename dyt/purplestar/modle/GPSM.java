package com.hxyd.dyt.purplestar.modle;

import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.purplestar.modle.entity.GPS;
import com.hxyd.dyt.purplestar.modle.in.GPSMI;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/8/30.
 */

public class GPSM implements GPSMI{
    @Override
    public Observable<GPS> getLocationInfo(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().getLocationInfo(map).map(new ResponseFunc<GPS>());
    }
}
