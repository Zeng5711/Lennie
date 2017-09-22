package com.hxyd.dyt.accountManager.modle.in;

import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.accountManager.modle.entity.ImageInfo;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by win7 on 2017/3/10.
 */

public interface ImageInfoMI {

//    Observable<List<ImageInfo>> imgReadAll(String token,String loanId,String posttime,String sign);
    Observable<CountTotal> imgUpload(String url,Map<String,Object> map, Map<String,RequestBody> params);
    Observable<Object> onDeleteImage(Map<String,Object> map);
    Observable<Object>  onSyncappdata(Map<String,Object> map);
    void onDestroy();
}
