package com.zqx.mypwd.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zqx.mypwd.R;
import com.zqx.mypwd.adapter.AccountAdapter;
import com.zqx.mypwd.bean.AccountBean;
import com.zqx.mypwd.dialog.AccountDialog;
import com.zqx.mypwd.presenter.AccountsPresenter;
import com.zqx.mypwd.util.DensityUtil;
import com.zqx.mypwd.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AccountsActivity extends BaseActivity implements
        SearchView.OnQueryTextListener, AccountsView, SwipeMenuCreator, OnSwipeMenuItemClickListener {
    @BindView(R.id.search)
    SearchView            mSearch;
    @BindView(R.id.toolbar)
    Toolbar               mToolbar;
    @BindView(R.id.rv_list)
    SwipeMenuRecyclerView mRvList;
    @BindView(R.id.coor)
    CoordinatorLayout     mCoor;
    private AccountsPresenter      mPresenter;
    private ArrayList<AccountBean> mAccounts;
    private AccountAdapter         mAdapter;


    @Override
    protected int getLayout() {
        return R.layout.activity_accounts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
        mSearch.setOnQueryTextListener(this);
        initSwipeRecycler();
        mPresenter = new AccountsPresenter(this);
        mPresenter.queryAllAccounts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("debug", "onResume: ");
        mRvList.smoothCloseMenu();
        mSearch.clearFocus();
    }

    //初始化侧滑菜单recyclerView
    private void initSwipeRecycler() {
        //初始化原生recycler属性
        mRvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //初始化第三方属性
        mRvList.setSwipeMenuCreator(this);
        mRvList.setSwipeMenuItemClickListener(this);
        mAccounts = new ArrayList<>();
        mAdapter = new AccountAdapter(mAccounts);
        mRvList.setAdapter(mAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unRegister();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                new AccountDialog(this).show();
                break;
            case R.id.action_setting:
                ToastUtil.show("设置功能暂未开通");
                startActivity(SettingsActivity.class, false);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuBuilder menuBuilder = (MenuBuilder) menu;
        menuBuilder.setOptionalIconsVisible(true); //显示图标
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        doSearch(query);
        mSearch.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        doSearch(newText);
        return true;
    }

    private void doSearch(String text) {
        mPresenter.queryLike(text);
    }

    @Override
    public void onQueryAllAccounts(List<AccountBean> accounts) {
        mAccounts.clear();
        mAccounts.addAll(accounts);
        mAdapter.notifyDataSetChanged();
        Log.d("debug", "AccountsActivity->onQueryAllAccounts->notifyDataSetChanged");

    }

    @Override
    public void onAddAccount() {
        mPresenter.queryAllAccounts();
        Log.d("debug", "PwdActivity_onAddAccount被调用");
    }

    @Override
    public void onSearchResult(List<AccountBean> accounts) {
        mAccounts.clear();
        mAccounts.addAll(accounts);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteAccount(int deleteNum, int dataPosition) {
        if (deleteNum != 0) {
            ToastUtil.show("(数据库中)成功删除了" + deleteNum + "条记录");
        } else {
            ToastUtil.show("删除失败");
        }
    }

    @Override
    public void onUpdateAccount() {
        Log.d("debug", "onUpdateAccount: ");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
        SwipeMenuItem item_edit = new SwipeMenuItem(this)
                .setBackgroundColor(Color.parseColor("#9c9c9c"))// 点击的背景。
                .setImage(R.mipmap.edit_white) // 图标。
                .setText("编辑")
                .setTextColor(Color.WHITE) // 文字颜色。
                .setTextSize(DensityUtil.sp2px(this, 7)) // 文字大小。
                .setWidth(DensityUtil.dip2px(this, 60)) // 宽度。
                .setHeight(DensityUtil.dip2px(this, 85)); // 高度。
        swipeRightMenu.addMenuItem(item_edit); // 添加一个按钮到左侧菜单。

        SwipeMenuItem deleteItem = new SwipeMenuItem(this)
                .setBackgroundColor(Color.parseColor("#8c8c8c"))
                .setImage(R.mipmap.delete_white) // 图标。
                .setText("删除") // 文字。
                .setTextColor(Color.WHITE) // 文字颜色。
                .setTextSize(DensityUtil.sp2px(this, 7)) // 文字大小。
                .setWidth(DensityUtil.dip2px(this, 60))
                .setHeight(DensityUtil.dip2px(this, 85));
        swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。.

    }
    
    @Override
    public void onItemClick(Closeable closeable, final int dataPosition, int menuPosition, int direction) {

        final AccountBean bean = mAccounts.get(dataPosition);
        if (menuPosition == 1) {//点击删除
            Log.d("debug", "onItemClick: 点击了删除");
            mAccounts.remove(dataPosition);
            mAdapter.notifyItemRemoved(dataPosition);
            ToastUtil.show("点击撤回可以撤销删除");
            Snackbar.make(mCoor, "删除一条记录", Snackbar.LENGTH_LONG)
                    .setAction("撤回", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            switch (event) {
                                case DISMISS_EVENT_SWIPE:
                                case DISMISS_EVENT_TIMEOUT:
                                    mPresenter.deleteAccount(bean, dataPosition);
                                    break;
                                case DISMISS_EVENT_ACTION:
                                    mAccounts.add(dataPosition, bean);
                                    mAdapter.notifyItemInserted(dataPosition);
                                    mRvList.smoothCloseMenu();
                                    ToastUtil.show("撤销成功");
                                    break;
                                default:
                                    break;
                            }
                        }

                    }).show();

        } else if (menuPosition == 0) {//点击编辑
            Log.d("debug", "onItemClick: 点击了编辑");
            mRvList.smoothCloseMenu();
            new AccountDialog(this,mAccounts.get(dataPosition)).show();

        }
    }
}
