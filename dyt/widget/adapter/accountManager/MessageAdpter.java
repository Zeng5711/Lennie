package com.hxyd.dyt.widget.adapter.accountManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.jpush.modle.entity.MessageListBean;
import com.hxyd.dyt.widget.holder.accountManager.FooterViewHolder;
import com.hxyd.dyt.widget.holder.accountManager.MessageViewHolder;

import java.util.List;

/**
 * Created by win7 on 2017/3/14.
 */

public class MessageAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MessageListBean> mList;
    private int footerView = 1;
    private int footerViewStart = 0;

    public MessageAdpter(Context context, List<MessageListBean> list) {
        this.mContext = context;
        mList = list;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MessageViewHolder) {
            if (mList.size() > 0) {
                MessageListBean m = mList.get(position);
                ((MessageViewHolder) holder).data.setText(m.getPullTime());
                ((MessageViewHolder) holder).name.setText(m.getCustomerName());
                ((MessageViewHolder) holder).message.setText(m.getContent());
            }
        } else if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).status(footerViewStart);
        }


    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return footerView;
        }
        return super.getItemViewType(position);
    }
}
