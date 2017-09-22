package com.hxyd.dyt.gpsinstallation.presenter.in;

import java.util.Map;

/**
 * Created by win7 on 2017/9/13.
 */

public interface DeviceInfoPI {

    void getImeiInfo(String imeiId, String businessId, String custId);

    void binding(Map<String,Object> map);
}
