package com.hxyd.dyt.accountManager.view.in;

import com.hxyd.dyt.accountManager.modle.entity.GPSInstall;
import com.hxyd.dyt.accountManager.modle.entity.OrderInfo;
import com.hxyd.dyt.accountManager.modle.entity.Task;

/**
 * Created by win7 on 2017/5/24.
 */

public interface TaskVI {
    void onSetInfolist(Task list);
    void onErr(int type,String str);
    void showDialg(String message);
    void dissm();
    void setGPSInstall(GPSInstall date);

}
