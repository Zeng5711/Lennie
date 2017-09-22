package com.hxyd.dyt.main.presenter.in;

import com.hxyd.dyt.main.CallDownAPKListener;

import java.util.Map;

/**
 * Created by win7 on 2017/5/11.
 */

public interface MainPI {
    void checkVersion();
    void mainInfo();
    void downAPK();
    void onDestroy();
}
