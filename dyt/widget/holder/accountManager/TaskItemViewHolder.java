package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/9/8.
 */

public class TaskItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name)
    public TextView name;

    @BindView(R.id.car_num)
    public TextView carNum;

    @BindView(R.id.model)
    public TextView model;


    public TaskItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
