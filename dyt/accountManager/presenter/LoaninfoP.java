package com.hxyd.dyt.accountManager.presenter;

import com.google.gson.Gson;
import com.hxyd.dyt.accountManager.modle.LoaninfoM;
import com.hxyd.dyt.accountManager.modle.entity.LoanInfo;
import com.hxyd.dyt.accountManager.modle.in.LoaninfoMI;
import com.hxyd.dyt.accountManager.presenter.in.LoaninfoPI;
import com.hxyd.dyt.accountManager.view.in.LoaninfoVI;
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

public class LoaninfoP implements LoaninfoPI {

    private LoaninfoMI MI;
    private LoaninfoVI VI;

    public LoaninfoP(LoaninfoVI vi) {
        this.VI = vi;
        MI = new LoaninfoM();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onGetData() {

    }

    @Override
    public void onSubmit(LoanInfo loanInfo) {
        if (MI != null && VI != null) {
            Gson gson = new Gson();
            Map<String, Object> map = JsonToMap.toMap(gson.toJson(loanInfo));
            map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());//Constant.getToken()
            map = Constant.addMap(map);
            MI.saveLoanInfo(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<Object>() {
                        @Override
                        public void onNext(Object data) {
                            Logger.e(data.toString());
                            if(VI!=null) {
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
