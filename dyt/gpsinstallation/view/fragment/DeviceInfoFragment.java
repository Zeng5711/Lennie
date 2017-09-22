package com.hxyd.dyt.gpsinstallation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.gpsinstallation.entity.DeviceInfo;
import com.hxyd.dyt.gpsinstallation.presenter.DeviceInfoP;
import com.hxyd.dyt.gpsinstallation.presenter.in.DeviceInfoPI;
import com.hxyd.dyt.gpsinstallation.view.GPSInstallActivity;
import com.hxyd.dyt.gpsinstallation.view.in.DeviceInfoVI;
import com.hxyd.dyt.widget.ContainsEmojiEditText;
import com.hxyd.dyt.zxing.ScanActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by win7 on 2017/9/9.
 */

public class DeviceInfoFragment extends Fragment implements DeviceInfoVI {
    private GPSInstallActivity mActivity;

    @BindView(R.id.device_information_IMEI)
    ContainsEmojiEditText mIMEI;

    @BindView(R.id.device_information_sim)
    EditText device_information_sim;

    @BindView(R.id.device_information_type)
    TextView device_information_type;

    @BindView(R.id.device_information_name)
    TextView device_information_name;

    private String businessId;
    private String custId;
    private DeviceInfoPI p;
    private int type;


    public void setData(String businessId, String custId) {
        this.businessId = businessId;
        this.custId = custId;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (GPSInstallActivity) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_information, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        p = new DeviceInfoP(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.button2, R.id.device_information_scanning})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:


                if (TextUtils.isEmpty(mIMEI.getText().toString().trim())) {
                    Tools.makeText("IMEI号不能为空");
                    return;
                }

                if (TextUtils.isEmpty(device_information_sim.getText().toString().trim())) {
                    Tools.makeText("SIM卡号不能为空");
                    return;
                }

                Map<String, Object> map = new HashMap<>();
                map.put("businessId", businessId);
                map.put("imeiId", mIMEI.getText().toString().trim());
                map.put("simId", device_information_sim.getText().toString().trim());
                map.put("versionType", device_information_name.getText().toString().trim());
                map.put("type", type);
                map.put("custId", custId);
                p.binding(map);
                break;
            case R.id.device_information_scanning:
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivityForResult(intent, 10001);
//                mIMEI.setText("201709060006");
//                device_information_sim.setText("201708300002");
//                p.getImeiInfo("201709060006", businessId, custId);

                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001 && null != mIMEI && null != data) {
            String imei = data.getStringExtra("IMEI");
            mIMEI.setText(imei);
            p.getImeiInfo(imei, businessId, custId);
        }
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
    }

    @Override
    public void setDeviceInfo(DeviceInfo deviceInfo) {

        if (deviceInfo != null && deviceInfo.getReturnList() != null && deviceInfo.getReturnList().size() > 0) {
            custId = deviceInfo.getCustId();
            deviceInfo.getReturnList().get(0).getBelongsId();
            type = deviceInfo.getReturnList().get(0).getType();
            device_information_type.setText(0 == type ? "有线" : "无线");
            device_information_name.setText(deviceInfo.getReturnList().get(0).getVersionType());
        } else {
            Tools.makeText("设备信息为空！");
        }

    }

    @Override
    public void binding() {
        Tools.makeText("绑定成功");
        mActivity.showLoading(businessId, mIMEI.getText().toString().trim());
    }

    public void clean() {
        device_information_type.setText("");
        device_information_name.setText("");
    }
}
