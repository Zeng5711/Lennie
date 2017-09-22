package com.hxyd.dyt.gpsinstallation.entity;

import java.util.List;

/**
 * Created by win7 on 2017/9/18.
 */

public class ImeiInfo {


    private List<ReturnListBean> returnList;

    public List<ReturnListBean> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ReturnListBean> returnList) {
        this.returnList = returnList;
    }

    public static class ReturnListBean {
        /**
         * receiveDate :
         * serviceDate :
         * accState : 0
         * type : 0
         * onlineStatus : 0
         * phoneNum : 13828137250
         * installDate :
         * name : 林丹号
         * longitude : 113.30095746313394
         * batteryCapacity : 0
         * gpsSignalIntensity : 0
         * gpsOrGms : 0
         * status : 停止
         * speed : 0
         * simId : 201708300002
         * orgName : 深圳
         * gsmSignalIntensity : 0
         * color : 4
         * carNumber : 粤E15K05
         * locationDate : 2017-09-07 11:33:23
         * imeiId : 201709060006
         * stateLength : 0秒
         * latitude : 23.218473055022677
         * locationState : 0
         * versionType : XT800
         */

        private String receiveDate;
        private String serviceDate;
        private String accState;
        private int type;
        private int onlineStatus;
        private String phoneNum;
        private String installDate;
        private String name;
        private String longitude;
        private int batteryCapacity;
        private int gpsSignalIntensity;
        private String gpsOrGms;
        private String status;
        private int speed;
        private String simId;
        private String orgName;
        private int gsmSignalIntensity;
        private String color;
        private String carNumber;
        private String locationDate;
        private String imeiId;
        private String stateLength;
        private String latitude;
        private int locationState;
        private String versionType;

        public String getReceiveDate() {
            return receiveDate;
        }

        public void setReceiveDate(String receiveDate) {
            this.receiveDate = receiveDate;
        }

        public String getServiceDate() {
            return serviceDate;
        }

        public void setServiceDate(String serviceDate) {
            this.serviceDate = serviceDate;
        }

        public String getAccState() {
            return accState;
        }

        public void setAccState(String accState) {
            this.accState = accState;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(int onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getInstallDate() {
            return installDate;
        }

        public void setInstallDate(String installDate) {
            this.installDate = installDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public int getBatteryCapacity() {
            return batteryCapacity;
        }

        public void setBatteryCapacity(int batteryCapacity) {
            this.batteryCapacity = batteryCapacity;
        }

        public int getGpsSignalIntensity() {
            return gpsSignalIntensity;
        }

        public void setGpsSignalIntensity(int gpsSignalIntensity) {
            this.gpsSignalIntensity = gpsSignalIntensity;
        }

        public String getGpsOrGms() {
            return gpsOrGms;
        }

        public void setGpsOrGms(String gpsOrGms) {
            this.gpsOrGms = gpsOrGms;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public String getSimId() {
            return simId;
        }

        public void setSimId(String simId) {
            this.simId = simId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public int getGsmSignalIntensity() {
            return gsmSignalIntensity;
        }

        public void setGsmSignalIntensity(int gsmSignalIntensity) {
            this.gsmSignalIntensity = gsmSignalIntensity;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        public String getLocationDate() {
            return locationDate;
        }

        public void setLocationDate(String locationDate) {
            this.locationDate = locationDate;
        }

        public String getImeiId() {
            return imeiId;
        }

        public void setImeiId(String imeiId) {
            this.imeiId = imeiId;
        }

        public String getStateLength() {
            return stateLength;
        }

        public void setStateLength(String stateLength) {
            this.stateLength = stateLength;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public int getLocationState() {
            return locationState;
        }

        public void setLocationState(int locationState) {
            this.locationState = locationState;
        }

        public String getVersionType() {
            return versionType;
        }

        public void setVersionType(String versionType) {
            this.versionType = versionType;
        }
    }
}
