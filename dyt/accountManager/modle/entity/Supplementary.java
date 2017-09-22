package com.hxyd.dyt.accountManager.modle.entity;

/**
 * Created by win7 on 2017/5/19.
 */

public class Supplementary {

    private String loanInfoId ="";//贷款录入主Id
    private String loanPurpose ="";//借款用途
    private String loanMount ="";//借款金额
    private String productTypes ="";//贷款产品
    private String versions ="";//版本号
    private String repaymentMethods ="";//还款方式
    private String repaymentPeriodhods ="";//贷款周期
    private String licencePlate ="";//车牌号
    private String carFrameNo ="";//车架号
    private String produceDate ="";//出厂日期
    private String carFuel ="";//燃料
    private String carColor ="";//颜色
    private String carModel ="";
    private String carEngineNo ="";//发动机号
    private String certificateDate ="";//发证日期
    private String assess ="";//新车单价
    private String surfaceConditionDescription ="";//车辆表面情况

    public String getModel() {
        return carModel;
    }

    public void setModel(String model) {
        this.carModel = model;
    }

    public String getLoanInfoId() {
        return loanInfoId;
    }

    public void setLoanInfoId(String loanInfoId) {
        this.loanInfoId = loanInfoId;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getLoanMount() {
        return loanMount;
    }

    public void setLoanMount(String loanMount) {
        this.loanMount = loanMount;
    }

    public String getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(String productTypes) {
        this.productTypes = productTypes;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getRepaymentMethods() {
        return repaymentMethods;
    }

    public void setRepaymentMethods(String repaymentMethods) {
        this.repaymentMethods = repaymentMethods;
    }

    public String getRepaymentPeriodhods() {
        return repaymentPeriodhods;
    }

    public void setRepaymentPeriodhods(String repaymentPeriodhods) {
        this.repaymentPeriodhods = repaymentPeriodhods;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getCarFrameNo() {
        return carFrameNo;
    }

    public void setCarFrameNo(String carFrameNo) {
        this.carFrameNo = carFrameNo;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getCarFuel() {
        return carFuel;
    }

    public void setCarFuel(String carFuel) {
        this.carFuel = carFuel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarEngineNo() {
        return carEngineNo;
    }

    public void setCarEngineNo(String carEngineNo) {
        this.carEngineNo = carEngineNo;
    }

    public String getCertificateDate() {
        return certificateDate;
    }

    public void setCertificateDate(String certificateDate) {
        this.certificateDate = certificateDate;
    }

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public String getSurfaceConditionDescription() {
        return surfaceConditionDescription;
    }

    public void setSurfaceConditionDescription(String surfaceConditionDescription) {
        this.surfaceConditionDescription = surfaceConditionDescription;
    }
}
