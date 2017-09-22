package com.hxyd.dyt.accountManager.modle.entity;

import io.realm.RealmObject;

/**
 * Created by win7 on 2017/3/16.
 */

public class BaseInfoBean  {

    /**
     * companyPlaceCityCode : 1
     * companyPlaceDetail : 积极
     * carFrameNo : 观后感
     * phone : 555
     * isLockEd : Y
     * companyPhone : 55555
     * loanPurposeValue : 资金周转
     * relationPerson : 呼呼
     * companyPlaceCityValue : 北京市
     * repaymentMethodsValue : 先息后本
     * customerBaseInfoId : 295
     * loanPurposeCode : 1
     * housingTypeCode : 1
     * housingTypeValue : 商业按揭房
     * companyPlaceDistrictCode : 2
     * mileage : 5555.00
     * livingPlaceProvinceCode : 1
     * repaymentMethods : 0
     * educationValue : 硕士或以上
     * livingPlaceDistrictValue : 东城区
     * companyName : 很急
     * relationPhone : 666555
     * livingPlaceDetail : 舅舅家
     * livingPlaceProvinceValue : 北京市
     * carInfoId : 81
     * loanStatus : 2
     * loanInfoId : 251
     * sexValue : 男
     * companyPlaceDistrictValue : 东城区
     * productTypesCode : 0
     * sex : 1
     * produceDate : 2017-03-16
     * licencePlate : 共和花园
     * companyPlaceProvinceCode : 1
     * customerRelationCode : 1
     * customerRelationValue : 父母
     * education : 1
     * repaymentPeriodhods : 1
     * name : 嘎嘎嘎嘎
     * marriageValue : 已婚
     * assess : 5555.00
     * loanMount : 855.00
     * idCard : 55555
     * livingPlaceDistrictCode : 2
     * livingPlaceCityCode : 1
     * marriage : 1
     * productTypesValue : 全款不押车
     * versions : 1
     * carModel : 黄河鬼棺
     * idCardValidityDates : 2017-03-16
     * livingPlaceCityValue : 北京市
     * brand : 嘎嘎嘎嘎
     * companyPlaceProvinceValue : 北京市
     * "homePlaceProvinceCode":"户籍地址省份code",
     * "homePlaceProvinceValue"："户籍地址省份value",
     * "homePlaceCityCode":"户籍地址市code",
     * "homePlaceCityValue":"户籍地址市value",
     * "homePlaceDistrictCode":"户籍地址区code",
     * "homePlaceDistrictValue":"户籍地址value",
     * "homePlaceDetail":"户籍详细地址",
     * "rename_01":"客户联系人1姓名",
     * "rephone_01":"客户联系人1电话",
     * "relationship_01":"客户联系人1关系",
     * "rename_02":"客户联系人2姓名",
     * "rephone_02":"客户联系人2电话",
     * "relationship_02":"客户联系人2关系",
     * "rename_03":"客户联系人3姓名",
     * "rephone_03":"客户联系人3电话",
     * "relationship_03":"客户联系人3关系",
     * "carEngineNo":"发动机号",
     * "carColor":"车身颜色",
     * "carFuel":"燃料",
     * "certificateDate":"发证日期",
     * "registerDate":"注册日期"
     */

    private String companyPlaceCityCode = "";
    private String companyPlaceDetail = "";
    private String carFrameNo = "";
    private String phone = "";
    private String isLockEd = "";
    private String companyPhone = "";
    private String loanPurposeValue = "";
    private String companyPlaceCityValue = "";
    private String repaymentMethodsValue = "";
    private String customerBaseInfoId = "";
    private String loanPurposeCode = "";
    private String housingTypeCode = "";
    private String housingTypeValue = "";
    private String companyPlaceDistrictCode = "";
    private String mileage = "";
    private String livingPlaceProvinceCode = "";
    private String repaymentMethods = "";
    private String educationValue = "";
    private String livingPlaceDistrictValue = "";
    private String companyName = "";
    private String livingPlaceDetail = "";
    private String livingPlaceProvinceValue = "";
    private String carInfoId = "";
    private String loanStatus = "";
    private String loanInfoId = "";
    private String sexValue = "";
    private String companyPlaceDistrictValue = "";
    private String productTypesCode = "";
    private String sex = "";
    private String produceDate = "";
    private String licencePlate = "";
    private String companyPlaceProvinceCode = "";
    private String customerRelationCode = "";
    private String education = "";
    private String repaymentPeriodhods = "";
    private String name = "";
    private String marriageValue = "";
    private String assess = "";
    private String loanMount = "";
    private String idCard = "";
    private String livingPlaceDistrictCode = "";
    private String livingPlaceCityCode = "";
    private String marriage = "";
    private String productTypesValue = "";
    private String versions = "";
    private String carModel = "";
    private String idCardValidityDates = "";
    private String livingPlaceCityValue = "";
    private String brand = "";
    private String companyPlaceProvinceValue = "";
    private String evaluationPrice = "";
    private String surfaceConditionDescription = "";

    private String homePlaceProvinceCode = "";
    private String homePlaceProvinceValue = "";//："户籍地址省份value",
    private String homePlaceCityCode = "";//":"户籍地址市code",
    private String homePlaceCityValue = "";//":"户籍地址市value",
    private String homePlaceDistrictCode = "";//":"户籍地址区code",
    private String homePlaceDistrictValue = "";//":"户籍地址value",
    private String homePlaceDetail = "";//":"户籍详细地址",
    private String rename_01 = "";//":"客户联系人1姓名",
    private String rephone_01 = "";//":"客户联系人1电话",
    private String relationship_01 = "";//":"客户联系人1关系",
    private String relationship_01Value = "";
    private String rename_02 = "";//":"客户联系人2姓名",
    private String rephone_02 = "";//":"客户联系人2电话",
    private String relationship_02 = "";//":"客户联系人2关系",
    private String relationship_02Value = "";
    private String rename_03 = "";//":"客户联系人3姓名",
    private String rephone_03 = "";//":"客户联系人3电话",
    private String relationship_03 = "";//":"客户联系人3关系",
    private String relationship_03Value = "";
    private String carEngineNo = "";//":"发动机号",
    private String carColor = "";//":"车身颜色",
    private String carFuel = "";//":"燃料",
    private String certificateDate = "";//":"发证日期",
    private String registerDate = "";//":"注册日期"
    private String backReason = "";
    private String spouseName = "";
    private String spouseIdcard = "";
    private String spousePhone = "";
    private String audiAmount = "";
    private String audiTerm = "";
    private String audiProduct = "";
    private String cityId = "";
    private String trimId = "";
    private String cityName = "";
    private String valuationPriceReall = "";
    private String valuationPrice= "";
    private String audiValuationPrice = "";

    public String getValuationPrice() {
        return valuationPrice;
    }

    public void setValuationPrice(String valuationPrice) {
        this.valuationPrice = valuationPrice;
    }

    public String getAudiValuationPrice() {
        return audiValuationPrice;
    }

    public void setAudiValuationPrice(String audiValuationPrice) {
        this.audiValuationPrice = audiValuationPrice;
    }

    public String getValuationPriceReall() {
        return valuationPriceReall;
    }

    public void setValuationPriceReall(String valuationPriceReall) {
        this.valuationPriceReall = valuationPriceReall;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTrimId() {
        return trimId;
    }

    public void setTrimId(String trimId) {
        this.trimId = trimId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAudiAmount() {
        return audiAmount;
    }

    public void setAudiAmount(String audiAmount) {
        this.audiAmount = audiAmount;
    }

    public String getAudiTerm() {
        return audiTerm;
    }

    public void setAudiTerm(String audiTerm) {
        this.audiTerm = audiTerm;
    }

    public String getAudiProduct() {
        return audiProduct;
    }

    public void setAudiProduct(String audiProduct) {
        this.audiProduct = audiProduct;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getSpouseIdcard() {
        return spouseIdcard;
    }

    public void setSpouseIdcard(String spouseIdcard) {
        this.spouseIdcard = spouseIdcard;
    }

    public String getSpousePhone() {
        return spousePhone;
    }

    public void setSpousePhone(String spousePhone) {
        this.spousePhone = spousePhone;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

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

    public String getCompanyPlaceCityCode() {
        return companyPlaceCityCode;
    }

    public void setCompanyPlaceCityCode(String companyPlaceCityCode) {
        this.companyPlaceCityCode = companyPlaceCityCode;
    }

    public String getCompanyPlaceDetail() {
        return companyPlaceDetail;
    }

    public void setCompanyPlaceDetail(String companyPlaceDetail) {
        this.companyPlaceDetail = companyPlaceDetail;
    }

    public String getCarFrameNo() {
        return carFrameNo;
    }

    public void setCarFrameNo(String carFrameNo) {
        this.carFrameNo = carFrameNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsLockEd() {
        return isLockEd;
    }

    public void setIsLockEd(String isLockEd) {
        this.isLockEd = isLockEd;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getLoanPurposeValue() {
        return loanPurposeValue;
    }

    public void setLoanPurposeValue(String loanPurposeValue) {
        this.loanPurposeValue = loanPurposeValue;
    }

    public String getCompanyPlaceCityValue() {
        return companyPlaceCityValue;
    }

    public void setCompanyPlaceCityValue(String companyPlaceCityValue) {
        this.companyPlaceCityValue = companyPlaceCityValue;
    }

    public String getRepaymentMethodsValue() {
        return repaymentMethodsValue;
    }

    public void setRepaymentMethodsValue(String repaymentMethodsValue) {
        this.repaymentMethodsValue = repaymentMethodsValue;
    }

    public String getCustomerBaseInfoId() {
        return customerBaseInfoId;
    }

    public void setCustomerBaseInfoId(String customerBaseInfoId) {
        this.customerBaseInfoId = customerBaseInfoId;
    }

    public String getLoanPurposeCode() {
        return loanPurposeCode;
    }

    public void setLoanPurposeCode(String loanPurposeCode) {
        this.loanPurposeCode = loanPurposeCode;
    }

    public String getHousingTypeCode() {
        return housingTypeCode;
    }

    public void setHousingTypeCode(String housingTypeCode) {
        this.housingTypeCode = housingTypeCode;
    }

    public String getHousingTypeValue() {
        return housingTypeValue;
    }

    public void setHousingTypeValue(String housingTypeValue) {
        this.housingTypeValue = housingTypeValue;
    }

    public String getCompanyPlaceDistrictCode() {
        return companyPlaceDistrictCode;
    }

    public void setCompanyPlaceDistrictCode(String companyPlaceDistrictCode) {
        this.companyPlaceDistrictCode = companyPlaceDistrictCode;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getLivingPlaceProvinceCode() {
        return livingPlaceProvinceCode;
    }

    public void setLivingPlaceProvinceCode(String livingPlaceProvinceCode) {
        this.livingPlaceProvinceCode = livingPlaceProvinceCode;
    }

    public String getRepaymentMethods() {
        return repaymentMethods;
    }

    public void setRepaymentMethods(String repaymentMethods) {
        this.repaymentMethods = repaymentMethods;
    }

    public String getEducationValue() {
        return educationValue;
    }

    public void setEducationValue(String educationValue) {
        this.educationValue = educationValue;
    }

    public String getLivingPlaceDistrictValue() {
        return livingPlaceDistrictValue;
    }

    public void setLivingPlaceDistrictValue(String livingPlaceDistrictValue) {
        this.livingPlaceDistrictValue = livingPlaceDistrictValue;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLivingPlaceDetail() {
        return livingPlaceDetail;
    }

    public void setLivingPlaceDetail(String livingPlaceDetail) {
        this.livingPlaceDetail = livingPlaceDetail;
    }

    public String getLivingPlaceProvinceValue() {
        return livingPlaceProvinceValue;
    }

    public void setLivingPlaceProvinceValue(String livingPlaceProvinceValue) {
        this.livingPlaceProvinceValue = livingPlaceProvinceValue;
    }

    public String getCarInfoId() {
        return carInfoId;
    }

    public void setCarInfoId(String carInfoId) {
        this.carInfoId = carInfoId;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getLoanInfoId() {
        return loanInfoId;
    }

    public void setLoanInfoId(String loanInfoId) {
        this.loanInfoId = loanInfoId;
    }

    public String getSexValue() {
        return sexValue;
    }

    public void setSexValue(String sexValue) {
        this.sexValue = sexValue;
    }

    public String getCompanyPlaceDistrictValue() {
        return companyPlaceDistrictValue;
    }

    public void setCompanyPlaceDistrictValue(String companyPlaceDistrictValue) {
        this.companyPlaceDistrictValue = companyPlaceDistrictValue;
    }

    public String getProductTypesCode() {
        return productTypesCode;
    }

    public void setProductTypesCode(String productTypesCode) {
        this.productTypesCode = productTypesCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getCompanyPlaceProvinceCode() {
        return companyPlaceProvinceCode;
    }

    public void setCompanyPlaceProvinceCode(String companyPlaceProvinceCode) {
        this.companyPlaceProvinceCode = companyPlaceProvinceCode;
    }

    public String getCustomerRelationCode() {
        return customerRelationCode;
    }

    public void setCustomerRelationCode(String customerRelationCode) {
        this.customerRelationCode = customerRelationCode;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getRepaymentPeriodhods() {
        return repaymentPeriodhods;
    }

    public void setRepaymentPeriodhods(String repaymentPeriodhods) {
        this.repaymentPeriodhods = repaymentPeriodhods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarriageValue() {
        return marriageValue;
    }

    public void setMarriageValue(String marriageValue) {
        this.marriageValue = marriageValue;
    }

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public String getLoanMount() {
        return loanMount;
    }

    public void setLoanMount(String loanMount) {
        this.loanMount = loanMount;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getLivingPlaceDistrictCode() {
        return livingPlaceDistrictCode;
    }

    public void setLivingPlaceDistrictCode(String livingPlaceDistrictCode) {
        this.livingPlaceDistrictCode = livingPlaceDistrictCode;
    }

    public String getLivingPlaceCityCode() {
        return livingPlaceCityCode;
    }

    public void setLivingPlaceCityCode(String livingPlaceCityCode) {
        this.livingPlaceCityCode = livingPlaceCityCode;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getProductTypesValue() {
        return productTypesValue;
    }

    public void setProductTypesValue(String productTypesValue) {
        this.productTypesValue = productTypesValue;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getIdCardValidityDates() {
        return idCardValidityDates;
    }

    public void setIdCardValidityDates(String idCardValidityDates) {
        this.idCardValidityDates = idCardValidityDates;
    }

    public String getLivingPlaceCityValue() {
        return livingPlaceCityValue;
    }

    public void setLivingPlaceCityValue(String livingPlaceCityValue) {
        this.livingPlaceCityValue = livingPlaceCityValue;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCompanyPlaceProvinceValue() {
        return companyPlaceProvinceValue;
    }

    public void setCompanyPlaceProvinceValue(String companyPlaceProvinceValue) {
        this.companyPlaceProvinceValue = companyPlaceProvinceValue;
    }

    public String getHomePlaceProvinceCode() {
        return homePlaceProvinceCode;
    }

    public void setHomePlaceProvinceCode(String homePlaceProvinceCode) {
        this.homePlaceProvinceCode = homePlaceProvinceCode;
    }

    public String getHomePlaceProvinceValue() {
        return homePlaceProvinceValue;
    }

    public void setHomePlaceProvinceValue(String homePlaceProvinceValue) {
        this.homePlaceProvinceValue = homePlaceProvinceValue;
    }

    public String getHomePlaceCityCode() {
        return homePlaceCityCode;
    }

    public void setHomePlaceCityCode(String homePlaceCityCode) {
        this.homePlaceCityCode = homePlaceCityCode;
    }

    public String getHomePlaceCityValue() {
        return homePlaceCityValue;
    }

    public void setHomePlaceCityValue(String homePlaceCityValue) {
        this.homePlaceCityValue = homePlaceCityValue;
    }

    public String getHomePlaceDistrictCode() {
        return homePlaceDistrictCode;
    }

    public void setHomePlaceDistrictCode(String homePlaceDistrictCode) {
        this.homePlaceDistrictCode = homePlaceDistrictCode;
    }

    public String getHomePlaceDistrictValue() {
        return homePlaceDistrictValue;
    }

    public void setHomePlaceDistrictValue(String homePlaceDistrictValue) {
        this.homePlaceDistrictValue = homePlaceDistrictValue;
    }

    public String getHomePlaceDetail() {
        return homePlaceDetail;
    }

    public void setHomePlaceDetail(String homePlaceDetail) {
        this.homePlaceDetail = homePlaceDetail;
    }

    public String getRename_01() {
        return rename_01;
    }

    public void setRename_01(String rename_01) {
        this.rename_01 = rename_01;
    }

    public String getRephone_01() {
        return rephone_01;
    }

    public void setRephone_01(String rephone_01) {
        this.rephone_01 = rephone_01;
    }

    public String getRelationship_01() {
        return relationship_01;
    }

    public void setRelationship_01(String relationship_01) {
        this.relationship_01 = relationship_01;
    }

    public String getRename_02() {
        return rename_02;
    }

    public void setRename_02(String rename_02) {
        this.rename_02 = rename_02;
    }

    public String getRephone_02() {
        return rephone_02;
    }

    public void setRephone_02(String rephone_02) {
        this.rephone_02 = rephone_02;
    }

    public String getRelationship_02() {
        return relationship_02;
    }

    public void setRelationship_02(String relationship_02) {
        this.relationship_02 = relationship_02;
    }

    public String getRename_03() {
        return rename_03;
    }

    public void setRename_03(String rename_03) {
        this.rename_03 = rename_03;
    }

    public String getRephone_03() {
        return rephone_03;
    }

    public void setRephone_03(String rephone_03) {
        this.rephone_03 = rephone_03;
    }

    public String getRelationship_03() {
        return relationship_03;
    }

    public void setRelationship_03(String relationship_03) {
        this.relationship_03 = relationship_03;
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

    public String getRelationship_01Value() {
        return relationship_01Value;
    }

    public void setRelationship_01Value(String relationship_01Value) {
        this.relationship_01Value = relationship_01Value;
    }

    public String getRelationship_02Value() {
        return relationship_02Value;
    }

    public void setRelationship_02Value(String relationship_02Value) {
        this.relationship_02Value = relationship_02Value;
    }

    public String getRelationship_03Value() {
        return relationship_03Value;
    }

    public void setRelationship_03Value(String relationship_03Value) {
        this.relationship_03Value = relationship_03Value;
    }
}
