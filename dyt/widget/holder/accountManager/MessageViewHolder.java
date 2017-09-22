package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/3/14.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.im_message)
    public ImageView imageView;
    @BindView(R.id.tv_message_data)
    public TextView data;
    @BindView(R.id.tv_message_name)
    public TextView name;
    @BindView(R.id.tv_message)
    public TextView message;

    public MessageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
