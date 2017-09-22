package com.hxyd.dyt.purplestar.modle.entity;

import java.util.List;

/**
 * Created by win7 on 2017/8/29.
 */

public class Container {


    private List<ReturnListBean> returnList;

    private long countTotal;
    private long pageSize;

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getCountTota() {
        return countTotal;
    }

    public void setCountTota(long countTota) {
        this.countTotal = countTota;
    }

    public List<ReturnListBean> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ReturnListBean> returnList) {
        this.returnList = returnList;
    }

    public static class ReturnListBean {
        /**
         * status : 设备报警
         * speed : 0
         * accState : 1  0关 1开
         * orgName : 广东
         * type : 1  设备类型 0-有线，1-无线
         * warning_name : 越界报警
         * location_time : 2017-08-27 14:17:57
         * driveStatus : 静止
         * name : 测试14530108763
         * carNumber : 车牌0876
         * imeiId : 14530108763
         * warning_type : 5
         * beOnline : 3
         * online_status : 0
         */

        private String status;
        private double speed;
        private int accState;
        private String orgName;
        private int type;
        private String warning_name;
        private String location_time;
        private String driveStatus;
        private String name;
        private String carNumber;
        private String imeiId;
        private int warning_type;
        private int beOnline;
        private int online_status;
        private String color;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getSpeed() {
            return speed;
        }

        public int getAccState() {
            return accState;
        }

        public void setAccState(int accState) {
            this.accState = accState;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getWarning_name() {
            return warning_name;
        }

        public void setWarning_name(String warning_name) {
            this.warning_name = warning_name;
        }

        public String getLocation_time() {
            return location_time;
        }

        public void setLocation_time(String location_time) {
            this.location_time = location_time;
        }

        public String getDriveStatus() {
            return driveStatus;
        }

        public void setDriveStatus(String driveStatus) {
            this.driveStatus = driveStatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        public String getImeiId() {
            return imeiId;
        }

        public void setImeiId(String imeiId) {
            this.imeiId = imeiId;
        }

        public int getWarning_type() {
            return warning_type;
        }

        public void setWarning_type(int warning_type) {
            this.warning_type = warning_type;
        }

        public int getBeOnline() {
            return beOnline;
        }

        public void setBeOnline(int beOnline) {
            this.beOnline = beOnline;
        }

        public int getOnline_status() {
            return online_status;
        }

        public void setOnline_status(int online_status) {
            this.online_status = online_status;
        }
    }
}
