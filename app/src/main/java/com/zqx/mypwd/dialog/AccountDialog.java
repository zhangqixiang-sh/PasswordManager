package com.zqx.mypwd.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zqx.mypwd.R;
import com.zqx.mypwd.bean.AccountBean;
import com.zqx.mypwd.event.AddAccountEvent;
import com.zqx.mypwd.event.UpdateAccountEvent;
import com.zqx.mypwd.util.StringUtil;
import com.zqx.mypwd.util.ToastUtil;
import com.zqx.mypwd.activity.AccountsActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZhangQixiang on 2017/2/14.
 */
public class AccountDialog extends TopDownDialog implements DialogInterface.OnDismissListener {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_server)
    EditText mEtServer;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    private boolean mIsAdd = true;
    private AccountBean mBeanToUpdate;

    public AccountDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_entry);
        ButterKnife.bind(this);
        setOnDismissListener(this);

    }

    public AccountDialog(AccountsActivity context, AccountBean bean) {
        this(context);
        mIsAdd = false;
        mBeanToUpdate = bean;
        mEtServer.setText(bean.server);
        mEtName.setText(bean.name);
        mEtPwd.setText(bean.pwd);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEtServer.requestFocus();

        mEtPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    doConfirm();
                    return true;
                }
                return false;
            }
        });

    }

    @OnClick({R.id.btn_cancel, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_confirm:
                doConfirm();
                break;
        }
    }

    private void doConfirm() {
        String server = mEtServer.getText().toString().trim();
        String name = mEtName.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        if (StringUtil.hasEmpty(server, name, pwd)) {
            ToastUtil.show("不能有空");
        } else {
            if (mIsAdd) {
                doAdd(server, name, pwd);
            } else {
                doUpdate(server, name, pwd);
            }
            dismiss();
        }
    }

    private void doUpdate(String server, String name, String pwd) {
        Log.d("debug", "doConfirm: 发送更新事件");
        mBeanToUpdate.server = server;
        mBeanToUpdate.name = name;
        mBeanToUpdate.pwd = pwd;
        EventBus.getDefault().post(new UpdateAccountEvent(mBeanToUpdate));
    }

    private void doAdd(String server, String name, String pwd) {
        AccountBean accountBean = new AccountBean(server, name, pwd);
        EventBus.getDefault().post(new AddAccountEvent(accountBean));
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        EventBus.getDefault().unregister(this);
    }
}
