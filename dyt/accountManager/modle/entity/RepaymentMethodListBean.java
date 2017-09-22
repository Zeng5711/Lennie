package com.hxyd.dyt.accountManager.modle.entity;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by win7 on 2017/3/14.
 */

public class RepaymentMethodListBean {
    /**
     * repaymentMethodCode : 0
     * repaymentMethodValues : 先息后本
     * periodList : [{"periodValues":"0.5个月","periodCode":"0.5"},{"periodValues":"1个月","periodCode":"1"},{"periodValues":"2个月","periodCode":"2"},{"periodValues":"3个月","periodCode":"3"},{"periodValues":"4个月","periodCode":"4"},{"periodValues":"5个月","periodCode":"5"},{"periodValues":"6个月","periodCode":"6"}]
     */

    private String repaymentMethodCode="";
    private String repaymentMethodValues="";
    private List<PeriodListBean> periodList;

    public String getRepaymentMethodCode() {
        return repaymentMethodCode;
    }

    public void setRepaymentMethodCode(String repaymentMethodCode) {
        this.repaymentMethodCode = repaymentMethodCode;
    }

    public String getRepaymentMethodValues() {
        return repaymentMethodValues;
    }

    public void setRepaymentMethodValues(String repaymentMethodValues) {
        this.repaymentMethodValues = repaymentMethodValues;
    }

    public List<PeriodListBean> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<PeriodListBean> periodList) {
        this.periodList = periodList;
    }


}
