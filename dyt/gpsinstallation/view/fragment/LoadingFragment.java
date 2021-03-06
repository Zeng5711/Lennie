package com.hxyd.dyt.gpsinstallation.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.common.uitl.UriTools;
import com.hxyd.dyt.gpsinstallation.entity.ImeiInfo;
import com.hxyd.dyt.gpsinstallation.entity.Loading;
import com.hxyd.dyt.gpsinstallation.presenter.LoadingP;
import com.hxyd.dyt.gpsinstallation.presenter.in.LoadingPI;
import com.hxyd.dyt.gpsinstallation.view.GPSInstallActivity;
import com.hxyd.dyt.gpsinstallation.view.in.LoadingVI;
import com.hxyd.dyt.widget.RecyclerSpace;
import com.hxyd.dyt.widget.adapter.vehiclemortage.MortageMaterialAdapter;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by win7 on 2017/9/9.
 */

public class LoadingFragment extends Fragment implements LoadingVI, EasyPermissions.PermissionCallbacks {

    private GPSInstallActivity mActivity;

    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.vehicleinfo_scd)
    EditText vehicleinfo_scd;

    private String businessId, imeiIds;
    private String imageName = "";

    private MortageMaterialAdapter adapter;
    private LoadingPI p;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (GPSInstallActivity) context;
    }

    public void setBusinessId(String businessId, String imeiIds) {
        this.businessId = businessId;
        this.imeiIds = imeiIds;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gps_loading, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        p = new LoadingP(this);

        p.queryAllImgByParams(p.positionImages, businessId, imeiIds);

        imageLoader = new ImageLoader() {

            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                ImageloadTools.load(context, path, imageView);
            }
        };

        adapter = new MortageMaterialAdapter(getContext(), new MortageMaterialAdapter.SelectListener() {
            @Override
            public void showLmageselector(int num, String name,int position) {
                imageName = name;
                startLmageselector(num);
            }

            @Override
            public void showImageDetail(ArrayList<String> list, int currentItem, int position, boolean isShowDelete) {
                startImageDetail(list, currentItem, position, isShowDelete);
            }

            @Override
            public void showCamer(String name,int position) {
                imageName = name;
                openCamera();
            }
        });

        rv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new RecyclerSpace(5));
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
    }


    @OnClick(R.id.button2)
    public void onClick(View v) {

        if (null != vehicleinfo_scd && TextUtils.isEmpty(vehicleinfo_scd.getText().toString().trim())) {
            Tools.makeText("请填写车辆位置");
            return;
        }

        if (null != adapter && adapter.getDataSize() == 0) {
            Tools.makeText("上传相片");
            return;
        }
        p.updateImeiInfo(vehicleinfo_scd.getText().toString().trim(), imeiIds, businessId);

    }

    private ImageLoader imageLoader;
    private ImgSelConfig config;
    private static final int STARTLMAGESELECTOR = 2;

    private void startLmageselector(int num) {
        config = new ImgSelConfig.Builder(getContext(), imageLoader)
                .rememberSelected(false)
                .btnTextColor(Color.parseColor("#333333"))
                .statusBarColor(Color.parseColor("#FFFFFF"))
                .backResId(R.mipmap.back)
                .title("图片")
                .titleColor(Color.parseColor("#333333"))
                .titleBgColor(Color.parseColor("#FFFFFF"))
                .needCamera(false)
                .maxNum(num)
                .build();
        ImgSelActivity.startActivity(this, config, STARTLMAGESELECTOR);
    }

    private void startImageDetail(ArrayList<String> list, int currentItem, int position, boolean isShowDelete) {
        PhotoPreview.builder()
                .setPhotos(list)
                .setAction(1)
                .setCurrentItem(currentItem)
                .setShowActiononly()
//                .setKey(item.stype)
                .setPosition(position)
                .isShowDelete(isShowDelete)
                .start(getContext(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STARTLMAGESELECTOR && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            adapter.setData(pathList);
            p.uploadImg(p.positionImages, businessId, imageName, pathList.get(0), imeiIds);
        } else if (requestCode == PhotoPreview.REQUEST_CODE) {
            ArrayList<String> list = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            if (list != null && !list.isEmpty() && list.size() > 0) {
                int position = data.getIntExtra(PhotoPreview.EXTRA_POSITION, -1);
                String name = adapter.getName(position);
                p.deleteImg(p.positionImages, businessId, name, imeiIds);
                adapter.deleData(position);
            }
        } else if (requestCode == STARTCAMERA && resultCode == -1) {
            if (UriTools.mImageFileUri != null) {
                String path = UriTools.mImageFileUri.getPath();
                adapter.setData(path);
                p.uploadImg(p.positionImages, businessId, imageName, path, imeiIds);
            }

        }
    }

    @Override
    public void showDialog(String m) {
        mActivity.showProgressDialog(m);
    }

    @Override
    public void dismiss() {
        mActivity.dismiss();
    }

    @Override
    public void onErr(int type, String s) {
        Tools.makeText(s);
    }

    @Override
    public void uploadImg(int type) {
        Tools.makeText("图片上传成功！");
    }

    @Override
    public void deletImg(int type,String url) {
        if (adapter != null) {
            adapter.deleData(url);
        }
    }

    @Override
    public void setLoading(int type,Loading loading) {
        if (null != loading && null != loading.getImgList()) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < loading.getImgList().size(); i++) {
                list.add(Constant.BASE_URL + loading.getImgList().get(i).getUrl());
            }
            adapter.setData(list);

        } else {
            Tools.makeText("图片查询失败");
        }
    }

    @Override
    public void setImeiInfo(ImeiInfo imeiInfo) {
        if (imeiInfo != null && imeiInfo.getReturnList() != null && imeiInfo.getReturnList().size() > 0) {

            ImeiInfo.ReturnListBean bean = imeiInfo.getReturnList().get(0);
            String receiveDate = bean.getReceiveDate();
            String locationDate = bean.getLocationDate();
            double lon = Double.parseDouble(bean.getLongitude());
            double lat = Double.parseDouble(bean.getLatitude());

            if (lon == 0 && lat == 0) {
                Tools.makeText("定位信息异常，请");
                return;
            }

            mActivity.showInstall(bean.getImeiId(), receiveDate, locationDate, lon, lat);


        } else {
            Tools.makeText("检测的数据为空");
        }
    }

    private void startCamera() {
        UriTools.mImageFileUri = UriTools.getOutFileUri(UriTools.TYPE_FILE_IMAGE);//得到一个File Uri
        if (UriTools.mImageFileUri != null) {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, UriTools.mImageFileUri);
            this.startActivityForResult(intent, STARTCAMERA);
        } else {
            Tools.makeText("请假插入SD卡！");
        }
    }

    private final int STARTCAMERA = 101;
    public static final int RC_CAMERA_AND_LOCATION = 100;

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void openCamera() {
        //&& EasyPermissions.somePermissionDenied(this, perms)
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            // 已经有了许可，做这件事
            startCamera();
        } else {
            // 没有权限，现在就请求它们
            EasyPermissions.requestPermissions(this, "是否开启相机权限，否则无法使用此功能",
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //已授予某些权限。
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        String[] perm = new String[perms.size()];
        for (int i = 0; i < perms.size(); i++) {
            perm[i] = perms.get(i);
        }


        //某些权限已被拒绝。用户拒绝后
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置界面以修改应用权限")
                    .setTitle("必需权限")
                    .build()
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
