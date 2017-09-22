package com.hxyd.dyt.main.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hxyd.dyt.R;
import com.hxyd.dyt.main.view.MainActivity;
import com.hxyd.dyt.widget.adapter.main.ShowPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/9/7.
 */

public class ShowPageFragment extends Fragment {


    @BindView(R.id.account_task_tabs)
    TabLayout tabLayout;
    @BindView(R.id.account_task_viewpager)
    ViewPager viewPager;

    private ShowPageAdapter pagerAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_task_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pagerAdapter = new ShowPageAdapter(getFragmentManager(),getContext());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        int bottomHegiht = ((MainActivity) getActivity()).getBottomHeight();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, bottomHegiht);
        viewPager.setLayoutParams(layoutParams);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
