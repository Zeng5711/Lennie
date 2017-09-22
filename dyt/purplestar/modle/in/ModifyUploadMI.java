package com.hxyd.dyt.purplestar.modle.in;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/4.
 */

public interface ModifyUploadMI {
    Observable<Object> updateUploadTimek(Map<String, Object> map);
}
