package com.hxyd.dyt.accountManager.modle.entity;

import java.util.List;

/**
 * Created by win7 on 2017/9/12.
 */

public class GPSInstall {

    /**
     * countTotal : 3
     * returnList : [{"cust_name":"小白","plate_no":"粤B000000","business_id":1,"car_brand":"品牌"},{"cust_name":"小白2","plate_no":"粤B000002","business_id":2,"car_brand":"品牌2"},{"cust_name":"小白3","plate_no":"粤B000003","business_id":3,"car_brand":"品牌3"}]
     */

    private String countTotal;
    private List<ReturnListBean> returnList;

    public String getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(String countTotal) {
        this.countTotal = countTotal;
    }

    public List<ReturnListBean> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ReturnListBean> returnList) {
        this.returnList = returnList;
    }

    public static class ReturnListBean {
        /**
         * cust_name : 小白
         * plate_no : 粤B000000
         * business_id : 1
         * car_brand : 品牌
         */

        private String cust_name;
        private String plate_no;
        private String business_id;
        private String car_brand;
        private String process_instance_id;
        private String task_id;

        public String getProcess_instance_id() {
            return process_instance_id;
        }

        public void setProcess_instance_id(String process_instance_id) {
            this.process_instance_id = process_instance_id;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

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

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        public String getCar_brand() {
            return car_brand;
        }

        public void setCar_brand(String car_brand) {
            this.car_brand = car_brand;
        }
    }
}
