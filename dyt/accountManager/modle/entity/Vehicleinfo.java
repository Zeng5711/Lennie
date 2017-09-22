package com.hxyd.dyt.accountManager.modle.entity;

import io.realm.RealmObject;

/**
 * Created by win7 on 2017/3/9.
 */

public class Vehicleinfo {


    /**
     * loanInfoId  : PY-KM-DB-201509160002
     * carInfoId  : PY-KM-DB-201509160002
     * licencePlate : 粤B4586
     * brand : 宝马
     * produceDate : 2016-10-10 11:11:11
     * carFrameNo : 468486
     * carModel : X5
     * mileage : 159
     * assess : 258593.5
     */

    private String loanInfoId="";
    private String carInfoId="";
    private String licencePlate="";
    private String brand="";
    private String produceDate="";
    private String carFrameNo="";
    private String carModel="";
    private String mileage="";
    private String assess="";
    private String updatedBy="";
    private String evaluationPrice="";
    private String surfaceConditionDescription="";
    private String carEngineNo = "";
    private String carColor = "";
    private String carFuel = "";
    private String certificateDate = "";
    private String registerDate = "";

    public String getEvaluationPrice() {
        return evaluationPrice;
    }

    public void setEvaluationPrice(String evaluationPrice) {
        this.evaluationPrice = evaluationPrice;
    }

    public String getSurfaceConditionDescription() {
        return surfaceConditionDescription;
    }

    public void setSurfaceConditionDescription(String surfaceConditionDescription) {
        this.surfaceConditionDescription = surfaceConditionDescription;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getLoanInfoId() {
        return loanInfoId;
    }

    public void setLoanInfoId(String loanInfoId) {
        this.loanInfoId = loanInfoId;
    }

    public String getCarInfoId() {
        return carInfoId;
    }

    public void setCarInfoId(String carInfoId) {
        this.carInfoId = carInfoId;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getCarFrameNo() {
        return carFrameNo;
    }

    public void setCarFrameNo(String carFrameNo) {
        this.carFrameNo = carFrameNo;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public String getCarEngineNo() {
        return carEngineNo;
    }

    public void setCarEngineNo(String carEngineNo) {
        this.carEngineNo = carEngineNo;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarFuel() {
        return carFuel;
    }

    public void setCarFuel(String carFuel) {
        this.carFuel = carFuel;
    }

    public String getCertificateDate() {
        return certificateDate;
    }

    public void setCertificateDate(String certificateDate) {
        this.certificateDate = certificateDate;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}
