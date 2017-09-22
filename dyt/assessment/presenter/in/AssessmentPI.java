package com.hxyd.dyt.assessment.presenter.in;

import com.hxyd.dyt.accountManager.presenter.in.ImageItemDeleteCall;
import com.hxyd.dyt.common.entity.ImageItem;

import java.util.Map;

/**
 * Created by win7 on 2017/5/25.
 */

public interface AssessmentPI {

    void saveAssessment(Map<String, Object> map);

    void evaluateInformationDetails(Map<String, Object> map);

    void onImgUploadNew(final  String orderNo ,final String url, final String Code, final String name, int imageItem);

    void onDeleteImage(String orderNo ,String systemType,String name,ImageItemDeleteCall call);

    void onDestroy();
}
