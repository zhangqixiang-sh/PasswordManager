package com.zqx.password_manage.ui.activity;

import com.zqx.password_manage.model.bean.AccountBean;

import java.util.List;

/**
 * Created by ZhangQixiang on 2017/2/13.
 */

public interface AccountsView {

    void onQueryAllAccounts(List<AccountBean> accounts);

    void onAddAccount();

    void onSearchResult(List<AccountBean> accounts);

    void onDeleteAccount(int deleteNum, int dataPosition);

    void onUpdateAccount();

}
