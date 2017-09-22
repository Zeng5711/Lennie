package com.hxyd.dyt.accountManager.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.ImageInfoBean;
import com.hxyd.dyt.accountManager.presenter.ADDataManager;
import com.hxyd.dyt.accountManager.presenter.ImageInfoNewP;
import com.hxyd.dyt.accountManager.presenter.in.ImageInfoNewPI;
import com.hxyd.dyt.accountManager.presenter.in.ImageItemDeleteCall;
import com.hxyd.dyt.accountManager.view.SingleNewActivity;
import com.hxyd.dyt.accountManager.view.in.ImageInfoVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.ImageCategoryBean;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.purplestar.utils.GPS3DUtils;
import com.hxyd.dyt.widget.adapter.accountManager.ImageInfoAdpter;
import com.hxyd.dyt.widget.adapter.accountManager.ImageInfoAdpterNew;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by win7 on 2017/5/15.
 */

public class ImageInfoFragment extends Fragment implements ImageInfoVI {

    @BindView(R.id.image_new_rv)
    public RecyclerView mRV;

    private ImageInfoAdpterNew mAdpter;
    private LinearLayoutManager mLinearLayoutManager;
    private int lastPosition = 0;
    private int lastOffset = 0;
    private List<ImageCategoryBean> list;
    private ImageInfoNewPI PI;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String address = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        PI = new ImageInfoNewP(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imageinfo_new, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        GPS3DUtils.getInstance().startLocation(getContext(), new GPS3DUtils.LocationListener() {

            @Override
            public void onCallback(double lat, double lon, String add) {
                latitude = lat;
                longitude = lon;
                address = add;
            }

            @Override
            public void onErr() {
                Tools.makeText("定位失败，请重新定位！");
            }
        });

        mAdpter = new ImageInfoAdpterNew(this.getContext(), true, this);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRV.setLayoutManager(mLinearLayoutManager);
        mRV.setAdapter(mAdpter);
        mRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mRV.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });

        PI.getBaseData();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        PI.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {
        switch (event.event) {
            case BusEvent.IMAGEINFO_SHOWCAMERA:
                if (null != event.imageItem) {
                    ((SingleNewActivity) getActivity()).showProgressDialog("正在上传中请稍后...");
                    final String url = event.imageItem.url;
                    final String key = event.imageItem.stype;
                    final int position = event.imageItem.position;
                    mAdpter.setData(position, key, url, event.imageItem.currentItem);
                    mAdpter.refreshState(position);
                    final String str[] = key.split("_");
                    final String code = getCode(str[0]);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String path = url;//UriTools.amendRotatePhoto(url);
                            Logger.e("IMAGE_UP_SIZE = " + path);
                            if (!TextUtils.isEmpty(path)) {
                                PI.onImgUploadNew(path, code, str[1], event.imageItem, true);
                            } else {
                                EventBus.getDefault().post(new BusEvent(BusEvent.IAMGE_ITEM_RESH_STATE, event.imageItem));
//                                EventBus.getDefault().post(new BusEvent(BusEvent.IMAGEINFO_DIMS_UP_DAILOG, 1));
                            }
                        }
                    }).start();
                }
                break;
            case BusEvent.IMAGEINFO_SHOWIMAGESELECTOR:
                if (null != event.imageItem) {
                    final List<String> list = event.imageItem.list;
                    String key = event.imageItem.stype;
                    int position = event.imageItem.position;

                    ((SingleNewActivity) getActivity()).showProgressDialog("正在上传中请稍后...");
                    mAdpter.setData(position, key, list.get(0), event.imageItem.currentItem);
                    String str[] = key.split("_");
                    String code = getCode(str[0]);
                    PI.onImgUploadNew(list.get(0), code, str[1], event.imageItem, false);
                    mAdpter.refreshState(position);
                }
                break;
            case BusEvent.IAMGE_ITEM_RESH_STATE:
                if (event.imageItem != null) {
                    mAdpter.deletData(event.imageItem.position, event.imageItem.stype,
                            TextUtils.isEmpty(event.imageItem.url) ? event.imageItem.list.get(0) : event.imageItem.url);
                    mAdpter.refreshState(event.imageItem.position);
                }

                break;
            case BusEvent.IAMGE_SYNCAPPDATA:
                ((SingleNewActivity) getActivity()).showProgressDialog("正在上传中请稍后...");
                PI.onSyncappdata(longitude,latitude,address);
                break;
            case BusEvent.IMAGE_PREVIEW_DELETE:
                if (event.imageItem != null) {
                    EventBus.getDefault().post(new BusEvent(BusEvent.IMAGE_PREVIEW_DELETE_SHOW));
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
                    final List<String> list = event.imageItem.list;
                    if (null != list && !list.isEmpty()) {
                        for (final String s : list) {
                            final String key = mAdpter.getKey(s);
                            String str[] = key.split("_");
                            String code = getCode(str[0]);
                            String name = code + "_" + str[1];//+ ".jpg";
                            PI.onDeleteImage(name, new ImageItemDeleteCall() {
                                @Override
                                public void onSuccess() {
//                                            if (s.equals(list.get(list.size() - 1))) {
//                                                EventBus.getDefault().post(new BusEvent(BusEvent.IMAGEINFO_DIMS_UP_DAILOG));
//                                            }
                                    mAdpter.deletData(event.imageItem.position, key, s);
                                    mAdpter.refreshState(event.imageItem.position);
                                    Tools.makeText("刪除成功！");
                                }

                                @Override
                                public void onErr(String meaage) {
//                                            if (s.equals(list.get(list.size() - 1))) {
//                                                EventBus.getDefault().post(new BusEvent(BusEvent.IMAGEINFO_DIMS_UP_DAILOG));
//                                            }
                                    Tools.makeText(meaage);
                                }
                            });
                        }
                    } else {
                        Tools.makeText("刪除失敗！");
                    }

//                        }
//                    }).start();

                }
                break;
            default:
                break;

        }
    }

    /**
     * 记录RecyclerView当前位置
     */
    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRV.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    private String getCode(String key) {
        if (list != null) {
            for (ImageCategoryBean i : list) {
                String str[] = i.getCode().split("_");
                if (str[str.length - 1].equals(key)) {
                    return i.getCode();
                }
            }
        }
        return "";
    }

    @Override
    public void setImageCategoryBean(List<ImageCategoryBean> list) {
        if (list != null) {
            this.list = list;
            if (((SingleNewActivity) getActivity()).isEdit()) {
                PI.getOrderDefult(ADDataManager.getInstance().getLoanInfoId());
            }
        }
    }

    @Override
    public void setImageInfoBean(List<ImageInfoBean> o) {
        for (ImageInfoBean infoBean : o) {
            String category = infoBean.getCategory();
            String str[] = category.split("_");
            String s = str[str.length - 1];
            int type = -1;
            if (s.equals("ID")) {
                type = ImageInfoAdpter.id;
            } else if (s.equals("RD")) {
                type = ImageInfoAdpter.dd;
            } else if (s.equals("DL")) {
                type = ImageInfoAdpter.jd;
            } else if (s.equals("DRL")) {
                type = ImageInfoAdpter.xd;
            } else if (s.equals("OI")) {
                type = ImageInfoAdpter.od;
            } else if (s.equals("VI")) {
                type = ImageInfoAdpter.vd;
            }
            String key[] = infoBean.getName().split("_");
            mAdpter.setData(type, key[2] + "_" + key[3], Constant.BASE_URL + infoBean.getOriginalUrl(), 0);
        }
        mAdpter.refreshState();


    }

    @Override
    public void onSccess() {
        EventBus.getDefault().post(new BusEvent(BusEvent.SINGLE_SCCESS));
    }

    @Override
    public void onErr(String str) {
        Tools.makeText(str);
    }

    @Override
    public void dismiss() {
        ((SingleNewActivity) getActivity()).dismiss();
    }
}
