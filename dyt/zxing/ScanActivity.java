package com.hxyd.dyt.zxing;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.uitl.Tools;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {


    private QRCodeView mQRCodeView;
    CheckBox mLamp;

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        setIL(R.mipmap.back);
        isShowIR(false);
        isShowSpoy(false);
        isShowTR(false);
        setTitle("扫描");

        mQRCodeView = (ZBarView) findViewById(R.id.zbarview);
        mQRCodeView.setDelegate(this);
        mLamp = (CheckBox) findViewById(R.id.scan_lamp);
        mLamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mLamp.setText("开灯");
                    mQRCodeView.openFlashlight();
                } else {
                    mLamp.setText("关灯");
                    mQRCodeView.closeFlashlight();
                }
            }
        });
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_scan);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mQRCodeView.startSpot();
        mQRCodeView.startCamera();
        mQRCodeView.startSpotAndShowRect();
        mQRCodeView.changeToScanQRCodeStyle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mQRCodeView.stopSpot();
        mQRCodeView.stopCamera();
        mQRCodeView.stopSpotAndHiddenRect();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
//        Log.i(TAG, "result:" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
//        mQRCodeView.startSpot();

        Intent intent = new Intent();
        intent.putExtra("IMEI", result);
        setResult(10001, intent);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Tools.makeText("打开相机出错");
    }
}
