package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/5/22.
 */

public class CityViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_city)
    public TextView textView;
    @BindView(R.id.rl_view)
    public RelativeLayout rl;
    @BindView(R.id.iv_avatar)
    public View view;

    public CityViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
