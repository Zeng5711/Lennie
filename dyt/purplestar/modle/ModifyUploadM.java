package com.hxyd.dyt.purplestar.modle;

import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.purplestar.modle.in.ModifyUploadMI;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/4.
 */

public class ModifyUploadM implements ModifyUploadMI {
    @Override
    public Observable<Object> updateUploadTimek(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().updateUploadTimek(map).map(new ResponseFunc<>());
    }
}
