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

public class ContainerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.container_name)
    public TextView container_name;

    @BindView(R.id.container_plate)
    public TextView container_plate;

    @BindView(R.id.containe_image)
    public ImageView containe_image;

//    @BindView(R.id.container_munber)
//    public TextView container_munber;

    @BindView(R.id.container_type)
    public TextView container_type;

    @BindView(R.id.container_parameter)
    public TextView container_parameter;


    @BindView(R.id.container_date)
    public TextView container_date;


    @BindView(R.id.container_state)
    public TextView container_state;


    public ContainerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
