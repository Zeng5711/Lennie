package com.hxyd.dyt.common.entity;

import io.realm.RealmObject;

/**
 * Created by win7 on 2017/3/10.
 */

public class HousingTypeBean extends RealmObject {
    /**
     * value : 商业按揭房
     * code : 1
     */

    private String value="";
    private String code="";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
