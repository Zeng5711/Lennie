package com.hxyd.dyt.common.http;

import java.util.List;

/**
 * Created by win7 on 2017/3/13.
 */

public class ResultT<T> {
    public int code;
    public String msg;
    public List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return  "{ code :" + code
                +" msg :" + msg
                +" data " + data
                + " }";
    }
}
