package com.hxyd.dyt.widget.adapter.purplestar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.purplestar.modle.entity.Container;
import com.hxyd.dyt.purplestar.view.GPSActivity;
import com.hxyd.dyt.widget.holder.accountManager.FooterViewHolder;
import com.hxyd.dyt.widget.holder.purplestar.ContainerViewHolder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2017/8/22.
 */

public class ContainerAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<Container.ReturnListBean> returnList = new ArrayList<>();

    private int footerView = 1;
    private int footerViewStart = 0;

    public ContainerAdapter(Context context) {
        mContext = context;
    }

    private boolean isCallPolice = false;

    public void setCallPolice(boolean b) {
        this.isCallPolice = b;
    }


//    public void clearData(){
//        if (this.returnList != null) {
//            this.returnList.clear();
//        }
//    }

    public void setShowRefresh(int start) {
        this.footerViewStart = start;
    }

    public void setData(List<Container.ReturnListBean> returnList) {
        if (this.returnList != null) {
            this.returnList.clear();
        }
        this.returnList.addAll(returnList);

//        if (this.returnList != null && this.returnList.size() > 0) {
//            notifyItemChanged(1);
//        } else {
        notifyDataSetChanged();
//        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == footerView) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.footer_view, parent, false);
            FooterViewHolder viewHolder = new FooterViewHolder(view);
            return viewHolder;
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.container_view, parent, false);
        return new ContainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ContainerViewHolder) {
            if (returnList != null && returnList.size() > 0) {
                final Container.ReturnListBean bean = returnList.get(position);
                ContainerViewHolder cv = (ContainerViewHolder) holder;

                cv.container_name.setText(bean.getName());
                cv.container_plate.setText(bean.getCarNumber());
                if (bean.getType() == 1) {
                    cv.containe_image.setImageResource(R.mipmap.alarm_info_icon_wireless);
                }

//            cv.container_munber.setText();
                cv.container_type.setText(bean.getDriveStatus());

                String orgName = bean.getOrgName();
                String acc = "ACC";
                int accState = bean.getAccState();
                if (accState == 0) {
                    acc += " 关";
                } else if (accState == 1) {
                    acc += " 开";
                }
                String speed = bean.getSpeed() + "KM/H";
                cv.container_type.setTextColor(bean.getSpeed() > 0 ? Color.GREEN : Color.BLUE);
                cv.container_parameter.setText(orgName + "|" + acc + " " + speed);


                String time = bean.getLocation_time();
                String status = bean.getStatus();
                cv.container_date.setText(time + " " + status);


                cv.container_state.setText(bean.getWarning_name());
//                if (getColor(bean.getColor()) != -1) {
                cv.container_state.setTextColor(getColor(bean.getColor()));
//                }

                cv.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, GPSActivity.class);
                        intent.putExtra("imeiId", bean.getImeiId());
                        intent.putExtra("orgName", bean.getOrgName());
                        intent.putExtra("isCallPolice", isCallPolice);
                        intent.putExtra("type", bean.getType());
                        mContext.startActivity(intent);
                    }
                });
            }
        } else if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).status(footerViewStart);
        }


    }

    @Override
    public int getItemCount() {
        return returnList != null ? returnList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        Logger.e("getItemViewType ==== Footer >" + position);
        if (position + 1 == getItemCount()) {
            return footerView;
        }
        return super.getItemViewType(position);
    }

    private int getColor(String color) {
        int c = -1;
        if ("0".equals(color)) {
            c = Color.GRAY;
        } else if ("1".equals(color)) {
            c = Color.RED;
        } else if ("2".equals(color)) {
            c = Color.GREEN;
        } else if ("3".equals(color)) {
            c = Color.YELLOW;
        } else if ("4".equals(color)) {
            c = Color.BLUE;
        }
        return c;
    }
}
