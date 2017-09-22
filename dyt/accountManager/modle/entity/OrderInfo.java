package com.hxyd.dyt.accountManager.modle.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by win7 on 2017/3/14.
 */

public class OrderInfo {

    private List<LoanListBean> loanList;

    private String countTotal;

    public String getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(String countTotal) {
        this.countTotal = countTotal;
    }

    public List<LoanListBean> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<LoanListBean> loanList) {
        this.loanList = loanList;
    }

}
