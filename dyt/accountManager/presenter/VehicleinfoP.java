package com.hxyd.dyt.accountManager.presenter;

import com.google.gson.Gson;
import com.hxyd.dyt.accountManager.modle.VehicleinfoM;
import com.hxyd.dyt.accountManager.modle.entity.Vehicleinfo;
import com.hxyd.dyt.accountManager.modle.in.VehicleinfoMI;
import com.hxyd.dyt.accountManager.presenter.in.VehicleinfoPI;
import com.hxyd.dyt.accountManager.view.in.VehicleinfoVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.JsonToMap;
import com.hxyd.dyt.common.uitl.Sign;
import com.orhanobut.logger.Logger;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/3/9.
 */

public class VehicleinfoP implements VehicleinfoPI {

    private VehicleinfoMI MI;
    private VehicleinfoVI VI;

    public VehicleinfoP(VehicleinfoVI vi) {
        this.VI = vi;
        this.MI = new VehicleinfoM();

    }


    @Override
    public void onDestroy() {
        MI.onDestroy();
        MI = null;
        VI = null;
    }

    @Override
    public void onGetData() {

    }

    @Override
    public void onSubmit(Vehicleinfo vehicleinfo) {
        if (MI != null && VI != null) {
            Gson gson = new Gson();
            Map<String, Object> map = JsonToMap.toMap(gson.toJson(vehicleinfo));
            map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());//Constant.getToken()
            map = Constant.addMap(map);
            MI.saveCarInfo(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<Object>() {
                        @Override
                        public void onNext(Object data) {
                            if(VI!=null) {
                                Logger.e(data.toString());
                                VI.onSccess();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if(VI!=null) {
                                VI.onErr(str);
                                Logger.e(str);
                            }
                        }
                    });
        }
    }
}
