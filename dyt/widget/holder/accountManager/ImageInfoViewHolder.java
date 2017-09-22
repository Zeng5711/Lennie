package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/3/14.
 */

public class ImageInfoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.im_od_ID_1)
    public ImageView ID_1;
    @BindView(R.id.im_od_ID_2)
    public ImageView ID_2;

    @BindView(R.id.im_od_DD_1)
    public ImageView DD_1;
    @BindView(R.id.im_od_DD_2)
    public ImageView DD_2;

    @BindView(R.id.im_od_JD_1)
    public ImageView JD_1;
    @BindView(R.id.im_od_JD_2)
    public ImageView JD_2;

    @BindView(R.id.im_od_XD_1)
    public ImageView XD_1;
    @BindView(R.id.im_od_XD_2)
    public ImageView XD_2;

    @BindView(R.id.im_od_OD_1)
    public ImageView OD_1;
    @BindView(R.id.im_od_OD_2)
    public ImageView OD_2;

    @BindView(R.id.im_od_VD_1)
    public ImageView VD_1;
    @BindView(R.id.im_od_VD_2)
    public ImageView VD_2;



    public ImageInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        ID_1.setDrawingCacheEnabled(true);
        ID_2.setDrawingCacheEnabled(true);

        DD_1.setDrawingCacheEnabled(true);
        DD_2.setDrawingCacheEnabled(true);

        JD_1.setDrawingCacheEnabled(true);
        JD_2.setDrawingCacheEnabled(true);

        XD_1.setDrawingCacheEnabled(true);
        XD_2.setDrawingCacheEnabled(true);

        OD_1.setDrawingCacheEnabled(true);
        OD_2.setDrawingCacheEnabled(true);

        VD_1.setDrawingCacheEnabled(true);
        VD_2.setDrawingCacheEnabled(true);


    }
}
