package com.hxyd.dyt.appraiser.presenter.in;

/**
 * Created by win7 on 2017/9/19.
 */

public interface CarInfoPI {

    void queryEvalCarInfo(String businessId);

    void updateEvalCarInfo(String businessId,String plateNo,String registerDate,String travelDistance,String carRecognizationNo,String engineNo);
}
