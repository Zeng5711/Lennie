package com.hxyd.dyt.gpsinstallation.presenter;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.gpsinstallation.entity.CarInfo;
import com.hxyd.dyt.gpsinstallation.modle.CarInfoM;
import com.hxyd.dyt.gpsinstallation.modle.in.CarInfoMI;
import com.hxyd.dyt.gpsinstallation.presenter.in.CarInfoPI;
import com.hxyd.dyt.gpsinstallation.view.in.CarInfoVI;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/9/13.
 */

public class CarInfoP implements CarInfoPI {

    private CarInfoMI m;
    private CarInfoVI v;

    public CarInfoP(CarInfoVI v) {
        this.v = v;
        m = new CarInfoM();
    }

    @Override
    public void queryGpsCarInfo(String businessId) {

        v.showDialog("正在努力加载中...");

        Map<String, Object> map = new HashMap<>();
        map = Constant.addMap(map);
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map.put("businessId", businessId);

        m.queryGpsCarInfo(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<CarInfo>() {
                    @Override
                    public void onNext(CarInfo data) {
                        v.dismiss();
                        v.setCarInfo(data);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.dismiss();
                        v.onErr(1, str);
                    }
                });

    }
}
