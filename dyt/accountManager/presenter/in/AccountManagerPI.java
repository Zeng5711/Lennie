package com.hxyd.dyt.accountManager.presenter.in;


/**
 * Created by win7 on 2017/3/3.
 */

public interface AccountManagerPI {

    void onDestroy();

    void onResume();

    void onLoginOut();

    void onSetData();

    void checkVersion();

    void downAPK(String url);

    void getCountTotal();

}
