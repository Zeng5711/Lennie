package com.hxyd.dyt.gpsinstallation.view;

import android.Manifest;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;


import com.hxyd.dyt.R;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.gpsinstallation.view.fragment.CarInfoFragment;
import com.hxyd.dyt.gpsinstallation.view.fragment.DeviceInfoFragment;
import com.hxyd.dyt.gpsinstallation.view.fragment.InstallationTestFragment;
import com.hxyd.dyt.gpsinstallation.view.fragment.LoadingFragment;

//import permissions.dispatcher.NeedsPermission;
//import permissions.dispatcher.OnNeverAskAgain;
//import permissions.dispatcher.OnPermissionDenied;
//import permissions.dispatcher.OnShowRationale;
//import permissions.dispatcher.PermissionRequest;

public class GPSInstallActivity extends BaseActivity {

    private String TAG_FALG = "";
    private String TAG_CAR_INFO = "CAR_INFO";
    private String TAG_DEVICE_INFO = "DEVICE_INFO";
    private String TAG_LOADING_INFO = "LOADING_INFO";
    private String TAG_INSTALL_INFO = "INSTALL_INFO";

    private FragmentManager fm;
    private CarInfoFragment carInfo;
    private DeviceInfoFragment deviceInfo;
    private LoadingFragment loading;
    private InstallationTestFragment install;

    private String businessId;
    private String taskId = "";
    private String processInstanceId = "";
    private int page = -1;

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        setIL(R.mipmap.back);
        isShowTR(false);
        isShowIR(false);
        isShowSpoy(false);
        setTitle("车辆信息");
        initFragment();
        getIL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    private void back() {
        if (TAG_FALG.equals(TAG_CAR_INFO)) {
            finish();
        } else if (TAG_FALG.equals(TAG_DEVICE_INFO)) {
            setTitle("车辆信息");
            switchContent(deviceInfo, carInfo, TAG_CAR_INFO);
        } else if (TAG_FALG.equals(TAG_LOADING_INFO)) {
            setTitle("设备信息");
            switchContent(loading, deviceInfo, TAG_DEVICE_INFO);
        } else if (TAG_FALG.equals(TAG_INSTALL_INFO)) {
            setTitle("装车位置");
            switchContent(install, loading, TAG_LOADING_INFO);
        }
    }

    private void initFragment() {

        businessId = getIntent().getStringExtra("businessId");
        taskId = getIntent().getStringExtra("taskId");
        processInstanceId = getIntent().getStringExtra("processInstanceId");
        page = getIntent().getIntExtra("page", -1);

        Bundle bundle = new Bundle();
        bundle.putString("businessId", businessId);
        bundle.putString("taskId", taskId);
        bundle.putInt("page", page);
        bundle.putString("processInstanceId", processInstanceId);

        carInfo = new CarInfoFragment();
        carInfo.setArguments(bundle);

        deviceInfo = new DeviceInfoFragment();
        loading = new LoadingFragment();
        install = new InstallationTestFragment();
        install.setArguments(bundle);
        TAG_FALG = TAG_CAR_INFO;
        changeFragment(true, carInfo, TAG_CAR_INFO);
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_appraiser);
    }

    private void changeFragment(Fragment f, String tag) {
        changeFragment(false, f, tag);
    }

    private void changeFragment(boolean isadd, Fragment f, String tag) {
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (isadd) {
            ft.add(R.id.framelayout, f, tag);
        } else {
            ft.replace(R.id.framelayout, f);
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    private void switchContent(Fragment from, Fragment to, String tag) {
        if (null != from && null != to) {
            TAG_FALG = tag;
            FragmentTransaction transaction = fm.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.framelayout, to, tag).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    public void showDeviceInfo(String custId) {
        setTitle("设备信息");
        deviceInfo.setData(businessId, custId);
        switchContent(carInfo, deviceInfo, TAG_DEVICE_INFO);
    }

    public void showLoading(String businessId, String imeiIds) {
        setTitle("装车位置");
        loading.setBusinessId(businessId, imeiIds);
        switchContent(deviceInfo, loading, TAG_LOADING_INFO);
    }

    public void showInstall(String imeiId, String receiveDate, String locationDate, double lon, double lat) {
        setTitle("安全检查");
        install.setData(imeiId, receiveDate, locationDate, lon, lat);
        switchContent(loading, install, TAG_INSTALL_INFO);
    }

    public void showDeviceInfo() {
//        deviceInfo.clean();
        setTitle("设备信息");
        switchContent(install, deviceInfo, TAG_DEVICE_INFO);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
