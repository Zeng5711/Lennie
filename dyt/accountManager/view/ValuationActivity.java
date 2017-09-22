package com.hxyd.dyt.accountManager.view;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.view.fragment.ValuationHelp;
import com.hxyd.dyt.accountManager.view.fragment.ValuationResult;
import com.hxyd.dyt.common.BaseActivity;

import butterknife.BindView;

public class ValuationActivity extends BaseActivity {


    private FragmentManager fm;
    private ValuationHelp vh;
    private ValuationResult vr;

    private static String currentTAG = "";
    public static String TAG_VH = "valuationHelp";
    public static String TAG_VR = "valuationResult";

    public ValuationHelp getVh() {
        return vh;
    }

    public ValuationResult getVr() {
        return vr;
    }

    public void showClose() {
        setTitle("评估结果");
        isShowTR(true);
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        isShowTR(false);
        isShowIR(false);
        isShowSpoy(false);
        isCkeckBack(false);
        setIL(R.mipmap.back);
        setTR("关闭");
        setTitle("帮你评估");
        getTR().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValuationActivity.this.finish();
            }
        });
        getIL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentBack();
            }
        });
        initFragment();
    }

    private void initFragment() {
        vh = new ValuationHelp();
        vr = new ValuationResult();
        currentTAG = TAG_VR;
        changeFragment(true, vr, currentTAG);
        switchContent(vr, vh, TAG_VH);
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_valuation);
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
            if (isadd) {
                ft.add(R.id.activity_valuation_fragment, f, tag);
            } else {
                ft.replace(R.id.activity_valuation_fragment, f, tag);
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }

    public void switchContent(Fragment from, Fragment to, String tag) {
        if (null != from && null != to) {
            FragmentTransaction transaction = getMyFragmentManager().beginTransaction();
            currentTAG = tag;
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.activity_valuation_fragment, to, tag).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            fragmentBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void fragmentBack() {
        if (currentTAG.equals(TAG_VH)) {
            this.finish();
        } else if (currentTAG.equals(TAG_VR)) {
            setTitle("帮你评估");
            switchContent(vr, vh, TAG_VH);
            isShowTR(false);
        }
    }

    public void setC2BPrices(String C2BPrices) {
        if (vr != null) {
            vr.setMoneyText(C2BPrices);
        }
    }

}
