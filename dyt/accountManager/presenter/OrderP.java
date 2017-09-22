package com.hxyd.dyt.accountManager.presenter;

import com.hxyd.dyt.accountManager.modle.OrderM;
import com.hxyd.dyt.accountManager.modle.entity.OrderInfo;
import com.hxyd.dyt.accountManager.modle.in.OrderMI;
import com.hxyd.dyt.accountManager.presenter.in.OrderPI;
import com.hxyd.dyt.accountManager.view.in.OrderVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.Sign;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/3/14.
 */

public class OrderP implements OrderPI {

    private OrderMI MI;
    private OrderVI VI;

    public OrderP(OrderVI vi) {
        this.VI = vi;
        MI = new OrderM();
    }

    @Override
    public void getInfolist(String token, String pageIndex, String queryParameter, boolean isShow) {
        if (MI != null && VI != null) {
            if (isShow) {
                VI.showDialg("正在努力加载中...");
            }

            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("pageIndex", pageIndex);
            map.put("queryParameter", queryParameter);
            map = Constant.addMap(map);

            MI.getInfolist(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<OrderInfo>() {
                        @Override
                        public void onNext(OrderInfo data) {
                            if (VI != null) {
                                Logger.e("data === >" + data.getLoanList());
                                VI.onSetInfolist(data);
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (VI != null) {
                                VI.onErr(str);
                                Logger.e(str);
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
