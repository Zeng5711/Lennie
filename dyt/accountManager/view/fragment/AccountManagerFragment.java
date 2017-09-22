package com.hxyd.dyt.accountManager.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxyd.dyt.accountManager.view.HelpActivity;
import com.hxyd.dyt.accountManager.view.OrderActivity;
import com.hxyd.dyt.accountManager.view.SettingActivity;
import com.hxyd.dyt.accountManager.view.SingleNewActivity;
import com.hxyd.dyt.accountManager.view.ValuationActivity;
import com.hxyd.dyt.accountManager.view.ValuationSystemActivity;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.SharedPrefsUtil;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.main.view.MainActivity;
import com.hxyd.dyt.R;
import com.hxyd.dyt.purplestar.view.ContainerActivity;
import com.hxyd.dyt.purplestar.view.PurpleStarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by win7 on 2017/5/10.
 */

public class AccountManagerFragment extends Fragment {

    @BindView(R.id.linearLayout)
    public RelativeLayout linearLayout;

    @BindView(R.id.activity_main_tv_total_application)
    public TextView tvCountTotal;
    @BindView(R.id.activity_main_tv_username)
    public TextView mUserName;
    @BindView(R.id.activity_main_tv_usertitle)
    public TextView mUserTitle;

//    @BindView(R.id.activity_main_rl_gps)
//    RelativeLayout rlGPS;

    private MainActivity mainActivity;
    private long clickTime = 0;

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
        View view = inflater.inflate(R.layout.activity_account_manager, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = ((MainActivity) getActivity());

        String username = SharedPrefsUtil.getValue(null, Constant.USER_NAME, "");
        String roleName = SharedPrefsUtil.getValue(null, Constant.ROLE_NAME, "");
        mUserName.setText(username);
        mUserTitle.setText(roleName);
        tvCountTotal.setText("已申请" + 0 + "单");


//        if (!SharedPrefsUtil.getValue(null, Constant.IS_GPS_PERMISSION, false)) {
//            rlGPS.setVisibility(View.GONE);
//        }

        int bottomHegiht = mainActivity.getBottomHeight();
        int linearLayoutHeight = getLinearLayoutHeight();
//        DisplayMetrics metric = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, linearLayoutHeight + bottomHegiht + 20);
//        layoutParams.setMargins(0, 0, 0, (bottomHegiht) * (metric.densityDpi) / DisplayMetrics.DENSITY_DEFAULT);
        linearLayout.setLayoutParams(layoutParams);
    }

    public int getLinearLayoutHeight() {
        linearLayout.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        return linearLayout.getMeasuredHeight();
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
        mainActivity = null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setCountTotal(String countTotal) {

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {
        switch (event.event) {
            case BusEvent.REFRESH_COUNTTOTAL:
                if (null != tvCountTotal) {
                    String countTotal = TextUtils.isEmpty(event.para) ? "0" : event.para;
                    tvCountTotal.setText("已申请" + countTotal + "单");
                }
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.activity_main_tv_assessment,
            R.id.activity_main_rl_setting,
            //R.id.activity_main_bt_single,
            // R.id.activity_main_rl_my_help,R.id.activity_main_rl_gps
            R.id.single_home_rl_order
            })
    public void onClick(View v) {

        if ((System.currentTimeMillis() - clickTime) > 500) {
            clickTime = System.currentTimeMillis();

            switch (v.getId()) {
                case R.id.activity_main_tv_assessment:
                    if (mainActivity != null) {
                        mainActivity.startActivity(ValuationActivity.class);
                    }
                    break;
                case R.id.activity_main_rl_setting:
                    if (mainActivity != null) {
                        mainActivity.startActivity(SettingActivity.class);
                    }
                    break;
//                case R.id.activity_main_bt_single:
//                    if (mainActivity != null) {
//                        mainActivity.startActivity(SingleNewActivity.class);
//                    }
//                    break;
//            case R.id.activity_main_rl_my_help:
//                if (mainActivity != null) {
//                    mainActivity.startActivity(HelpActivity.class);
//                }
//                break;
                case R.id.single_home_rl_order:
                    startActivity(OrderActivity.class);
                    break;
//                case R.id.activity_main_rl_gps:
//                    Intent intent;
//                    if (SharedPrefsUtil.getValue(null, Constant.ACCOUNT_NAME, "").equals("admin")) {
//                        intent = new Intent(getActivity(), PurpleStarActivity.class);
//                    } else {
//                        intent = new Intent(getActivity(), ContainerActivity.class);
//                        intent.putExtra("showData", true);
//                        intent.putExtra("ACCOUNT_NAME",false);
//                    }
//                    getActivity().startActivity(intent);
//                    break;
                default:
                    break;
            }
        }
    }


    private void startActivity(Class cla) {
        Intent intent = new Intent(getActivity(), cla);
        getActivity().startActivity(intent);
    }



}
