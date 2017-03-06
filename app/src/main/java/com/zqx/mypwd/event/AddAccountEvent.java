package com.zqx.mypwd.event;

import com.zqx.mypwd.bean.AccountBean;

/**
 * Created by ZhangQixiang on 2017/2/13.
 */
public class AddAccountEvent {


    public AccountBean accountBean;

    public AddAccountEvent(AccountBean accountBean) {

        this.accountBean = accountBean;
    }
}
