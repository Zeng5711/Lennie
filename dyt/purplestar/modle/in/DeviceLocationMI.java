package com.hxyd.dyt.purplestar.modle.in;

import com.hxyd.dyt.purplestar.modle.entity.DeviceLocation;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/1.
 */

public interface DeviceLocationMI {

    Observable<DeviceLocation> queryAllUser(Map<String,Object> map);
    Observable<Object> equipmentAuthorize(Map<String,Object> map);

}
