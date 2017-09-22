package com.hxyd.dyt.accountManager.modle.entity;

import io.realm.RealmObject;

/**
 * Created by win7 on 2017/3/9.
 */

public class LoanInfo  {

    /**
     * loanInfoId   : PY-KM-DB-201509160002
     * loanPurpose : 2
     * loanMount : 529.6
     * productTypes : 1
     * repaymentMethods : 2
     * repaymentPeriodhods : 6个月
     */

    private String loanInfoId="";
    private String loanPurpose="";
    private String loanMount="";
    private String productTypes="";
    private String repaymentMethods="";
    private String repaymentPeriodhods="";
    private String updatedBy="";
    private String versions="";

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getLoanInfoId() {
        return loanInfoId;
    }

    public void setLoanInfoId(String loanInfoId) {
        this.loanInfoId = loanInfoId;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getLoanMount() {
        return loanMount;
    }

    public void setLoanMount(String loanMount) {
        this.loanMount = loanMount;
    }

    public String getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(String productTypes) {
        this.productTypes = productTypes;
    }

    public String getRepaymentMethods() {
        return repaymentMethods;
    }

    public void setRepaymentMethods(String repaymentMethods) {
        this.repaymentMethods = repaymentMethods;
    }

    public String getRepaymentPeriodhods() {
        return repaymentPeriodhods;
    }

    public void setRepaymentPeriodhods(String repaymentPeriodhods) {
        this.repaymentPeriodhods = repaymentPeriodhods;
    }
}
