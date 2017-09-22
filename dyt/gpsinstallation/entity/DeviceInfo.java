package com.hxyd.dyt.gpsinstallation.entity;

import java.util.List;

/**
 * Created by win7 on 2017/9/13.
 */

public class DeviceInfo {


    /**
     * custId : 551126
     * returnList : [{"versionType":"HX-110","type":1,"belongsId":"16"}]
     */

    private String custId;
    private List<ReturnListBean> returnList;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public List<ReturnListBean> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ReturnListBean> returnList) {
        this.returnList = returnList;
    }

    public static class ReturnListBean {
        /**
         * versionType : HX-110
         * type : 1
         * belongsId : 16
         */

        private String versionType;
        private int type;
        private String belongsId;

        public String getVersionType() {
            return versionType;
        }

        public void setVersionType(String versionType) {
            this.versionType = versionType;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getBelongsId() {
            return belongsId;
        }

        public void setBelongsId(String belongsId) {
            this.belongsId = belongsId;
        }
    }
}
