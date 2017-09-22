package com.hxyd.dyt.vehiclemortgage.model;


import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.vehiclemortgage.entity.VehicleEvaluation;
import com.hxyd.dyt.vehiclemortgage.model.in.VehicleEvaluationMI;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/14.
 */

public class VehicleEvaluationM implements VehicleEvaluationMI {
    @Override
    public Observable<VehicleEvaluation> queryMortgageInfo(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().queryMortgageInfo(map).map(new ResponseFunc<VehicleEvaluation>());
    }
}
