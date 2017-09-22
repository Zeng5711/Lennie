package com.hxyd.dyt.widget.holder.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/3/30.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.footer_view_refresh)
    LinearLayout ll;
    @BindView(R.id.footer_view_tv)
    TextView tv;

    public FooterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void status(int status){
        switch (status){
            case 0:
                ll.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
            break ;
            case 1:
                ll.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                break;
            case 2:
                ll.setVisibility(View.VISIBLE);
                tv.setVisibility(View.GONE);
                break;
        }
    }
}
