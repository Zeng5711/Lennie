package com.hxyd.dyt.purplestar.presenter;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.purplestar.modle.GPSM;
import com.hxyd.dyt.purplestar.modle.entity.GPS;
import com.hxyd.dyt.purplestar.modle.in.GPSMI;
import com.hxyd.dyt.purplestar.presenter.in.GPSPI;
import com.hxyd.dyt.purplestar.view.in.GPSVI;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/8/30.
 */

public class GPSP implements GPSPI {

    private GPSMI m;
    private GPSVI v;

    public GPSP(GPSVI v){
        this.v = v;
        m = new GPSM();
    }

    @Override
    public void getLocationInfo(String imeiId) {

        v.onShowDialog("正在努力加载中...");

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("imeiId",imeiId);
        map = Constant.addMap(map);

        m.getLocationInfo(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<GPS>() {
                    @Override
                    public void onNext(GPS data) {
                        v.onDissm();
                        v.setData(data);
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
