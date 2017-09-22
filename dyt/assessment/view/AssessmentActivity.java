package com.hxyd.dyt.assessment.view;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.view.fragment.ValuationHelp;
import com.hxyd.dyt.accountManager.view.fragment.ValuationResult;
import com.hxyd.dyt.assessment.view.fragment.AssessmentFragment;
import com.hxyd.dyt.assessment.view.fragment.SeeCarDataFragment;
import com.hxyd.dyt.common.BaseActivity;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

public class AssessmentActivity extends BaseActivity {

    private FragmentManager fm;
    private static String currentTAG = "";
    private static String TAG_AF = "AssessmentFragment";
    private static String TAG_SF = "SeeCarDataFragment";
    private AssessmentFragment af;
    private SeeCarDataFragment sf;
    private String af_title = "评估";
    private String sf_title = "车辆资料";
    private String systemType = "";
    private String orderNo = "";

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
//        EventBus.getDefault().register(this);
        isShowIR(false);
        isShowSpoy(false);
        isCkeckBack(false);
        setIL(R.mipmap.back);
        getIL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentBack(currentTAG);
            }
        });
        setTR("查看车辆资料");
        getTR().setTextSize(12);
        getTR().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setTitle(sf_title);
                isShowTR(false);
                Bundle bundle = new Bundle();
                bundle.putString("orderNo", orderNo);
                bundle.putString("systemType", systemType);
                sf.setArguments(bundle);
                switchContent(af, sf, TAG_SF);
            }
        });
        setTitle(af_title);
        initFragment();
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_assessment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }


    private void initFragment() {

        systemType = getIntent().getStringExtra("systemType");
        orderNo = getIntent().getStringExtra("orderNo");
        Bundle bundle = new Bundle();
        bundle.putString("orderNo", orderNo);
        bundle.putString("systemType", systemType);
        af = new AssessmentFragment();
        sf = new SeeCarDataFragment();
        currentTAG = TAG_AF;
        af.setArguments(bundle);
        changeFragment(true, af, currentTAG);

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
                ft.add(R.id.assessment_fragment_ll, f, tag);
            } else {
                ft.replace(R.id.assessment_fragment_ll, f, tag);
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
                        .add(R.id.assessment_fragment_ll
                                , to, tag).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            fragmentBack(currentTAG);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void fragmentBack(String currentTAG) {
        if (TAG_AF.equals(currentTAG)) {
            this.finish();
        } else if (TAG_SF.equals(currentTAG)) {
            setTitle(af_title);
            isShowTR(true);
            switchContent(sf, af, TAG_AF);
        }
    }
}
