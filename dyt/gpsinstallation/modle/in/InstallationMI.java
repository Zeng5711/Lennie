package com.hxyd.dyt.gpsinstallation.modle.in;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/18.
 */

public interface InstallationMI {

    Observable<Object> teardown(Map<String,Object> map);


    Observable<Object> completeTask(Map<String,Object> map);
}
