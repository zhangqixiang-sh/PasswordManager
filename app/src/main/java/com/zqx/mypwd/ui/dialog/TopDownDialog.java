package com.zqx.mypwd.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.zqx.mypwd.R;

/**
 * Created by ZhangQixiang on 2017/1/12.
 * 从上降下的对话框
 */
public class TopDownDialog extends Dialog {

    public TopDownDialog(final Context context) {
        //设置了对话框的样式,其中包括了窗口的进出动画
        super(context, R.style.dialog_top_down_style);

        //设置基本属性: 窗口的宽、高, 窗口外点击可结束对话框
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.gravity = Gravity.TOP;
        window.setAttributes(attributes);

        //点击外部可取消
        setCanceledOnTouchOutside(true);

    }

}
