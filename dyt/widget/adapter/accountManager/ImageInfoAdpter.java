package com.hxyd.dyt.widget.adapter.accountManager;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.widget.holder.accountManager.ImageInfoNewViewHolder;
import com.hxyd.dyt.widget.holder.accountManager.ImageItemNewFoolViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by win7 on 2017/4/5.
 */

public class ImageInfoAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private Dialog mDialog;
    private boolean isSingle;
    private static final int FOOLVIEW = 1;
    private static final int IMAGEVIEW = 2;
    public static final int id = 0;
    public static final int dd = 1;
    public static final int jd = 2;
    public static final int xd = 3;
    public static final int vd = 4;
    public static final int od = 5;
    private List<String> listID = new ArrayList<String>();
    private List<String> listDD = new ArrayList<String>();
    private List<String> listJD = new ArrayList<String>();
    private List<String> listXD = new ArrayList<String>();
    private List<String> listVD = new ArrayList<String>();
    private List<String> listOD = new ArrayList<String>();


    public ImageInfoAdpter(Context context, boolean isSingle) {
        this.mContext = context;
        this.isSingle = isSingle;
    }


    public void setData(int type, String url) {
        switch (type) {
            case id:
                listID.add(url);
                break;
            case dd:
                listDD.add(url);
                break;
            case jd:
                listJD.add(url);
                break;
            case xd:
                listXD.add(url);
                break;
            case vd:
                listVD.add(url);
                break;
            case od:
                listOD.add(url);
                break;
        }
    }

    public void setData(int type, List<String> url) {
        switch (type) {
            case id:
                listID.addAll(url);
                break;
            case dd:
                listDD.addAll(url);
                break;
            case jd:
                listJD.addAll(url);
                break;
            case xd:
                listXD.addAll(url);
                break;
            case vd:
                listVD.addAll(url);
                break;
            case od:
                listOD.addAll(url);
                break;
        }
    }

    public void refreshState() {
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (FOOLVIEW == viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.image_item_new_fool, parent, false);
            return new ImageItemNewFoolViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.image_item_new, parent, false);
            return new ImageInfoNewViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ImageInfoNewViewHolder) {
            if (id == position) {
                ((ImageInfoNewViewHolder) holder).tvID.setText("身份证");
                ((ImageInfoNewViewHolder) holder).viewPager.setAdapter(new ImageItemAdpter(mContext, listID, isSingle));
            } else if (position == dd) {
                ((ImageInfoNewViewHolder) holder).tvID.setText("登记证");
                ((ImageInfoNewViewHolder) holder).viewPager.setAdapter(new ImageItemAdpter(mContext, listDD, isSingle));
            } else if (jd == position) {
                ((ImageInfoNewViewHolder) holder).tvID.setText("驾驶证");
                ((ImageInfoNewViewHolder) holder).viewPager.setAdapter(new ImageItemAdpter(mContext, listJD, isSingle));
            } else if (xd == position) {
                ((ImageInfoNewViewHolder) holder).tvID.setText("行驶证");
                ((ImageInfoNewViewHolder) holder).viewPager.setAdapter(new ImageItemAdpter(mContext, listXD, isSingle));
            } else if (vd == position) {
                ((ImageInfoNewViewHolder) holder).tvID.setText("车辆图片");
                ((ImageInfoNewViewHolder) holder).viewPager.setAdapter(new ImageItemAdpter(mContext, listVD, isSingle));
            } else if (od == position) {
                ((ImageInfoNewViewHolder) holder).tvID.setText("其他资料");
                ((ImageInfoNewViewHolder) holder).viewPager.setAdapter(new ImageItemAdpter(mContext, listOD, isSingle));
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((ImageInfoNewViewHolder) holder).viewPager.setLayoutManager(linearLayoutManager);
            if (!isSingle) {
                ((ImageInfoNewViewHolder) holder).setGone();
            }
        } else if (holder instanceof ImageItemNewFoolViewHolder) {
            ((ImageItemNewFoolViewHolder) holder).previousStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new BusEvent(BusEvent.SHOW_VEHICLEINFO_VIEW));
                }
            });

            ((ImageItemNewFoolViewHolder) holder).submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new BusEvent(BusEvent.IAMGE_SYNCAPPDATA));
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return isSingle ? 7 : 6;
    }

    @Override
    public int getItemViewType(int position) {
        if (isSingle) {
            if ((position + 1) == getItemCount()) {
                return FOOLVIEW;
            }
            return IMAGEVIEW;
        } else {
            return IMAGEVIEW;
        }
    }

    private void showDialog(final int num, final int stype) {
        mDialog = new Dialog(mContext, R.style.dialog_bottom_full);//.create();
        mDialog.show();

        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.popupAnimation);
        View view = View.inflate(mContext, R.layout.bottom_dailog, null);

        view.findViewById(R.id.iv_userinfo_takepic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                EventBus.getDefault().post(new BusEvent(BusEvent.STARTIMAGESELECTOR, num, stype));
            }
        });

        view.findViewById(R.id.iv_userinfo_choosepic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                EventBus.getDefault().post(new BusEvent(BusEvent.STARTCAMERA, stype));
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
}
