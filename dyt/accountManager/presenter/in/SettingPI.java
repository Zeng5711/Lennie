package com.hxyd.dyt.accountManager.presenter.in;

/**
 * Created by win7 on 2017/4/17.
 */

public interface SettingPI {
    void onLoginOut();
    void checkVersion();
    void downAPK(String url);
    void onDestroy();
}
