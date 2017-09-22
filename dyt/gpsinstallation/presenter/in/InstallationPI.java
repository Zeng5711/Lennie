package com.hxyd.dyt.gpsinstallation.presenter.in;

/**
 * Created by win7 on 2017/9/18.
 */

public interface InstallationPI {

    void teardown(String imeiId);

    void completeTask(String taskId, String processInstanceId);
}
