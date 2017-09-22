package com.hxyd.dyt.widget.adapter.purplestar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.purplestar.modle.entity.DeviceLocation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/9/1.
 */

public class DeviceLocationAdapter extends RecyclerView.Adapter<DeviceLocationAdapter.DeviceLocationViewHolder> {


    private Context mContext;
    private List<DeviceLocation.UserListBean> userList;

    private DeviceLocationCallback callback;

    public DeviceLocationAdapter(Context context, List<DeviceLocation.UserListBean> userList, DeviceLocationCallback callback) {
        this.mContext = context;
        this.userList = userList;
        this.callback = callback;
    }

    @Override
    public DeviceLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.devicelocation_item, parent, false);
        return new DeviceLocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeviceLocationViewHolder holder, final int position) {
        if (null != userList && userList.size() > 0) {
            holder.tv.setText(userList.get(position).getUserChnName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onCallback(userList.get(position).getUserChnName(),
                                userList.get(position).getUserAccount());
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return null != userList ? userList.size() : 0;
    }

    class DeviceLocationViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.device_location_item_tv)
        public TextView tv;

        public DeviceLocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public  interface DeviceLocationCallback {
        void onCallback(String userChnName, String equipmentId);
    }
}
