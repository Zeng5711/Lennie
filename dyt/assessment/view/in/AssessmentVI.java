package com.hxyd.dyt.assessment.view.in;

import com.hxyd.dyt.assessment.modle.entity.CarData;

/**
 * Created by win7 on 2017/5/25.
 */

public interface AssessmentVI {

    void onPrompt(int type, String message);

    void showDialog(int type, String title, String message);

    void colseDialog();

    void onSuccess();

    void onSuccess(CarData carData);

}
