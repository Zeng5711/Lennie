package com.hxyd.dyt.main;

/**
 * Created by win7 on 2017/5/11.
 */

public interface CallDownAPKListener {
    void onSetDownDialogStr(int max, int Progress);

    void onCloseDownDialog();

    void onSuccess(String path);

    void onErr();

}
