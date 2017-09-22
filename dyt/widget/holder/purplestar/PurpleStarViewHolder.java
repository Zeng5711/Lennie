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

public class PurpleStarViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.purple_store)
    public TextView mStore;

    @BindView(R.id.purple_total)
    public TextView mTotal;

    @BindView(R.id.purple_on_line)
    public TextView mOnLine;

    @BindView(R.id.purple_off_line)
    public TextView mOffLine;

    @BindView(R.id.purple_invalid)
    public TextView mInvalid;

    @BindView(R.id.purple_image)
    public ImageView mImage;


    public PurpleStarViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
