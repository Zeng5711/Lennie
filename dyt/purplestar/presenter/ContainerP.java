package com.hxyd.dyt.purplestar.presenter;

import android.text.TextUtils;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.SharedPrefsUtil;
import com.hxyd.dyt.purplestar.modle.ContainerM;
import com.hxyd.dyt.purplestar.modle.entity.AlarmScreen;
import com.hxyd.dyt.purplestar.modle.entity.Container;
import com.hxyd.dyt.purplestar.modle.in.ContainerMI;
import com.hxyd.dyt.purplestar.presenter.in.ContainerPI;
import com.hxyd.dyt.purplestar.view.in.ContainerVI;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/8/29.
 */

public class ContainerP implements ContainerPI {

    private ContainerMI m;
    private ContainerVI v;

    public ContainerP(ContainerVI v) {
        this.v = v;
        m = new ContainerM();
    }


    @Override
    public void getEquipmentList(boolean isShowDialog, final boolean isSearch, String conditions, long pageIndex) {
        if (isShowDialog) {
            v.onShowDialog("正在努力加载中...");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", SharedPrefsUtil.getValue(null, Constant.USER_ID, ""));
        map.put("pageIndex", pageIndex);
        if (!TextUtils.isEmpty(conditions)) {
            map.put("conditions", conditions);
        }
        map = Constant.addMap(map);

        m.getEquipmentList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Container>() {
                    @Override
                    public void onNext(Container data) {
                        v.onDissm();
                        v.setEquipmentList(isSearch ? 0 : 1, data, data.getCountTota());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.onDissm();
                        v.onShowMessage(1, str);
                    }
                });
    }

    @Override
    public void getEquipmentListForSuperManager(boolean isShowDialog, final boolean isSearch, int orgId, String conditions, long pageIndex) {
        if (isShowDialog) {
            v.onShowDialog("正在努力加载中...");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (orgId != -1) {
            map.put("orgId", orgId);
        }else{
            map.put("name",SharedPrefsUtil.getValue(null,Constant.ACCOUNT_NAME,""));
        }
        map.put("pageIndex", pageIndex);
        if (!TextUtils.isEmpty(conditions)) {
            map.put("conditions", conditions);
        }
        map = Constant.addMap(map);

        m.getEquipmentListForSuperManager(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Container>() {
                    @Override
                    public void onNext(Container data) {
                        v.onDissm();
                        v.setEquipmentList(isSearch ? 0 : 1, data, data.getCountTota());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.onDissm();
                        v.onShowMessage(1, str);
                    }
                });

    }

    @Override
    public void getAlarmList(boolean isShowDialog, final boolean isSearch, int orgId, String conditions, int warningType, long pageIndex) {
        if (isShowDialog) {
            v.onShowDialog("正在努力加载中...");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgId", orgId);
        map.put("pageIndex", pageIndex);
        map.put("accountName", SharedPrefsUtil.getValue(null, Constant.ACCOUNT_NAME, ""));
        if (!TextUtils.isEmpty(conditions)) {
            map.put("conditions", conditions);
        }
        if (warningType != -1) {
            map.put("warningType", warningType);
        }
        map = Constant.addMap(map);

        m.getAlarmList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Container>() {
                    @Override
                    public void onNext(Container data) {
                        v.onDissm();
                        v.setEquipmentList(isSearch ? 0 : 2, data, data.getCountTota());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.onDissm();
                        v.onShowMessage(1, str);
                    }
                });
    }

    @Override
    public void getAlarmScreeningList(int orgId) {
        v.onShowDialog("正在努力加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgId", orgId);
        map.put("accountName", SharedPrefsUtil.getValue(null, Constant.ACCOUNT_NAME, ""));
        map = Constant.addMap(map);

        m.getAlarmScreeningList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<AlarmScreen>() {
                    @Override
                    public void onNext(AlarmScreen data) {
                        v.onDissm();
                        v.setAlarmScreen(data);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.onDissm();
                        v.onShowMessage(1, str);
                    }
                });
    }
}
