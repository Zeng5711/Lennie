package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/3/14.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder {

    public TextView mOrderLoaninfoId, mOrderFlowState,
            mOrderUsername, mOrderCarframeNo,
            mOrderCarModel, mOrderDetails, mOrderflow;
    public ImageView mOrderState;
    public RelativeLayout rl;

    public OrderViewHolder(View itemView) {
        super(itemView);
        mOrderLoaninfoId = (TextView) itemView.findViewById(R.id.tv_order_loaninfoId);
        mOrderFlowState = (TextView) itemView.findViewById(R.id.tv_order_flow_state);
        mOrderflow = (TextView) itemView.findViewById(R.id.tv_order_flow);
        mOrderUsername = (TextView) itemView.findViewById(R.id.tv_order_username);
        mOrderCarframeNo = (TextView) itemView.findViewById(R.id.tv_order_carframeNo);
        mOrderCarModel = (TextView) itemView.findViewById(R.id.tv_order_carModel);
        mOrderDetails = (TextView) itemView.findViewById(R.id.tv_order_details);
        mOrderState = (ImageView) itemView.findViewById(R.id.im_order_state);
        rl = (RelativeLayout) itemView.findViewById(R.id.order_rl_go);

    }


}
