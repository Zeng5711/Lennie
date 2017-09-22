package com.hxyd.dyt.accountManager.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.presenter.SettingP;
import com.hxyd.dyt.accountManager.presenter.in.SettingPI;
import com.hxyd.dyt.accountManager.view.in.AccountManagerVI;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.PermissionUtils;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.login.view.UpdataPasswordActivity;
import com.orhanobut.logger.Logger;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class SettingActivity extends BaseActivity implements AccountManagerVI, ActivityCompat.OnRequestPermissionsResultCallback {

    @BindView(R.id.im_version_go)
    TextView mVersion;
    @BindString(R.string.main_setting)
    String mTitle;
    //    private boolean isCheckUpdataAPK = false;
    private SettingPI mAnagerPI;
    private ProgressDialog mProgressDialog;
    private boolean isShowUpdataDialog = false;
    private PermissionUtils.PermissionGrant permissionGrant;
    private boolean idUpdataAPK = false;
    private long clickTime = 0;



    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        isShowTR(false);
        isShowIR(false);
        setIL(R.mipmap.back);
        isShowSpoy(false);
        setTitle(mTitle);
        permissionGrant = new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(int requestCode, boolean isSuccess) {
                if (requestCode == PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE) {
                    if (isSuccess) {
//                        isCheckUpdataAPK = true;
                        mAnagerPI.checkVersion();
                    } else {
                        SettingActivity.this.showAlertDialog("您拒绝了申请读写权限");
                    }
                }
            }
        };
        mAnagerPI = new SettingP(this);
        mVersion.setText("V " + Tools.getVersionName());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        permissionGrant = null;
        mAnagerPI.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_setting);
    }


    private void startActivity(Class cla) {
        Intent intent = new Intent(this, cla);
        startActivity(intent);
    }

    @OnClick({R.id.activity_main_rl_binding_phone,
            R.id.activity_main_rl_updata_passsword,
            R.id.activity_main_rl_version,
            R.id.activty_main_bt_sign_out})
    public void onClick(View v) {

        if ( (System.currentTimeMillis() - clickTime) > 500) {

            clickTime = System.currentTimeMillis();


            switch (v.getId()) {
                case R.id.activity_main_rl_binding_phone:
                    Tools.makeText("功能正在开发中，敬请期待！");
                    break;
                case R.id.activity_main_rl_updata_passsword:
                    startActivity(UpdataPasswordActivity.class);
                    break;
                case R.id.activity_main_rl_version:
                    PermissionUtils.getInstance().requestPermission(SettingActivity.this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, permissionGrant);
                    break;

                case R.id.activty_main_bt_sign_out:
                    showAlertDialog("确认要退出当前账号？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showProgressDialog("正在退出账号...");
                            mAnagerPI.onLoginOut();
                            dialog.dismiss();
                        }
                    });
                    break;

                default:
                    break;
            }
        }

    }

    /**
     * 回调获取授权结果，判断是否授权
     * 权限开启结果检查 grantResults[0]==0=PackageManager.PERMISSION_GRANTED的话 权限开启成功
     * 权限开启结果检查 grantResults[0]==-1=PackageManager.PERMISSION_DENIED 权限开启失败
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length > 0) {
            PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onStartActivity(Class cla) {
        dismiss();
        Constant.setToken(null);
        EventBus.getDefault().post(new BusEvent(BusEvent.LOGIN_OUT));
        finish();
    }

    @Override
    public void setUser(String userName, String title, int orderTotal) {

    }

    @Override
    public void setTotalApplication(String totalApplication) {

    }

    @Override
    public void setHeadPortrait(String url) {

    }

    @Override
    public void onShowDownDialog(String str, boolean isCheck) {
        isShowUpdataDialog = isCheck;
        if (isCheck) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressNumberFormat("%1d KB/%2d KB");
            mProgressDialog.setTitle("下载");
            mProgressDialog.setMessage("正在下载，请稍后...");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        } else {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle(mTitle);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(str);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }
    }

    @Override
    public void onSetDownDialogStr(int max, int Progress) {
        if (isShowUpdataDialog && mProgressDialog != null) {
            mProgressDialog.setMax(max);
            mProgressDialog.setProgress(Progress);
        }
    }

    @Override
    public void onCloseDownDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onPrompt(int type, String str) {
        switch (type) {
            case -1:
//                if (isCheckUpdataAPK) {
                Tools.makeText(str);
//                }
                break;
            case -2:
                Tools.makeText(str);
                finish();
                EventBus.getDefault().post(new BusEvent(BusEvent.ACTIVITY_OUT));
                break;
            case 1:
                dismiss();
                Tools.makeText(str);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (idUpdataAPK) {
            finish();
            EventBus.getDefault().post(new BusEvent(BusEvent.ACTIVITY_OUT));
        }
    }

    @Override
    public void onSetCountTotal(String countTotal) {

    }

    @Override
    public void onShowUpdataAPK(String str, final String url, final boolean isForce) {
        showAlertDialog(str,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mAnagerPI.downAPK(url);
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        SettingActivity.this.finish();
//                        EventBus.getDefault().post(new BusEvent(BusEvent.ACTIVITY_OUT));
                    }
                });
    }

    @Override
    public void installApk(String path) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.parse("file://" + path),
                    "application/vnd.android" + ".package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivityForResult(i, 1001);
            onCloseDownDialog();
        } catch (Exception e) {
            Logger.e("安装失败");
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {
        switch (event.event) {
            case BusEvent.UPDATA_LOGIN_SUCCESS:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            idUpdataAPK = true;
        }
    }

}
