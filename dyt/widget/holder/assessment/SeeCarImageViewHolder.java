package com.hxyd.dyt.widget.holder.assessment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.hxyd.dyt.R;
import com.hxyd.dyt.widget.ImageItemLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/5/25.
 */

public class SeeCarImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.see_car_image_ll)
    public ImageItemLinearLayout see_car_image_ll;

    public SeeCarImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public List<View> getImageItemViews(int item, Context context) {
        List<View> list = new ArrayList<View>();
        for (int i = 0; i < item; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, null);
            list.add(view);
        }
        return list;
    }
}
