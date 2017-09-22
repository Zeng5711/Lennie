package com.hxyd.dyt.vehiclemortgage.view;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.vehiclemortgage.view.fragment.VehicleEvaluationFragment;
import com.hxyd.dyt.vehiclemortgage.view.fragment.VehicleMortgageFragment;

import butterknife.BindView;

public class VehicleMortgageActivity extends BaseActivity {


    private String TAG_FALG = "";
    private String TAG_VEHICLE_MORTGAGE = "VEHICLE_MORTGAGE";
    private String TAG_VEHICLE_EVALUATION = "VEHICLE_EVALUATION";

    private FragmentManager fm;
    private VehicleMortgageFragment vehicleMort;
    private VehicleEvaluationFragment vehicleEValuation;

    private String businessId;




    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        setIL(R.mipmap.back);
        isShowTR(false);
        isShowIR(false);
        isShowSpoy(false);
        setTitle("查看信息");
        initFragment();
        getIL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    private void back() {
        if (TAG_FALG.equals(TAG_VEHICLE_EVALUATION)) {
            finish();
        } else if (TAG_FALG.equals(TAG_VEHICLE_MORTGAGE)) {
            setTitle("查看信息");
            switchContent(vehicleMort, vehicleEValuation, TAG_VEHICLE_EVALUATION);
        }
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_vehicle_mortgage);
    }

    private void initFragment() {

        businessId = getIntent().getStringExtra("businessId");
        int page = getIntent().getIntExtra("page", -1);
        Bundle bundle = new Bundle();
        bundle.putString("businessId", businessId);
        bundle.putInt("page", page);

        vehicleEValuation = new VehicleEvaluationFragment();
        vehicleEValuation.setArguments(bundle);

        vehicleMort = new VehicleMortgageFragment();
        vehicleMort.setArguments(bundle);

        TAG_FALG = TAG_VEHICLE_EVALUATION;
        changeFragment(true, vehicleEValuation, TAG_VEHICLE_EVALUATION);

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

    public void showVehicleMort() {
        setTitle("车辆抵押");
        switchContent(vehicleEValuation, vehicleMort, TAG_VEHICLE_MORTGAGE);
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
