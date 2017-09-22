package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/4/6.
 */

public class ImageItemNewFoolViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageinfo_bt_previous_step)
    public Button previousStep;
    @BindView(R.id.imageinfo_bt_submit)
    public Button submit;

    public ImageItemNewFoolViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
