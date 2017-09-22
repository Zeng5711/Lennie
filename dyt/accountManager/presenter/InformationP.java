package com.hxyd.dyt.accountManager.presenter;

import com.hxyd.dyt.accountManager.modle.InformationM;
import com.hxyd.dyt.accountManager.modle.entity.Information;
import com.hxyd.dyt.accountManager.modle.in.InformationMI;
import com.hxyd.dyt.accountManager.presenter.in.InformationPI;
import com.hxyd.dyt.accountManager.view.in.InformationVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.Sign;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/4/20.
 */

public class InformationP implements InformationPI {

    private InformationMI MI;
    private InformationVI VI;

    public InformationP(InformationVI v) {
        this.VI = v;
        MI = new InformationM();
    }

    @Override
    public void getInformation() {
        if (MI != null && VI != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            String token = Constant.getToken();
            map.put(Constant.TOKEN_MAP_KEY, token);
            map = Constant.addMap(map);

            MI.readImageSample(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<Information>() {
                        @Override
                        public void onNext(Information data) {
                            if(VI!=null) {
                                VI.setInformationData(data);
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if(VI!=null) {
                                VI.onErr(str);
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        MI = null;
        VI = null;
    }
}
