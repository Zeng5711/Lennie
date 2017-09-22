package com.hxyd.dyt.main.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hxyd.dyt.R;
import com.hxyd.dyt.main.view.MainActivity;
import com.hxyd.dyt.widget.RecyclerSpace;
import com.hxyd.dyt.widget.adapter.accountManager.BannerAdapter;
import com.hxyd.dyt.widget.adapter.main.MainAdapter;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/9/7.
 */

public class MainFragment extends Fragment {


    @BindView(R.id.mian_rv)
    RecyclerView rv;

    @BindView(R.id.roll_viewpager)
    RollPagerView mRollViewPager;

//    @BindView(R.id.main_fragment_ll)
//    LinearLayout linearLayout;

    private MainActivity mainActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = ((MainActivity) getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mian_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //设置播放时间间隔
        mRollViewPager.setPlayDelay(3000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(1000);
        mRollViewPager.setHintView(new ColorPointHintView(getContext(), Color.YELLOW,Color.WHITE));
        mRollViewPager.setAdapter(new BannerAdapter());

        //实例化Adapter并且给RecyclerView设上
        MainAdapter adapter = new MainAdapter(getContext());
        rv.setAdapter(adapter);

        // 如果我们想要一个GridView形式的RecyclerView，那么在LayoutManager上我们就要使用GridLayoutManager
        // 实例化一个GridLayoutManager，列数为3
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rv.addItemDecoration(new RecyclerSpace(2, Color.argb(255,246,246,246), 0));

        //调用以下方法让RecyclerView的第一个条目仅为1列
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                //如果位置是0，那么这个条目将占用SpanCount()这么多的列数，再此也就是3
//                //而如果不是0，则说明不是Header，就占用1列即可
//                return layoutManager.getSpanCount();
//            }
//        });



        rv.setLayoutManager(layoutManager);

//        int bottomHegiht = mainActivity.getBottomHeight();
//        linearLayout.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        int linearLayoutHeight = linearLayout.getMeasuredHeight();
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, linearLayoutHeight + bottomHegiht + 20);
//        linearLayout.setLayoutParams(layoutParams);

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
