package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by win7 on 2017/4/5.
 */

public class ImageInfoOrderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_new_rv)
    public RecyclerView recyclerView;

    public ImageInfoOrderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
