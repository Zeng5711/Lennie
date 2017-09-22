package com.hxyd.dyt.common;

import com.hxyd.dyt.BuildConfig;
import com.hxyd.dyt.common.uitl.SharedPrefsUtil;
import com.hxyd.dyt.common.uitl.Sign;

import java.util.Map;

/**
 * Created by win7 on 2017/3/2.
 */

public class Constant {

    public static final String VALUATION_SYSTEM_URL = "https://www.che300.com/hxyd";

    public static final String BASE_URL = BuildConfig.HOST_URL;

    public static final String IMG_UPLOAD_URL = BuildConfig.IMG_UPLOAD_URL;

    public static final String SIGN_KEY = BuildConfig.SIGN_KEY;

    public static final String DES_KEY = BuildConfig.DES_KEY;

    public static final String SH_NAME = "DYT_APP_SH";

    public static final String PLATFORM = "ANDROID";

    public static final String CHANNEL = "dyt";

    public static final String APP_TYPE = "1";

    public static final String TAG = "dyt";

    public static final String USER_NAME = "userName";

    public static final String USER = "user";

    public static final String ROLE_NAME = "roleName";

    public static final String USER_ID = "userID";

    private static final String TOKEN = "DYT_TOKEN";

    public static final String SIGN_MAP_KEY = "sign";

    public static final String POSTTIME_MAP_KEY = "posttime";

    public static final String TOKEN_MAP_KEY = "token";

    private static final String USER_ID_KEY = "userID";

    private static final String ALIAS = "Alias";

    private static final String BASEDATA_VERSION = "BaseDataVersion";

    private static final String PRODUCTINFOLIST_VERSION = "ProductInfoListVersion";

    private static final String AREALIST_VERSION = "AreaListVersion";

    private static final String JPUSH_MESSAGE = "jpush_message";

    public static final String ISORDERDEFAULT = "isorderdefault";

    public static final String PRODUCT_TYPE = "product_type";

    public static final int CACHE_SIZE = 10;

    public static final String OCR_KEY = BuildConfig.OCR_KEY;

    public static final String APK_NAME = "dyt";

    public static final String APK_SUFFIX = ".apk";

    public static final String CACHE_CATALOG_NAME = "dyt";

    public static final String VERCODE_KEY = "verCode";

    public static final String TYPE_KEY = "type";

    public static final String IS_GPS_PERMISSION = "isGpsPermission";

    public static final String ACCOUNT_NAME = "accountName";

    public static final String MENUPE_RMISSION_BEAN = "MenuPermissionBean";


    public static String getProductType() {
        return SharedPrefsUtil.getValue(null, PRODUCT_TYPE, "");
    }

    public static void setProductType(String value) {
        SharedPrefsUtil.putValue(null, PRODUCT_TYPE, value);
    }

    public static String getToken() {
        return SharedPrefsUtil.getValue(null, TOKEN, "");
    }

    public static void setAlias(String alias) {
        SharedPrefsUtil.putValue(null, ALIAS, alias);
    }

    public static String getAlias() {
        return SharedPrefsUtil.getValue(null, ALIAS, "");
    }

    public static void setToken(String token) {
        SharedPrefsUtil.putValue(null, TOKEN, token);
    }

    public static String getUserID() {
        return SharedPrefsUtil.getValue(null, USER_ID_KEY, "");
    }

    public static void setUserID(String userID) {
        SharedPrefsUtil.putValue(null, USER_ID_KEY, userID);
    }

    public static String getBaseDataVersion() {
        return SharedPrefsUtil.getValue(null, BASEDATA_VERSION, "");
    }

    public static void setBaseDataVersion(String version) {
        SharedPrefsUtil.putValue(null, BASEDATA_VERSION, version);
    }

    public static String getProductInfoListVersion() {
        return SharedPrefsUtil.getValue(null, PRODUCTINFOLIST_VERSION, "");
    }

    public static void setProductInfoListVersion(String version) {
        SharedPrefsUtil.putValue(null, PRODUCTINFOLIST_VERSION, version);
    }

    public static String getAreaListVersion() {
        return SharedPrefsUtil.getValue(null, AREALIST_VERSION, "");
    }

    public static void setAreaListVersion(String version) {
        SharedPrefsUtil.putValue(null, AREALIST_VERSION, version);
    }

    public static void setMessage(boolean is) {
        SharedPrefsUtil.putValue(null, JPUSH_MESSAGE, is);
    }

    public static boolean getMessage() {
        return SharedPrefsUtil.getValue(null, JPUSH_MESSAGE, false);
    }

    public static Map<String, Object> addMap(Map<String, Object> map) {
        String time = System.currentTimeMillis() + "";
        String sign = Sign.signNew(Constant.SIGN_KEY, time);
        map.put(Constant.SIGN_MAP_KEY, sign);
        map.put(Constant.POSTTIME_MAP_KEY, time);
        map.put(USER_ID, SharedPrefsUtil.getValue(null, USER_ID, ""));
        return map;
    }
}
