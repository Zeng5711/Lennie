package com.hxyd.dyt.common;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.uitl.Tools;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * Created by win7 on 2017/2/27.
 */

public abstract class BaseActivity extends FragmentActivity {

    RelativeLayout mRL;
    @BindView(R.id.activity_base_tv_title)
    TextView mTitle;
    @BindView(R.id.activity_base_tv_rigth)
    TextView mTRight;
    @BindView(R.id.activity_base_im_left)
    ImageView mILeft;
    @BindView(R.id.activity_base_im_right)
    ImageView mIRiht;
    @BindView(R.id.activity_base_im_message_spot)
    ImageView mSpot;
    @BindView(R.id.activity_base_rl_right)
    View mViwe;
    @BindString(R.string.progressdialog_title)
    String mDialogTitle;

    private Unbinder mUnbinder;

    private boolean mIsCheck = true;
    private ProgressDialog mProgressDialog;
    private Dialog mDialog;
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        mRL = (RelativeLayout) findViewById(R.id.activity_base_rl);
        addContentView();
        mUnbinder = ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        initAllMembersView(savedInstanceState);
    }

    public void showAlertDialog(String str) {
        new AlertDialog.Builder(this)
                .setTitle(mDialogTitle)
                .setMessage(str)
                .setPositiveButton(R.string.alertdialog_positive_button, null)
                .create().show();

    }

    public void showAlertDialog(String title, String str) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(str)
                .setPositiveButton(R.string.alertdialog_positive_button, null)
                .create().show();

    }

    public void showAlertDialog(String str, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setTitle(mDialogTitle)
                .setMessage(str)
                .setPositiveButton(R.string.alertdialog_positive_button, listener)
                .create()
                .show();

    }

    public void showAlertDialog(String str, DialogInterface.OnClickListener listener, DialogInterface.OnClickListener listener2) {
        new AlertDialog.Builder(this)
                .setTitle(mDialogTitle)
                .setMessage(str)
                .setPositiveButton(R.string.alertdialog_positive_button, listener)
                .setNegativeButton(R.string.alertdialog_negative_button, listener2)
                .setCancelable(false)
                .create()
                .show();

    }

    public void showAlertDialog(String str, String posButton, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setTitle(mDialogTitle)
                .setMessage(str)
                .setPositiveButton(posButton, listener)
                .setNegativeButton(R.string.alertdialog_negative_button, null)
                .create()
                .show();

    }

    public void showProgressDialog(String message) {
        if(mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle(mDialogTitle);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(message);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }else{
            mProgressDialog.setMessage(message);
        }
        mProgressDialog.show();

    }

    public  void showProgressDialog(String title, String message) {
        if(mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle(title);
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }else{
            mProgressDialog.setMessage(message);
        }
        mProgressDialog.show();

    }

    public  void dismiss() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public boolean isProgressDialogshow() {
        if (mProgressDialog != null) {
            return mProgressDialog.isShowing();
        }
        return false;
    }


    protected abstract void initAllMembersView(Bundle savedInstanceState);

    protected abstract void addContentView();

    public  int getViewTop(){
        return findViewById(R.id.activity_base_ll).getTop();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.BELOW, R.id.activity_base_ll);
        if (null != mRL) {
            mRL.addView(view, lp);
        }
    }

    public void setTitle(String name) {
        if (null != mTitle) {
            mTitle.setText(name);
        }
    }

    public void setTR(String ok) {
        if (null != mTRight) {
            mTRight.setText(ok);
        }
    }


    public void isShowTR(boolean falg) {
        if (null != mTRight) {
            mTRight.setVisibility(falg ? View.VISIBLE : View.GONE);
            if (!falg) {
                RelativeLayout.LayoutParams pl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                pl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                pl.addRule(RelativeLayout.CENTER_VERTICAL);
                mIRiht.setLayoutParams(pl);
            }
        }
    }

    public void setIL(int id) {
        if (null != mILeft) {
            mILeft.setImageResource(id);
        }
    }

    public void isShowIL(boolean falg) {
        if (null != mILeft) {
            mILeft.setVisibility(falg ? View.VISIBLE : View.GONE);
        }
    }


    public void setIR(int id) {
        if (null != mIRiht) {
            mIRiht.setImageResource(id);
        }
    }

    public void isShowIR(boolean falg) {
        if (null != mIRiht) {
            mIRiht.setVisibility(falg ? View.VISIBLE : View.GONE);
        }
    }


    public void isShowSpoy(boolean falg) {
        if (null != mSpot) {
            mSpot.setVisibility(falg ? View.VISIBLE : View.GONE);
        }
    }


    public TextView getTR() {
        return mTRight;
    }

    public ImageView getIR() {
        return mIRiht;
    }

    public ImageView getIL() {
        return mILeft;
    }

    public View getViwe() {
        return mViwe;
    }

    public TextView getMTitle() {
        return mTitle;
    }

    public void isCkeckBack(boolean isCheck) {
        mIsCheck = isCheck;
    }

    @OnClick(R.id.activity_base_ll_back)
    public void activity_base_back() {
        if (mIsCheck) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
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




}
