package com.hxyd.dyt.purplestar.modle.entity;

import java.util.List;

/**
 * Created by win7 on 2017/8/29.
 */

public class AlarmScreen {

    private List<ReturnListBean> returnList;

    //{"returnList":[{"crossing":2,"displacement":0,"sum":3,"outages":1}]}

    public List<ReturnListBean> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ReturnListBean> returnList) {
        this.returnList = returnList;
    }

    public static class ReturnListBean {
        /**
         * crossing : 2  越界报警数
         * displacement : 0  位移报警数
         * sum : 3  所有报警数
         * outages : 1  断电报警数
         */

        private int crossing;
        private int displacement;
        private int sum;
        private int outages;

        public int getCrossing() {
            return crossing;
        }

        public void setCrossing(int crossing) {
            this.crossing = crossing;
        }

        public int getDisplacement() {
            return displacement;
        }

        public void setDisplacement(int displacement) {
            this.displacement = displacement;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public int getOutages() {
            return outages;
        }

        public void setOutages(int outages) {
            this.outages = outages;
        }
    }
}
