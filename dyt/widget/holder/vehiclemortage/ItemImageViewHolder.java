package com.hxyd.dyt.widget.holder.vehiclemortage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.widget.ItemImageView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by win7 on 2017/9/9.
 */

public class ItemImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_image_iv)
    public ItemImageView imageView;

    @BindView(R.id.item_image_tv)
    public TextView tv;


    public ItemImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
