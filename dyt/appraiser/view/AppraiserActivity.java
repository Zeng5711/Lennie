package com.hxyd.dyt.appraiser.view;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.hxyd.dyt.R;
import com.hxyd.dyt.appraiser.view.fragment.CarAssessmentFragment;
import com.hxyd.dyt.appraiser.view.fragment.CarInfoFragment;
import com.hxyd.dyt.common.BaseActivity;

import butterknife.BindView;

public class AppraiserActivity extends BaseActivity {


    private String TAG_FALG = "";
    private String TAG_CAR_INFO = "CAR_INFO";
    private String TAG_CAR_ASS = "CAR_ASS";


    private FragmentManager fm;
    private CarInfoFragment carInfo;
    private CarAssessmentFragment catAss;

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
        } else if (TAG_FALG.equals(TAG_CAR_ASS)) {
            setTitle("车辆信息");
            switchContent(catAss, carInfo, TAG_CAR_INFO);
        }
    }

    private void initFragment() {

        String businessId = getIntent().getStringExtra("businessId");
        String taskId = getIntent().getStringExtra("taskId");
        String processInstanceId = getIntent().getStringExtra("processInstanceId");
        String brand = getIntent().getStringExtra("brand");
        int page = getIntent().getIntExtra("page", -1);

        Bundle bundle = new Bundle();
        bundle.putString("businessId", businessId);
        bundle.putString("taskId", taskId);
        bundle.putString("processInstanceId", processInstanceId);
        bundle.putInt("page", page);
        bundle.putString("brand", brand);

        carInfo = new CarInfoFragment();
        carInfo.setArguments(bundle);
        catAss = new CarAssessmentFragment();
        catAss.setArguments(bundle);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showASS() {
        setTitle("车辆评估分析");
        switchContent(carInfo, catAss, TAG_CAR_ASS);
    }
}
