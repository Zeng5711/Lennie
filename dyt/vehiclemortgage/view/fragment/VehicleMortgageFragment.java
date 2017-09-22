package com.hxyd.dyt.vehiclemortgage.view.fragment;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.hxyd.dyt.R;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.CardBean;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.common.uitl.UriTools;
import com.hxyd.dyt.gpsinstallation.entity.ImeiInfo;
import com.hxyd.dyt.gpsinstallation.entity.Loading;
import com.hxyd.dyt.gpsinstallation.presenter.LoadingP;
import com.hxyd.dyt.gpsinstallation.presenter.in.LoadingPI;
import com.hxyd.dyt.gpsinstallation.view.in.LoadingVI;
import com.hxyd.dyt.vehiclemortgage.presenter.VehicleEvaluationP;
import com.hxyd.dyt.vehiclemortgage.presenter.in.VehicleEvaluationPI;
import com.hxyd.dyt.vehiclemortgage.view.VehicleMortgageActivity;
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

public class VehicleMortgageFragment extends Fragment implements LoadingVI, EasyPermissions.PermissionCallbacks {

    private VehicleMortgageActivity mActivity;
    private OptionsPickerView pvUsage_of_loan;
    private MortageMaterialAdapter materialAdapter, contractAdapter;
    private ImageLoader imageLoader;
    private ImgSelConfig config;
    private LoadingPI p;

    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.rv2)
    RecyclerView contracRV;

    @BindView(R.id.vehicle_mortgage_start)
    TextView vehicle_mortgage_start;

    private int FALG = 0;
    private final int MATERIAL = 1;
    private final int CONTRACT = 2;
    private final int STARTLMAGESELECTOR = 2;
    private final int STARTCAMERA = 101;
    private final int RC_CAMERA_AND_LOCATION = 100;

    private String imageName = "";
    private String businessId = "";
    private String mortgageState = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (VehicleMortgageActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessId = getArguments().getString("businessId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vehicle_mortgage, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        p = new LoadingP(this);
        usageItem.add(new CardBean("0", "是"));
        usageItem.add(new CardBean("1", "否"));
        initCPUsage();
        pvUsage_of_loan.setPicker(usageItem);

        imageLoader = new ImageLoader() {

            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                ImageloadTools.load(context, path, imageView);
            }
        };


        materialAdapter = new MortageMaterialAdapter(getContext(), new MortageMaterialAdapter.SelectListener() {
            @Override
            public void showLmageselector(int num, String name,int position) {
                FALG = MATERIAL;
                imageName = name;
                startLmageselector(num);
            }

            @Override
            public void showImageDetail(ArrayList<String> list, int currentItem, int position, boolean isShowDelete) {
                FALG = MATERIAL;
                startImageDetail(list, currentItem, position, isShowDelete);
            }

            @Override
            public void showCamer(String name,int position) {
                FALG = MATERIAL;
                imageName = name;
                openCamera();
            }
        });
        rv.setAdapter(materialAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new RecyclerSpace(5));

        contractAdapter = new MortageMaterialAdapter(getContext(), new MortageMaterialAdapter.SelectListener() {
            @Override
            public void showLmageselector(int num, String name,int position) {
                FALG = CONTRACT;
                imageName = name;
                startLmageselector(num);
            }

            @Override
            public void showImageDetail(ArrayList<String> list, int currentItem, int position, boolean isShowDelete) {
                FALG = CONTRACT;
                startImageDetail(list, currentItem, position, isShowDelete);
            }

            @Override
            public void showCamer(String name,int position) {
                FALG = CONTRACT;
                imageName = name;
                openCamera();
            }
        });
        contracRV.setAdapter(contractAdapter);
        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 3);
        contracRV.setLayoutManager(layoutManager2);
        contracRV.addItemDecoration(new RecyclerSpace(5));
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

    @OnClick({R.id.button2, R.id.vehicle_mortgage_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vehicle_mortgage_rl:
                if (null != pvUsage_of_loan) {
                    pvUsage_of_loan.show();
                }
                break;
            case R.id.button2:
                if (TextUtils.isEmpty(vehicle_mortgage_start.getText().toString().trim())) {
                    Tools.makeText("请选择抵押状态");
                    return;
                }

                if (materialAdapter.getDataSize() == 0) {
                    Tools.makeText("抵押材料不能为空");
                    return;
                }

                if (contractAdapter.getDataSize() == 0) {
                    Tools.makeText("合同资料不能为空");
                    return;
                }

                p.updateLoanAudiByBussinessId(businessId, mortgageState);

                break;
        }
    }

    private ArrayList<CardBean> usageItem = new ArrayList<>();

    private void initCPUsage() {
        pvUsage_of_loan = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (usageItem == null) {
                    return;
                }

                if (usageItem.isEmpty()) {
                    return;
                }
                //返回的分别是三个级别的选中位置
                String tx = usageItem.get(options1).getPickerViewText();
                mortgageState = usageItem.get(options1).getCode();
                vehicle_mortgage_start.setText(tx);
            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置滚轮文字大小
                .build();

    }

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
    public void deletImg(int type, String url) {

        if (type == p.materialsImages) {
            if (materialAdapter != null) {
                materialAdapter.deleData(url);
            }
        } else if (type == p.contractImages) {
            if (contractAdapter != null) {
                contractAdapter.deleData(url);
            }
        }

    }

    @Override
    public void setLoading(int type, Loading loading) {
        if (null != loading && null != loading.getImgList()) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < loading.getImgList().size(); i++) {
                list.add(Constant.BASE_URL + "/" + loading.getImgList().get(i).getUrl());
            }
            if (type == p.materialsImages) {
                materialAdapter.setData(list);
            } else if (type == p.contractImages) {
                contractAdapter.setData(list);
            }

        } else {
            Tools.makeText("图片查询失败");
        }
    }

    @Override
    public void setImeiInfo(ImeiInfo imeiInfo) {
        Tools.makeText("提交成功");
        mActivity.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STARTLMAGESELECTOR && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (FALG == MATERIAL) {
                materialAdapter.setData(pathList);
                p.uploadImg(p.materialsImages, businessId, imageName, pathList.get(0), "");
            } else {
                contractAdapter.setData(pathList);
                p.uploadImg(p.contractImages, businessId, imageName, pathList.get(0), "");
            }

        } else if (requestCode == PhotoPreview.REQUEST_CODE) {
            ArrayList<String> list = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            if (list != null && !list.isEmpty() && list.size() > 0) {
                int position = data.getIntExtra(PhotoPreview.EXTRA_POSITION, -1);
                if (FALG == MATERIAL) {
                    String name = materialAdapter.getName(position);
                    p.deleteImg(p.materialsImages, businessId, name, "");
                    materialAdapter.deleData(position);
                } else {
                    String name = contractAdapter.getName(position);
                    p.deleteImg(p.contractImages, businessId, name, "");
                    contractAdapter.deleData(position);
                }
            }
        } else if (requestCode == STARTCAMERA && resultCode == -1) {
            if (UriTools.mImageFileUri != null) {
                String path = UriTools.mImageFileUri.getPath();
                if (FALG == MATERIAL) {
                    materialAdapter.setData(path);
                    p.uploadImg(p.materialsImages, businessId, imageName, path, "");
                } else {
                    contractAdapter.setData(path);
                    p.uploadImg(p.contractImages, businessId, imageName, path, "");
                }
            }
        }
    }
}
