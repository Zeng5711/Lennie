package com.hxyd.dyt.appraiser.modle.entity;

/**
 * Created by win7 on 2017/9/19.
 */

public class CarInfo {


    /**
     * evalCarInfo : {"plate_no":"?E15K05","register_date":"2012-09-06","travel_distance":10}
     */

    private EvalCarInfoBean evalCarInfo;

    public EvalCarInfoBean getEvalCarInfo() {
        return evalCarInfo;
    }

    public void setEvalCarInfo(EvalCarInfoBean evalCarInfo) {
        this.evalCarInfo = evalCarInfo;
    }

    public static class EvalCarInfoBean {
        /**
         * plate_no : ?E15K05
         * register_date : 2012-09-06
         * travel_distance : 10
         */

        private String plate_no;
        private String register_date;
        private double travel_distance;
        private double c2b_prices;

        public double getC2b_prices() {
            return c2b_prices;
        }

        public void setC2b_prices(double c2b_prices) {
            this.c2b_prices = c2b_prices;
        }

        public String getPlate_no() {
            return plate_no;
        }

        public void setPlate_no(String plate_no) {
            this.plate_no = plate_no;
        }

        public String getRegister_date() {
            return register_date;
        }

        public void setRegister_date(String register_date) {
            this.register_date = register_date;
        }

        public double getTravel_distance() {
            return travel_distance;
        }

        public void setTravel_distance(double travel_distance) {
            this.travel_distance = travel_distance;
        }
    }
}
