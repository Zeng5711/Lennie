package com.hxyd.dyt.main.view.in;

/**
 * Created by win7 on 2017/5/11.
 */

public interface MainVI {
    void onShowDialog(String title, String message);

    void onCloseDialog();

    void onShowProgress(String title, String message);

    void onSetProgress(int max, int Progress);

    void onCloseProgress();

    void onPrompt(int type, String message);

    void onSetCountTotal(String countTotal);

    void installApk(String path);

    void onShowAlertDialog(String title, String message,boolean isForce);

}
