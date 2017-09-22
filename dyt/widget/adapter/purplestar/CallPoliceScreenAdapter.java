package com.hxyd.dyt.widget.adapter.purplestar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.purplestar.modle.entity.AlarmScreen;
import com.hxyd.dyt.widget.holder.purplestar.CallPoliceScreenViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2017/8/22.
 */

public class CallPoliceScreenAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private ScreenCallback mCallback;
    private List<AlarmScreen.ReturnListBean> returnList;

    public CallPoliceScreenAdapter(Context context, ScreenCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
    }

    public void setData(List<AlarmScreen.ReturnListBean> list) {
        if (returnList != null) {
            returnList.clear();
        }
        returnList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.call_police_screen, parent, false);
        return new CallPoliceScreenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        CallPoliceScreenViewHolder cs = (CallPoliceScreenViewHolder) holder;
        if (position == 0) {
            cs.iamge.setImageResource(R.mipmap.all_alarm);
            cs.title.setText("所有报警");
            if (returnList != null) {
                cs.num.setText(returnList.get(0).getSum() + "");
            }
        } else if (position == 1) {
            cs.iamge.setImageResource(R.mipmap.alarm_displacement);
            cs.title.setText("位移报警");
            if (returnList != null) {
                cs.num.setText(returnList.get(0).getDisplacement() + "");
            }
        } else if (position == 2) {
            cs.iamge.setImageResource(R.mipmap.alarm_power_off);
            cs.title.setText("断点报警");
            if (returnList != null) {
                cs.num.setText(returnList.get(0).getOutages() + "");
            }
        } else if (position == 3) {
            cs.iamge.setImageResource(R.mipmap.alarm_cross_border);
            cs.title.setText("越界报警");
            cs.v.setVisibility(View.GONE);
            if (returnList != null) {
                cs.num.setText(returnList.get(0).getCrossing() + "");
            }
        }

        cs.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onCallback(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 4;
    }


    public static interface ScreenCallback {
        void onCallback(int position);
    }
}
