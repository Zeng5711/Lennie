package com.hxyd.dyt.accountManager.presenter;


import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.hxyd.dyt.accountManager.modle.JingZhenGuM;
import com.hxyd.dyt.accountManager.modle.entity.Contact;
import com.hxyd.dyt.accountManager.modle.entity.JingZhengu;
import com.hxyd.dyt.accountManager.modle.entity.Make;
import com.hxyd.dyt.accountManager.modle.entity.Model;
import com.hxyd.dyt.accountManager.modle.entity.Province;
import com.hxyd.dyt.accountManager.modle.entity.Style;
import com.hxyd.dyt.accountManager.modle.entity.VIN;
import com.hxyd.dyt.accountManager.modle.in.JingZhenGuMI;
import com.hxyd.dyt.accountManager.presenter.in.JingZhenGuPI;
import com.hxyd.dyt.accountManager.view.in.JingZhenGuVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.CardBean;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.GsonFactory;
import com.hxyd.dyt.common.uitl.Sign;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/5/4.
 */

public class JingZhenGuP implements JingZhenGuPI {

    public static final String OPERATE_VIN = "VIN";//VIN解析
    public static final String OPERATE_MAKE = "Make";//获取品牌
    public static final String OPERATE_MODEL = "Model";//根据品牌ID获取车系信息
    public static final String OPERATE_STYLE = "Style";//根据车系Id获取车型信息
    public static final String OPERATE_PROVINCE = "Province";//获取省份信息
    public static final String OPERATE_CITY = "City";//根据省份ID获取城市信息
    public static final String OPERATE_ESTIMATE = "Estimate";//估值方法
    public static final String OPERATE_LICENCEPLATECITY = "";//车牌号码解析城市

    private JingZhenGuMI M;

    private JingZhenGuVI V;

    public JingZhenGuP(JingZhenGuVI v) {
        this.V = v;
        M = new JingZhenGuM();
    }

    @Override
    public void getJingZhenGuM(final String Operate, Map<String, Object> map) {
        if (M != null && V != null) {
            V.showDialog(0, "", "正在努力查询中...");

            map.put("token", Constant.getToken());
            map.put("Operate", Operate);
            map = Constant.addMap(map);


            M.getJingZhenGuData(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<JsonArray>() {
                        @Override
                        public void onNext(JsonArray data) {
                            if (V != null && data != null) {
                                Logger.e("JsonArray ====> " + data.toString());
                                if (OPERATE_MAKE.equals(Operate)) {
                                    ArrayList<Make> list = GsonFactory.jsonToArrayList(data.toString(), Make.class);
                                    setMake(list);
                                } else if (OPERATE_VIN.equals(Operate)) {
                                    ArrayList<VIN> list = GsonFactory.jsonToArrayList(data.toString(), VIN.class);
                                    V.setVIN(list);
                                } else if (OPERATE_MODEL.equals(Operate)) {
                                    ArrayList<Model> list = GsonFactory.jsonToArrayList(data.toString(), Model.class);
                                    setModel(list);
                                } else if (OPERATE_STYLE.equals(Operate)) {
                                    ArrayList<Style> list = GsonFactory.jsonToArrayList(data.toString(), Style.class);
                                    setStyle(list);
                                } else if (OPERATE_PROVINCE.equals(Operate) || OPERATE_CITY.equals(Operate)) {
                                    String jsonArray = data.toString();
                                    if(!TextUtils.isEmpty(jsonArray)) {
                                        jsonArray = jsonArray.replace("[", "");
                                        jsonArray = jsonArray.replace("]", "");
                                        String json[] = jsonArray.split(",");
                                        ArrayList<Province> list = new ArrayList<Province>();
                                        for (int i = 0; i < json.length; i++) {
                                            String str = json[i];
                                            str = str.replace("{", "");
                                            str = str.replace("}", "");
                                            str = str.replace("\"", "");
                                            String newStr[] = str.split(":");
                                            list.add(new Province(newStr[1], newStr[0], false));
                                        }

//                            ArrayList<Province> list = GsonFactory.jsonToArrayList(data.toString(), Province.class);
                                        if (OPERATE_PROVINCE.equals(Operate)) {
                                            V.setProvince(list);
                                        } else {
                                            V.setCity(list);
                                        }
                                    }
                                }
                                V.colseDialog();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                V.onPrompt(0, str);
                                V.colseDialog();
                            }
                        }
                    });

        }
    }

    @Override
    public void getJingZhenGuObjectData(String Operate, Map<String, Object> map) {
        if (M != null && V != null) {
            V.showDialog(0, "", "正在努力查询中...");

            map.put("token", Constant.getToken());
            map.put("Operate", Operate);
            map = Constant.addMap(map);
            M.getJingZhenGuObjectData(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<JingZhengu>() {
                        @Override
                        public void onNext(JingZhengu data) {
                            if (V != null) {
                                V.colseDialog();
                                String C2BPrices = data.getC2BPrices();
                                Logger.e("C2BPrices" + C2BPrices);
                                V.setEstimate(C2BPrices);
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                V.onPrompt(0, str);
                                V.colseDialog();
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        M.onDestroy();
        M = null;
        V = null;
    }

    private void setMake(ArrayList<Make> list) {
        if (V != null) {
            if (list != null) {
                ArrayList<Contact> contacts = new ArrayList<>();
                for (Make m : list) {
                    contacts.add(new Contact(m.getGroupName(), m.getName(), m.getMakeId(), false));
                }
                V.setMake(contacts);
            }
        }
    }

    private void setModel(ArrayList<Model> list) {
        if (V != null) {
            if (list != null) {
                ArrayList<Contact> contacts = new ArrayList<>();
                for (Model m : list) {
                    contacts.add(new Contact(m.getGroupName(), m.getText(), m.getValue(), false));
                }
                V.setModel(contacts);
            }
        }

    }

    private void setStyle(ArrayList<Style> list) {
        if (V != null) {
            if (list != null) {
                ArrayList<Contact> contacts = new ArrayList<>();
                for (Style m : list) {
                    contacts.add(new Contact(m.getGroupName(), m.getText(), m.getValue(), false));
                }
                V.setStyle(contacts);
            }
        }
    }

//    public void setProvince(ArrayList<Province> list) {
//        if (list != null) {
//            ArrayList<CardBean> cardBean = new ArrayList<>();
//            for (Province p : list) {
//                cardBean.add(new CardBean(p.getCityId(), p.getCityName()));
//            }
//            V.setProvince(cardBean);
//        }
//    }
}
