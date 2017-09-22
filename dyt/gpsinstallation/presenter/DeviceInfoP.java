package com.hxyd.dyt.gpsinstallation.presenter;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.gpsinstallation.entity.DeviceInfo;
import com.hxyd.dyt.gpsinstallation.modle.DeviceInfoM;
import com.hxyd.dyt.gpsinstallation.modle.in.DeviceInfoMI;
import com.hxyd.dyt.gpsinstallation.presenter.in.DeviceInfoPI;
import com.hxyd.dyt.gpsinstallation.view.in.DeviceInfoVI;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/9/13.
 */

public class DeviceInfoP implements DeviceInfoPI {

    private DeviceInfoMI m;
    private DeviceInfoVI v;

    public DeviceInfoP(DeviceInfoVI v) {
        this.v = v;
        m = new DeviceInfoM();
    }

    @Override
    public void getImeiInfo(String imeiId, String businessId, String custId) {

        v.showDialog("正在努力加载中...");

        Map<String, Object> map = new HashMap<String, Object>();
        map = Constant.addMap(map);
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map.put("imeiId", imeiId);
        map.put("businessId", businessId);
        map.put("custId", custId);

        m.getImeiInfo(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<DeviceInfo>() {
                    @Override
                    public void onNext(DeviceInfo data) {
                        v.dismiss();
                        v.setDeviceInfo(data);
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
    public void binding(Map<String, Object> map) {
        v.showDialog("正在努力加载中...");
        map.put(Constant.TOKEN_MAP_KEY,Constant.getToken());
        map = Constant.addMap(map);

        m.binding(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        v.dismiss();
                        v.binding();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.dismiss();
                        v.onErr(1,str);
                    }
                });
    }
}
