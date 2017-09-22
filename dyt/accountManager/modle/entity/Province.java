package com.hxyd.dyt.accountManager.modle.entity;

/**
 * Created by win7 on 2017/5/17.
 */

public class Province {


    /**
     * cityId : 201
     * cityName : 北京
     */

    private String cityId;
    private String cityName;
    private boolean isCheck;

    public Province(String cityId,String cityName,boolean isCheck){
        this.cityId = cityId;
        this.cityName = cityName;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
