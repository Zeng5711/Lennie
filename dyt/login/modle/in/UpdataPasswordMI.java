package com.hxyd.dyt.login.modle.in;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/3/7.
 */

public interface UpdataPasswordMI {
    Observable<Object> submit(Map<String,Object> map);
    Observable<Object> logOut(Map<String,Object> map);
    void closeTeken();
    void onDestroy();
}
