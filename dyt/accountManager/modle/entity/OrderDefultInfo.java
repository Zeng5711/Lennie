package com.hxyd.dyt.accountManager.modle.entity;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by win7 on 2017/3/14.
 */

public class OrderDefultInfo {


    /**
     * imageInfo : [{"imageId":"8246","loanId":null,"name":"39_1_ID_01","category":"39_1_ID","thumbnailUrl":"/app/auth/image/imgRead?path=/filestorage/20170314/2/img/20170314112653416_s.jpg","originalUrl":"/app/auth/image/imgRead?path=/filestorage/20170314/2/img/20170314112653416.jpg","isValid":"1","createdBy":"2","updatedBy":null}]
     * baseInfo : {"companyPlaceCityCode":"3223","companyPlaceDetail":"KKK默默","carFrameNo":"556688","phone":"1358788996","isLockEd":"N","companyPhone":"1358788996","loanPurposeValue":"房屋装修","companyPlaceCityValue":"台中市","customerBaseInfoId":14,"loanPurposeCode":"3","housingTypeCode":"3","housingTypeValue":"无按揭购房","companyPlaceDistrictCode":"3284","mileage":"55699","livingPlaceProvinceCode":"465","repaymentMethods":"2","educationValue":"本科","livingPlaceDistrictValue":"和平区","companyName":"国贸","relationPhone":"路路通","livingPlaceDetail":"国贸","livingPlaceProvinceValue":"辽宁省","carInfoId":12,"loanStatus":"0","loanInfoId":6,"sexValue":"男","companyPlaceDistrictValue":"南区","productTypesCode":"2","sex":"1","produceDate":1489334400000,"licencePlate":"536800","companyPlaceProvinceCode":"3219","customerRelationCode":"2","customerRelationValue":"子女","education":"2","relationPerson":"咯哦哦","repaymentPeriodhods":"10","name":"唐","marriageValue":"未婚","assess":3000,"loanMount":1365000,"idCard":"431182654556525588","livingPlaceDistrictCode":"467","livingPlaceCityCode":"466","marriage":"2","productTypesValue":"按揭押车","versions":"1","carModel":"4566","idCardValidityDates":1489334400000,"livingPlaceCityValue":"沈阳市","brand":"×5","companyPlaceProvinceValue":"台湾省"}
     */

    @PrimaryKey
    private long primary;
    private BaseInfoBean baseInfo;
    private List<ImageInfoBean> imageInfo;

    public long getPrimary() {
        return primary;
    }

    public void setPrimary(long primary) {
        this.primary = primary;
    }

    public BaseInfoBean getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfoBean baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<ImageInfoBean> getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(List<ImageInfoBean> imageInfo) {
        this.imageInfo = imageInfo;
    }

}
