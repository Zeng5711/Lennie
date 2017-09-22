package com.hxyd.dyt.accountManager.modle;

import com.hxyd.dyt.accountManager.modle.entity.LoanInfoID;
import com.hxyd.dyt.accountManager.modle.in.BaseDataMI;
import com.hxyd.dyt.accountManager.modle.in.UserInfoMI;
import com.hxyd.dyt.common.db.DBHelp;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.login.modle.entity.UserInfo;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by win7 on 2017/3/9.
 */

public class UserInfoM implements UserInfoMI {

    private RealmAsyncTask mTransaction;
//    @Override
//    public Observable<UserInfo> queryCustomerInfo(String loanInfoId, String token) {
//        return GitHubAPI.craetRetrofit().queryCustomerInfo(loanInfoId,token).map(new ResponseFunc<UserInfo>());
//    }

    @Override
    public Observable<LoanInfoID> saveCustomerInfo(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().saveCustomerInfo(map).map(new ResponseFunc<LoanInfoID>());
    }

    @Override
    public void saveRealm(UserInfo userInfo) {

    }

    @Override
    public void onDestroy() {

    }
}
