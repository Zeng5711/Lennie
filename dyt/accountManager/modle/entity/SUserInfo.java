package com.hxyd.dyt.accountManager.modle.entity;

import io.realm.RealmObject;

/**
 * Created by win7 on 2017/3/9.
 */

public class SUserInfo  {

    /**
     * loanInfoId   : PY-KM-DB-201509160002
     * customerBaseInfoId   : PY-KM-DB-201509160002
     * name : 张三
     * sex : 1
     * idCard : 420821198056895689
     * marriage : 1
     * education : 2
     * phone : 13510020656
     * housingType : 1
     * livingPlaceDistrict : 928
     * livingPlaceDetail : 星海大厦
     * companyName : 恒信易贷
     * companyPlaceDistrict : 928
     * companyPlaceDetail : 罗湖国贸
     * companyPhone : 555656
     *
     *
     livingPlaceCity = 1,//市
     livingPlaceProvince = 1,//省
     companyPlaceProvince = 1//省
     companyPlaceCity = 1,//市
     */

    private String livingPlaceCity="";
    private String livingPlaceProvince="";
    private String companyPlaceProvince="";
    private String companyPlaceCity="";
    private String loanInfoId=""; //贷款录入主Id
    private String customerBaseInfoId=""; //客户信息Id
    private String name=""; //姓名
    private String sex=""; //性别
    private String idCard=""; //身份证
    private String marriage=""; //婚姻状况
    private String education="";//教育程度
    private String phone="";//联系电话
    private String housingType="";//房产类型
    private String livingPlaceDistrict="";//现住址地区Id
    private String livingPlaceDetail="";//现住址详细地址
    private String companyName="";//工作单位
    private String companyPlaceDistrict="";//工作地区Id
    private String companyPlaceDetail="";//工作地址
    private String companyPhone="";//工作电话
    private String idCardValidityDates="";//身份证有效期限止
    private String updatedBy="";//创建/修改人

    private String rename_01 = "";//客户联系人
    private String rephone_01 = "";//联系人电话
    private String relationship_01 = "";//客户关系

    private String rename_02 = "";
    private String rephone_02 = "";
    private String relationship_02 = "";

    private String rename_03 = "";
    private String rephone_03 = "";
    private String relationship_03 = "";

    private String homePlaceProvince = ""; //户籍地址省份
    private String homePlaceCity = "";   //户籍地址市
    private String homePlaceDistrict = "";//户籍地址区
    private String homePlaceDetail = ""; //户籍详细地址

    private String spouseName="";
    private String spouseIdcard="";
    private String spousePhone="";

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

    public String getLivingPlaceCity() {
        return livingPlaceCity;
    }

    public void setLivingPlaceCity(String livingPlaceCity) {
        this.livingPlaceCity = livingPlaceCity;
    }

    public String getLivingPlaceProvince() {
        return livingPlaceProvince;
    }

    public void setLivingPlaceProvince(String livingPlaceProvince) {
        this.livingPlaceProvince = livingPlaceProvince;
    }

    public String getCompanyPlaceProvince() {
        return companyPlaceProvince;
    }

    public void setCompanyPlaceProvince(String companyPlaceProvince) {
        this.companyPlaceProvince = companyPlaceProvince;
    }

    public String getCompanyPlaceCity() {
        return companyPlaceCity;
    }

    public void setCompanyPlaceCity(String companyPlaceCity) {
        this.companyPlaceCity = companyPlaceCity;
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

    public String getCustomerBaseInfoId() {
        return customerBaseInfoId;
    }

    public void setCustomerBaseInfoId(String customerBaseInfoId) {
        this.customerBaseInfoId = customerBaseInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHousingType() {
        return housingType;
    }

    public void setHousingType(String housingType) {
        this.housingType = housingType;
    }

    public String getLivingPlaceDistrict() {
        return livingPlaceDistrict;
    }

    public void setLivingPlaceDistrict(String livingPlaceDistrict) {
        this.livingPlaceDistrict = livingPlaceDistrict;
    }

    public String getLivingPlaceDetail() {
        return livingPlaceDetail;
    }

    public void setLivingPlaceDetail(String livingPlaceDetail) {
        this.livingPlaceDetail = livingPlaceDetail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPlaceDistrict() {
        return companyPlaceDistrict;
    }

    public void setCompanyPlaceDistrict(String companyPlaceDistrict) {
        this.companyPlaceDistrict = companyPlaceDistrict;
    }

    public String getCompanyPlaceDetail() {
        return companyPlaceDetail;
    }

    public void setCompanyPlaceDetail(String companyPlaceDetail) {
        this.companyPlaceDetail = companyPlaceDetail;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getIdCardValidityDates() {
        return idCardValidityDates;
    }

    public void setIdCardValidityDates(String idCardValidityDates) {
        this.idCardValidityDates = idCardValidityDates;
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

    public String getHomePlaceProvince() {
        return homePlaceProvince;
    }

    public void setHomePlaceProvince(String homePlaceProvince) {
        this.homePlaceProvince = homePlaceProvince;
    }

    public String getHomePlaceCity() {
        return homePlaceCity;
    }

    public void setHomePlaceCity(String homePlaceCity) {
        this.homePlaceCity = homePlaceCity;
    }

    public String getHomePlaceDistrict() {
        return homePlaceDistrict;
    }

    public void setHomePlaceDistrict(String homePlaceDistrict) {
        this.homePlaceDistrict = homePlaceDistrict;
    }

    public String getHomePlaceDetail() {
        return homePlaceDetail;
    }

    public void setHomePlaceDetail(String homePlaceDetail) {
        this.homePlaceDetail = homePlaceDetail;
    }


}
