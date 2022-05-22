package com.zyy.sport.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * 盐，用于混交md5
     */
    private static final String slat = "&%5123***&&%%$$#@";

    /**
     * 加密 无法解密
     */
    public static String md5(String password) {
        if (StringUtils.isNotEmpty(password)) {
            byte[] b = null;
            try {
                b = MessageDigest.getInstance("md5").digest(password.getBytes());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            StringBuilder sb = new StringBuilder(new BigInteger(1, b).toString(16));
            for (int i = 0; i < 32 - sb.length(); i++) {
                sb.insert(0, "0");
            }
            return sb.toString();
        } else {
            return null;
        }
    }
}