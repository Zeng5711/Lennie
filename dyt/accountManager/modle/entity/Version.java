package com.hxyd.dyt.accountManager.modle.entity;

import io.realm.RealmObject;

/**
 * Created by win7 on 2017/3/6.
 */

public class Version extends RealmObject{

    /**
     * verName : V1.0.0
     * verCode : 1000
     * force : 1
     * url : http://dyt.html?download=1
     * info : 版本介绍
     */

    private String verName="";
    private String verCode="";
    private String force="";
    private String url="";
    private String info="";

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getForce() {
        return force;
    }

    public void setForce(String force) {
        this.force = force;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
