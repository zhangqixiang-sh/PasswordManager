package com.zqx.mypwd.activity;

import com.zqx.mypwd.bean.AccountBean;

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
