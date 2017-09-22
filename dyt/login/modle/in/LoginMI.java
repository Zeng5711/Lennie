package com.hxyd.dyt.login.modle.in;


import com.hxyd.dyt.login.LoginListener;
import com.hxyd.dyt.login.modle.entity.UserInfo;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/3/2.
 */

public interface LoginMI {
    Observable<UserInfo> submit(Map<String,Object> map);
    void saveUser(UserInfo userInfo, LoginListener listener);
    void onDestroy();
}
