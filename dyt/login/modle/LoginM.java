package com.hxyd.dyt.login.modle;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.db.DBHelp;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.login.LoginListener;
import com.hxyd.dyt.login.modle.entity.UserInfo;
import com.hxyd.dyt.login.modle.in.LoginMI;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import rx.Observable;


/**
 * Created by win7 on 2017/3/2.
 */

public class LoginM implements LoginMI {


    private RealmAsyncTask mTransaction;

    @Override
    public Observable<UserInfo> submit(Map<String,Object> map) {
        return GitHubAPI.craetRetrofit().login(map).map(new ResponseFunc());
    }

    @Override
    public void saveUser(final UserInfo userInfo,final LoginListener listener) {
//        Constant.setUserID(userInfo.getUserId());
//        Realm realm = DBHelp.getInstance().getRealm();
//        mTransaction = realm.executeTransactionAsync(new Realm.Transaction() {
//                @Override
//                public void execute(Realm bgRealm) {
//                    bgRealm.copyToRealmOrUpdate(userInfo);
//                }
//            }, new Realm.Transaction.OnSuccess() {
//                @Override
//                public void onSuccess() {
//                    // Transaction was a success.
//                    listener.onSuccess();
//                }
//            }, new Realm.Transaction.OnError() {
//                @Override
//                public void onError(Throwable error) {
//                    // Transaction failed and was automatically canceled.
//                    listener.onErr("登录异常，请重新登录");
//                }
//            });
    }



    @Override
    public void onDestroy() {
        if (mTransaction != null && !mTransaction.isCancelled()) {
            mTransaction.cancel();
        }
    }

}
