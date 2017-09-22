package com.hxyd.dyt.gpsinstallation.presenter;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.gpsinstallation.modle.InstallationM;
import com.hxyd.dyt.gpsinstallation.modle.in.InstallationMI;
import com.hxyd.dyt.gpsinstallation.presenter.in.InstallationPI;
import com.hxyd.dyt.gpsinstallation.view.in.InstallationVI;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/9/18.
 */

public class InstallationP implements InstallationPI {


    private InstallationMI m;
    private InstallationVI v;

    public InstallationP(InstallationVI v) {
        this.v = v;
        m = new InstallationM();
    }

    @Override
    public void teardown(String imeiId) {
        v.showDialog("正在努力加载中...");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map.put("imeiId", imeiId);
        map = Constant.addMap(map);

        m.teardown(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        v.dismiss();
                        v.onErr(3,"拆除成功");
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
    public void completeTask(String taskId, String processInstanceId) {


        v.showDialog("正在努力加载中...");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map.put("taskId", taskId);
        map.put("processInstanceId", processInstanceId);
        map = Constant.addMap(map);


        m.completeTask(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        v.dismiss();
                        v.onErr(2, "OK");
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
