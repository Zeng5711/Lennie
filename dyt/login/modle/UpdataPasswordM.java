package com.hxyd.dyt.login.modle;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.db.DBHelp;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.login.modle.entity.UserInfo;
import com.hxyd.dyt.login.modle.in.UpdataPasswordMI;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by win7 on 2017/3/7.
 */

public class UpdataPasswordM implements UpdataPasswordMI {

    private RealmAsyncTask mTransaction;

    @Override
    public Observable<Object> submit(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().update(map).map(new ResponseFunc<Object>());
    }

    @Override
    public Observable<Object> logOut(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().logout(map).map(new ResponseFunc<Object>());
    }

    @Override
    public void closeTeken() {
//        Realm realm = DBHelp.getInstance().getRealm();
//        realm.where(UserInfo.class).equalTo("token", Constant.getToken()).findFirst().setToken("");
    }

    @Override
    public void onDestroy() {

    }
}
