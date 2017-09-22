package com.hxyd.dyt.accountManager.presenter;

import android.text.TextUtils;

import com.hxyd.dyt.accountManager.modle.OrderM;
import com.hxyd.dyt.accountManager.modle.SingleM;
import com.hxyd.dyt.accountManager.modle.entity.CustomerInfo;
import com.hxyd.dyt.accountManager.modle.entity.ProductInfoList;
import com.hxyd.dyt.accountManager.modle.in.SingleMI;
import com.hxyd.dyt.accountManager.presenter.in.SinglePI;
import com.hxyd.dyt.accountManager.view.in.SingleVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.Sign;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/3/13.
 */

public class SingleP implements SinglePI {

    private SingleMI MI;
    private SingleVI VI;

    public SingleP(SingleVI vi) {
        MI = new SingleM();
        this.VI = vi;
    }

    private void loadAreaList() {
        if (MI != null && VI != null) {
            Map<String, Object> map = new HashMap<>();
            map = Constant.addMap(map);
            MI.getAreaList(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<List<AreaList>>() {
                        @Override
                        public void onNext(List<AreaList> data) {
                            if(VI!=null && VI!=null) {
                                MI.saveAreaList(data);
                                VI.onSetArealist(data);
                                loadProductInfoList();
                                VI.onDismiss();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if(VI!=null) {
                                VI.onErr("更新区域数据失败");
                                VI.onDismiss();
                            }
                        }
                    });
        }
    }

    private void loadProductInfoList() {
        if (MI != null && VI != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("token", Constant.getToken());
            map = Constant.addMap(map);
            MI.getProductInfoList(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<ProductInfoList>() {
                        @Override
                        public void onNext(ProductInfoList data) {
                            if(VI!=null && MI!=null) {
                                Logger.e("List<ProductInfoList> === >" + data.getList());
                                MI.saveProductInfoList(data);
                                VI.onSetProductInfoList(data);
                                VI.onGetOrderDefault();
                                VI.onDismiss();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if(VI!=null) {
                                Logger.e("List<ProductInfoList> === >" + str);
                                VI.onErr("更新产品数据失败");
                                VI.onDismiss();
                            }
                        }
                    });
        }
    }

    @Override
    public void getBaseData() {
        if (MI != null && VI != null) {
            Map<String, Object> map = new HashMap<>();
            map = Constant.addMap(map);
            MI.getSelectData(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<BaseData>() {
                        @Override
                        public void onNext(BaseData data) {
                            if(VI!=null) {
                                Logger.e(data.toString());
                                MI.saveBaseData(data);
                                VI.onSetBaseData(data);
                                loadAreaList();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if(VI!=null) {
                                Logger.e(str);
                                VI.onErr("更新基础数据失败");
                                VI.onDismiss();
                            }
                        }
                    });
        }
    }

    @Override
    public void getArealist() {
    }

    @Override
    public void getProductInfoList() {
    }

    @Override
    public void getOrderDefault(String orderNo) {
        if (MI != null && VI != null) {
            VI.onSetOrderDefault(((SingleM) MI).getOrderDefultInfo(orderNo));
        }
    }

    @Override
    public void onDestroy() {
        MI.onDestroy();
        MI = null;
        VI = null;
    }

    @Override
    public void onSubmit(CustomerInfo c) {

    }
}
