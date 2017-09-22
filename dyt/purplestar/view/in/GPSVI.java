package com.hxyd.dyt.purplestar.view.in;

import com.hxyd.dyt.purplestar.modle.entity.GPS;

/**
 * Created by win7 on 2017/8/30.
 */

public interface GPSVI {

    void onShowDialog(String str);

    void onDissm();

    void onShowMessage(int type,String message);

    void setData(GPS gps);

}
