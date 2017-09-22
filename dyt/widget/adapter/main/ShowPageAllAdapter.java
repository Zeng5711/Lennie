package com.hxyd.dyt.widget.adapter.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.widget.holder.main.ShowPageAllItem;

/**
 * Created by win7 on 2017/9/7.
 */

public class ShowPageAllAdapter extends RecyclerView.Adapter {

    public ShowPageAllAdapter(){

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_page_all_time,null);
        return new ShowPageAllItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
