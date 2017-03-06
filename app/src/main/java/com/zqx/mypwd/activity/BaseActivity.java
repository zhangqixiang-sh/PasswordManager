package com.zqx.mypwd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by ZhangQixiang on 2017/2/12.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    @LayoutRes
    protected abstract int getLayout();

    protected void startActivity(Class<? extends Activity> clazz, boolean finish) {
        startActivity(new Intent(this, clazz));
        if (finish) {
            finish();
        }
    }


}
