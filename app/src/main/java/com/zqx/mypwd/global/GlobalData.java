package com.zqx.mypwd.global;

import com.zqx.mypwd.util.AESUtil;
import com.zqx.mypwd.util.SpUtil;


/**
 * Created by ZhangQixiang on 2017/4/7.
 */

public class GlobalData {

    private static byte[] keyBytes;

    public static byte[] getKeyBytes() {

        if (keyBytes == null) {
            keyBytes = SpUtil.getByteArray(Spkey.KEY_BYTES, null);
            if (keyBytes == null) {
                keyBytes = AESUtil.createKeyBytes(Const.strings.seed);
                SpUtil.saveByteArray(Spkey.KEY_BYTES, keyBytes);
            }
        }

        return keyBytes;
    }

}
