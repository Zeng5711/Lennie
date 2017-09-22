package com.hxyd.dyt.login.presenter.in;

/**
 * Created by win7 on 2017/3/7.
 */

public interface UpdataPasswordPI {

    void submit(String lod,String new_one,String new_tow);

    void onDestroy();

    void loginOut();
}
