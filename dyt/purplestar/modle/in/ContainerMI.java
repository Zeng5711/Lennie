package com.hxyd.dyt.purplestar.modle.in;

import com.hxyd.dyt.common.http.Result;
import com.hxyd.dyt.purplestar.modle.entity.AlarmScreen;
import com.hxyd.dyt.purplestar.modle.entity.Container;

import java.util.Map;

import rx.Observable;


/**
 * Created by win7 on 2017/8/29.
 */

public interface ContainerMI {

    Observable<Container> getEquipmentList(Map<String ,Object> map);

    Observable<Container> getEquipmentListForSuperManager(Map<String ,Object> map);

    Observable<Container> getAlarmList(Map<String ,Object> map);

    Observable<AlarmScreen> getAlarmScreeningList(Map<String ,Object> map);
}
