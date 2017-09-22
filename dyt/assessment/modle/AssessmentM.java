package com.hxyd.dyt.assessment.modle;

import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.assessment.modle.entity.CarData;
import com.hxyd.dyt.assessment.modle.in.AssessmentMI;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by win7 on 2017/5/25.
 */

public class AssessmentM implements AssessmentMI{
    @Override
    public Observable<Object> saveAssessmentData(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().saveAssessmentData(map).map(new ResponseFunc<>());
    }

    @Override
    public Observable<CarData> evaluateInformationDetails(Map<String, Object> map) {
        return  GitHubAPI.craetRetrofit().evaluateInformationDetails(map).map(new ResponseFunc<CarData>());
    }

    @Override
    public Observable<CountTotal> imgUpload(String url, Map<String, Object> map, Map<String, RequestBody> params) {
        return GitHubAPI.craetRetrofit().imgUpload(url,map,params).map(new ResponseFunc<CountTotal>());
    }

    @Override
    public Observable<Object> onDeleteImage(Map<String, Object> map) {
        return  GitHubAPI.craetRetrofit().deleteFile(map).map(new ResponseFunc<>());
    }
}
