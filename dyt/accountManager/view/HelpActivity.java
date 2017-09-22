package com.hxyd.dyt.accountManager.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.widget.adapter.accountManager.HelpFragmentAdapter;

import butterknife.BindView;

public class HelpActivity extends BaseActivity {

    @BindView(R.id.help_tabs)
    TabLayout tabLayout;
    @BindView(R.id.help_viewpager)
    ViewPager viewPager;

    private HelpFragmentAdapter pagerAdapter;

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        setIL(R.mipmap.back);
        setTitle("帮助");
        isShowSpoy(false);
        isShowTR(false);
        isShowIR(false);

        pagerAdapter = new HelpFragmentAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_help);
    }

}
