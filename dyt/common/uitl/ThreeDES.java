package com.hxyd.dyt.common.uitl;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;


/**
 * 加密算法
 * <p>
 * <br> Author: Simon
 * <br> Version: 3.0.0
 * <br> Date: 2017/2/15
 */
public class ThreeDES {

    /**
     * 加密
     */
    public static String encrypt(String src, String key) throws Exception {
        DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        SecretKey securekey = keyFactory.generateSecret(dks);

        Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        byte[] b = cipher.doFinal(src.getBytes("utf-8"));
        return HexBytesUtils.bytesToHexString(b);
    }

    /**
     * 解密
     */
    public static String decrypt(String src, String key) throws Exception {

        DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        SecretKey securekey = keyFactory.generateSecret(dks);

        // --Chipher对象解密
        Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        byte[] retByte = cipher.doFinal(src.getBytes("UTF-8"));
        return new String(retByte);
    }
}
