package com.hxyd.dyt.widget.holder.accountManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxyd.dyt.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/4/10.
 */

public class ImageInfoNewsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_item_title)
    public TextView title;
    @BindView(R.id.image_item_ll)
    public LinearLayout linearLayout;

    public ImageInfoNewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public View getImageItemView(Context context){
        return  LayoutInflater.from(context).inflate(R.layout.item_image, null);
    }

    public List<View> getImageItemViews(int item,Context context){
        List<View> list = new ArrayList<View>();
        for (int i=0;i<item;i++){
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, null);
            list.add(view);
        }
        return list;
    }
}
