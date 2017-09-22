package com.hxyd.dyt.vehiclemortgage.presenter;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.vehiclemortgage.entity.VehicleEvaluation;
import com.hxyd.dyt.vehiclemortgage.model.VehicleEvaluationM;
import com.hxyd.dyt.vehiclemortgage.model.in.VehicleEvaluationMI;
import com.hxyd.dyt.vehiclemortgage.presenter.in.VehicleEvaluationPI;
import com.hxyd.dyt.vehiclemortgage.view.in.VehicleEvaluationVI;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/9/14.
 */

public class VehicleEvaluationP implements VehicleEvaluationPI {

    private VehicleEvaluationMI m;
    private VehicleEvaluationVI v;

    public VehicleEvaluationP(VehicleEvaluationVI v) {
        this.v = v;
        m = new VehicleEvaluationM();
    }

    @Override
    public void queryMortgageInfo(String businessId) {

        v.showDialog("正在努力加载中...");

        Map<String, Object> map = new HashMap<>();
        map.put("businessId", businessId);
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map = Constant.addMap(map);

        m.queryMortgageInfo(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<VehicleEvaluation>() {
                    @Override
                    public void onNext(VehicleEvaluation data) {
                        v.dismiss();
                        v.setData(data);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.dismiss();
                    }
                });

    }
}
