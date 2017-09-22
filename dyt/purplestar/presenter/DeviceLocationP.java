package com.hxyd.dyt.purplestar.presenter;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.purplestar.modle.DeviceLocationM;
import com.hxyd.dyt.purplestar.modle.entity.DeviceLocation;
import com.hxyd.dyt.purplestar.modle.in.DeviceLocationMI;
import com.hxyd.dyt.purplestar.presenter.in.DeviceLocationPI;
import com.hxyd.dyt.purplestar.view.fragment.in.DeviceLocationVI;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/9/1.
 */

public class DeviceLocationP implements DeviceLocationPI {

    private DeviceLocationMI m;
    private DeviceLocationVI v;

    public DeviceLocationP(DeviceLocationVI v){
        this.v = v;
        this.m = new DeviceLocationM();
    }

    @Override
    public void getqueryAllUser() {

        v.onShowDialog("正在努力加载中...");

        Map<String,Object> map = new HashMap<>();
        map = Constant.addMap(map);

        m.queryAllUser(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<DeviceLocation>() {
                    @Override
                    public void onNext(DeviceLocation data) {
                        v.onDissm();
                        v.setDeviceLocation(data);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.onDissm();
                        v.onShowMessage(1,str);
                    }
                });

    }

    @Override
    public void equipmentAuthorize(String userName, String equipmentId) {
        v.onShowDialog("正在努力加载中...");

        Map<String,Object> map = new HashMap<>();
        map.put("userName",userName);
        map.put("equipmentId",equipmentId);
        map = Constant.addMap(map);
        m.equipmentAuthorize(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        v.onDissm();
                        v.onShowMessage(2,"授权成功！");
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.onDissm();
                        v.onShowMessage(1,str);
                    }
                });
    }
}
