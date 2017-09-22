package com.hxyd.dyt.purplestar.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hxyd.dyt.R;
import com.hxyd.dyt.purplestar.view.LocationSettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by win7 on 2017/8/28.
 */

public class SettingFragment extends Fragment {


    private LocationSettingActivity mActivity;

    @BindView(R.id.location_setting_updata_time)
    RelativeLayout ll;

    private String imeiId;
    private int type;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (LocationSettingActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type", -1);
        imeiId = getArguments().getString("imeiId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.location_setting_updata_time, R.id.location_setting_device})
    public void onClick(View v) {
        if (v.getId() == R.id.location_setting_updata_time) {
            mActivity.showModifyUploadTimeFragment();
        } else if (v.getId() == R.id.location_setting_device) {
            mActivity.showDeviceLocationAuthorityFragment();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (0 == type) {
            ll.setVisibility(View.GONE);
        }
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
    }
}
