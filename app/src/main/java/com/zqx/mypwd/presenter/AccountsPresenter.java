package com.zqx.mypwd.presenter;

import android.content.Context;

import com.zqx.mypwd.dao.AccountDao;
import com.zqx.mypwd.model.bean.AccountBean;
import com.zqx.mypwd.model.manager.EncryptManager;
import com.zqx.mypwd.ui.activity.AccountsActivity;
import com.zqx.mypwd.ui.activity.AccountsView;
import com.zqx.mypwd.util.LogUtil;
import com.zqx.mypwd.util.Run;

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
    }

    @Override
    public void queryAllAccounts() {
        Run.onSub(new Runnable() {
            @Override
            public void run() {
                final List<AccountBean> accounts = AccountDao.getAllAccounts(mContext);
                for (AccountBean account : accounts) {
                    EncryptManager.decryptAccount(account);
                }
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
                for (AccountBean account : accounts) {
                    EncryptManager.decryptAccount(account);
                }
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


    public void updateAccount(final AccountBean bean) {
        Run.onSub(new Runnable() {
            @Override
            public void run() {
                EncryptManager.encryptAccount(bean);
                LogUtil.d("debug","加密结果: name="+bean.name+" pwd="+bean.pwd);
                AccountDao.updateAccount(mContext, bean);
                EncryptManager.decryptAccount(bean);
                Run.onMain(new Runnable() {
                    @Override
                    public void run() {
                        mAccountsView.onUpdateAccount();
                    }
                });
            }
        });

    }


    public void addAccount(final AccountBean bean) {
        Run.onSub(new Runnable() {
            @Override
            public void run() {
                EncryptManager.encryptAccount(bean);
                LogUtil.d("debug","加密结果: name="+bean.name+" pwd="+bean.pwd);
                AccountDao.saveAccount(mContext, bean);
                Run.onMain(new Runnable() {
                    @Override
                    public void run() {
                        mAccountsView.onAddAccount();
                    }
                });
            }
        });

    }


}
