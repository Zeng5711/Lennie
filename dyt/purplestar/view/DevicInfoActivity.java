package com.hxyd.dyt.purplestar.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hxyd.dyt.R;

import butterknife.BindView;


public class DevicInfoActivity extends com.hxyd.dyt.common.BaseActivity {

    @BindView(R.id.dev_name)
    TextView name;

    @BindView(R.id.dev_car_mun)
    TextView carNum;

    @BindView(R.id.dev_type)
    TextView type;

    @BindView(R.id.dev_model)
    TextView model;

    @BindView(R.id.dev_imei)
    TextView imei;

    @BindView(R.id.dev_isim)
    TextView isim;

    @BindView(R.id.dev_start_time)
    TextView startTime;

    @BindView(R.id.dev_stop_time)
    TextView stopTime;

    @BindView(R.id.dev_phone)
    TextView phone;

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        isShowSpoy(false);
        isShowIR(false);
        isShowTR(false);
        setIL(R.mipmap.back);

        setTitle("设备信息");

        getIL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            name.setText(intent.getStringExtra("name"));
            carNum.setText(intent.getStringExtra("carNum"));
            if (0==intent.getIntExtra("type",-1)) {
                type.setText("有线");
            } else if (1==intent.getIntExtra("type",-1)) {
                type.setText("无线");
            }

            model.setText(intent.getStringExtra("model"));
            imei.setText(intent.getStringExtra("imei"));
            isim.setText(intent.getStringExtra("isim"));
            startTime.setText(intent.getStringExtra("startTime"));
            stopTime.setText(intent.getStringExtra("stopTime"));
            phone.setText(intent.getStringExtra("phone"));
        }

    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_devic_info);
    }

}
