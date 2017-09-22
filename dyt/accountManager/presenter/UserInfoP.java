package com.hxyd.dyt.accountManager.presenter;

import com.google.gson.Gson;
import com.hxyd.dyt.accountManager.modle.UserInfoM;
import com.hxyd.dyt.accountManager.modle.entity.LoanInfoID;
import com.hxyd.dyt.accountManager.modle.entity.SUserInfo;
import com.hxyd.dyt.accountManager.modle.in.UserInfoMI;
import com.hxyd.dyt.accountManager.presenter.in.UserInfoPI;
import com.hxyd.dyt.accountManager.view.in.UserInfoVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.JsonToMap;
import com.orhanobut.logger.Logger;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by win7 on 2017/3/9.
 */

public class UserInfoP implements UserInfoPI {

    private UserInfoMI M;
    private UserInfoVI V;

    public UserInfoP(UserInfoVI mv) {
        this.V = mv;
        M = new UserInfoM();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onGetData() {

    }

    @Override
    public void onSubmit(SUserInfo SUserInfo) {
        if (M != null && V != null) {
            Gson gson = new Gson();
            String json = gson.toJson(SUserInfo);
            Logger.e("json===>" + json);

            Map<String, Object> map = JsonToMap.toMap(json);
            map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());//Constant.getToken()
            map = Constant.addMap(map);
            Logger.e(map.toString());
            Logger.e("Constant.getToken()===>" + Constant.getToken());
            M.saveCustomerInfo(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<LoanInfoID>() {
                        @Override
                        public void onNext(LoanInfoID data) {
                            if(V!=null) {
                                Logger.e(data.toString());
                                V.onSccess(data.getLoanInfoId());
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if(V!=null) {
                                Logger.e(str);
                                V.onErr(str);
                            }

                        }
                    });
        }

    }
}
