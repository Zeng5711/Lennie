package com.hxyd.dyt.vehiclemortgage.model.in;

import com.hxyd.dyt.vehiclemortgage.entity.VehicleEvaluation;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/9/14.
 */

public interface VehicleEvaluationMI {
    Observable<VehicleEvaluation> queryMortgageInfo(Map<String, Object> map);
}
