package com.hxyd.dyt.accountManager.presenter;

import com.google.gson.Gson;
import com.hxyd.dyt.accountManager.modle.SingleM;
import com.hxyd.dyt.accountManager.modle.entity.LoanInfoID;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.entity.UserInfoNew;
import com.hxyd.dyt.accountManager.modle.in.SingleMI;
import com.hxyd.dyt.accountManager.presenter.in.UserInfoNewPI;
import com.hxyd.dyt.accountManager.view.in.UserInfoNewVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.JsonToMap;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/5/18.
 */

public class UserInfoNewP implements UserInfoNewPI {

    private SingleMI M;
    private UserInfoNewVI V;

    public static int ERROR_ONE = 0;

    public UserInfoNewP(UserInfoNewVI v) {
        this.V = v;
        this.M = new SingleM();
    }

    @Override
    public void getBaseData() {
        if (M != null && V != null) {
            BaseData baseData = ADDataManager.getInstance().getBaseData();
//            if (baseData == null) {
//                baseData = M.getBaseData();
//                ADDataManager.getInstance().setBaseData(baseData);
//            }
            if (baseData != null) {
                V.setBaseData(baseData);
            }
        }
    }

    @Override
    public void getArealist() {
        if (M != null && V != null) {
            List<AreaList> list = ADDataManager.getInstance().getAreaLists();
//            if (list == null) {
//                list = M.getArealist();
//                ADDataManager.getInstance().setAreaLists(list);
//            }
            if(list!=null) {
                V.setArealist(list);
            }
        }
    }


    @Override
    public void getOrderDefault(String orderNo) {
        if (M != null && V != null) {
            OrderDefultInfo defultInfo = ADDataManager.getInstance().getDefultInfo();
//            if (defultInfo == null) {
//                defultInfo = M.getOrderDefultInfo(orderNo);
//                ADDataManager.getInstance().setDefultInfo(defultInfo);
//            }
            if(defultInfo!=null) {
                V.setOrderDefault(defultInfo);
            }
        }
    }

    @Override
    public void onDestroy() {
        M.onDestroy();
        M = null;
        V = null;
    }

    @Override
    public void submit(UserInfoNew user) {
        if (M != null && V != null) {
            Gson gson = new Gson();
            String json = gson.toJson(user);
            Logger.e("json===>" + json);

            Map<String, Object> map = JsonToMap.toMap(json);
            map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());//Constant.getToken()
            map = Constant.addMap(map);
            Logger.e(map.toString());
            M.customerInformationInput(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<LoanInfoID>() {
                        @Override
                        public void onNext(LoanInfoID data) {
                            if (V != null) {
                                V.nextStep();
                                V.onDismiss();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                V.onErr(ERROR_ONE, str);
                                V.onDismiss();
                            }
                        }
                    });
        }
    }
}
