package com.ddp.res.common.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;

/**
 * @author zengsl
 */
@UtilityClass
public class CryptUtil {

    String key = "sdfsdfsd&&&sdfsd!...)99=+";

    public static String digest(String str) {
        return SecureUtil.md5(str);
    }

    public static String digest(String str, String salt) {
        if (salt == null) {
            return digest(str);
        }
        return new MD5(salt.getBytes(StandardCharsets.UTF_8)).digestHex16(str);
    }

    public  static String encrypt(String str) {
        return SecureUtil.aes(key.getBytes()).encryptHex(str);
    }

    public static String decrypt(String str) {
        return SecureUtil.aes(key.getBytes()).decryptStr(str);
    }
}
