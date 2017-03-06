package com.zqx.mypwd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by ZhangQixiang on 2017/2/8.
 */
public abstract class BaseRecycleAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {


    protected List mDatas;
    protected Context mContext;

    public <T>BaseRecycleAdapter(List<T> datas) {
        mDatas = datas;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext().getApplicationContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        return getViewHolder(view);
    }

    protected abstract int getItemLayout();

    protected abstract VH getViewHolder(View view);

    @Override
    public int getItemCount() {
        return mDatas == null?0:mDatas.size();
    }

}
