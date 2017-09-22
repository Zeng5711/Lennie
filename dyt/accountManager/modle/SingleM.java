package com.hxyd.dyt.accountManager.modle;

import com.hxyd.dyt.accountManager.modle.entity.LoanInfoID;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.entity.ProductInfoList;
import com.hxyd.dyt.accountManager.modle.in.SingleMI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.db.DBHelp;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.common.http.ResponseFuncT;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by win7 on 2017/3/13.
 */

public class SingleM implements SingleMI {

    private RealmAsyncTask mTransaction;
    private RealmAsyncTask mTransaction2;

    @Override
    public BaseData getBaseData() {
//        Realm realm = DBHelp.getInstance().getRealm();
//        RealmResults<BaseData> results = realm.where(BaseData.class).findAll();
//        if (!results.isEmpty()) {
//            return results.first();
//        }
        return null;
    }

    @Override
    public List<AreaList> getArealist() {
//        Realm realm = DBHelp.getInstance().getRealm();//.where(AreaList.class).findAll();
//        RealmResults<AreaList> results = realm.where(AreaList.class).findAll();
//        if (!results.isEmpty()) {
//            return results;
//        }
        return null;
    }

    @Override
    public ProductInfoList getProductInfoList() {
//        Realm realm = DBHelp.getInstance().getRealm();//.where(AreaList.class).findAll();
//        RealmResults<ProductInfoList> results = realm.where(ProductInfoList.class).findAll();
//        if (!results.isEmpty()) {
//            return results.first();
//        }
        return null;
    }

    @Override
    public OrderDefultInfo getOrderDefultInfo(String orderNo) {
//        Realm realm = DBHelp.getInstance().getRealm();//.where(AreaList.class).findAll();
//        RealmResults<OrderDefultInfo> results = realm.where(OrderDefultInfo.class).equalTo("baseInfo.loanInfoId", orderNo).findAll();
//        if (!results.isEmpty()) {
//            return results.first();
//        }
        return null;
    }

    @Override
    public Observable<BaseData> getSelectData(Map<String,Object> map) {
        return GitHubAPI.craetRetrofit().getSelectData(map).map(new ResponseFunc<BaseData>());
    }

    @Override
    public Observable<List<AreaList>> getAreaList(Map<String,Object> map) {
        return GitHubAPI.craetRetrofit().getAreaList(map).map(new ResponseFuncT<AreaList>());
    }

    @Override
    public Observable<ProductInfoList> getProductInfoList(Map<String,Object> map) {
        return GitHubAPI.craetRetrofit().getProductInfoList(map).map(new ResponseFunc<ProductInfoList>());
    }


    @Override
    public void saveBaseData(final BaseData baseData) {
        Realm realm = DBHelp.getInstance().getRealm();
        if (baseData.getPrimary() == 0) {
            baseData.setPrimary(1);
        }
        mTransaction = realm.executeTransactionAsync(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                RealmResults<BaseData> results = DBHelp.getInstance().getRealm().where(BaseData.class).findAll();
                results.deleteAllFromRealm();
                realm.copyToRealm(baseData);
                Logger.e("saveBaseData =========>execute");

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
            }
        });
    }


    @Override
    public void saveAreaList(final List<AreaList> areaList) {
//        final Realm realm = DBHelp.getInstance().getRealm();
//        mTransaction2 = realm.executeTransactionAsync(
//                new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        RealmResults<AreaList> results = DBHelp.getInstance().getRealm().where(AreaList.class).findAll();
//                        results.deleteAllFromRealm();
//                        for (AreaList areaList1 : areaList) {
////                            areaList1.setPrimary(generateNewPrimaryKey(realm));
//                            realm.copyToRealm(areaList1);
//                        }
//                    }
//                }, new Realm.Transaction.OnSuccess() {
//                    @Override
//                    public void onSuccess() {
////                        RealmResults<AreaList> results = DBHelp.getInstance().getRealm().where(AreaList.class).findAll();
////                        Logger.e("results == >" + results.size());
//
//                    }
//                }
//                , new Realm.Transaction.OnError() {
//                    @Override
//                    public void onError(Throwable error) {
//
//                    }
//                });
    }

    @Override
    public void saveProductInfoList(final ProductInfoList productInfoLists) {
//        final Realm realm = DBHelp.getInstance().getRealm();
////        realm.beginTransaction();
////        ProductInfoList realmUser = realm.copyToRealm(productInfoLists);
////        realm.commitTransaction();
//
//        mTransaction2 = realm.executeTransactionAsync(
//                new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        RealmResults<ProductInfoList> results = DBHelp.getInstance().getRealm().where(ProductInfoList.class).findAll();
////                        if (results.size() == 0) {
////                            productInfoLists.setPrimary(1);
////                        }
//                        results.deleteAllFromRealm();
//                        realm.copyToRealm(productInfoLists);
//                        Logger.e("saveProductInfoList =========>execute");
//                    }
//                }, new Realm.Transaction.OnSuccess() {
//                    @Override
//                    public void onSuccess() {
////                        RealmResults<AreaList> results = DBHelp.getInstance().getRealm().where(AreaList.class).findAll();
////                        Logger.e("results == >" + results.size());
//
//                    }
//                }
//                , new Realm.Transaction.OnError() {
//                    @Override
//                    public void onError(Throwable error) {
//
//                    }
//                });
    }

    @Override
    public void onDestroy() {
        if (mTransaction != null && !mTransaction.isCancelled()) {
            mTransaction.cancel();
        }
        if (mTransaction2 != null && !mTransaction2.isCancelled()) {
            mTransaction2.cancel();
        }
    }

    @Override
    public String getBaseDataVersion() {
        Realm realm = DBHelp.getInstance().getRealm();
        RealmResults<BaseData> results = realm.where(BaseData.class).equalTo("version", Constant.getBaseDataVersion()).findAll();
        if (!results.isEmpty()) {
            return results.first().getVersion();
        }
        return null;
    }

    @Override
    public String getArealistVersion() {
//        Realm realm = DBHelp.getInstance().getRealm();
//        RealmResults<AreaList> results = realm.where(AreaList.class).equalTo("version", Constant.getAreaListVersion()).findAll();
//        if (!results.isEmpty()) {
//            return results.first().getVersion();
//        }
        return null;
    }

    @Override
    public String getProductInfoListVersion() {
//        Realm realm = DBHelp.getInstance().getRealm();
//        RealmResults<ProductInfoList> results = realm.where(ProductInfoList.class).equalTo("version", Constant.getProductInfoListVersion()).findAll();
//        if (!results.isEmpty()) {
//            return results.first().getVersion();
//        }
        return null;
    }

    @Override
    public Observable<LoanInfoID> recordLoan(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().recordLoan(map).map(new ResponseFunc<LoanInfoID>());
    }

    @Override
    public Observable<LoanInfoID> customerInformationInput(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().customerInformationInput(map).map(new ResponseFunc<LoanInfoID>());
    }

    @Override
    public Observable<LoanInfoID> loanAndCarMessageInput(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().loanAndCarMessageInput(map).map(new ResponseFunc<LoanInfoID>());
    }

    private long generateNewPrimaryKey(Realm realm) {
        long primaryKey = 0;
//        RealmResults<AreaList> results = realm.where(AreaList.class).findAll();
//        if (results != null && results.size() > 0) {
//            AreaList last = results.last();
//            primaryKey = last.getPrimary() + 1;
//        }
        return primaryKey;
    }


}
