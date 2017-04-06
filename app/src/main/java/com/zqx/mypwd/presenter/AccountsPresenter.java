package com.zqx.mypwd.presenter;

import android.content.Context;

import com.zqx.mypwd.ui.activity.AccountsActivity;
import com.zqx.mypwd.ui.activity.AccountsView;
import com.zqx.mypwd.bean.AccountBean;
import com.zqx.mypwd.dao.AccountDao;
import com.zqx.mypwd.event.AddAccountEvent;
import com.zqx.mypwd.event.UpdateAccountEvent;
import com.zqx.mypwd.util.Run;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by ZhangQixiang on 2017/2/13.
 */
public class AccountsPresenter implements IAccountsPresenter {

    private final Context      mContext;
    private       AccountsView mAccountsView;

    public AccountsPresenter(AccountsView accountsView) {
        mAccountsView = accountsView;
        mContext = ((AccountsActivity) accountsView).getApplicationContext();
        EventBus.getDefault().register(this);

    }

    @Override
    public void queryAllAccounts() {
        Run.onSub(new Runnable() {
            @Override
            public void run() {
                final List<AccountBean> accounts = AccountDao.getAllAccounts(mContext);

                Run.onMain(new Runnable() {
                    @Override
                    public void run() {
                        mAccountsView.onQueryAllAccounts(accounts);

                    }
                });

            }
        });

    }

    @Override
    public void queryLike(final String text) {
        Run.onSub(new Runnable() {
            @Override
            public void run() {
                final List<AccountBean> accounts = AccountDao.getAccountsLike(mContext, text);
                Run.onMain(new Runnable() {
                    @Override
                    public void run() {
                        mAccountsView.onSearchResult(accounts);
                    }
                });

            }
        });
    }

    @Override
    public void deleteAccount(final AccountBean accountBean, final int dataPosition) {
        Run.onSub(new Runnable() {
            @Override
            public void run() {
                final int deleteNum = AccountDao.deleteAccount(mContext, accountBean);

                Run.onMain(new Runnable() {
                    @Override
                    public void run() {
                        mAccountsView.onDeleteAccount(deleteNum, dataPosition);
                    }
                });

            }
        });
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(UpdateAccountEvent event) {
        AccountDao.updateAccount(mContext, event.accountBean);
        Run.onMain(new Runnable() {
            @Override
            public void run() {
                mAccountsView.onUpdateAccount();
            }
        });
    }



    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(AddAccountEvent event) {
        AccountDao.saveAccount(mContext, event.accountBean);
        Run.onMain(new Runnable() {
            @Override
            public void run() {
                mAccountsView.onAddAccount();
            }
        });
    }


}
