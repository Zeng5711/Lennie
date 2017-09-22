package com.hxyd.dyt.appraiser.modle.in;

import com.hxyd.dyt.appraiser.modle.entity.CarInfo;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/19.
 */

public interface CarInfoMI {

    Observable<CarInfo> queryEvalCarInfo(Map<String,Object> map);

    Observable<Object> updateEvalCarInfo(Map<String,Object> map);
}
