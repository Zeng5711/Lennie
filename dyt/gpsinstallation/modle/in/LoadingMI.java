package com.hxyd.dyt.gpsinstallation.modle.in;

import com.hxyd.dyt.gpsinstallation.entity.ImeiInfo;
import com.hxyd.dyt.gpsinstallation.entity.Loading;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by win7 on 2017/9/14.
 */

public interface LoadingMI {

    Observable<Object> uploadImg(String url, Map<String, Object> map, Map<String, RequestBody> params);

    Observable<Object> deleteImg(Map<String, Object> map);

    Observable<Loading> queryAllImgByParams(Map<String, Object> map);

    Observable<ImeiInfo> updateImeiInfo(Map<String, Object> map);

    Observable<Object> updateLoanAudiByBussinessId(Map<String, Object> map);

    Observable<Object> saveCarEvaluation(Map<String, Object> map);

}
