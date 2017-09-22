package com.hxyd.dyt.accountManager.modle;

import android.util.Log;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.accountManager.modle.entity.ProductInfoList;
import com.hxyd.dyt.accountManager.modle.entity.Version;
import com.hxyd.dyt.accountManager.modle.in.AccountManagerMI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.db.DBHelp;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.common.entity.CityBean;
import com.hxyd.dyt.common.entity.CityBeanX;
import com.hxyd.dyt.common.entity.PickerViewData;
import com.hxyd.dyt.common.entity.ProvinceBean;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.common.http.ResponseFuncT;
import com.hxyd.dyt.common.http.Result;
import com.hxyd.dyt.login.modle.entity.UserInfo;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmList;
import io.realm.RealmResults;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import rx.Observable;

/**
 * Created by win7 on 2017/3/3.
 */

public class AccountManagerM implements AccountManagerMI {



    @Override
    public Observable<Object> logOut(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().logout(map).map(new ResponseFunc<Object>());
    }

    @Override
    public Observable<Version> checkVersion(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().check(map).map(new ResponseFunc<Version>());
    }

    @Override
    public UserInfo getUserInfo() {
        UserInfo userInfo = null;
//        Realm realm = DBHelp.getInstance().getRealm();
//        RealmResults<UserInfo> results = realm.where(UserInfo.class).equalTo("userId", Constant.getUserID()).findAll();
//        if (!results.isEmpty()) {
//            userInfo = results.first();
//        }
        return userInfo;
    }

    @Override
    public Observable<CountTotal> mainInfo(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().mainInfo(map). map(new ResponseFunc<CountTotal>());
    }


    @Override
    public void onDestroy() {

    }
}
