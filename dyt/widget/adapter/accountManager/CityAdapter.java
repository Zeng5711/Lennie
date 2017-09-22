package com.hxyd.dyt.widget.adapter.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.Province;
import com.hxyd.dyt.widget.holder.accountManager.CityViewHolder;

import java.util.ArrayList;

/**
 * Created by win7 on 2017/5/22.
 */

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Province> list;
    private onClickCallback onClickCallback;
    private View view;
    private View view2;
    private long clickTime = 0;
    private int selectPosition = -1;

    public CityAdapter(ArrayList<Province> list, onClickCallback callback) {
        this.list = list;
        this.onClickCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CityViewHolder view = new CityViewHolder(inflater.inflate(R.layout.city, null));
        return view;
    }

    @Override
    public void onBindViewHolder(final  RecyclerView.ViewHolder holder, final int position) {
        if (list != null && !list.isEmpty()) {
            ((CityViewHolder) holder).textView.setText(list.get(position).getCityName());
            RelativeLayout rl = ((CityViewHolder) holder).rl;
            if (list.get(position).isCheck()) {
                ((CityViewHolder) holder).view.setBackgroundResource(R.color.jzg_color_view);
                rl.setBackgroundResource(R.color.jzg_color);
            } else {
                ((CityViewHolder) holder).view.setBackgroundResource(R.color.default_whit);
                rl.setBackgroundResource(R.color.default_whit);
            }
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((System.currentTimeMillis() - clickTime) > 1000) {
                        clickTime = System.currentTimeMillis();
                        if (onClickCallback != null) {


                            if (selectPosition != -1 && view != null && view2 != null) {
                                list.get(selectPosition).setCheck(false);
                                view.setBackgroundResource(R.color.default_whit);
                                view2.setBackgroundResource(R.color.default_whit);
                                CityAdapter.this.notifyItemChanged(selectPosition);
                            }
                            selectPosition = position;
                            view = v;
                            view2 = ((CityViewHolder) holder).view;
                            ((CityViewHolder) holder).view.setBackgroundResource(R.color.jzg_color_view);
                            list.get(position).setCheck(true);
                            v.setBackgroundResource(R.color.jzg_color);
                            onClickCallback.onClick(v, list.get(position));
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface onClickCallback {
        void onClick(View v, Province cardBean);
    }
}
