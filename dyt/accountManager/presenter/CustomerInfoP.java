package com.hxyd.dyt.accountManager.presenter;

import com.google.gson.Gson;
import com.hxyd.dyt.accountManager.modle.SingleM;
import com.hxyd.dyt.accountManager.modle.entity.CustomerInfo;
import com.hxyd.dyt.accountManager.modle.entity.LoanInfoID;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.entity.ProductInfoList;
import com.hxyd.dyt.accountManager.modle.in.SingleMI;
import com.hxyd.dyt.accountManager.presenter.in.SinglePI;
import com.hxyd.dyt.accountManager.view.in.CustomerInfoVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.JsonToMap;
import com.hxyd.dyt.common.uitl.Sign;
import com.hxyd.dyt.common.uitl.Tools;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/5/16.
 */

public class CustomerInfoP implements SinglePI {

    private SingleMI M;

    private CustomerInfoVI V;

    public static final int PROMPT_ONE = 0;
    public static final int PROMPT_TOW = 1;

    public CustomerInfoP(CustomerInfoVI vi) {
        this.V = vi;
        M = new SingleM();
    }

    @Override
    public void getBaseData() {
        if (M != null && V != null) {

            V.showDial("正在努力加载中...");
            Map<String, Object> map = new HashMap<String, Object>();
            map = Constant.addMap(map);
            M.getSelectData(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<BaseData>() {
                        @Override
                        public void onNext(BaseData data) {
                            if (V != null) {
                                ADDataManager.getInstance().setBaseData(data);
//                                M.saveBaseData(data);
                                getArealist();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                if (!str.equals("登录已过期，请重新登录！")) {
                                    V.onErr(PROMPT_ONE, "更新基础数据失败");
                                } else {
                                    Tools.makeText(str);
                                }
                                V.onDismiss();
                            }
                        }
                    });
        }
    }

    @Override
    public void getArealist() {
        if (M != null && V != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map = Constant.addMap(map);
            M.getAreaList(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<List<AreaList>>() {
                        @Override
                        public void onNext(List<AreaList> data) {
                            if (V != null) {
                                ADDataManager.getInstance().setAreaLists(data);
//                                M.saveAreaList(data);
                                V.onSetArealist(data);
                                getProductInfoList();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                if (!str.equals("登录已过期，请重新登录！")) {
                                    V.onErr(PROMPT_ONE, "更新区域数据失败");
                                } else {
                                    Tools.makeText(str);
                                }
                                V.onDismiss();
                            }
                        }
                    });
        }
    }

    @Override
    public void getProductInfoList() {
        if (M != null && V != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("token", Constant.getToken());
            map = Constant.addMap(map);
            M.getProductInfoList(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<ProductInfoList>() {
                        @Override
                        public void onNext(ProductInfoList data) {
                            if (V != null) {
                                if (data.getCode().equals("0") && data.getList() != null && !data.getList().isEmpty()) {
                                    ADDataManager.getInstance().setProductInfoList(data);
//                                    M.saveProductInfoList(data);
                                    V.onLoadProvince();
                                } else if (data.getCode().equals("1")) {
                                    V.onErr(PROMPT_ONE, "用户未同步counter,请联系管理员");
                                } else if (data.getCode().equals("2")) {
                                    V.onErr(PROMPT_ONE, "用户未设置门店,请联系管理员");
                                }
                                V.onDismiss();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                if (!str.equals("登录已过期，请重新登录！")) {
                                    V.onErr(PROMPT_ONE, "更新产品数据失败");
                                } else {
                                    Tools.makeText(str);
                                }

                                V.onDismiss();
                            }
                        }
                    });
        }
    }

    @Override
    public void getOrderDefault(String orderNo) {
        if (M != null && V != null) {
            OrderDefultInfo defultInfo = ADDataManager.getInstance().getDefultInfo();
//            if (defultInfo == null) {
//                defultInfo = M.getOrderDefultInfo(orderNo);
//                ADDataManager.getnstance().setOrderDefultInfo(defultInfo);
//            }
            if (defultInfo != null) {
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
    public void onSubmit(CustomerInfo c) {
        if (M != null && V != null) {
            V.showDial("正在努力加载中...");
            Gson gson = new Gson();
            String json = gson.toJson(c);
            Logger.e("json===>" + json);

            Map<String, Object> map = JsonToMap.toMap(json);
            map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());//Constant.getToken()
            map = Constant.addMap(map);
            Logger.e(map.toString());

            M.recordLoan(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<LoanInfoID>() {
                        @Override
                        public void onNext(LoanInfoID data) {
                            Logger.e(data.toString());
                            if (V != null) {
                                ADDataManager.getInstance().setLoanInfoId(data.getLoanInfoId());
                                ADDataManager.getInstance().setValuationPrice(data.getValuationPrice());
                                ADDataManager.getInstance().setAudiValuationPriceReall(data.getAudiValuationPrice());
                                ADDataManager.getInstance().setMessage(data.getMessage());
                                V.onShowAcAssessmentResult();
                                V.onDismiss();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                V.onDismiss();
                                V.onErr(PROMPT_TOW, str);
                            }
                        }
                    });
        }
    }
}
