package com.hxyd.dyt.main.model.in;

import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.accountManager.modle.entity.Version;
import com.hxyd.dyt.main.CallDownAPKListener;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;

/**
 * Created by win7 on 2017/5/11.
 */

public interface MainMI {
    Observable<Version> checkVersion(Map<String,Object> map);
    Observable<CountTotal> mainInfo(Map<String,Object> map);
    void downAPK(String url, CallDownAPKListener listener);
    void onDestroy();
}
