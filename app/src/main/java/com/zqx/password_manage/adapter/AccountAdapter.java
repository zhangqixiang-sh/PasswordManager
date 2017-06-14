package com.zqx.password_manage.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.zqx.password_manage.R;
import com.zqx.password_manage.global.GlobalData;
import com.zqx.password_manage.model.bean.AccountBean;
import com.zqx.password_manage.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        holder.mTvName.setText("账号: " + bean.name);
        if (GlobalData.isHidePwd()) {
            holder.mTvPwd.setText("密码: *********");
        } else {
            holder.mTvPwd.setText("密码: " + bean.pwd);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        @BindView(R.id.tv_server)
        TextView mTvServer;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_pwd)
        TextView mTvPwd;
        private ClipboardManager mCm;


        ViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            ButterKnife.bind(this, view);
            mCm = ((ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE));

        }

        @OnClick(R.id.iv_copy_pwd)
        public void onCopyClick(View view) {
            AccountBean bean = mDatas.get(getAdapterPosition());
            mCm.setText(bean.pwd);
            ToastUtil.show("已复制密码到剪贴板");
        }

    }

}
