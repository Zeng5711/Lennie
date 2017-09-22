package com.hxyd.dyt.purplestar.view.fragment.in;

import com.hxyd.dyt.purplestar.modle.entity.DeviceLocation;

/**
 * Created by win7 on 2017/9/1.
 */

public interface DeviceLocationVI {

    void onShowDialog(String str);

    void onDissm();

    void onShowMessage(int type,String message);

    void setDeviceLocation(DeviceLocation deviceLocation);
}
