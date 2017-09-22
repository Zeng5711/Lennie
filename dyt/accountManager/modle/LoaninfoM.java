package com.hxyd.dyt.accountManager.modle;

import com.hxyd.dyt.accountManager.modle.entity.LoanInfo;
import com.hxyd.dyt.accountManager.modle.in.BaseDataMI;
import com.hxyd.dyt.accountManager.modle.in.LoaninfoMI;
import com.hxyd.dyt.common.db.DBHelp;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by win7 on 2017/3/9.
 */

public class LoaninfoM implements LoaninfoMI {
//    @Override
//    public Observable<LoanInfo> queryLoanInfo(Map<String,Object> map) {
//        return GitHubAPI.craetRetrofit().queryLoanInfo(loanInfoId,token).map(new ResponseFunc<LoanInfo>());
//    }

    @Override
    public Observable<Object> saveLoanInfo(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().saveLoanInfo(map).map(new ResponseFunc<Object>());
    }

    @Override
    public void onDestroy() {

    }
}
