package com.hxyd.dyt.widget.adapter.accountManager;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.ImageItem;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.widget.holder.accountManager.ImageInfoNewsViewHolder;
import com.hxyd.dyt.widget.holder.accountManager.ImageItemNewFoolViewHolder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by win7 on 2017/4/10.
 */

public class ImageInfoAdpterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


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
    long ischeck;

    private ArrayList<String> listID = new ArrayList<String>();
    private ArrayList<String> listDD = new ArrayList<String>();
    private ArrayList<String> listJD = new ArrayList<String>();
    private ArrayList<String> listXD = new ArrayList<String>();
    private ArrayList<String> listVD = new ArrayList<String>();
    private ArrayList<String> listOD = new ArrayList<String>();

    private ArrayList<ImageUrl> imageUrlsID = new ArrayList<ImageUrl>();
    private ArrayList<ImageUrl> imageUrlsDD = new ArrayList<ImageUrl>();
    private ArrayList<ImageUrl> imageUrlsJD = new ArrayList<ImageUrl>();
    private ArrayList<ImageUrl> imageUrlsXD = new ArrayList<ImageUrl>();
    private ArrayList<ImageUrl> imageUrlsVD = new ArrayList<ImageUrl>();
    private ArrayList<ImageUrl> imageUrlsOD = new ArrayList<ImageUrl>();

    private HashMap<String, ImageUrl> IDMap = new HashMap<String, ImageUrl>();
    private HashMap<String, ImageUrl> DDMap = new HashMap<String, ImageUrl>();
    private HashMap<String, ImageUrl> JDMap = new HashMap<String, ImageUrl>();
    private HashMap<String, ImageUrl> XDMap = new HashMap<String, ImageUrl>();
    private HashMap<String, ImageUrl> VDMap = new HashMap<String, ImageUrl>();
    private HashMap<String, ImageUrl> ODMap = new HashMap<String, ImageUrl>();

    private HashMap<String, String> checkMap = new HashMap<String, String>();
    private HashMap<Integer, ArrayList<String>> previewMap = new HashMap<>();
    private HashMap<Integer, Map<String, String>> sampleMap = new HashMap<Integer, Map<String, String>>();
    private boolean isViewSample = false;
    private Fragment fragment;


    public ImageInfoAdpterNew(Context context, boolean isSingle, Fragment fragment) {
        this.mContext = context;
        this.isSingle = isSingle;
        this.fragment = fragment;
    }

    public ImageInfoAdpterNew(Context context, boolean isSingle) {
        this.mContext = context;
        this.isSingle = isSingle;
        this.fragment = fragment;
    }

    public void setData(int position, String key, String value, int currentItem) {
        checkMap.put(key, value);
        savePreview(position, checkMap.get(key), currentItem, key);
    }

    public String getKey(String s) {
        String key = "";
        for (String k : checkMap.keySet()) {
            if (s.equals(checkMap.get(k))) {
                return k;
            }
        }
        return key;
    }

    public void deletData(int position, String key, String value) {
        deletPreview(position, value, key);
        checkMap.remove(getKey(value));
    }

    public void setSampleData(HashMap<Integer, Map<String, String>> sampleData) {
        isViewSample = true;
        sampleMap = sampleData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (FOOLVIEW == viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.image_item_new_fool, parent, false);
            return new ImageItemNewFoolViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.image_item_, parent, false);
            return new ImageInfoNewsViewHolder(view);
        }
    }

    private int getMAXSize(int position) {
        if (position == dd) {
            if (imageUrlsDD != null && !imageUrlsDD.isEmpty() && imageUrlsDD.size() >= 1) {
                Collections.sort(imageUrlsDD);
                return imageUrlsDD.get(imageUrlsDD.size() - 1).getI();
            }
        } else if (position == od) {
            if (imageUrlsOD != null && !imageUrlsOD.isEmpty() && imageUrlsOD.size() >= 1) {
                Collections.sort(imageUrlsOD);
                return imageUrlsOD.get(imageUrlsOD.size() - 1).getI();
            }
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ImageInfoNewsViewHolder) {
            String title = getImageItemTitle(position);
            ((ImageInfoNewsViewHolder) holder).title.setText(title);

            List<View> list = ((ImageInfoNewsViewHolder) holder).getImageItemViews(getImageItem(position), mContext);
            if (position == dd) {//&& listDD.size() >= 2 && listDD.size() <= 9
                int size = getMAXSize(position);
                list.addAll(((ImageInfoNewsViewHolder) holder).getImageItemViews(size == 2 ? 1 : size == 9 ? size - 2 : size - 1, mContext));
            } else if (position == od) {
                int size = getMAXSize(position);
                list.addAll(((ImageInfoNewsViewHolder) holder).getImageItemViews(size == 2 ? 1 : size == 36 ? size - 2 : size - 1, mContext));
            }

            int j = 0;
            ((ImageInfoNewsViewHolder) holder).linearLayout.removeAllViews();
            for (View view : list) {
                final String key = getImageItemKey(position, j);
                final int currentItem = j;
                if (!isViewSample) {

                    ImageView imageView = (ImageView) view.findViewById(R.id.item_image_iv);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    if (checkMap.containsKey(key) && !TextUtils.isEmpty(checkMap.get(key))) {
                        ImageloadTools.load(mContext, checkMap.get(key), imageView);
                    } else {
                        ImageloadTools.load(mContext, R.mipmap.item_image, imageView);
                    }

                    view.findViewById(R.id.item_image_iv).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(checkMap.get(key))) {
                                ImageItem item = new ImageItem();
                                item.stype = key;
                                item.position = position;
                                item.list = sort(previewMap.get(position), position, key);
                                int currentItem = item.list.indexOf(checkMap.get(key));
                                item.currentItem = currentItem == -1 ? 0 : currentItem;
                                item.isShowDelete = true;
                                EventBus.getDefault().post(new BusEvent(BusEvent.IMAGE_PREVIEW, item));
                            } else {
                                showDialog(1, key, position, currentItem);
                            }
                        }
                    });

                } else {
                    if (sampleMap.containsKey(position)) {
                        ImageloadTools.load(mContext, sampleMap.get(position).get(key), (ImageView) view.findViewById(R.id.item_image_iv));
                    } else {
                        ImageloadTools.load(mContext, R.mipmap.item_image, (ImageView) view.findViewById(R.id.item_image_iv));
                    }

                    view.findViewById(R.id.item_image_iv).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ArrayList<String> list1 = new ArrayList<String>();
                            String[] str = new String[sampleMap.get(position).size()];

                            for (String key : sampleMap.get(position).keySet()) {
                                int index = Integer.parseInt(key.substring(key.length() - 1));
                                str[index - 1] = sampleMap.get(position).get(key);
                            }

                            for (String s : str) {
                                list1.add(s);
                            }


                            ImageItem item = new ImageItem();
                            item.stype = key;
                            item.position = position;
                            item.list = list1;
                            item.currentItem = currentItem;
                            item.isShowDelete = false;
                            EventBus.getDefault().post(new BusEvent(BusEvent.IMAGE_PREVIEW, item));

                        }
                    });

                }

                ((TextView) view.findViewById(R.id.item_image_tv)).setText(getImageItemTV(position, j));
                j++;
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                ((ImageInfoNewsViewHolder) holder).linearLayout.addView(view, layoutParams);
            }
        } else if (holder instanceof ImageItemNewFoolViewHolder) {
            ((ImageItemNewFoolViewHolder) holder).previousStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new BusEvent(BusEvent.SINGLE_BACK));
                }
            });

            ((ImageItemNewFoolViewHolder) holder).submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    long i = System.currentTimeMillis();
                    if (i - ischeck > 500) {
                        ischeck = System.currentTimeMillis();

                        if (listID.size() != 2) {
                            Tools.makeText("身份证图片至少要两张");
                            return;
                        }

                        if (!Constant.getProductType().equals("2")) {
                            if (listDD.size() < 2) {
                                Tools.makeText("登记证至少要两张");
                                return;
                            }
                        }

                        if (listJD.size() != 1) {
                            Tools.makeText("驾驶证至少要一张");
                            return;
                        }

                        if (listXD.size() != 2) {
                            Tools.makeText("行驶证至少要两张");
                            return;
                        }

                        if (listVD.size() != 8) {
                            Tools.makeText("车辆图片至少要八张");
                            return;
                        }

                        EventBus.getDefault().post(new BusEvent(BusEvent.IAMGE_SYNCAPPDATA));
                    }
                }
            });
        }

    }

    private ArrayList<String> sort(ArrayList<String> list, int position, String key) {
        switch (position) {
            case id:
                if (imageUrlsID != null && !imageUrlsID.isEmpty() && imageUrlsID.size() >= 1) {
                    list.clear();
                    Collections.sort(imageUrlsID);
                    for (ImageUrl i : imageUrlsID) {
                        list.add(i.getV());
                    }
                }
                break;
            case dd:
                if (imageUrlsDD != null && !imageUrlsDD.isEmpty() && imageUrlsDD.size() >= 1) {
                    list.clear();
                    Collections.sort(imageUrlsDD);
                    for (ImageUrl i : imageUrlsDD) {
                        list.add(i.getV());
                    }
                }
                break;
            case jd:

                break;
            case xd:
                if (imageUrlsXD != null && !imageUrlsXD.isEmpty() && imageUrlsXD.size() >= 1) {
                    list.clear();
                    Collections.sort(imageUrlsXD);
                    for (ImageUrl i : imageUrlsXD) {
                        list.add(i.getV());
                    }
                }
                break;
            case vd:
                if (imageUrlsVD != null && !imageUrlsVD.isEmpty() && imageUrlsVD.size() >= 1) {
                    list.clear();
                    Collections.sort(imageUrlsVD);
                    for (ImageUrl i : imageUrlsVD) {
                        list.add(i.getV());
                    }
                }
                break;
            case od:
                if (imageUrlsOD != null && !imageUrlsOD.isEmpty() && imageUrlsOD.size() >= 1) {
                    list.clear();
                    Collections.sort(imageUrlsOD);
                    for (ImageUrl i : imageUrlsOD) {
                        list.add(i.getV());
                    }
                }
                break;
        }
        return list;
    }

    private void deletPreview(int position, String s, String key) {
        switch (position) {
            case id:
                listID.remove(s);
                previewMap.put(position, listID);
                ImageUrl imageUrl = IDMap.get(getKey(s));
                imageUrlsID.remove(imageUrl);
                break;
            case dd:
                listDD.remove(s);
                previewMap.put(position, listDD);
                imageUrlsDD.remove(DDMap.get(getKey(s)));
                break;
            case jd:
                listJD.remove(s);
                previewMap.put(position, listJD);
                imageUrlsJD.remove(JDMap.get(getKey(s)));
                break;
            case xd:
                listXD.remove(s);
                previewMap.put(position, listXD);
                imageUrlsXD.remove(XDMap.get(getKey(s)));
                break;
            case vd:
                listVD.remove(s);
                previewMap.put(position, listVD);
                imageUrlsVD.remove(VDMap.get(getKey(s)));
                break;
            case od:
                listOD.remove(s);
                previewMap.put(position, listOD);
                imageUrlsOD.remove(ODMap.get(getKey(s)));
                break;
        }
    }

    private void savePreview(int position, String s, int currentItem, String key) {
        String split[] = key.split("_");
        String num = split[1];
        String str;
        if (num.substring(0, 1).equals("0")) {
            str = num.substring(1, num.length());
        } else {
            str = num;
        }
        ImageUrl imageUrl = new ImageUrl(Integer.parseInt(str), s, key);
        switch (position) {
            case id:
                listID.add(s);
                previewMap.put(position, listID);
                imageUrlsID.add(imageUrl);
                IDMap.put(key, imageUrl);
                break;
            case dd:
                listDD.add(s);
                previewMap.put(position, listDD);
                imageUrlsDD.add(imageUrl);
                DDMap.put(key, imageUrl);
                break;
            case jd:
                listJD.add(s);
                previewMap.put(position, listJD);
                imageUrlsJD.add(imageUrl);
                JDMap.put(key, imageUrl);
                break;
            case xd:
                listXD.add(s);
                previewMap.put(position, listXD);
                imageUrlsXD.add(imageUrl);
                XDMap.put(key, imageUrl);
                break;
            case vd:
                listVD.add(s);
                previewMap.put(position, listVD);
                imageUrlsVD.add(imageUrl);
                VDMap.put(key, imageUrl);
                break;
            case od:
                listOD.add(s);
                previewMap.put(position, listOD);
                imageUrlsOD.add(imageUrl);
                ODMap.put(key, imageUrl);
                break;
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

    private String getImageItemKey(int i, int j) {
        String key = "";
        switch (i) {
            case id:
                if (j == 0) {
                    key = "ID_01";
                } else {
                    key = "ID_02";
                }
                break;
            case dd:
                if (j == 0) {
                    key = "RD_01";
                } else if (j == 1) {
                    key = "RD_02";
                } else {
                    key = "RD_0" + (j + 1);
                }
                break;
            case jd:
                key = "DL_01";
                break;
            case xd:
                if (j == 0) {
                    key = "DRL_01";
                } else {
                    key = "DRL_02";
                }
                break;
            case vd:
                if (j == 0) {
                    key = "VI_01";
                } else if (j == 1) {
                    key = "VI_02";
                } else if (j == 2) {
                    key = "VI_03";
                } else if (j == 3) {
                    key = "VI_04";
                } else if (j == 4) {
                    key = "VI_05";
                } else if (j == 5) {
                    key = "VI_06";
                } else if (j == 6) {
                    key = "VI_07";
                } else if (j == 7) {
                    key = "VI_08";
                }
                break;
            case od:
                if (j == 0) {
                    key = "OI_01";
                } else if (j == 1) {
                    key = "OI_02";
                } else {
                    if (j < 9) {
                        key = "OI_0" + (j + 1);
                    } else {
                        key = "OI_" + (j + 1);
                    }
                }
                break;
        }
        return key;
    }


    private String getImageItemTV(int i, int j) {
        String text = "";
        switch (i) {
            case id:
                if (j == 0) {
                    text = "身份证正面";
                } else {
                    text = "身份证反面";
                }
                break;
            case dd:
                if (j == 0) {
                    text = "第一页";
                } else if (j == 1) {
                    text = "第二页";
                }
                break;
            case jd:
                text = "驾驶证";
                break;
            case xd:
                if (j == 0) {
                    text = "第一页";
                } else {
                    text = "第二页";
                }
                break;
            case vd:
                if (j == 0) {
                    text = "车辆正面图";
                } else if (j == 1) {
                    text = "客户与车合照";
                } else if (j == 2) {
                    text = "车辆仪表图";
                } else if (j == 3) {
                    text = "车辆车尾图";
                } else if (j == 4) {
                    text = "车尾箱图";
                } else if (j == 5) {
                    text = "车辆前排配置图";
                } else if (j == 6) {
                    text = "车辆后排配置图";
                } else if (j == 7) {
                    text = "车铭牌";
                }
                break;
            case od:
                if (isViewSample) {
                    if (j == 0) {
                        text = "保险单";
                    } else if (j == 1) {
                        text = "银行卡正面";
                    } else if (j == 2) {
                        text = "银行卡反面";
                    }
                } else {
                    if (j == 0) {
                        text = "银行卡正面";
                    } else if (j == 1) {
                        text = "银行卡反面";
                    }
                }
                break;
        }
        return text;
    }

    private String getImageItemTitle(int i) {
        String title = "";
        switch (i) {
            case id:
                title = "身份证";
                break;
            case dd:
                title = "登记证";
                break;
            case jd:
                title = "驾驶证";
                break;
            case xd:
                title = "行驶证";
                break;
            case vd:
                title = "车辆图片";
                break;
            case od:
                title = "其他资料";
                break;
        }

        return title;
    }

    private int getImageItem(int i) {
        int count = 0;
        switch (i) {
            case id:
                count = 2;
                break;
            case dd:
                count = 2;
                break;
            case jd:
                count = 1;
                break;
            case xd:
                count = 2;
                break;
            case vd:
                count = 8;
                break;
            case od:
                if (isViewSample) {
                    count = 3;
                } else {
                    count = 2;
                }
                break;
        }

        return count;
    }

    private void showDialog(final int num, final String stype, final int position, int currentItem) {
        mDialog = new Dialog(mContext, R.style.dialog_bottom_full);//.create();
        mDialog.show();

        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.popupAnimation);
        View view = View.inflate(mContext, R.layout.bottom_dailog, null);

        final ImageItem item = new ImageItem();
        item.num = num;
        item.stype = stype;
        item.position = position;
        item.currentItem = currentItem;

        view.findViewById(R.id.iv_userinfo_takepic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                EventBus.getDefault().post(new BusEvent(BusEvent.STARTIMAGESELECTOR, item));
            }
        });

        view.findViewById(R.id.iv_userinfo_choosepic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                EventBus.getDefault().post(new BusEvent(BusEvent.STARTCAMERA, item));
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

    public void refreshState(int position) {
        this.notifyItemChanged(position != -1 ? position : 0);
    }

    public void refreshState() {
        this.notifyDataSetChanged();
    }

    class ImageUrl implements Comparable<ImageUrl> {
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
}
