package com.hxyd.dyt.purplestar.modle.entity;

import java.util.List;

/**
 * Created by win7 on 2017/8/29.
 */

public class PurpleStar {


    private List<ReturnListBean> returnList;

    private String sum;

    private String online;

    private String  offline;

    private String invalid;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }

    public List<ReturnListBean> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ReturnListBean> returnList) {
        this.returnList = returnList;
    }

    public static class ReturnListBean {
        /**
         * orgId : 2 门店id
         * invalid : 0 当前门店下失效设备总数
         * sum : 3  当前门店下设备总数
         * orgName : 上海 门店名称
         * offline : 0  当前门店下离线设备总数
         * online : 3 当前门店下在线设备总数
         */

        private int orgId;
        private int invalid;
        private int sum;
        private String orgName;
        private int offline;
        private int online;

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public int getInvalid() {
            return invalid;
        }

        public void setInvalid(int invalid) {
            this.invalid = invalid;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public int getOffline() {
            return offline;
        }

        public void setOffline(int offline) {
            this.offline = offline;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }
    }
}
