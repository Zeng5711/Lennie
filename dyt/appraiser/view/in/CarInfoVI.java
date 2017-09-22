package com.hxyd.dyt.appraiser.view.in;

import com.hxyd.dyt.appraiser.modle.entity.CarInfo;
import com.hxyd.dyt.gpsinstallation.view.in.BaseI;

/**
 * Created by win7 on 2017/9/19.
 */

public interface CarInfoVI extends BaseI {

    void setCarInfo(CarInfo carInfo);
    void updateEvalCarInfo();
}
