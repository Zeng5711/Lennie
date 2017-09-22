package com.hxyd.dyt.widget.holder.purplestar;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/8/22.
 */

public class CallPoliceScreenViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.screen_image)
    public ImageView iamge;

    @BindView(R.id.screen_go)
    public ImageView go;

    @BindView(R.id.screen_title)
    public TextView title;

    @BindView(R.id.screen_num)
    public TextView num;

    @BindView(R.id.screen_v)
    public View v;

    public CallPoliceScreenViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
