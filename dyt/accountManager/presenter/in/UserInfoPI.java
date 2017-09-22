package com.hxyd.dyt.accountManager.presenter.in;


import com.hxyd.dyt.accountManager.modle.entity.SUserInfo;

/**
 * Created by win7 on 2017/3/9.
 */

public interface UserInfoPI {

    void onDestroy();

    void onGetData();

    void onSubmit(SUserInfo SUserInfo);

}
