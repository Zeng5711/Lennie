package com.hxyd.dyt.purplestar.modle;

import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.purplestar.modle.entity.PurpleStar;
import com.hxyd.dyt.purplestar.modle.in.PurpleStarMI;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/8/29.
 */

public class PurpleStarM  implements PurpleStarMI {
    @Override
    public Observable<PurpleStar> getStoreList(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().getStoreList(map).map(new ResponseFunc<PurpleStar>());
    }
}
