package com.hxyd.dyt.accountManager.presenter.in;

import com.hxyd.dyt.accountManager.modle.entity.Vehicleinfo;

/**
 * Created by win7 on 2017/3/9.
 */

public interface VehicleinfoPI {
    void onDestroy();

    void onGetData();

    void onSubmit(Vehicleinfo vehicleinfo);
}
