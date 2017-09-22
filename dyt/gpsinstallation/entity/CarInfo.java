package com.hxyd.dyt.gpsinstallation.entity;

/**
 * Created by win7 on 2017/9/13.
 */

public class CarInfo {


    /**
     * custId :
     * sum : 0
     * carInfo : {"service_method":"上门服务","plate_no":"粤B000003",
     * "assignee":"小黑","car_brand":"奥迪","start_date":"2017-02-18"}
     */

    private String custId;
    private String sum;
    private CarInfoBean carInfo;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public CarInfoBean getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfoBean carInfo) {
        this.carInfo = carInfo;
    }

    public static class CarInfoBean {
        /**
         * service_method : 上门服务
         * plate_no : 粤B000003
         * assignee : 小黑
         * car_brand : 奥迪
         * start_date : 2017-02-18
         */

        private String service_method;
        private String plate_no;
        private String assignee;
        private String car_brand;
        private String start_date;

        public String getService_method() {
            return service_method;
        }

        public void setService_method(String service_method) {
            this.service_method = service_method;
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
