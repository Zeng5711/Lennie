package com.hxyd.dyt.assessment.modle.in;

import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.assessment.modle.entity.CarData;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by win7 on 2017/5/25.
 */

public interface AssessmentMI {

    Observable<Object> saveAssessmentData(Map<String, Object> map);

    Observable<CarData> evaluateInformationDetails(Map<String, Object> map);

    Observable<CountTotal> imgUpload(String url, Map<String,Object> map, Map<String, RequestBody> params);

    Observable<Object> onDeleteImage(Map<String,Object> map);

}
