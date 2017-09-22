package com.hxyd.dyt.accountManager.modle.in;

import com.hxyd.dyt.accountManager.modle.entity.Information;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/4/20.
 */

public interface InformationMI {
    Observable<Information> readImageSample(Map<String,Object> map);
}
