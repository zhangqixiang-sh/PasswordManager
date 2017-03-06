package com.zqx.mypwd.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.zqx.mypwd.R;
import com.zqx.mypwd.bean.AccountBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZhangQixiang on 2017/2/13.
 */
public class AccountAdapter extends SwipeMenuAdapter<AccountAdapter.ViewHolder> {
    private List<AccountBean> mDatas;

    public AccountAdapter(List<AccountBean> datas) {
        mDatas = datas;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
    }

    @Override
    public ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new ViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccountBean bean = mDatas.get(position);
                holder.mTvServer.setText(bean.server);
                holder.mTvName.setText("账号: "+bean.name);
                holder.mTvPwd.setText("密码: "+bean.pwd);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_server)
        TextView mTvServer;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_pwd)
        TextView mTvPwd;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
