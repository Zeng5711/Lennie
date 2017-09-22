package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/5/17.
 */

public class JgzErrorViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.jzg_err_im)
    public ImageView imError;

    @BindView(R.id.jzg_err_tv)
    public TextView tvError;

    public JgzErrorViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
