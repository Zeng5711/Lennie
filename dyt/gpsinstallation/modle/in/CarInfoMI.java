package com.hxyd.dyt.gpsinstallation.modle.in;

import com.hxyd.dyt.gpsinstallation.entity.CarInfo;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/13.
 */

public interface CarInfoMI {

    Observable<CarInfo> queryGpsCarInfo(Map<String ,Object> map);
}
