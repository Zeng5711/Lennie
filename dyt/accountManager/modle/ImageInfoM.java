package com.hxyd.dyt.accountManager.modle;

import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.accountManager.modle.entity.ImageInfo;
import com.hxyd.dyt.accountManager.modle.in.BaseDataMI;
import com.hxyd.dyt.accountManager.modle.in.ImageInfoMI;
import com.hxyd.dyt.common.db.DBHelp;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by win7 on 2017/3/10.
 */

public class ImageInfoM implements ImageInfoMI{

//    @Override
//    public Observable<List<ImageInfo>> imgReadAll(Map<String,Object> map) {
//        return GitHubAPI.craetRetrofit().imgReadAll(map).map(new ResponseFunc<List<ImageInfo>>());
//    }

    @Override
    public Observable<CountTotal> imgUpload(String url,Map<String,Object> map,  Map<String,RequestBody> params) {
        return GitHubAPI.craetRetrofit().imgUpload(url,map,params).map(new ResponseFunc<CountTotal>());
    }

    @Override
    public Observable<Object> onDeleteImage(Map<String,Object> map) {
        return GitHubAPI.craetRetrofit().deleteFile(map).map(new ResponseFunc<Object>());
    }

    @Override
    public Observable<Object> onSyncappdata(Map<String,Object> map) {
        return GitHubAPI.craetRetrofit().syncAppData(map).map(new ResponseFunc<Object>());
    }

    @Override
    public void onDestroy() {

    }
}
