package com.hxyd.dyt.assessment.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.presenter.in.ImageItemDeleteCall;
import com.hxyd.dyt.accountManager.view.SingleNewActivity;
import com.hxyd.dyt.assessment.modle.entity.CarData;
import com.hxyd.dyt.assessment.presenter.AssessmentP;
import com.hxyd.dyt.assessment.presenter.in.AssessmentPI;
import com.hxyd.dyt.assessment.view.AssessmentActivity;
import com.hxyd.dyt.assessment.view.in.AssessmentVI;
import com.hxyd.dyt.common.entity.ImageItem;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.common.uitl.PermissionUtils;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.common.uitl.UriTools;
import com.hxyd.dyt.widget.adapter.assessment.AssessmentAdapter;
import com.orhanobut.logger.Logger;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by win7 on 2017/5/24.
 */

public class AssessmentFragment extends Fragment implements AssessmentVI {

    @BindView(R.id.assesment_rv)
    RecyclerView rv;

    private AssessmentPI P;

    private ArrayList<String> spinnerList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private AssessmentAdapter assessmentAdapter;
    private PermissionUtils.PermissionGrant permissionGrant;
    private ImgSelConfig config;
    private ImageLoader imageLoader;
    private Uri mImageFileUri;
    private static final int STARTCAMERA = 1;
    private static final int STARTLMAGESELECTOR = 2;
    private static int count = 0;
    private String systemType = "";
    private String orderNo = "";
    private String CODE = "AS";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        P = new AssessmentP(this);
        orderNo = getArguments().getString("orderNo");
        systemType = getArguments().getString("orderNo");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assessment_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        spinnerList.add("正常");
        spinnerList.add("不正常");


        mLayoutManager = new LinearLayoutManager(getContext());
        assessmentAdapter = new AssessmentAdapter(getContext(), spinnerList, new AssessmentAdapter.CallbackListener() {
            @Override
            public void onCall(View v, String assessmentPrice, String carStatus, String assessmentOpinion) {

                Map<String, Object> map = new HashMap<>();
                map.put("orderNo", orderNo);
                map.put("systemType", systemType);
                map.put("carStatus", carStatus);
                map.put("assessmentPrice", assessmentPrice);
                map.put("assessmentOpinion", assessmentOpinion);

                P.saveAssessment(map);
            }
        });
        rv.setAdapter(assessmentAdapter);
        rv.setLayoutManager(mLayoutManager);

        imageLoader = new ImageLoader() {

            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                ImageloadTools.load(context, path, imageView);
            }
        };
        permissionGrant = new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(int requestCode, boolean isSuccess) {
                if (requestCode == PermissionUtils.CODE_CAMERA) {
                    if (isSuccess) {
                        startCamera();
                    } else {
                        ((AssessmentActivity) getActivity()).showAlertDialog("相机权限被拒绝");
                    }
                }
            }
        };


    }


    private void onImgUploadNew(String url, int index) {
        String name = "0" + count;
        P.onImgUploadNew(orderNo, url, CODE, name, index);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STARTCAMERA && resultCode == -1) {
            String url = mImageFileUri.getPath();
            if (!TextUtils.isEmpty(url)) {
                int index = count;
                assessmentAdapter.setImageUrl(new AssessmentAdapter.ImageUrl(count, url, ""));
                count++;
                onImgUploadNew(url, index);
            }
        } else if (requestCode == STARTLMAGESELECTOR && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (pathList != null && pathList.size() > 0) {
                int index = count;
                assessmentAdapter.setImageUrl(new AssessmentAdapter.ImageUrl(count, pathList.get(0), ""));
                count++;
                onImgUploadNew(pathList.get(0), index);
            }
        } else if (requestCode == PhotoPreview.REQUEST_CODE) {
            ArrayList<String> list = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            if (!list.isEmpty() && list.size() > 0) {
                showDialog(0,"","正在努力删除中...");
                final int current = data.getIntExtra(PhotoPreview.EXTRA_CURRENT_ITEM, -1);
                if (current >= 0) {
                    P.onDeleteImage(orderNo,systemType,"0"+(current+1),new ImageItemDeleteCall(){

                        @Override
                        public void onSuccess() {
                            colseDialog();
                            assessmentAdapter.deleteUrl(current);
                        }

                        @Override
                        public void onErr(String message) {
                            colseDialog();
                            onPrompt(0,message);
                        }
                    });


                }
            }
        }
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
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {
        switch (event.event) {
            case BusEvent.SHOW_ASSESSMENT_IMAGESELECTOR:
                startLmageselector(1);
                break;
            case BusEvent.SHOW_ASSESSMENT_CAMERA:
                PermissionUtils.getInstance().requestPermission(getActivity(), PermissionUtils.CODE_CAMERA, permissionGrant);
                break;
            case BusEvent.DELETE_ASSESSMENT_IMAGE:
                if (event.imageItem != null) {
                    showImageDetail(event.imageItem);
                }
                break;
            case BusEvent.SEE_CAR_DATA_IMAGE_ERR:
                int index = event.paraInt;
                if (index >= 0) {
                    assessmentAdapter.deleteUrl(index);
                }
            default:
                break;
        }
    }

    private void showImageDetail(ImageItem item) {
        PhotoPreview.builder()
                .setPhotos((ArrayList<String>) item.list)
                .setAction(1)
                .setCurrentItem(item.currentItem)
                .setShowActiononly()
                .setKey(item.stype)
                .setPosition(item.position)
                .isShowDelete(item.isShowDelete)
                .start(getContext(), this);
    }

    private void startLmageselector(int num) {
        config = new ImgSelConfig.Builder(getContext(), imageLoader)
                // 是否多选, 默认true
//                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
//                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.parseColor("#333333"))
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#FFFFFF"))
                // 返回图标ResId
                .backResId(R.mipmap.back)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.parseColor("#333333"))
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#FFFFFF"))
                // 裁剪大小。needCrop为true的时候配置
//                .cropSize(1, 1, 200, 200)
//                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(false)
                // 最大选择图片数量，默认9
                .maxNum(num)
                .build();

// 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, STARTLMAGESELECTOR);
    }

    /**
     * 回调获取授权结果，判断是否授权
     * 权限开启结果检查 grantResults[0]==0=PackageManager.PERMISSION_GRANTED的话 权限开启成功
     * 权限开启结果检查 grantResults[0]==-1=PackageManager.PERMISSION_DENIED 权限开启失败
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length > 0) {
            PermissionUtils.requestPermissionsResult(getActivity(), requestCode, permissions, grantResults);
        }
    }

    private void startCamera() {
        mImageFileUri = UriTools.getOutFileUri(UriTools.TYPE_FILE_IMAGE);//得到一个File Uri
        if (mImageFileUri != null) {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageFileUri);
            this.startActivityForResult(intent, STARTCAMERA);
        } else {
            Tools.makeText("请假插入SD卡！");
        }
    }

    @Override
    public void onPrompt(int type, String message) {
        Tools.makeText(message);
    }

    @Override
    public void showDialog(int type, String title, String message) {
        ((AssessmentActivity) getActivity()).showProgressDialog(message);
    }

    @Override
    public void colseDialog() {
        ((AssessmentActivity) getActivity()).dismiss();
    }

    @Override
    public void onSuccess() {
        ((AssessmentActivity) getActivity()).finish();
    }

    @Override
    public void onSuccess(CarData carData) {

    }
}
