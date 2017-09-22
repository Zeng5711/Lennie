package com.hxyd.dyt.gpsinstallation.modle;

import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.gpsinstallation.entity.DeviceInfo;
import com.hxyd.dyt.gpsinstallation.modle.in.DeviceInfoMI;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/13.
 */

public class DeviceInfoM implements DeviceInfoMI {

    @Override
    public Observable<DeviceInfo> getImeiInfo(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().getImeiInfo(map).map(new ResponseFunc<DeviceInfo>());
    }

    @Override
    public Observable<Object> binding(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().binding(map).map(new ResponseFunc<>());
    }
}
