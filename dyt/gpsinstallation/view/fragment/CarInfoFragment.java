package com.hxyd.dyt.gpsinstallation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.gpsinstallation.entity.CarInfo;
import com.hxyd.dyt.gpsinstallation.presenter.CarInfoP;
import com.hxyd.dyt.gpsinstallation.view.GPSInstallActivity;
import com.hxyd.dyt.gpsinstallation.view.in.CarInfoVI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by win7 on 2017/9/9.
 */

public class CarInfoFragment extends Fragment implements CarInfoVI {


    private GPSInstallActivity mActivity;
    private CarInfoP p;
    private String businessId;
    private String custId;
    private int page = -1;

    @BindView(R.id.gps_install_car_num)
    TextView carNum;

    @BindView(R.id.gps_install_brand)
    TextView brand;

    @BindView(R.id.gps_install_service_mode)
    TextView model;

    @BindView(R.id.gps_install_service_time)
    TextView time;

    @BindView(R.id.gps_install_executor)
    TextView executor;

    @BindView(R.id.devic_num)
    TextView devic_num;

    @BindView(R.id.gps_install_brand_tv)
    TextView gps_install_brand_tv;

    @BindView(R.id.car_info_ll)
    LinearLayout ll;

    private int brandWidth;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (GPSInstallActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessId = getArguments().getString("businessId", "");
        page = getArguments().getInt("page");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gps_install_car, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        p = new CarInfoP(this);
        if (!TextUtils.isEmpty(businessId)) {
            p.queryGpsCarInfo(businessId);
        } else {
            Tools.makeText("businessId 为空！");
            mActivity.finish();
        }

        if (page == 1) {
            ll.setVisibility(View.GONE);
        }

        gps_install_brand_tv.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        brandWidth = Tools.getwidthPixels(getContext()) - gps_install_brand_tv.getMeasuredWidth() - (int) (Tools.getDisplayDensity(getContext()) * 70);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(brandWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.alignWithParent = true;
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        brand.setLayoutParams(layoutParams);
        brand.setGravity(Gravity.RIGHT);

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

    @OnClick(R.id.button2)
    public void onClick(View v) {
        mActivity.showDeviceInfo(custId);
    }

    @Override
    public void showDialog(String m) {
        mActivity.showProgressDialog(m);
    }

    @Override
    public void dismiss() {
        mActivity.dismiss();
    }

    @Override
    public void onErr(int type, String s) {
        Tools.makeText(s);
        mActivity.finish();
    }

    @Override
    public void setCarInfo(CarInfo carInfo) {

        if (carInfo != null && carInfo.getCarInfo() != null) {
            custId = carInfo.getCustId();
            carNum.setText(carInfo.getCarInfo().getPlate_no());
            brand.setText(carInfo.getCarInfo().getCar_brand());
//            int textlength = (int) brand.getPaint().measureText(carInfo.getCarInfo().getCar_brand());
//            if (textlength > brandWidth) {
//                brand.setGravity(Gravity.LEFT);
//            } else {
//                brand.setGravity(Gravity.RIGHT);
//            }

            model.setText(carInfo.getCarInfo().getService_method());
            time.setText(carInfo.getCarInfo().getStart_date());
            executor.setText(carInfo.getCarInfo().getAssignee());
            devic_num.setText("已安装设备（" + carInfo.getSum() + "）");
        } else {
            Tools.makeText("carInfo 数据为空");
        }

    }
}
