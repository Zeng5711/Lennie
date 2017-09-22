package com.hxyd.dyt.appraiser.modle;


import com.hxyd.dyt.appraiser.modle.entity.CarInfo;
import com.hxyd.dyt.appraiser.modle.in.CarInfoMI;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/19.
 */

public class CarInfoM implements CarInfoMI {
    @Override
    public Observable<CarInfo> queryEvalCarInfo(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().queryEvalCarInfo(map).map(new ResponseFunc<CarInfo>());
    }

    @Override
    public Observable<Object> updateEvalCarInfo(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().updateEvalCarInfo(map).map(new ResponseFunc<>());
    }
}
