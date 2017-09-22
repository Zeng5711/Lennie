package com.hxyd.dyt.gpsinstallation.modle;

import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.gpsinstallation.entity.CarInfo;
import com.hxyd.dyt.gpsinstallation.modle.in.CarInfoMI;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/13.
 */

public class CarInfoM  implements CarInfoMI{
    @Override
    public Observable<CarInfo> queryGpsCarInfo(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().queryGpsCarInfo(map).map(new ResponseFunc<CarInfo>());
    }
}
