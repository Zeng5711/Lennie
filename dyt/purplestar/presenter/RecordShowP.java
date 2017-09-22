package com.hxyd.dyt.purplestar.presenter;

import android.text.TextUtils;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.purplestar.modle.RecordShowM;
import com.hxyd.dyt.purplestar.modle.entity.RecorShow;
import com.hxyd.dyt.purplestar.modle.in.RecordShowMI;
import com.hxyd.dyt.purplestar.presenter.in.RecordShowPI;
import com.hxyd.dyt.purplestar.view.in.RecordShowVI;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/8/31.
 */

public class RecordShowP implements RecordShowPI {


    private RecordShowMI m;
    private RecordShowVI v;

    public RecordShowP(RecordShowVI v) {
        this.v = v;
        m = new RecordShowM();
    }

    @Override
    public void getTrackPlayback(String imeiId, String first, final String startTime, String endTime, int type, int day) {

        v.onShowDialog("正在努力加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("imeiId", imeiId);
        map.put("type", type);
        map.put("first", first);
        if (!TextUtils.isEmpty(startTime)) {
            map.put("startTime", startTime);
            map.put("endTime", endTime);
        }
        map.put("day", day);
        map = Constant.addMap(map);

        m.getTrackPlayback(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<RecorShow>() {
                    @Override
                    public void onNext(RecorShow data) {
                        v.onDissm();
                        v.showRecordShow(data);
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
