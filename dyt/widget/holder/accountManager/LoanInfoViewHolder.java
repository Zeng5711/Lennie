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

public class LoanInfoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_od_loanPurpose)
    public TextView loanPurpose;

    @BindView(R.id.tv_od_loanMount)
    public TextView loanMount;

    @BindView(R.id.tv_od_productTypes)
    public TextView productTypes;

    @BindView(R.id.tv_od_repaymentMethods)
    public TextView repaymentMethods;

    @BindView(R.id.tv_od_repaymentPeriodhods)
    public TextView repaymentPeriodhods;

    public LoanInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
