package com.hxyd.dyt.purplestar.modle;

import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.common.http.Result;
import com.hxyd.dyt.purplestar.modle.entity.AlarmScreen;
import com.hxyd.dyt.purplestar.modle.entity.Container;
import com.hxyd.dyt.purplestar.modle.in.ContainerMI;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/8/29.
 */

public class ContainerM implements ContainerMI {

    @Override
    public Observable<Container> getEquipmentList(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().getEquipmentList(map).map(new ResponseFunc<Container>());
    }

    @Override
    public Observable<Container> getEquipmentListForSuperManager(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().getEquipmentListForSuperManager(map).map(new ResponseFunc<Container>());
    }

    @Override
    public Observable<Container> getAlarmList(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().getAlarmList(map).map(new ResponseFunc<Container>());
    }

    @Override
    public Observable<AlarmScreen> getAlarmScreeningList(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().getAlarmScreeningList(map).map(new ResponseFunc<AlarmScreen>());
    }
}
