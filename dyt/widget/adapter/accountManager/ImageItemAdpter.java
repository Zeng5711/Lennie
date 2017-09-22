package com.hxyd.dyt.widget.adapter.accountManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.common.uitl.SharedPrefsUtil;
import com.hxyd.dyt.widget.holder.accountManager.ImageItemViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by win7 on 2017/4/5.
 */

public class ImageItemAdpter extends RecyclerView.Adapter<ImageItemViewHolder> {

    private List<String> mList;
    private Context mContext;
    private int mState = -1;
    private boolean isSingle;

    public static final int OK = 0;
    public static final int ERR = 1;
    public static final int UP = 2;

    public ImageItemAdpter(Context context, List<String> list, boolean isSingle) {
        this.mContext = context;
        this.mList = list;
//        this.mState = state;
        this.isSingle = isSingle;
    }

    @Override
    public ImageItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_viewpager, parent, false);
        return new ImageItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageItemViewHolder holder, final int position) {
        if (mList != null && !mList.isEmpty()) {
            ImageloadTools.loadShape(mContext, mList.get(position), holder.image);
            holder.image.setDrawingCacheEnabled(true);
            holder.tvNum.setText((position + 1) + "/" + mList.size());
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击放大
                    ArrayList<String> list = (ArrayList<String>) mList;
                    if (isSingle) {
                        EventBus.getDefault().post(new BusEvent(BusEvent.SHOW_SPACE_IMAGE_DETAIL_ACTIVITY_SINGLE, position, list, holder.image));
                    } else {
                        EventBus.getDefault().post(new BusEvent(BusEvent.SHOW_SPACE_IMAGE_DETAIL_ACTIVITY, position, list, holder.image));
                    }

                }
            });

            boolean falg = SharedPrefsUtil.getValue(null, Constant.ISORDERDEFAULT, false);

            mState = SharedPrefsUtil.getValue(null, mList.get(position), -1);
            switch (mState) {
                case OK:
                    holder.tvState.setText("上传完成");
                    holder.ivDelte.setVisibility(View.VISIBLE);
                    break;
                case UP:
                    holder.tvState.setText("上传中");
                    holder.ivDelte.setVisibility(View.GONE);
                    break;
                case ERR:
                    holder.tvState.setText("上传失败");
                    holder.ivDelte.setVisibility(View.VISIBLE);
                    break;
                default:
                    holder.tvState.setVisibility(View.GONE);
                    holder.ivDelte.setVisibility(View.GONE);
                    break;
            }

        } else {
            holder.tvState.setVisibility(View.GONE);
            holder.ivDelte.setVisibility(View.GONE);
        }

        if (!isSingle) {
            holder.tvState.setVisibility(View.GONE);
            holder.ivDelte.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 1;
        }
        return mList.isEmpty() ? 1 : mList.size();
    }
}
