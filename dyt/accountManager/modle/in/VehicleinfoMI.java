package com.hxyd.dyt.accountManager.modle.in;

import com.hxyd.dyt.accountManager.modle.entity.Vehicleinfo;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/3/9.
 */

public interface VehicleinfoMI {

//    Observable<Vehicleinfo> queryCarInfo(String loanInfoId, String token);

    Observable<Object> saveCarInfo(Map<String, Object> map);

    void onDestroy();
}
