package com.hxyd.dyt.accountManager.modle.entity;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by win7 on 2017/3/14.
 */

public class VersionListBean {
    /**
     * repaymentMethodList : [{"repaymentMethodCode":"0","repaymentMethodValues":"先息后本 ","periodList":[{"periodValues":"0.5个月","periodCode":"0.5"},{"periodValues":"1个月","periodCode":"1"},{"periodValues":"2个月","periodCode":"2"},{"periodValues":"3个月","periodCode":"3"},{"periodValues":"4个月","periodCode":"4"},{"periodValues":"5个月","periodCode":"5"},{"periodValues":"6个月","periodCode":"6"}]},{"repaymentMethodCode":"1","repaymentMethodValues":"等本等息","periodList":[{"periodValues":"0.5个月","periodCode":"0.5"},{"periodValues":"1个月","periodCode":"1"},{"periodValues":"2个月","periodCode":"2"},{"periodValues":"3个月","periodCode":"3"},{"periodValues":"4个月","periodCode":"4"},{"periodValues":"5个月","periodCode":"5"},{"periodValues":"6个月","periodCode":"6"},{"periodValues":"7个月","periodCode":"7"},{"periodValues":"8个月","periodCode":"8"},{"periodValues":"9个月","periodCode":"9"},{"periodValues":"10个月","periodCode":"10"},{"periodValues":"11个月","periodCode":"11"},{"periodValues":"12个月","periodCode":"12"},{"periodValues":"13个月","periodCode":"13"},{"periodValues":"14个月","periodCode":"14"},{"periodValues":"15个月","periodCode":"15"},{"periodValues":"16个月","periodCode":"16"},{"periodValues":"17个月","periodCode":"17"},{"periodValues":"18个月","periodCode":"18"},{"periodValues":"19个月","periodCode":"19"},{"periodValues":"20个月","periodCode":"20"},{"periodValues":"21个月","periodCode":"21"},{"periodValues":"22个月","periodCode":"22"},{"periodValues":"23个月","periodCode":"23"},{"periodValues":"24个月","periodCode":"24"}]}]
     * versionValues : 1
     * versionCode : 1
     */

    private String versionValues="";
    private String versionCode="";
    private List<RepaymentMethodListBean> repaymentMethodList;

    public String getVersionValues() {
        return versionValues;
    }

    public void setVersionValues(String versionValues) {
        this.versionValues = versionValues;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public List<RepaymentMethodListBean> getRepaymentMethodList() {
        return repaymentMethodList;
    }

    public void setRepaymentMethodList(List<RepaymentMethodListBean> repaymentMethodList) {
        this.repaymentMethodList = repaymentMethodList;
    }

}
