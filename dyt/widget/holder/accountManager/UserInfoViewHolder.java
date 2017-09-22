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

public class UserInfoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_od_name)
    public TextView name;

    @BindView(R.id.tv_od_sex)
    public TextView sex;

    @BindView(R.id.tv_od_ID)
    public TextView ID;

//    @BindView(R.id.tv_od_marriage)
//    public TextView marriage;

//    @BindView(R.id.tv_od_education)
//    public TextView education;

    @BindView(R.id.tv_od_phone)
    public TextView phone;

//    @BindView(R.id.tv_od_livingPlaceDistrict)
//    public TextView livingPlaceDistrict;

//    @BindView(R.id.tv_od_companyName)
//    public TextView companyName;
//
//    @BindView(R.id.tv_od_companyPhone)
//    public TextView companyPhone;

//    @BindView(R.id.tv_od_companyPlaceDistrict)
//    public TextView companyPlaceDistrict;

    public UserInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
