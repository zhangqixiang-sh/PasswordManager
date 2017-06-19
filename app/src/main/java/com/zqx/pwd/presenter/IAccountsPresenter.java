package com.zqx.pwd.presenter;

import com.zqx.pwd.model.bean.AccountBean;

/**
 * Created by ZhangQixiang on 2017/2/13.
 */

public interface IAccountsPresenter {
    void queryAllAccounts();

    void queryLike(String text);

    void deleteAccount(AccountBean accountBean, int dataPosition);

}
