package com.hxyd.dyt.common.uitl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by win7 on 2017/3/6.
 */

public class Sign {


//    public static String sign(String secret) {
//        try {
//            byte[] sha1Digest = getSHA1Digest(secret);
//            return byte2hex(sha1Digest);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static String signNew(String secret,String time ) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(secret);
            sb.append(time);
            sb.append(secret);
            byte[] sha1Digest = getSHA1Digest(sb.toString());
            return byte2hex(sha1Digest);
        } catch (IOException e) {

            return null;
        }
    }


    /**
     * 对paramValues进行签名，其中ignoreParamNames这些参数不参与签名
     *
     * @return
     */
//    public static String sign(Map<String, Object> paramValues, List<String> ignoreParamNames, String secret) {
//        try {
//            StringBuilder sb = new StringBuilder();
//            List<String> paramNames = new ArrayList<String>(paramValues.size());
//            paramNames.addAll(paramValues.keySet());
//            if (ignoreParamNames != null && ignoreParamNames.size() > 0) {
//                for (String ignoreParamName : ignoreParamNames) {
//                    paramNames.remove(ignoreParamName);
//                }
//            }
//            Collections.sort(paramNames);
//
//            sb.append(secret);
//            for (String paramName : paramNames) {
//                sb.append(paramName).append(paramValues.get(paramName));
//            }
//            sb.append(secret);
//            byte[] sha1Digest = getSHA1Digest(sb.toString());
//            return byte2hex(sha1Digest);
//        } catch (IOException e) {
//
//            return null;
//        }
//    }

    private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.getMessage());
        }
        return bytes;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

}
