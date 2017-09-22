package com.hxyd.dyt.purplestar.modle.entity;

import java.util.List;

/**
 * Created by win7 on 2017/8/31.
 */

public class RecorShow {


    private List<ReturnListBean> returnList;

    public List<ReturnListBean> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ReturnListBean> returnList) {
        this.returnList = returnList;
    }

    public static class ReturnListBean {
        /**
         * receiveDate : 1491543450000 定位时间
         * speed : 37 速度
         * altitude : 0
         * direction : 81 方向
         * imei : 14530098897
         * state : 未定位  (有线为：定位，未定位  ；无线为 gps定位，基站定位)
         * accState : ACC状态:关 仅有线设备有acc状态
         * longitude : 114.1314468384
         * latitude : 22.5487003326
         * createDate : 1491543463000 接收时间
         */

        private String receiveDate;
        private double speed;
        private int altitude;
        private int direction;
        private String imei;
        private String state;
        private String accState;
        private String longitude;
        private String latitude;
        private String createDate;

        public String getReceiveDate() {
            return receiveDate;
        }

        public void setReceiveDate(String receiveDate) {
            this.receiveDate = receiveDate;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public int getAltitude() {
            return altitude;
        }

        public void setAltitude(int altitude) {
            this.altitude = altitude;
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAccState() {
            return accState;
        }

        public void setAccState(String accState) {
            this.accState = accState;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
