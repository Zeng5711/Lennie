package com.hxyd.dyt.accountManager.modle.entity;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by win7 on 2017/3/14.
 */

public class ProductInfoList {

    @PrimaryKey
    private long primary =1;
    private String code="";
    private String version="";
    private List<ProductInfoListBenam> list;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getPrimary() {
        return primary;
    }

    public void setPrimary(long primary) {
        this.primary = primary;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ProductInfoListBenam> getList() {
        return list;
    }

    public void setList(List<ProductInfoListBenam> list) {
        this.list = list;
    }
}
