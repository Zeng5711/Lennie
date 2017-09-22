package com.hxyd.dyt.login.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.hxyd.dyt.BuildConfig;
import com.hxyd.dyt.R;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.SharedPrefsUtil;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.login.presenter.LoginP;
import com.hxyd.dyt.login.presenter.in.LoginPI;
import com.hxyd.dyt.login.view.in.LoginVI;
import com.hxyd.dyt.main.presenter.ApplictionP;
import com.hxyd.dyt.main.view.MainActivity;
import com.hxyd.dyt.widget.DYTEditText;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivty extends AppCompatActivity implements LoginVI {

    @BindView(R.id.activity_login_submit)
    Button mSubmit;
    @BindView(R.id.activity_login_username)
    DYTEditText mUserNmae;
    @BindView(R.id.activity_login_password)
    DYTEditText mPassWord;
    @BindString(R.string.progressdialog_title)
    String mTitle;
    @BindString(R.string.login_progressdialog_message)
    String mMessage;


    private Unbinder mUnbinder;
    private ProgressDialog mProgressDialog;
    private LoginPI mLoginP;
    private long clickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e("LoginActivty onCreate===>");
        setContentView(R.layout.login_activty);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLoginP!=null) {
            mLoginP.onDestroy();
        }
    }

    @OnClick(R.id.activity_login_submit)
    public void submit(View view) {
        if ((System.currentTimeMillis() - clickTime) > 800) {
            clickTime = System.currentTimeMillis();
            mLoginP.submit(mUserNmae.getText().toString().trim(), mPassWord.getText().toString().trim());
        }
    }

    private void startActivity(String username, String roleName, String orderTotal) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onShowProgressBar() {
        if (null != mProgressDialog) {
            mProgressDialog.show();
        }
    }

    @Override
    public void onCloseProgressBar() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onLoginSuccess(String username, String roleName, String orderTotal) {
        startActivity(username, roleName, orderTotal);
        finish();
    }

    @Override
    public void onLoginErr(String str) {
        new AlertDialog.Builder(this)
                .setTitle(mTitle)
                .setMessage(str)
                .setPositiveButton(R.string.alertdialog_positive_button, null)
                .create()
                .show();
    }

    @Override
    public void onPrompt(int type, String str) {
        if (type == 1) {
            mUserNmae.setShakeAnimation();
        } else {
            mPassWord.setShakeAnimation();
        }
        Tools.makeText(str);
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (!TextUtils.isEmpty(Constant.getToken())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        ApplictionP p = new ApplictionP(this);
        p.checkVersion();

        mUnbinder = ButterKnife.bind(this);
        mLoginP = new LoginP(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(mTitle);
        mProgressDialog.setMessage(mMessage);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

        String user = SharedPrefsUtil.getValue(null, Constant.USER, "");
        mUserNmae.setText(user);

//        getSingInfo();

    }

    int click = 0;

    @OnClick(R.id.login_log)
    public void onClick(View v) {
        if (BuildConfig.LOG_DEBUG) {
            if (click == 6) {
                String message = "";
                String CPU_ABI = android.os.Build.CPU_ABI;
                String MODEL = android.os.Build.MODEL;
                String MANUFACTURER = android.os.Build.MANUFACTURER;
                int version = android.os.Build.VERSION.SDK_INT;
                String BRAND = android.os.Build.BRAND;
                String PRODUCT = android.os.Build.PRODUCT;
//                String MD5 = getSingInfo();


                message = "CPU架构: " + CPU_ABI + "\n" +
                        "型号: " + MODEL + "\n" +
                        "硬件厂商: " + MANUFACTURER + "\n" +
                        "系统版本: " + version + "\n" +
                        "android系统定制商: " + BRAND + "\n" +
                        "手机厂商: " + PRODUCT + "\n";
                new AlertDialog.Builder(this)
                        .setTitle("本机参数")
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .create()
                        .show();

            } else {
                click++;
            }
        }

    }


    public void getSingInfo() {
        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo("com.hxyd.dyt", PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sign.toByteArray());
            byte[] digest = md.digest();
            String res = byte2HexStr(digest);
            Logger.e("res = " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }


}
