package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/3/14.
 */

public class VehicleInfoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_od_licencePlate)
    public TextView licencePlate;

    @BindView(R.id.tv_od_brand)
    public TextView brand;

//    @BindView(R.id.tv_od_produceDate)
//    public TextView produceDate;

    @BindView(R.id.tv_od_carFrameNo)
    public TextView carFrameNo;

//    @BindView(R.id.tv_od_carModel)
//    public TextView carModel;

    @BindView(R.id.tv_od_mileage)
    public TextView mileage;

//    @BindView(R.id.tv_od_assess)
//    public TextView assess;

    @BindView(R.id.tv_od_evaluationPrice)
    public TextView evaluationPrice;

//    @BindView(R.id.tv_od_surfaceConditionDescription)
//    public TextView surfaceConditionDescription;

    @BindView(R.id.tv_od_carEngineNo)
    public TextView carEngineNo;

    @BindView(R.id.tv_od_carColor)
    public TextView carColor;

//    @BindView(R.id.tv_od_carFuel)
//    public TextView carFuel;

//    @BindView(R.id.tv_od_certificateDate)
//    public TextView certificateDate;

//    @BindView(R.id.tv_od_registerDate)
//    public TextView registerDate;

    public VehicleInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
