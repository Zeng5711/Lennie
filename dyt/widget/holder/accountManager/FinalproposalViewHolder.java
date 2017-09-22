package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by win7 on 2017/4/20.
 */

public class FinalproposalViewHolder extends RecyclerView.ViewHolder  {

    @BindView(R.id.final_proposal_money)
    public TextView audiAmount;

    @BindView(R.id.final_proposal_repayment_cycle)
    public TextView audiTerm;

    @BindView(R.id.final_proposal_product)
    public TextView audiProduct;

    public FinalproposalViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
