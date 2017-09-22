package com.hxyd.dyt.accountManager.presenter;

import android.text.TextUtils;

import com.hxyd.dyt.accountManager.modle.SingleM;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.in.SingleMI;
import com.hxyd.dyt.accountManager.presenter.in.AssessmentResultPI;
import com.hxyd.dyt.accountManager.view.in.AssessmentResultVI;

/**
 * Created by win7 on 2017/5/22.
 */

public class AssessmentResultP implements AssessmentResultPI {

    private SingleMI M;
    private AssessmentResultVI V;

    public AssessmentResultP(AssessmentResultVI V) {
        this.V = V;
        M = new SingleM();
    }

    @Override
    public void getOrderDefault(String orderNo) {
        if (M != null && V != null) {
            OrderDefultInfo orderDefultInfo = ADDataManager.getInstance().getDefultInfo();
            if (orderDefultInfo != null && orderDefultInfo.getBaseInfo() != null) {
//                String monye = orderDefultInfo.getBaseInfo().getEvaluationPrice();
                String name = orderDefultInfo.getBaseInfo().getName();
                String phone = orderDefultInfo.getBaseInfo().getPhone();
                String valuationPrice = orderDefultInfo.getBaseInfo().getValuationPrice();
                String audiValuationPrice = orderDefultInfo.getBaseInfo().getAudiValuationPrice();

                String n = ADDataManager.getInstance().getName();
                String p = ADDataManager.getInstance().getPhone();


                if (!TextUtils.isEmpty(n)) {
                    name = n;
                }

                if (!TextUtils.isEmpty(p)) {
                    phone = p;
                }

                V.setMoneyText(name, phone, valuationPrice, audiValuationPrice);
            }
        }
    }

    @Override
    public void onDestroy() {
        M = null;
        V = null;
    }

}
