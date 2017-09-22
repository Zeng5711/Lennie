package com.hxyd.dyt.accountManager.view.in;

import android.content.Intent;

import com.hxyd.dyt.accountManager.modle.entity.Version;

/**
 * Created by win7 on 2017/3/3.
 */

public interface AccountManagerVI {

    void onStartActivity(Class cla);

    void setUser(String userName,String title,int orderTotal);

    void setTotalApplication(String totalApplication);

    void setHeadPortrait(String url);

    void onShowDownDialog(String str,boolean isCheck);

    void onSetDownDialogStr(int max,int  Progress);

    void onCloseDownDialog();

    void onPrompt(int type,String str);

    void onSetCountTotal(String countTotal);

    void onShowUpdataAPK(String  str,String url,boolean isForce);

    void installApk(String path);

}
