package com.hxyd.dyt.widget.holder.accountManager;


import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/4/5.
 */

public class ImageItemViewHolder extends ViewHolder {
    @BindView(R.id.iamge_item_del)
    public ImageView ivDelte;
    @BindView(R.id.iamge_item_image)
    public ImageView image;
    @BindView(R.id.iamge_item_state)
    public TextView tvState;
    @BindView(R.id.iamge_item_num)
    public TextView tvNum;

    public ImageItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
