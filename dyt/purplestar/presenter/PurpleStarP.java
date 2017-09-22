package com.hxyd.dyt.purplestar.presenter;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.SharedPrefsUtil;
import com.hxyd.dyt.purplestar.modle.PurpleStarM;
import com.hxyd.dyt.purplestar.modle.entity.PurpleStar;
import com.hxyd.dyt.purplestar.modle.in.PurpleStarMI;
import com.hxyd.dyt.purplestar.presenter.in.PurpleStarPI;
import com.hxyd.dyt.purplestar.view.in.PurpleStarVI;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/8/29.
 */

public class PurpleStarP implements PurpleStarPI {
    private PurpleStarMI m;
    private PurpleStarVI v;

    public PurpleStarP(PurpleStarVI v){
        this.v = v;
        m = new PurpleStarM();
    }


    @Override
    public void getStoreList() {

        v.onShowDialog("正在努力加载中...");

        Map<String,Object> map = new HashMap<String,Object>();
        map = Constant.addMap(map);
        map.put("name", SharedPrefsUtil.getValue(null,Constant.ACCOUNT_NAME,""));
        map = Constant.addMap(map);

        m.getStoreList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<PurpleStar>() {
                    @Override
                    public void onNext(PurpleStar data) {
                        v.onDissm();
                        v.setStoreList(data);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.onShowMessage(1,str);
                        v.onDissm();
                    }
                });

    }
}
