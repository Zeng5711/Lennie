package com.hxyd.dyt.appraiser.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.appraiser.modle.entity.CarInfo;
import com.hxyd.dyt.appraiser.presenter.CarInfoP;
import com.hxyd.dyt.appraiser.presenter.in.CarInfoPI;
import com.hxyd.dyt.appraiser.view.AppraiserActivity;
import com.hxyd.dyt.appraiser.view.in.CarInfoVI;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.widget.ContainsEmojiEditText;
import com.hxyd.dyt.zxing.ScanActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by win7 on 2017/9/9.
 */

public class CarInfoFragment extends Fragment implements CarInfoVI {

    private AppraiserActivity mActivity;

    @BindView(R.id.vechicle_ev_car_num)
    TextView vechicle_ev_car_num;

    @BindView(R.id.vechicle_ev_register)
    TextView vechicle_ev_register;

    @BindView(R.id.vechicle_ev_run)
    TextView vechicle_ev_run;

    @BindView(R.id.vechicle_ev_executor)
    TextView vechicle_ev_executor;

    @BindView(R.id.vechicle_ev_IMEI)
    ContainsEmojiEditText vechicle_ev_IMEI;

    @BindView(R.id.vechicle_ev_engine_number)
    ContainsEmojiEditText vechicle_ev_engine_number;

    @BindView(R.id.car_num_ch)
    CheckBox car_num_ch;

    @BindView(R.id.register_ch)
    CheckBox register_ch;

    @BindView(R.id.run_ch)
    CheckBox run_ch;


    private String businessId = "";
    private CarInfoPI p;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppraiserActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessId = getArguments().getString("businessId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vehicle_evaluation, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        p = new CarInfoP(this);

        p.queryEvalCarInfo(businessId);
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

    @OnClick({R.id.button2, R.id.device_information_scanning})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.device_information_scanning:
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivityForResult(intent, 10001);
                break;
            case R.id.button2:

                if (TextUtils.isEmpty(vechicle_ev_car_num.getText().toString().trim())) {
                    Tools.makeText("车牌号不能为空");
                    return;
                }

                if (TextUtils.isEmpty(vechicle_ev_register.getText().toString().trim())) {
                    Tools.makeText("注册时间不能为空");
                    return;
                }

                if (TextUtils.isEmpty(vechicle_ev_run.getText().toString().trim())) {
                    Tools.makeText("行驶公里数不能空");
                    return;
                }

                if (TextUtils.isEmpty(vechicle_ev_executor.getText().toString().trim())) {
                    Tools.makeText("系统评估不能为空");
                    return;
                }

                if (TextUtils.isEmpty(vechicle_ev_IMEI.getText().toString().trim())) {
                    Tools.makeText("车架号不能为空");
                    return;
                }

                if (TextUtils.isEmpty(vechicle_ev_engine_number.getText().toString().trim())) {
                    Tools.makeText("发动机号不能为空");
                    return;
                }


                p.updateEvalCarInfo(businessId,
                        vechicle_ev_car_num.getText().toString().trim(),
                        vechicle_ev_register.getText().toString().trim(),
                        vechicle_ev_car_num.getText().toString().trim(),
                        vechicle_ev_IMEI.getText().toString().trim(),
                        vechicle_ev_engine_number.getText().toString().trim());


                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001 && null != vechicle_ev_IMEI && null != data) {
            String imei = data.getStringExtra("IMEI");
            vechicle_ev_IMEI.setText(imei);
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
    public void setCarInfo(CarInfo carInfo) {
        if (carInfo != null && carInfo.getEvalCarInfo() != null) {
            CarInfo.EvalCarInfoBean bean = carInfo.getEvalCarInfo();
            vechicle_ev_car_num.setText(bean.getPlate_no());
            vechicle_ev_register.setText(bean.getRegister_date());
            vechicle_ev_run.setText(bean.getTravel_distance()+"");
            vechicle_ev_executor.setText(bean.getC2b_prices()+"");
        }
    }

    @Override
    public void updateEvalCarInfo() {
        mActivity.showASS();
    }
}
