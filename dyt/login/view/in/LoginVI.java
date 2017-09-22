package com.hxyd.dyt.login.view.in;

/**
 * Created by win7 on 2017/3/2.
 */

public interface LoginVI {

    void onShowProgressBar();

    void onCloseProgressBar();

    void onLoginSuccess(String username,String roleName,String orderTotal);

    void onLoginErr(String str);

    void onPrompt(int type ,String str);
}
