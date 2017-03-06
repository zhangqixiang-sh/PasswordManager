package com.zqx.mypwd.event;

import com.zqx.mypwd.bean.AccountBean;

/**
 * Created by ZhangQixiang on 2017/2/17.
 */
public class UpdateAccountEvent {
    public AccountBean accountBean;

    public UpdateAccountEvent(AccountBean accountBean) {
        this.accountBean = accountBean;
    }
}
