package com.hxyd.dyt.purplestar.modle.in;

import com.hxyd.dyt.purplestar.modle.entity.RecorShow;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/8/31.
 */

public interface RecordShowMI {

    Observable<RecorShow> getTrackPlayback(Map<String,Object> map);
}
