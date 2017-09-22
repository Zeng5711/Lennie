package com.hxyd.dyt.appraiser.presenter;

import com.hxyd.dyt.appraiser.modle.CarInfoM;
import com.hxyd.dyt.appraiser.modle.entity.CarInfo;
import com.hxyd.dyt.appraiser.modle.in.CarInfoMI;
import com.hxyd.dyt.appraiser.presenter.in.CarInfoPI;
import com.hxyd.dyt.appraiser.view.in.CarInfoVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/9/19.
 */

public class CarInfoP implements CarInfoPI {

    private CarInfoMI m;
    private CarInfoVI v;

    public CarInfoP(CarInfoVI v) {
        this.v = v;
        m = new CarInfoM();
    }

    @Override
    public void queryEvalCarInfo(String businessId) {

        v.showDialog("正在努力加载中...");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("businessId", businessId);
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map = Constant.addMap(map);

        m.queryEvalCarInfo(map)
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

    @Override
    public void updateEvalCarInfo(String businessId, String plateNo, String registerDate,
                                  String travelDistance, String carRecognizationNo,String engineNo) {
        v.showDialog("正在努力加载中...");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("businessId", businessId);
        map.put("plateNo", plateNo);
        map.put("registerDate", registerDate);
        map.put("travelDistance", travelDistance);
        map.put("carRecognizationNo", carRecognizationNo);
        map.put("engineNo", engineNo);
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map = Constant.addMap(map);

        m.updateEvalCarInfo(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        v.dismiss();
                        v.updateEvalCarInfo();
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
