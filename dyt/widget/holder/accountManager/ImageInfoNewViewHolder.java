package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.uitl.ImageloadTools;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/4/5.
 */

public class ImageInfoNewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iamge_item_id)
    public TextView tvID;
    @BindView(R.id.iamge_item_viewPager)
    public RecyclerView viewPager;

    public ImageInfoNewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setGone() {

    }

}
