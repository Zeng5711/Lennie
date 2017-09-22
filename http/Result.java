package com.hxyd.dyt.common.http;

/**
 * Created by win7 on 2017/2/16.
 */

public class Result<T> {
    public int code;
    public String msg;
    public T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
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
