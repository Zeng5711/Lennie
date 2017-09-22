package com.hxyd.dyt.accountManager.presenter;

import com.google.gson.Gson;
import com.hxyd.dyt.accountManager.modle.SingleM;
import com.hxyd.dyt.accountManager.modle.entity.LoanInfoID;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.entity.ProductInfoList;
import com.hxyd.dyt.accountManager.modle.entity.Supplementary;
import com.hxyd.dyt.accountManager.modle.in.SingleMI;
import com.hxyd.dyt.accountManager.presenter.in.SupplementaryPI;
import com.hxyd.dyt.accountManager.view.in.SupplementaryVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.JsonToMap;
import com.orhanobut.logger.Logger;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/5/18.
 */

public class SupplementaryP implements SupplementaryPI {

    private SingleMI M;
    private SupplementaryVI V;

    public SupplementaryP(SupplementaryVI v) {
        this.V = v;
        M = new SingleM();
    }

    @Override
    public void getBaseData() {
        if (M != null && V != null) {
            BaseData baseData = ADDataManager.getInstance().getBaseData();
//            if (baseData == null) {
//                baseData = M.getBaseData();
//                ADDataManager.getInstance().setBaseData(baseData);
//            }
            if(baseData!=null) {
                V.setBaseData(baseData);
            }
        }
    }

    @Override
    public void getBaseInfoBean(String id) {
        if (M != null && V != null) {
            OrderDefultInfo defultInfo = ADDataManager.getInstance().getDefultInfo();
//            if (defultInfo == null) {
//                defultInfo = M.getOrderDefultInfo(id);
//                ADDataManager.getInstance().setDefultInfo(defultInfo);
//            }
            if(defultInfo!=null) {
                V.setBaseInfoBean(defultInfo.getBaseInfo());
            }
        }
    }

    @Override
    public void getProductInfoList() {
        if (M != null && V != null) {
            ProductInfoList productInfoList = ADDataManager.getInstance().getProductInfoList();
            if (productInfoList != null) {
                V.setProductInfoList(productInfoList.getList());
            }
        }
    }

    @Override
    public void submit(Supplementary supplementary) {
        if (M != null && V != null) {
            Gson gson = new Gson();
            String json = gson.toJson(supplementary);
            Logger.e("json===>" + json);

            Map<String, Object> map = JsonToMap.toMap(json);
            map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());//Constant.getToken()
            map = Constant.addMap(map);
            Logger.e(map.toString());

            M.loanAndCarMessageInput(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<LoanInfoID>() {
                        @Override
                        public void onNext(LoanInfoID data) {
                            if (V != null) {
                                V.onDismiss();
                                V.nextStep();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                V.onErr(0, str);
                                V.onDismiss();
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        M = null;
        V = null;
    }


}
