package com.hxyd.dyt.accountManager.modle;

import com.hxyd.dyt.accountManager.modle.entity.Vehicleinfo;
import com.hxyd.dyt.accountManager.modle.in.VehicleinfoMI;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/3/9.
 */

public class VehicleinfoM implements VehicleinfoMI {
//    @Override
//    public Observable<Vehicleinfo> queryCarInfo(Map<String,Object> map) {
//        return GitHubAPI.craetRetrofit().queryCarInfo(map).map(new ResponseFunc<Vehicleinfo>());
//    }

    @Override
    public Observable<Object> saveCarInfo(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().saveCarInfo(map).map(new ResponseFunc<Object>());
    }

    @Override
    public void onDestroy() {

    }
}
