package com.hxyd.dyt.accountManager.modle.entity;

/**
 * Created by win7 on 2017/5/17.
 */

public class CustomerInfo {
    private String loanInfoId = "";
    private String name = "";//姓名
    private String idCard = "";//身份证
    private String phone = "";//联系电话
    private String livingPlaceProvince = "";//现住地址省份
    private String livingPlaceCity = "";//现住地址城市
    private String livingPlaceDistrict = "";//现住址地区Id
    private String livingPlaceDetail = "";//现住址详细地址
    private String spouseName = "";//配偶姓名
    private String spousePhone = "";//配偶电话
    private String spouseIdcard = "";//配偶身份证号

    private String carFrameNo = "";//车架号
    private String brand = "";//车辆品牌
    private String carModel = "";//车型
    private String BuyCarDate = "";//上牌时间
    private String Mileage = "";//行驶公里数
    private String CityId = "";//上牌城市
    private String TrimId = "";//车型Id
    private String cityName = "";
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String formattedAddress = "";

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLoanInfoId() {
        return loanInfoId;
    }

    public void setLoanInfoId(String loanInfoId) {
        this.loanInfoId = loanInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLivingPlaceProvince() {
        return livingPlaceProvince;
    }

    public void setLivingPlaceProvince(String livingPlaceProvince) {
        this.livingPlaceProvince = livingPlaceProvince;
    }

    public String getLivingPlaceCity() {
        return livingPlaceCity;
    }

    public void setLivingPlaceCity(String livingPlaceCity) {
        this.livingPlaceCity = livingPlaceCity;
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

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getSpousePhone() {
        return spousePhone;
    }

    public void setSpousePhone(String spousePhone) {
        this.spousePhone = spousePhone;
    }

    public String getSpouseIdcard() {
        return spouseIdcard;
    }

    public void setSpouseIdcard(String spouseIdcard) {
        this.spouseIdcard = spouseIdcard;
    }

    public String getCarFrameNo() {
        return carFrameNo;
    }

    public void setCarFrameNo(String carFrameNo) {
        this.carFrameNo = carFrameNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getMileage() {
        return Mileage;
    }

    public void setMileage(String mileage) {
        this.Mileage = mileage;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getTrimId() {
        return TrimId;
    }

    public void setTrimId(String trimId) {
        TrimId = trimId;
    }

    public String getBuyCarDate() {
        return BuyCarDate;
    }

    public void setBuyCarDate(String buyCarDate) {
        BuyCarDate = buyCarDate;
    }
}
