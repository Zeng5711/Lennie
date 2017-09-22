package com.hxyd.dyt.accountManager.presenter.in;

import com.hxyd.dyt.accountManager.modle.entity.UserInfoNew;

/**
 * Created by win7 on 2017/5/18.
 */

public interface UserInfoNewPI {
    void getBaseData();
    void getArealist();
    void getOrderDefault(String orderNo);
    void onDestroy();
    void submit(UserInfoNew user);
}
