package com.hxyd.dyt.widget.holder.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/9/7.
 */

public class MainViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.mian_item_im)
    public ImageView im;

    @BindView(R.id.mian_item_tv)
    public TextView tv;


    public MainViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
