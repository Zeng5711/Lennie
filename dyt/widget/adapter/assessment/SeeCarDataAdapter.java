package com.hxyd.dyt.widget.adapter.assessment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.assessment.modle.entity.CarData;
import com.hxyd.dyt.assessment.view.fragment.SeeCarDataFragment;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.ImageItem;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.widget.holder.assessment.SeeCarDataViewHolder;
import com.hxyd.dyt.widget.holder.assessment.SeeCarImageViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by win7 on 2017/5/25.
 */

public class SeeCarDataAdapter extends RecyclerView.Adapter {

    private int TYPE_VIEW_DATA = 0;
    private int TYPE_VIEW_IMAGE = 1;
    private Context mContext;
    private CarData carData;

    public SeeCarDataAdapter(Context context) {
        this.mContext = context;
    }

    public void setCarData(CarData carData) {
        this.carData = carData;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view;
        if (viewType == TYPE_VIEW_DATA) {
            view = LayoutInflater.from(mContext).inflate(R.layout.see_car_data_item, parent, false);
            viewHolder = new SeeCarDataViewHolder(view);
        } else if (viewType == TYPE_VIEW_IMAGE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.see_car_image_item, parent, false);
            viewHolder = new SeeCarImageViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            if (position == TYPE_VIEW_DATA) {
                if (carData != null) {
                    CarData.BaseInfoBean bean = carData.getBaseInfo();
                    SeeCarDataViewHolder carDataViewHolder = (SeeCarDataViewHolder) holder;
                    carDataViewHolder.tv_licencePlate.setText(bean.getLicencePlate());
                    carDataViewHolder.tv_assessment.setText(bean.getAssess());
                    carDataViewHolder.tv_brand.setText(bean.getBrand());
                    carDataViewHolder.tv_carFuel.setText(bean.getCarFuel());
                    carDataViewHolder.tv_car_color.setText(bean.getCarColor());
                    carDataViewHolder.tv_evaluationPrice.setText(bean.getEvaluationPrice());
                    carDataViewHolder.tv_mileage.setText(bean.getMileage());

                }

            } else if (position == TYPE_VIEW_IMAGE) {
                SeeCarImageViewHolder imageViewHolder = (SeeCarImageViewHolder) holder;
                if (carData != null) {
                    List<String> imageList = carData.getImageInfo();
                    List<View> list = imageViewHolder.getImageItemViews(imageList.size(), mContext);
                    for (int i = 0; i < list.size(); i++) {
                        View view = list.get(i);

                        ImageView imageView = (ImageView) view.findViewById(R.id.item_image_iv);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        String url = Constant.BASE_URL + imageList.get(i);
                        ImageloadTools.load(mContext, url, imageView);
                        imageView.setTag(i);

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (v.getTag() != null) {
                                    int tag = Integer.parseInt(v.getTag().toString());
                                    ImageItem item = new ImageItem();
                                    item.stype = "";
                                    item.position = tag;
                                    item.list = getList(carData.getImageInfo());
                                    item.currentItem = tag;
                                    item.isShowDelete = false;
                                    EventBus.getDefault().post(new BusEvent(BusEvent.SEE_CAR_DATA_IMAGE, item));
                                }
                            }
                        });

                        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        imageViewHolder.see_car_image_ll.addView(list.get(i), layoutParams);
                    }
                }
            }

        }
    }

    private List<String> getList(List<String> lsit) {
        List<String> newList = new ArrayList<>();
        if (lsit != null) {
            for (String url : lsit) {
                newList.add(Constant.BASE_URL + url);
            }
        }
        return newList;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if (position % 2 == 0) {
            type = TYPE_VIEW_DATA;
        } else if (position % 2 == 1) {
            type = TYPE_VIEW_IMAGE;
        }
        return type;
    }
}
