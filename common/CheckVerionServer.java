package com.hxyd.dyt.common;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.hxyd.dyt.R;

/**
 * Created by win7 on 2017/6/22.
 */

public class CheckVerionServer extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {


//        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setTitle("提示");
//                builder.setMessage("系统级别的提示框");
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ProgressDialog mProgressDialog = new ProgressDialog(getApplicationContext());
//                        mProgressDialog.setTitle("提示");
//                        mProgressDialog.setMessage(".....");
//                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                        mProgressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                        mProgressDialog.show();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.setCancelable(false);
//                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                dialog.show();

        Builder builder=new Builder(getApplicationContext());
        builder.setTitle("Title");
        builder.setMessage("This is message");
        builder.setNegativeButton("OK", null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.updata_dailog);
        }
        Dialog dialog=builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
