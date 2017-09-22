package com.hxyd.dyt.accountManager.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.view.OrderActivity;
import com.hxyd.dyt.accountManager.view.SingleNewActivity;
import com.hxyd.dyt.common.uitl.BusEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by win7 on 2017/5/15.
 */

public class SingleHomeFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({R.id.single_bt, R.id.single_loan})
    public void onClick(View v) {
        if (v.getId() == R.id.single_bt) {
            EventBus.getDefault().post(new BusEvent(BusEvent.FRAGMENT_CUSTOMERINFO,this));
        }  else if (v.getId() == R.id.single_loan) {

        }
    }



    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {

    }
}
