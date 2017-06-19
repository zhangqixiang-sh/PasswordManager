package com.zqx.pwd.global;

import com.zqx.pwd.util.AESUtil;
import com.zqx.pwd.util.SpUtil;


/**
 * Created by ZhangQixiang on 2017/4/7.
 */

public class GlobalData {

    private static byte[] keyBytes;//AES加密/解密用的秘钥
    private static boolean isHidePwd = SpUtil.getBoolean(Spkey.HIDE_PWD, true);//是否隐藏密码,默认隐藏

    public static byte[] getKeyBytes() {

        if (keyBytes == null) {
            keyBytes = SpUtil.getByteArray(Spkey.KEY_BYTES, null);
            if (keyBytes == null) {
                keyBytes = AESUtil.createKeyBytes(C.string.seed);
                SpUtil.saveByteArray(Spkey.KEY_BYTES, keyBytes);
            }
        }

        return keyBytes;
    }

    public static boolean isHidePwd() {
        return isHidePwd;
    }

    public static void toggleHidePwd() {
        isHidePwd = !isHidePwd;
        SpUtil.putBoolean(Spkey.HIDE_PWD, isHidePwd);
    }

}
