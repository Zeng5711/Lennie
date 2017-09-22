package com.hxyd.dyt.login.presenter.in;

import com.hxyd.dyt.login.LoginListener;

/**
 * Created by win7 on 2017/3/2.
 */

public interface LoginPI {
    void submit(String userName, String password);
    void onDestroy();
}
