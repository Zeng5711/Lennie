package com.hxyd.dyt.accountManager.view;


import android.os.Bundle;

import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.view.View;


import com.hxyd.dyt.R;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.uitl.BusEvent;

import com.hxyd.dyt.widget.adapter.accountManager.AccountTaskAdapter;

import butterknife.BindView;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


/**
 * Created by win7 on 2017/5/10.
 */

public class AccountTaskActivity extends BaseActivity {

    @BindView(R.id.account_task_tabs)
    TabLayout tabLayout;
    @BindView(R.id.account_task_viewpager)
    ViewPager viewPager;

    private AccountTaskAdapter pagerAdapter;
    private int type;

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        setIL(R.mipmap.back);
        isShowSpoy(false);
        isShowTR(false);
        isShowIR(false);

        getIL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        type = getIntent().getIntExtra("type", -1);
        if (type == 3) {
            setTitle("评估师");
        } else if (type == 4) {
            setTitle("GPS安装");
        } else if (type == 5) {
            setTitle("车辆抵押");
        }


        pagerAdapter = new AccountTaskAdapter(getSupportFragmentManager(), this,type);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.account_task_fragment);
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {

    }
}
