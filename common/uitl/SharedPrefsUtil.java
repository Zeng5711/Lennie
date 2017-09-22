package com.hxyd.dyt.common.uitl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxyd.dyt.MyAppliaction;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.login.modle.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bear
 *         <p>
 *         SharedPrefereces工具类
 */
public class SharedPrefsUtil {

    /**
     * 向SharedPreferences中写入int类型数据
     *
     * @param name  对应的xml文件名称
     * @param key   键
     * @param value 值
     */
    public static void putValue(String name, String key, int value) {
        Editor sp = getEditor(name);
        sp.putInt(key, value);
        sp.commit();
    }

    /**
     * 向SharedPreferences中写入boolean类型的数据
     *
     * @param name  对应的xml文件名称
     * @param key   键
     * @param value 值
     */
    public static void putValue(String name, String key,
                                boolean value) {
        Editor sp = getEditor(name);
        sp.putBoolean(key, value);
        sp.commit();
    }

    /**
     * 向SharedPreferences中写入String类型的数据
     *
     * @param name  对应的xml文件名称
     * @param key   键
     * @param value 值
     */
    public static void putValue(String name, String key,
                                String value) {
        Editor sp = getEditor(name);
        sp.putString(key, value);
        sp.commit();
    }

    /**
     * 向SharedPreferences中写入float类型的数据
     *
     * @param name  对应的xml文件名称
     * @param key   键
     * @param value 值
     */
    public static void putValue(String name, String key,
                                float value) {
        Editor sp = getEditor(name);
        sp.putFloat(key, value);
        sp.commit();
    }

    /**
     * 向SharedPreferences中写入long类型的数据
     *
     * @param name  对应的xml文件名称
     * @param key   键
     * @param value 值
     */
    public static void putValue(String name, String key,
                                long value) {
        Editor sp = getEditor(name);
        sp.putLong(key, value);
        sp.commit();
    }

    /**
     * 从SharedPreferences中读取int类型的数据
     *
     * @param name     对应的xml文件名称
     * @param key      键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static int getValue(String name, String key,
                               int defValue) {
        SharedPreferences sp = getSharedPreferences(name);
        int value = sp.getInt(key, defValue);
        return value;
    }

    /**
     * 从SharedPreferences中读取boolean类型的数据
     *
     * @param name     对应的xml文件名称
     * @param key      键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static boolean getValue(String name, String key,
                                   boolean defValue) {
        SharedPreferences sp = getSharedPreferences(name);
        boolean value = sp.getBoolean(key, defValue);
        return value;
    }

    /**
     * 从SharedPreferences中读取String类型的数据
     *
     * @param name     对应的xml文件名称
     * @param key      键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static String getValue(String name, String key,
                                  String defValue) {
        SharedPreferences sp = getSharedPreferences(name);
        String value = sp.getString(key, defValue);
        return value;
    }

    /**
     * 从SharedPreferences中读取float类型的数据
     *
     * @param name     对应的xml文件名称
     * @param key      键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static float getValue(String name, String key,
                                 float defValue) {
        SharedPreferences sp = getSharedPreferences(name);
        float value = sp.getFloat(key, defValue);
        return value;
    }

    /**
     * 从SharedPreferences中读取long类型的数据
     *
     * @param name     对应的xml文件名称
     * @param key      键
     * @param defValue 如果读取不成功则使用默认值
     * @return 返回读取的值
     */
    public static long getValue(String name, String key,
                                long defValue) {
        SharedPreferences sp = getSharedPreferences(name);
        long value = sp.getLong(key, defValue);
        return value;
    }

    //获取Editor实例
    private static Editor getEditor(String name) {
        return getSharedPreferences(name).edit();
    }

    //获取SharedPreferences实例
    private static SharedPreferences getSharedPreferences(String name) {
        return MyAppliaction.getContext().getSharedPreferences(TextUtils.isEmpty(name) ? Constant.SH_NAME : name, Context.MODE_PRIVATE);
    }


    /**
     * 保存List
     *
     * @param name
     * @param tag
     * @param datalist
     */
    public static <T> void putValue(String name, String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0) {
            return;
        }
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        Editor editor = getEditor(name);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();
    }

    /**
     * 获取List
     *
     * @param tag
     * @param name
     * @return
     */
//    public static <T> List<T> getValue(String name, String tag) {
//        List<T> datalist = new ArrayList<T>();
//        SharedPreferences preferences = getSharedPreferences(name);
//        String strJson = preferences.getString(tag, null);
//        if (null == strJson) {
//            return datalist;
//        }
//        Gson gson = new Gson();
//        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
//        }.getType());
//        return datalist;
//
//    }

    public static List<UserInfo.MenuPermissionBean> getValue(String name, String tag) {
        List<UserInfo.MenuPermissionBean> datalist = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences(name);
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<UserInfo.MenuPermissionBean>>() {
        }.getType());
        return datalist;

    }
}
