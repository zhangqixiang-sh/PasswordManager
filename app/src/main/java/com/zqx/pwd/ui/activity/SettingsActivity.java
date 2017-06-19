package com.zqx.pwd.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zqx.pwd.R;
import com.zqx.pwd.event.PwdChangedEvent;
import com.zqx.pwd.ui.dialog.PwdSettingDialog;
import com.zqx.pwd.util.SpUtil;
import com.zqx.pwd.global.Spkey;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    private String mPwd0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(PwdChangedEvent event) {
        mPwd0 = event.pwd;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPwd0 = SpUtil.getString(Spkey.PWD, "");
    }

    @OnClick(R.id.btn_pwd_setting)
    public void onClick() {
        new PwdSettingDialog(this,mPwd0).show();
    }
}
