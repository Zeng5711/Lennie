package com.hxyd.dyt.purplestar.modle;

import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.purplestar.modle.entity.DeviceLocation;
import com.hxyd.dyt.purplestar.modle.in.DeviceLocationMI;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/1.
 */

public class DeviceLocationM implements DeviceLocationMI {
    @Override
    public Observable<DeviceLocation> queryAllUser(Map<String,Object> map) {
        return GitHubAPI.craetRetrofit().queryAllUser(map).map(new ResponseFunc<DeviceLocation>());
    }

    @Override
    public Observable<Object> equipmentAuthorize(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().equipmentAuthorize(map).map(new ResponseFunc<Object>());
    }
}
