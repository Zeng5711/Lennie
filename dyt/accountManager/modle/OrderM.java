package com.hxyd.dyt.accountManager.modle;


import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.entity.OrderInfo;
import com.hxyd.dyt.accountManager.modle.in.OrderMI;
import com.hxyd.dyt.common.db.DBHelp;

import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by win7 on 2017/3/14.
 */

public class OrderM implements OrderMI {

    private RealmAsyncTask mTransaction;

    @Override
    public Observable<OrderInfo> getInfolist(Map<String,Object> map) {
        return GitHubAPI.craetRetrofit().infolist(map).map(new ResponseFunc<OrderInfo>());
    }

    @Override
    public Observable<OrderDefultInfo> getLoandetail(Map<String,Object> map) {
        return GitHubAPI.craetRetrofit().loandetail(map).map(new ResponseFunc<OrderDefultInfo>());
    }

    public void saveOrderDefultInfo(final OrderDefultInfo orderDefultInfo) {
//        Realm realm = DBHelp.getInstance().getRealm();
//        RealmResults<OrderDefultInfo> results = realm.where(OrderDefultInfo.class).equalTo("baseInfo.loanInfoId", orderDefultInfo.getBaseInfo().getLoanInfoId()).findAll();
//        if (results.size() == 0) {
//            orderDefultInfo.setPrimary(generateNewPrimaryKey(realm));
//        }
//        mTransaction = realm.executeTransactionAsync(new Realm.Transaction() {
//
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealmOrUpdate(orderDefultInfo);
//            }
//        });
    }

    private long generateNewPrimaryKey(Realm realm) {
        long primaryKey = 0;
//        RealmResults<OrderDefultInfo> results = realm.where(OrderDefultInfo.class).findAll();
//        if (results != null && results.size() > 0) {
//            OrderDefultInfo last = results.last();
//            primaryKey = last.getPrimary() + 1;
//        }
        return primaryKey;
    }
}
