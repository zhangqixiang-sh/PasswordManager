package com.zqx.pwd.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zqx.pwd.R;
import com.zqx.pwd.ui.dialog.PwdSettingDialog;
import com.zqx.pwd.event.PwdChangedEvent;
import com.zqx.pwd.util.SpUtil;
import com.zqx.pwd.global.Spkey;
import com.zqx.pwd.util.StatusBarUtil;
import com.zqx.pwd.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckInActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    public String mPwd;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    private FingerprintManager mFinManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#fafafa"));
        StatusBarUtil.setLightMode(this);
        setContentView(R.layout.activity_check_in);
        ButterKnife.bind(this);
        mEtPwd.setOnEditorActionListener(this);
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPwd = SpUtil.getString(Spkey.PWD, "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mFinManager = ((FingerprintManager) getSystemService(FINGERPRINT_SERVICE));
            if (mFinManager.isHardwareDetected()) {
                if (canUse()) {
                    mFinManager.authenticate(null, null, 0, new AuthCallback(this), null);
                }

            } else {
                ToastUtil.show("你的设备不支持指纹识别,该换手机了!");
                settingPwd();

            }
        } else {
            ToastUtil.show("你的设备不支持指纹识别\n第一次进入需先设置密码");
            settingPwd();
        }
    }

    private void settingPwd() {
        if (TextUtils.isEmpty(mPwd)) {
            new PwdSettingDialog(this, mPwd).show();
        }
    }

    @Subscribe
    public void onEvent(PwdChangedEvent event) {
        mPwd = event.pwd;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean canUse() {
        //检查权限
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            ToastUtil.show("应用没有获得指纹识别权限");
            return false;
        }

        //判断系统是否有已录入的指纹
        if (!mFinManager.hasEnrolledFingerprints()) {
            ToastUtil.show("还没有已录入的指纹,请在系统设置里添加指纹");
            return false;
        }
        return true;
    }

    @OnClick({R.id.btn_confirm, R.id.tv_pwd_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                doPwdEntryConfirm();
                break;
            case R.id.tv_pwd_setting:
                new PwdSettingDialog(this, mPwd).show();
                break;
        }

    }

    private void doPwdEntryConfirm() {
        String pwd = mEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.show("密码不能为空");
            return;
        }
        if (TextUtils.equals(mPwd, pwd)) {
            startActivity(new Intent(this, AccountsActivity.class));
            finish();
        } else {
            ToastUtil.show("密码不正确");
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            doPwdEntryConfirm();
        }
        return true;
    }


    //防止内存泄漏
    @RequiresApi(api = Build.VERSION_CODES.M)
     static class AuthCallback extends FingerprintManager.AuthenticationCallback {

        WeakReference<CheckInActivity> mActivity;

        AuthCallback(CheckInActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            CheckInActivity activity = mActivity.get();
            if (activity != null) {
                activity.startActivity(new Intent(activity, AccountsActivity.class));
                activity.finish();
            }

        }

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            CheckInActivity activity = mActivity.get();
            if (activity != null) {
                ToastUtil.show("验证错误");
            }

        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            CheckInActivity activity = mActivity.get();
            if (activity != null) {
                ToastUtil.show("验证失败");
            }

        }

    }

}
