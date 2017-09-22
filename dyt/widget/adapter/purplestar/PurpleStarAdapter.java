package com.hxyd.dyt.widget.adapter.purplestar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.purplestar.modle.entity.PurpleStar;
import com.hxyd.dyt.purplestar.view.ContainerActivity;
import com.hxyd.dyt.widget.holder.purplestar.PurpleStarViewHolder;

import java.util.List;

/**
 * Created by win7 on 2017/8/22.
 */

public class PurpleStarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<PurpleStar.ReturnListBean> returnList;

    public PurpleStarAdapter(Context context, List<PurpleStar.ReturnListBean> returnList) {
        mContext = context;
        this.returnList = returnList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.purple_star_view, parent, false);
        return new PurpleStarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (returnList.size() > 0) {

            final PurpleStar.ReturnListBean bean = returnList.get(position);
            PurpleStarViewHolder ps = (PurpleStarViewHolder) holder;

            ps.mStore.setText(bean.getOrgName());
            ps.mOnLine.setText("在线 | " + bean.getOnline());
            ps.mOffLine.setText("离线 | " + bean.getOffline());
            ps.mInvalid.setText("失效 | " + bean.getInvalid());
            ps.mTotal.setText("总数（" + bean.getSum() + "）");

            ps.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ContainerActivity.class);
                    intent.putExtra("showData", true);
                    intent.putExtra("orgId", bean.getOrgId());
                    intent.putExtra("orgName",bean.getOrgName());
                    intent.putExtra("orgName",bean.getOrgName());
                    mContext.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return returnList != null ? returnList.size() : 0;
    }
}
