package com.hxyd.dyt.assessment.modle.entity;

import java.util.List;

/**
 * Created by win7 on 2017/5/26.
 */

public class CarData {

    /**
     * imageInfo : ["/filestorage/20170502/473/img/20170502172420292_s.jpg","/filestorage/20170502/473/img/20170502172431862_s.jpg","/filestorage/20170502/473/img/20170502172443483_s.jpg","/filestorage/20170512/473/img/20170512142110955_s.jpg","/filestorage/20170502/473/img/20170502172453132_s.jpg","/filestorage/20170502/473/img/20170502172502521_s.jpg","/filestorage/20170512/473/img/20170512131732819_s.jpg","/filestorage/20170510/473/img/20170510172412833_s.jpg","/filestorage/20170510/473/img/20170510173149405_s.jpg","/filestorage/20170502/473/img/2017050217270011_s.jpg","/filestorage/20170502/473/img/20170502172719863_s.jpg","/filestorage/20170510/473/img/20170510173953669_s.jpg","/filestorage/20170510/473/img/20170510174724925_s.jpg","/filestorage/20170508/473/img/20170508091452867_s.jpg","/filestorage/20170502/473/img/20170502180447686_s.jpg","/filestorage/20170508/473/img/20170508091509258_s.jpg","/filestorage/20170508/473/img/20170508091525782_s.jpg","/filestorage/20170508/473/img/20170508091536674_s.jpg","/filestorage/20170508/473/img/20170508091613352_s.jpg","/filestorage/20170508/473/img/20170508091628132_s.jpg"]
     * baseInfo : {"mileage":"2.00","carFuel":"燃料","loanInfoId":1115,"licencePlate":"测测00001","assess":"50.00","brand":"安凯宝斯通2010款年2.2L手动高级版HFC4GA2-1B","carColor":"白色","evaluationPrice":"8.42"}
     */

    private BaseInfoBean baseInfo;
    private List<String> imageInfo;

    public BaseInfoBean getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfoBean baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<String> getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(List<String> imageInfo) {
        this.imageInfo = imageInfo;
    }

    public static class BaseInfoBean {
        /**
         * mileage : 2.00
         * carFuel : 燃料
         * loanInfoId : 1115
         * licencePlate : 测测00001
         * assess : 50.00
         * brand : 安凯宝斯通2010款年2.2L手动高级版HFC4GA2-1B
         * carColor : 白色
         * evaluationPrice : 8.42
         */

        private String mileage;
        private String carFuel;
        private int loanInfoId;
        private String licencePlate;
        private String assess;
        private String brand;
        private String carColor;
        private String evaluationPrice;

        public String getMileage() {
            return mileage;
        }

        public void setMileage(String mileage) {
            this.mileage = mileage;
        }

        public String getCarFuel() {
            return carFuel;
        }

        public void setCarFuel(String carFuel) {
            this.carFuel = carFuel;
        }

        public int getLoanInfoId() {
            return loanInfoId;
        }

        public void setLoanInfoId(int loanInfoId) {
            this.loanInfoId = loanInfoId;
        }

        public String getLicencePlate() {
            return licencePlate;
        }

        public void setLicencePlate(String licencePlate) {
            this.licencePlate = licencePlate;
        }

        public String getAssess() {
            return assess;
        }

        public void setAssess(String assess) {
            this.assess = assess;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCarColor() {
            return carColor;
        }

        public void setCarColor(String carColor) {
            this.carColor = carColor;
        }

        public String getEvaluationPrice() {
            return evaluationPrice;
        }

        public void setEvaluationPrice(String evaluationPrice) {
            this.evaluationPrice = evaluationPrice;
        }
    }
}
