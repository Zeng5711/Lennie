package com.hxyd.dyt.login.view.in;

/**
 * Created by win7 on 2017/3/7.
 */

public interface UpdataPasswordVI {

    static final int ERR_ONE = 1;
    static final int ERR_TOW = 2;
    static final int ERR_THREE = 3;
    static final int ERR_FOUR = 4;

    void onErr(int type ,String str);

    void onShowProgressBar();

    void onCloseProgressBar();

    void onShowDialog(String str);

    void onSuccess();

}
