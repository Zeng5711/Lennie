package com.hxyd.dyt.accountManager.view.in;

import com.hxyd.dyt.accountManager.modle.entity.LoanInfoID;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.common.entity.AreaList;

import java.util.List;

/**
 * Created by win7 on 2017/5/16.
 */

public interface CustomerInfoVI {
    void showDial(String meaage);
    void onSetArealist(List<AreaList> list);
    void onErr(int type,String err);
    void onDismiss();
    void onLoadProvince();
    void onShowAcAssessmentResult();
    void setOrderDefault(OrderDefultInfo defultInfo);
}
