package com.zqx.pwd.util;

import android.content.Context;

public class DensityUtil {

    public static int dip2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;//表示1dp有几个像素
        return (int)(dp * density + 0.5F);
    }

    public static int px2dip(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(px / density + 0.5F);
    }

    public static int sp2px(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(sp * scaledDensity + 0.5F);
    }

    public static int px2sp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(px / scaledDensity + 0.5F);
    }
}
