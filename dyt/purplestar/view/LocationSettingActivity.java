package com.hxyd.dyt.purplestar.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.hxyd.dyt.R;
import com.hxyd.dyt.purplestar.view.fragment.DeviceLocationAuthorityFragment;
import com.hxyd.dyt.purplestar.view.fragment.ModifyUploadTimeFragment;
import com.hxyd.dyt.purplestar.view.fragment.SettingFragment;


public class LocationSettingActivity extends com.hxyd.dyt.common.BaseActivity {


    private FragmentManager fm;
    private SettingFragment stf;
    private ModifyUploadTimeFragment mutf;
    private DeviceLocationAuthorityFragment dlaf;
    private String imeiId;


    private static String CURRENT_TAG = "";
    private static String STF_TAG = "SettingFragment";
    private static String MUTF_TAG = "ModifyUploadTimeFragment";
    private static String DLAF_TAG = "DeviceLocationAuthorityFragment";

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        setIL(R.mipmap.back);
        isShowSpoy(false);
        isShowTR(false);
        isShowIR(false);
        isShowIR(false);
        setTitle("配置");
        imeiId = getIntent().getStringExtra("imeiId");
        initFragment();

        getIL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

    }

    private void back() {
        setTitle("配置");
        if (CURRENT_TAG.equals(STF_TAG)) {
            finish();
        } else if (CURRENT_TAG.equals(MUTF_TAG)) {
            switchContent(mutf, stf, STF_TAG);
        } else if (CURRENT_TAG.equals(DLAF_TAG)) {
            switchContent(dlaf, stf, STF_TAG);
        }
    }

    private void initFragment() {
        int type = getIntent().getIntExtra("type", -1);
        Bundle bundle = new Bundle();
        bundle.putString("imeiId", imeiId);
        bundle.putInt("type", type);

        stf = new SettingFragment();
        stf.setArguments(bundle);

        mutf = new ModifyUploadTimeFragment();
        mutf.setArguments(bundle);

        dlaf = new DeviceLocationAuthorityFragment();
        dlaf.setArguments(bundle);
        changeFragment(true, stf, STF_TAG);
    }

    public void showModifyUploadTimeFragment() {
        setTitle("上传修改时间");
        switchContent(stf, mutf, MUTF_TAG);
    }

    public void showDeviceLocationAuthorityFragment() {
        setTitle("开启设备定位权限");
        switchContent(stf, dlaf, DLAF_TAG);
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_location_setting);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private FragmentManager getMyFragmentManager() {
        if (fm == null) {
            fm = getSupportFragmentManager();
        }
        return fm;
    }

    private void changeFragment(boolean isadd, Fragment f, String tag) {
        if (f != null) {
            FragmentTransaction ft = getMyFragmentManager().beginTransaction();
            CURRENT_TAG = tag;
            if (isadd) {
                ft.add(R.id.location_setting, f, tag);
            } else {
                ft.replace(R.id.location_setting, f, tag);
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }

    private void switchContent(Fragment from, Fragment to, String tag) {
        if (null != from && null != to) {
            FragmentTransaction transaction = getMyFragmentManager().beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.location_setting
                                , to, tag).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            CURRENT_TAG = tag;
        }
    }

}
