package com.hxyd.dyt.widget.holder.assessment;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/5/25.
 */

public class SeeCarDataViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_licencePlate)
    public TextView tv_licencePlate;

    @BindView(R.id.tv_assessment)
    public TextView tv_assessment;

    @BindView(R.id.tv_brand)
    public TextView tv_brand;

    @BindView(R.id.tv_carFuel)
    public TextView tv_carFuel;

    @BindView(R.id.tv_car_color)
    public TextView tv_car_color;

    @BindView(R.id.tv_evaluationPrice)
    public TextView tv_evaluationPrice;

    @BindView(R.id.tv_mileage)
    public TextView tv_mileage;

    public SeeCarDataViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
