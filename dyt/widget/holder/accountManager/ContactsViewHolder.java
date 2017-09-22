package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/5/17.
 */

public class ContactsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_index)
    public TextView tvIndex;
    @BindView(R.id.tv_name)
    public TextView tvName;
    @BindView(R.id.rl_view)
    public RelativeLayout rlView;
    @BindView(R.id.iv_avatar)
    public View view;

    public ContactsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
