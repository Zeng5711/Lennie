package com.hxyd.dyt.purplestar.modle.in;

import com.hxyd.dyt.purplestar.modle.entity.GPS;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/8/30.
 */

public interface GPSMI {

    Observable<GPS> getLocationInfo(Map<String, Object> map);

}
