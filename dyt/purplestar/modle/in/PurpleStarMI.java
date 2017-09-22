package com.hxyd.dyt.purplestar.modle.in;

import com.hxyd.dyt.purplestar.modle.entity.PurpleStar;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/8/29.
 */

public interface PurpleStarMI {

    Observable<PurpleStar> getStoreList(Map<String, Object> map);
}
