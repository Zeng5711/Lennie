package com.hxyd.dyt.appraiser.view.fragment;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.hxyd.dyt.R;
import com.hxyd.dyt.appraiser.view.AppraiserActivity;
import com.hxyd.dyt.common.entity.CardBean;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.common.uitl.UriTools;
import com.hxyd.dyt.gpsinstallation.entity.ImeiInfo;
import com.hxyd.dyt.gpsinstallation.entity.Loading;
import com.hxyd.dyt.gpsinstallation.presenter.LoadingP;
import com.hxyd.dyt.gpsinstallation.presenter.in.LoadingPI;
import com.hxyd.dyt.gpsinstallation.view.in.LoadingVI;
import com.hxyd.dyt.widget.ContainsEmojiEditText;
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
 * Created by win7 on 2017/9/11.
 */

public class CarAssessmentFragment extends Fragment implements LoadingVI, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.car_vdv_tv)
    TextView car_vdv_tv;

    @BindView(R.id.car_built_in_lock)
    TextView lockTV;

    @BindView(R.id.car_fault)
    TextView faultTV;

    @BindView(R.id.car_assess_explain_)
    TextView explainTV;

    @BindView(R.id.car_vdv)
    TextView car_vdv;

    @BindView(R.id.car_built_in_lock_)
    ContainsEmojiEditText car_built_in_lock_;

    @BindView(R.id.vehicleinfo_scd)
    EditText vehicleinfo_scd;

    @BindView(R.id.car_assess)
    ContainsEmojiEditText car_assess;

    @BindView(R.id.car_assess_explain)
    EditText car_assess_explain;

    private AppraiserActivity mActivity;
    private MortageMaterialAdapter adapter;
    private OptionsPickerView lock, fault, explain;
    private ArrayList<CardBean> lockItem = new ArrayList<>();
    private ArrayList<CardBean> faultItem = new ArrayList<>();
    private ArrayList<CardBean> explainItem = new ArrayList<>();
    private ImageLoader imageLoader;
    private ImgSelConfig config;
    private LoadingPI p;

    private static final int STARTLMAGESELECTOR = 2;
    private final int STARTCAMERA = 101;
    public static final int RC_CAMERA_AND_LOCATION = 100;

    private String lockState = "";
    private String faultState = "";
    private String explainState = "";
    private String businessId = "";
    private String imageName = "";
    private String taskId = "";
    private String processInstanceId = "";
    private String brand = "";
    private int index;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppraiserActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessId = getArguments().getString("businessId");
        taskId = getArguments().getString("taskId");
        processInstanceId = getArguments().getString("processInstanceId");
        brand = getArguments().getString("brand");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_assessment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        p = new LoadingP(this);

        car_vdv.setText(brand);
        car_vdv_tv.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int brandWidth = Tools.getwidthPixels(getContext()) - car_vdv_tv.getMeasuredWidth() - (int) (Tools.getDisplayDensity(getContext()) * 70);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(brandWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.alignWithParent = true;
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        car_vdv.setLayoutParams(layoutParams);
        car_vdv.setGravity(Gravity.RIGHT);

        lockItem.add(new CardBean("0", "是"));
        lockItem.add(new CardBean("1", "否"));

        faultItem.add(new CardBean("1", "正常"));
        faultItem.add(new CardBean("2", "轻微事故"));
        faultItem.add(new CardBean("3", "小事故"));
        faultItem.add(new CardBean("4", "大事故"));
        faultItem.add(new CardBean("5", "水泡车"));
        faultItem.add(new CardBean("6", "火烧车"));

        explainItem.add(new CardBean("1", "正常"));
        explainItem.add(new CardBean("0", "拒绝"));

        initLock();
        initFault();
        initExplain();

        lock.setPicker(lockItem);
        fault.setPicker(faultItem);
        explain.setPicker(explainItem);

        imageLoader = new ImageLoader() {

            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                ImageloadTools.load(context, path, imageView);
            }
        };


        adapter = new MortageMaterialAdapter(getContext(), new MortageMaterialAdapter.SelectListener() {

            @Override
            public void showLmageselector(int num, String name, int position) {
                imageName = name;
                index = position;
                startLmageselector(num);
            }

            @Override
            public void showImageDetail(ArrayList<String> list, int currentItem, int position, boolean isShowDelete) {
                startImageDetail(list, currentItem, position, isShowDelete);
            }

            @Override
            public void showCamer(String name, int position) {
                imageName = name;
                index = position;
                openCamera();
            }
        });
        rv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rv.setLayoutManager(layoutManager);
        adapter.setCarAssessmentData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STARTLMAGESELECTOR && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            adapter.setData(index, pathList.get(0));
            p.uploadImg(p.carImages, businessId, imageName, pathList.get(0), "");
        } else if (requestCode == PhotoPreview.REQUEST_CODE) {
            ArrayList<String> list = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            if (list != null && !list.isEmpty() && list.size() > 0) {
                int position = data.getIntExtra(PhotoPreview.EXTRA_POSITION, -1);
                String name = adapter.getName(position);
                p.deleteImg(p.positionImages, businessId, name, "");
                adapter.deleData(position);
            }
        } else if (requestCode == STARTCAMERA && resultCode == -1) {
            if (UriTools.mImageFileUri != null) {
                String path = UriTools.mImageFileUri.getPath();
                adapter.setData(index, path);
                p.uploadImg(p.carImages, businessId, imageName, path, "");
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
    }


    @OnClick({R.id.car_built_in_lock_rl, R.id.car_fault_rl,
            R.id.car_assess_explain_rl, R.id.button2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car_built_in_lock_rl:
                if (null != lock) {
                    lock.show();
                }
                break;
            case R.id.car_fault_rl:
                if (null != fault) {
                    fault.show();
                }
                break;
            case R.id.car_assess_explain_rl:
                if (null != explain) {
                    explain.show();
                }
                break;
            case R.id.button2:
                if (!isCheck()) {

                    p.saveCarEvaluation(businessId, lockState, car_built_in_lock_.getText().toString().trim(),
                            faultState, vehicleinfo_scd.getText().toString().trim(), car_assess.getText().toString().trim(),
                            car_assess_explain.getText().toString().trim(), explainState, taskId, processInstanceId);

                }
                break;
        }
    }

    private void initLock() {
        lock = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (lockItem == null) {
                    return;
                }

                if (lockItem.isEmpty()) {
                    return;
                }
                //返回的分别是三个级别的选中位置
                String tx = lockItem.get(options1).getPickerViewText();
                lockState = lockItem.get(options1).getCode();
                lockTV.setText(tx);
            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置滚轮文字大小
                .build();

    }

    private void initFault() {
        fault = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (faultItem == null) {
                    return;
                }

                if (faultItem.isEmpty()) {
                    return;
                }
                //返回的分别是三个级别的选中位置
                String tx = faultItem.get(options1).getPickerViewText();
                faultState = faultItem.get(options1).getCode();
                faultTV.setText(tx);
            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置滚轮文字大小
                .build();

    }


    private void initExplain() {
        explain = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (explainItem == null) {
                    return;
                }

                if (explainItem.isEmpty()) {
                    return;
                }
                //返回的分别是三个级别的选中位置
                String tx = explainItem.get(options1).getPickerViewText();
                explainState = explainItem.get(options1).getCode();
                explainTV.setText(tx);
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

    }

    @Override
    public void deletImg(int type, String url) {
        if (adapter != null) {
            adapter.deleData(url);
        }
    }

    @Override
    public void setLoading(int type, Loading loading) {

    }

    @Override
    public void setImeiInfo(ImeiInfo imeiInfo) {
        Tools.makeText("提交成功");
        mActivity.finish();

    }

    private boolean isCheck() {

        if (TextUtils.isEmpty(car_vdv.getText().toString().trim())) {
            Tools.makeText("车型/排量/版本不能为空");
            return true;
        }

        if (TextUtils.isEmpty(lockTV.getText().toString().trim())) {
            Tools.makeText("请选择是否有暗锁");
            return true;
        }

        if (TextUtils.isEmpty(car_built_in_lock_.getText().toString().trim())) {
            Tools.makeText("具体暗锁信息不能为空");
            return true;
        }

        if (TextUtils.isEmpty(faultTV.getText().toString().trim())) {
            Tools.makeText("请选择是否事故车");
            return true;
        }

        if (TextUtils.isEmpty(vehicleinfo_scd.getText().toString().trim())) {
            Tools.makeText("车辆表面情况不能为空");
            return true;
        }

        if (TextUtils.isEmpty(car_assess.getText().toString().trim())) {
            Tools.makeText("评估价不能为空");
            return true;
        }

        if (TextUtils.isEmpty(car_assess_explain.getText().toString().trim())) {
            Tools.makeText("车辆评估情况不能为空");
            return true;
        }

        if (TextUtils.isEmpty(explainTV.getText().toString().trim())) {
            Tools.makeText("车辆评估结果不能为空");
            return true;
        }

        if (!TextUtils.isEmpty(adapter.getAppDataSize())) {
            Tools.makeText(adapter.getAppDataSize() + "图片不能为空");
            return true;
        }

        return false;
    }


}
