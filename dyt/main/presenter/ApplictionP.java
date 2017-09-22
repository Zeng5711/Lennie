package com.hxyd.dyt.main.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.WindowManager;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.Version;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.login.view.LoginActivty;
import com.hxyd.dyt.main.CallDownAPKListener;
import com.hxyd.dyt.main.model.MainM;
import com.hxyd.dyt.main.model.in.MainMI;
import com.hxyd.dyt.main.presenter.in.MainPI;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/6/22.
 */

public class ApplictionP implements MainPI {

    private static MainMI M;
    private Context mContext;
    private String DOWNURL;

    public ApplictionP(Context context) {
        this.mContext = context;
        this.M = new MainM();
    }

    @Override
    public void checkVersion() {
        String verCode = Tools.getVersionName();
        String type = Constant.APP_TYPE;
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.VERCODE_KEY, verCode);
        map.put(Constant.TYPE_KEY, type);
        map = Constant.addMap(map);

        M.checkVersion(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Version>() {

                    @Override
                    public void onNext(Version data) {
                        if (data != null) {
                            checkCode(data);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {

                    }
                });
    }

    @Override
    public void mainInfo() {

    }

    @Override
    public void downAPK() {

        if (mContext == null) {
            return;
        }

        final ProgressDialog mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("提示");
        mProgressDialog.setMessage("正在下载中，请稍后...");
        mProgressDialog.setProgressNumberFormat("%1d KB/%2d KB");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mProgressDialog.show();


        M.downAPK(DOWNURL, new CallDownAPKListener() {
            @Override
            public void onSetDownDialogStr(int max, int Progress) {

                mProgressDialog.setMax(max);
                mProgressDialog.setProgress(Progress);

            }

            @Override
            public void onCloseDownDialog() {
                mProgressDialog.dismiss();
            }

            @Override
            public void onSuccess(String path) {
                if (mContext != null) {
                    try {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setDataAndType(Uri.parse("file://" + path),
                                "application/vnd.android" + ".package-archive");
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    } catch (Exception e) {
                        Logger.e("安装失败");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onErr() {
                mProgressDialog.dismiss();
                Tools.makeText("下载APK失败！");
            }
        });
    }

    @Override
    public void onDestroy() {

    }

    private void checkCode(Version version) {

        if (!TextUtils.isEmpty(version.getVerCode()) && Tools.getVersionCode() < Double.parseDouble(version.getVerCode())) {
            String str = "";
            boolean isForce = false;
            if (version.getForce().equals("1")) {
                isForce = true;
                str = "请下载最新的版本，否则会导致无法正常使用贷业通！";
            } else {
                str = "请下载最新的版本";
            }
            DOWNURL = version.getUrl();
            showAlertDialog("贷业通已经更新版本", str, isForce);

        }
    }

    private void showAlertDialog(String title, String message, final boolean isForce) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downAPK();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isForce) {
                    ((LoginActivty) mContext).finish();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

}
