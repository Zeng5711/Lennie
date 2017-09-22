package com.hxyd.dyt.widget.adapter.vehiclemortage;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.entity.ImageItem;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.widget.holder.vehiclemortage.ItemImageViewHolder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * Created by win7 on 2017/9/9.
 */

public class MortageMaterialAdapter extends RecyclerView.Adapter {

    private int size = 4;
    private Dialog mDialog;
    private Context mContext;
    private int TAG_FALG = 0;
    private int TAG_CAR_ASS = 1;
    public SelectListener selectListener;
    private List<String> mList = new ArrayList<>();

    public MortageMaterialAdapter(Context context, SelectListener listener) {
        this.mContext = context;
        this.selectListener = listener;
    }

    public void setCarAssessmentData() {
        size = 10;
        for (int i = 0; i < size; i++) {
            mList.add("");
        }
        TAG_FALG = TAG_CAR_ASS;
        notifyDataSetChanged();
    }

    public void setData(String url) {
        mList.add(url);
        notifyDataSetChanged();
    }

    public void setData(int index, String url) {
//            mList.add(index, url);
        mList.set(index, url);
        notifyDataSetChanged();
    }

    public void setData(List<String> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void deleData(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    public void deleData(String url) {
        mList.remove(url);
        notifyDataSetChanged();
    }

    public int getDataSize() {
        return null == mList ? 0 : mList.size();
    }

    public String  getAppDataSize(){
        int index = -1;
        for(int i = 0 ;i<size;i++){
            if(TextUtils.isEmpty(mList.get(i))){
                index = i;
                break;
            }
        }
        return  getItemName(index);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_, null);
        return new ItemImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ItemImageViewHolder imageViewHolder = (ItemImageViewHolder) holder;


//        if (TAG_FALG != TAG_CAR_ASS) {
        if (position < mList.size()) {
            if (TextUtils.isEmpty(mList.get(position))) {
                ImageloadTools.load(mContext, R.mipmap.item_image, imageViewHolder.imageView);
            } else {
                ImageloadTools.load(mContext, mList.get(position), imageViewHolder.imageView);
            }
        } else {
            ImageloadTools.load(mContext, R.mipmap.item_image, imageViewHolder.imageView);
        }
//        }

        imageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TAG_FALG != TAG_CAR_ASS) {
                    if ((position + 1) > mList.size()) {
                        showDialog(1, getName(position), position);
                    } else {
                        if (selectListener != null) {
                            selectListener.showImageDetail((ArrayList<String>) mList, position, position, true);
                        }
                    }
                } else {
                    String url = mList.get(position);
                    if (TextUtils.isEmpty(url)) {
                        showDialog(1, getName(position), position);
                    } else {
                        if (selectListener != null) {
                            selectListener.showImageDetail((ArrayList<String>) mList, position, position, true);
                        }
                    }
                }

            }
        });

        if (TAG_FALG == TAG_CAR_ASS) {
            imageViewHolder.tv.setText(getItemName(position));
        }


    }

    private String getItemName(int position){
        String t = "";
        switch (position) {
            case 0:
                t = "天窗";
                break;
            case 1:
                t = "座椅";
                break;
            case 2:
                t = "导航";
                break;
            case 3:
                t = "客户与车合照";
                break;
            case 4:
                t = "车辆仪表图";
                break;
            case 5:
                t = "车辆车尾图";
                break;
            case 6:
                t = "车辆前排配置";
                break;
            case 7:
                t = "车辆后排配置";
                break;
            case 8:
                t = "车尾箱图";
                break;
            case 9:
                t = "车辆铭牌";
                break;
            default:
                break;
        }
        return t;
    }

    @Override
    public int getItemCount() {
        if (TAG_FALG != TAG_CAR_ASS) {
            return mList.size() == 0 ? 1 : mList.size() + 1;
        } else {
            return size;
        }
    }


    public interface SelectListener {
        void showLmageselector(int num, String name, int position);

        void showImageDetail(ArrayList<String> list, int currentItem, int position, boolean isShowDelete);

        void showCamer(String name, int position);
    }

    private void showDialog(final int num, final String name, final int position) {
        mDialog = new Dialog(mContext, R.style.dialog_bottom_full);//.create();
        mDialog.show();

        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.popupAnimation);
        View view = View.inflate(mContext, R.layout.bottom_dailog, null);


        view.findViewById(R.id.iv_userinfo_takepic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener != null) {
                    selectListener.showLmageselector(num, name, position);
                }
                mDialog.dismiss();
            }
        });

        view.findViewById(R.id.iv_userinfo_choosepic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener != null) {
                    selectListener.showCamer(name, position);
                }
                mDialog.dismiss();
            }
        });

        view.findViewById(R.id.iv_userinfo_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
    }

    public String getName(int i) {
        if (i + 1 < 10) {
            return "0" + (1 + i);
        } else {
            return i + "";
        }
    }

}
