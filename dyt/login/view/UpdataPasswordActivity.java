package com.hxyd.dyt.login.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.login.presenter.UpdataPasswordP;
import com.hxyd.dyt.login.presenter.in.UpdataPasswordPI;
import com.hxyd.dyt.login.view.in.UpdataPasswordVI;
import com.hxyd.dyt.widget.DYTEditText;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class UpdataPasswordActivity extends BaseActivity implements UpdataPasswordVI {


    @BindView(R.id.activity_updata_password_lod)
    DYTEditText mLod;
    @BindView(R.id.activity_updata_password_new)
    DYTEditText mNew;
    @BindView(R.id.activity_updata_password_confirm)
    DYTEditText mConfirm;
    @BindString(R.string.progressdialog_title)
    String mPrompt;
    @BindString(R.string.title_name_updata_passsword)
    String mTitle;
    private long clickTime = 0;


    UpdataPasswordPI mUpdataPasswordPI;
    private ProgressDialog mProgressDialog;

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        init();
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_updata_password);
    }

    @OnClick(R.id.activity_updata_password_submit)
    public void onClick(View v) {

        if ( (System.currentTimeMillis() - clickTime) > 500) {
            clickTime = System.currentTimeMillis();

            mUpdataPasswordPI.submit(mLod.getText().toString(), mNew.getText().toString(), mConfirm.getText().toString());
        }
    }

    @Override
    protected void onDestroy() {
        mUpdataPasswordPI.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onErr(int type, String str) {
        switch (type) {
            case ERR_ONE:
                mLod.setShakeAnimation();
                break;
            case ERR_TOW:
                mNew.setShakeAnimation();
                break;
            case ERR_THREE:
                mConfirm.setShakeAnimation();
                break;
            case ERR_FOUR:
                mNew.setShakeAnimation();
                mConfirm.setShakeAnimation();
                break;
            default:
                break;
        }
        Tools.makeText(str);
    }

    @Override
    public void onShowProgressBar() {
        mProgressDialog.show();
    }

    @Override
    public void onCloseProgressBar() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onShowDialog(String str) {
        new AlertDialog.Builder(this)
                .setTitle(mPrompt)
                .setMessage(str)
                .setPositiveButton(R.string.alertdialog_positive_button, null)
                .create()
                .show();
    }

    @Override
    public void onSuccess() {
        Tools.makeText("密码修改成功,请重新登录!");
        EventBus.getDefault().post(new BusEvent(BusEvent.UPDATA_LOGIN_SUCCESS));
        EventBus.getDefault().post(new BusEvent(BusEvent.LOGIN_OUT));
        finish();
    }

    private void init() {

        isShowTR(false);
        isShowIR(false);
        setIL(R.mipmap.back);
        isShowSpoy(false);
        setTitle(mTitle);

        mUpdataPasswordPI = new UpdataPasswordP(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(mTitle);
        mProgressDialog.setMessage("正在努力修改中...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }
}
