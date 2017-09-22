package com.hxyd.dyt.accountManager.modle.entity;

import java.util.List;

/**
 * Created by win7 on 2017/5/24.
 */

public class Task {


    /**
     * countTotal : 2
     * loanList :
     * [
     * {"customerName":"林更好","isComplete":"0","orderNo":216,"systemType":"dyt","licencePlate":"4346978704","carmodel":"赛拉图欧风","entryTime":"2017-05-24 9:57:27"},
     * {"customerName":"刘德华","isComplete":"0","orderNo":212,"systemType":"dyt","licencePlate":"5655565555","carmodel":"赛拉图欧风","entryTime":"2017-05-24 9:57:27"}]
     */

    private int countTotal = 0;
    private List<LoanListBean> loanList;

    public int getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
    }

    public List<LoanListBean> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<LoanListBean> loanList) {
        this.loanList = loanList;
    }

    public static class LoanListBean {
        /**
         * customerName : 林更好
         * isComplete : 0
         * orderNo : 216
         * systemType : dyt
         * licencePlate : 4346978704
         * carmodel : 赛拉图欧风
         * entryTime : 2017-05-24 9:57:27
         */

        private String customerName;
        private String isComplete;
        private int orderNo;
        private String systemType;
        private String licencePlate;
        private String carmodel;
        private String entryTime;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getIsComplete() {
            return isComplete;
        }

        public void setIsComplete(String isComplete) {
            this.isComplete = isComplete;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }

        public String getSystemType() {
            return systemType;
        }

        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }

        public String getLicencePlate() {
            return licencePlate;
        }

        public void setLicencePlate(String licencePlate) {
            this.licencePlate = licencePlate;
        }

        public String getCarmodel() {
            return carmodel;
        }

        public void setCarmodel(String carmodel) {
            this.carmodel = carmodel;
        }

        public String getEntryTime() {
            return entryTime;
        }

        public void setEntryTime(String entryTime) {
            this.entryTime = entryTime;
        }
    }
}
