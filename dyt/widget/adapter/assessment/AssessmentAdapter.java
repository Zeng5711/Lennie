package com.hxyd.dyt.widget.adapter.assessment;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.entity.ImageItem;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.widget.adapter.accountManager.ImageInfoAdpterNew;
import com.hxyd.dyt.widget.holder.assessment.AssessmentViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by win7 on 2017/5/24.
 */

public class AssessmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private Dialog mDialog;
    private ArrayList<String> mList;
    private ArrayList<ImageUrl> imageUrls = new ArrayList<ImageUrl>();
    private int mPosition = 0;
    private List<String> url = new ArrayList<>();
    private CallbackListener listener;

    public AssessmentAdapter(Context context, ArrayList<String> list, CallbackListener listener) {
        this.mContext = context;
        this.mList = list;
        this.listener = listener;
    }

    public void setImageUrl(ImageUrl image) {
        imageUrls.add(image);
        url.add(image.getV());
        this.notifyDataSetChanged();
    }

    public void deleteUrl(int index) {
        imageUrls.remove(index);
        url.remove(index);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.assessment_item, parent, false);
        AssessmentViewHolder assessmentViewHolder = new AssessmentViewHolder(view);
        return assessmentViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null && holder instanceof AssessmentViewHolder) {
            final AssessmentViewHolder assess = (AssessmentViewHolder) holder;
            assess.setSpinnerAdapter(mContext, mList);
            assess.assesment_again_rl.setVisibility(View.GONE);
            assess.assesment_image_ll.setVisibility(View.GONE);
            assess.assesment_image_rl.setVisibility(View.GONE);
            assess.assessment_item_spinner.setSelection(mPosition, true);
            assess.assessment_item_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 0) {
                        mPosition = position;
                        assess.assesment_again_rl.setVisibility(View.GONE);
                        assess.assesment_image_ll.setVisibility(View.GONE);
                        assess.assesment_image_rl.setVisibility(View.GONE);
                    } else {
                        mPosition = position;
                        assess.assesment_again_rl.setVisibility(View.VISIBLE);
                        assess.assesment_image_ll.setVisibility(View.VISIBLE);
                        assess.assesment_image_rl.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            List<View> list = assess.getImageItemViews(url.size() == 0 ? 1 : url.size() < 9 ? url.size() + 1 : 9, mContext);
            assess.image_item_ll.removeAllViews();

            for (int i = 0; i < list.size(); i++) {
                ImageView imageView = (ImageView) list.get(i).findViewById(R.id.item_image_iv);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (url.size() > 0 && i <= (url.size() - 1)) {
                    ImageloadTools.load(mContext, url.get(i), imageView);
                    imageView.setTag(i);
                }

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getTag() != null) {
                            int tag = Integer.parseInt(v.getTag().toString());
                            Tools.makeText("tag = " + tag);
                            ImageItem item = new ImageItem();
                            item.stype = "";
                            item.position = tag;
                            item.list = url;
                            item.currentItem = tag;
                            item.isShowDelete = true;
                            EventBus.getDefault().post(new BusEvent(BusEvent.DELETE_ASSESSMENT_IMAGE, item));
                        } else {
                            showDialog();
                        }

                    }
                });

                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                assess.image_item_ll.addView(list.get(i), layoutParams);
            }

            assess.assessment_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mPosition == 1) {
                        if (TextUtils.isEmpty(assess.assessment_item_again.getText().toString().trim())) {
                            Tools.makeText("重新评估不能为空");
                            return;
                        }

                        if (url.size() == 0) {
                            Tools.makeText("请上传最少一张相片");
                            return;
                        }

                    }

                    if (TextUtils.isEmpty(assess.vehicleinfo_scd.getText().toString().trim())) {
                        Tools.makeText("备注不能为空");
                        return;
                    }

                    if (listener != null) {
                        listener.onCall(v,
                                assess.assessment_item_again.getText().toString().trim(),
                                mPosition + "",
                                assess.vehicleinfo_scd.getText().toString().trim());
                    }
                }
            });

        }
    }

    public interface CallbackListener {
        void onCall(View v, String assessmentPrice, String carStatus, String assessmentOpinion);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ImageUrl implements Comparable<ImageUrl> {
        private int i;
        private String v;
        private String k;

        public ImageUrl(int i, String v, String k) {
            this.i = i;
            this.v = v;
            this.k = k;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        @Override
        public int compareTo(@NonNull ImageUrl o) {
            if (this.i < o.i)
                return -1;
            else if (this.i == o.i)
                return 0;
            else if (this.i > o.i)
                return 1;
            return 0;
        }
    }

    private void showDialog() {
        mDialog = new Dialog(mContext, R.style.dialog_bottom_full);//.create();
        mDialog.show();

        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.popupAnimation);
        View view = View.inflate(mContext, R.layout.bottom_dailog, null);

//        final ImageItem item = new ImageItem();
//        item.num = num;
//        item.stype = stype;
//        item.position = position;
//        item.currentItem = currentItem;

        view.findViewById(R.id.iv_userinfo_takepic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                EventBus.getDefault().post(new BusEvent(BusEvent.SHOW_ASSESSMENT_IMAGESELECTOR));
            }
        });

        view.findViewById(R.id.iv_userinfo_choosepic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                EventBus.getDefault().post(new BusEvent(BusEvent.SHOW_ASSESSMENT_CAMERA));
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
