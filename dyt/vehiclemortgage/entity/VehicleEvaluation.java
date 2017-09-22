package com.hxyd.dyt.vehiclemortgage.entity;

/**
 * Created by win7 on 2017/9/14.
 */

public class VehicleEvaluation {


    /**
     * mortgageInfo : {"cust_name":"粤B000003","plate_no":"小白","assignee":"李白","car_brand":"奥迪","start_date":"2017-02-18"}
     */

    private MortgageInfoBean mortgageInfo;

    public MortgageInfoBean getMortgageInfo() {
        return mortgageInfo;
    }

    public void setMortgageInfo(MortgageInfoBean mortgageInfo) {
        this.mortgageInfo = mortgageInfo;
    }

    public static class MortgageInfoBean {
        /**
         * cust_name : 粤B000003
         * plate_no : 小白
         * assignee : 李白
         * car_brand : 奥迪
         * start_date : 2017-02-18
         */

        private String cust_name;
        private String plate_no;
        private String assignee;
        private String car_brand;
        private String start_date;

        public String getCust_name() {
            return cust_name;
        }

        public void setCust_name(String cust_name) {
            this.cust_name = cust_name;
        }

        public String getPlate_no() {
            return plate_no;
        }

        public void setPlate_no(String plate_no) {
            this.plate_no = plate_no;
        }

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }

        public String getCar_brand() {
            return car_brand;
        }

        public void setCar_brand(String car_brand) {
            this.car_brand = car_brand;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }
    }
}
