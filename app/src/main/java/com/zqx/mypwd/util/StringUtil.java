package com.zqx.mypwd.util;

import android.text.TextUtils;

/**
 * Created by ZhangQixiang on 2017/1/12.
 */
public class StringUtil {

    public static boolean hasEmpty(String... strs) {
        for (String str : strs) {
            if (TextUtils.isEmpty(str)) {
                return true;
            }
        }
        return false;
    }
}
