package com.hxyd.dyt.purplestar.presenter;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.purplestar.modle.ModifyUploadM;
import com.hxyd.dyt.purplestar.modle.in.ModifyUploadMI;
import com.hxyd.dyt.purplestar.presenter.in.ModifyUploadPI;
import com.hxyd.dyt.purplestar.view.fragment.in.ModifyUploadVI;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/9/4.
 */

public class ModifyUploadP implements ModifyUploadPI {

    private ModifyUploadMI m;
    private ModifyUploadVI v;

    public ModifyUploadP(ModifyUploadVI v) {
        this.v = v;
        m = new ModifyUploadM();
    }

    @Override
    public void updateUploadTimek(String imeiId, int type) {

        v.onShowDialog("正在加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("imeiId", imeiId);
        map.put("type", type);
        map = Constant.addMap(map);

        m.updateUploadTimek(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        v.onDissm();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.onDissm();
                        v.onShowMessage(1, str);
                    }
                });
    }
}
