package com.hxyd.dyt.purplestar.view.in;

import com.hxyd.dyt.purplestar.modle.entity.AlarmScreen;
import com.hxyd.dyt.purplestar.modle.entity.Container;

/**
 * Created by win7 on 2017/8/29.
 */

public interface ContainerVI {

    void onShowDialog(String str);

    void onDissm();

    void onShowMessage(int type, String message);

    void setEquipmentList(int type, Container data, long countTota);

    void setAlarmScreen(AlarmScreen alarmScreen);


}
