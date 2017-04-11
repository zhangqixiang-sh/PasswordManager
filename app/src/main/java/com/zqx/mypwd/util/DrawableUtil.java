package com.zqx.mypwd.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by ZhangQixiang on 2017/4/11.
 */

public class DrawableUtil {
    /**
     * convert view to drawable
     */
    public static BitmapDrawable v2d(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        view.setDrawingCacheEnabled(false);
        return new BitmapDrawable(bitmap);
    }

    /**
     * convert view to drawable from layout resource
     */
    public static BitmapDrawable v2d(Context context, @LayoutRes int layout) {
        View view = LayoutInflater.from(context).inflate(layout, null, false);
        return v2d(view);
    }

    /**
     * convert bitmap to drawable
     */
    public static BitmapDrawable b2d(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }
}
