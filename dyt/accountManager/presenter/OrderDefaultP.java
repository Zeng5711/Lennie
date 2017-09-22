package com.hxyd.dyt.accountManager.presenter;

import com.hxyd.dyt.accountManager.modle.OrderM;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.in.OrderMI;
import com.hxyd.dyt.accountManager.presenter.in.OrderDefaultPI;
import com.hxyd.dyt.accountManager.view.in.OrderDefaultVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.Sign;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/3/15.
 */

public class OrderDefaultP implements OrderDefaultPI {

    private OrderMI MI;
    private OrderDefaultVI VI;

    public OrderDefaultP(OrderDefaultVI vi) {
        this.VI = vi;
        MI = new OrderM();
    }

    @Override
    public void getLoandetail(String token, String orderNo) {
        if (MI != null && VI != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("orderNo", orderNo);
            map = Constant.addMap(map);
            MI.getLoandetail(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<OrderDefultInfo>() {
                        @Override
                        public void onNext(OrderDefultInfo data) {
                            if (VI != null) {
                                Logger.e("OrderDefultInfo ==== >" + data.toString());
//                                ((OrderM) MI).saveOrderDefultInfo(data);
                                ADDataManager.getInstance().setDefultInfo(data);
                                VI.setData(data);
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (VI != null) {
                                Logger.e(str);
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
