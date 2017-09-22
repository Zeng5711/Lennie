package com.hxyd.dyt.gpsinstallation.modle.in;

import com.hxyd.dyt.gpsinstallation.entity.DeviceInfo;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/13.
 */

public interface DeviceInfoMI {

    Observable<DeviceInfo> getImeiInfo(Map<String,Object>map);

    Observable<Object> binding(Map<String,Object>map);
}
