package com.hxyd.dyt.accountManager.modle.entity;

/**
 * Created by win7 on 2017/5/17.
 */

public class Contact {
    private String index;
    private String name;
    private String id;
    private boolean isCheck;

    public Contact(String index, String name, String id,boolean isCheck) {
        this.index = index;
        this.name = name;
        this.id = id;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
