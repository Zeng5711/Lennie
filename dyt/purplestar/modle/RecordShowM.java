package com.hxyd.dyt.purplestar.modle;

import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.purplestar.modle.entity.RecorShow;
import com.hxyd.dyt.purplestar.modle.in.RecordShowMI;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/8/31.
 */

public class RecordShowM implements RecordShowMI {
    @Override
    public Observable<RecorShow> getTrackPlayback(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().getTrackPlayback(map).map(new ResponseFunc<RecorShow>());
    }
}
