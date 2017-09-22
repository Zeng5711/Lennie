package com.hxyd.dyt.accountManager.modle.in;

import com.google.gson.JsonArray;
import com.hxyd.dyt.accountManager.modle.entity.JingZhengu;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/5/4.
 */

public interface JingZhenGuMI {
    Observable<JsonArray> getJingZhenGuData(Map<String,Object> map);
    Observable<JingZhengu> getJingZhenGuObjectData(Map<String,Object> map);
    void onDestroy();
}
