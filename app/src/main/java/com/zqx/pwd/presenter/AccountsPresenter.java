package com.zqx.pwd.presenter;

import android.content.Context;

import com.zqx.pwd.dao.AccountDao;
import com.zqx.pwd.model.bean.AccountBean;
import com.zqx.pwd.model.manager.EncryptManager;
import com.zqx.pwd.ui.activity.AccountsActivity;
import com.zqx.pwd.ui.activity.AccountsView;
import com.zqx.pwd.util.LogUtil;
import com.zqx.pwd.util.Run;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

        Observable.create(new ObservableOnSubscribe<List<AccountBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AccountBean>> e) throws Exception {
                List<AccountBean> accountList = AccountDao.getAllAccounts(mContext);
                EncryptManager.decryptAccountSet(accountList);
                e.onNext(accountList);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AccountBean>>() {
                    @Override
                    public void accept(List<AccountBean> accountList) throws Exception {

                            mAccountsView.onQueryAllAccounts(accountList);
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
                LogUtil.d("debug", "加密结果: name=" + bean.name + " pwd=" + bean.pwd);
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
                LogUtil.d("debug", "加密结果: name=" + bean.name + " pwd=" + bean.pwd);
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
