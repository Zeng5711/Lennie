package com.hxyd.dyt.widget.adapter.accountManager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.LoanListBean;
import com.hxyd.dyt.accountManager.view.OrderDefaultActivity;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.widget.holder.accountManager.FooterViewHolder;
import com.hxyd.dyt.widget.holder.accountManager.OrderViewHolder;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by win7 on 2017/3/14.
 */

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<LoanListBean> mLoanListBeen;
    private int footerView = 1;
    private int footerViewStart = 0;

    public OrderAdapter(Context context, List<LoanListBean> loanListBeen) {
        this.mContext = context;
        this.mLoanListBeen = loanListBeen;
    }

    public void setShowRefresh(int start) {
        this.footerViewStart = start;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == footerView) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.footer_view, parent, false);
            FooterViewHolder viewHolder = new FooterViewHolder(view);
            return viewHolder;
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        OrderViewHolder viewHolder = new OrderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OrderViewHolder) {
            ((OrderViewHolder) holder).mOrderLoaninfoId.setText(mLoanListBeen.get(position).getOrderNo());
            ((OrderViewHolder) holder).mOrderFlowState.setText(mLoanListBeen.get(position).getProcessStatus());
            ((OrderViewHolder) holder).mOrderUsername.setText(mLoanListBeen.get(position).getCustomerName());
            ((OrderViewHolder) holder).mOrderCarframeNo.setText(mLoanListBeen.get(position).getLicencePlate());
            ((OrderViewHolder) holder).mOrderCarModel.setText(mLoanListBeen.get(position).getCarmodel());
            String isLocked = mLoanListBeen.get(position).getIsLocked();
            if (isLocked.equals("Y")) {
                ImageloadTools.load(mContext, R.mipmap.order_item_up, ((OrderViewHolder) holder).mOrderState);
            } else {
                ImageloadTools.load(mContext, R.mipmap.order_item_on, ((OrderViewHolder) holder).mOrderState);
            }
            ((OrderViewHolder) holder).rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OrderDefaultActivity.class);
                    intent.putExtra("isLocked", mLoanListBeen.get(position).getIsLocked());
                    intent.putExtra("orderNo", mLoanListBeen.get(position).getOrderNo());
                    mContext.startActivity(intent);
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder)holder).status(footerViewStart);
        }
    }

    @Override
    public int getItemCount() {
        return null == mLoanListBeen ? 0 : mLoanListBeen.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        Logger.e("getItemViewType ==== Footer >" + position);
        if (position + 1 == getItemCount()) {
            return footerView;
        }
        return super.getItemViewType(position);
    }
}
