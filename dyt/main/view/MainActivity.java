package com.hxyd.dyt.main.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxyd.dyt.BuildConfig;
import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.view.fragment.AccountManagerFragment;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Files;
import com.hxyd.dyt.common.uitl.PermissionUtils;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.jpush.JPushUtil;
import com.hxyd.dyt.jpush.view.MessageActivity;
import com.hxyd.dyt.login.view.LoginActivty;
import com.hxyd.dyt.main.presenter.MainP;
import com.hxyd.dyt.main.presenter.in.MainPI;
import com.hxyd.dyt.main.view.fragment.MainFragment;
import com.hxyd.dyt.main.view.fragment.ShowPageFragment;
import com.hxyd.dyt.main.view.in.MainVI;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends FragmentActivity implements MainVI {

    @BindView(R.id.activity_main_home)
    public ImageView imHome;
    @BindView(R.id.activity_main_my)
    public ImageView imMy;

    @BindView(R.id.activity_main_show_page)
    ImageView imShowPage;

    @BindView(R.id.activity_main_im_message_spot)
    public ImageView messageSpot;

    @BindView(R.id.activity_main_fragment)
    public LinearLayout fragmentll;

    @BindView(R.id.activity_main_bottom)
    public LinearLayout bottomll;

    @BindView(R.id.activity_main_tv_title)
    public TextView tvTitle;

    @BindView(R.id.activity_main_ll_back)
    LinearLayout ll;

    private FragmentManager fm;
    private ProgressDialog mProgressDialog;
    private PermissionUtils.PermissionGrant permissionGrant;
    private MainPI P;


    private long clickTime = 0;
    private boolean idUpdataAPK = false;
    private String mCountTotal = "0";

    private ShowPageFragment showPageFragment;
    private MainFragment mainFragment;
    private AccountManagerFragment mAMF;

    private static String TAG_FALG = "";
    private static final String TAG_ACCOUNTMANAGER = "accountmanager";
    private static final String TAG_MAINFRAGMENT = "mainfragment";
    private static final String TAG_SHOWPAGEFRAGMENT = "showpagefragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Logger.e("MainActivity  onCreate ");
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        P = new MainP(this);

        permissionGrant = new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(int requestCode, boolean isSuccess) {
                if (requestCode == PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE) {
                    if (isSuccess) {
                        P.checkVersion();
                    } else {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("提示")
                                .setMessage("您拒绝了申请读写权限")
                                .setPositiveButton(R.string.alertdialog_positive_button, null)
                                .create().show();
                    }
                }
            }
        };

        PermissionUtils.getInstance().requestPermission(MainActivity.this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, permissionGrant);


    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {
        switch (event.event) {
            case BusEvent.LOGIN_OUT:
                loginOut();
                break;
            case BusEvent.ACTIVITY_OUT:
                finish();
                break;
            default:
                break;
        }
    }

    private void loginOut() {
        startActivity(new Intent(this, LoginActivty.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (P != null) {
            P.mainInfo();
        }
        if (Constant.getMessage()) {
            messageSpot.setVisibility(View.VISIBLE);
        } else {
            messageSpot.setVisibility(View.GONE);
        }
        if (idUpdataAPK) {
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        P.onDestroy();
        permissionGrant = null;
        EventBus.getDefault().unregister(this);
//        Constant.setToken(null);
        JPushUtil.getInstance(this).setAlias(null);
        double size = Files.getAutoFileOrFilesSize(Files.getStorageDirectory());
        if (size > Constant.CACHE_SIZE) {
            Files.deleteExtraFiles(Files.getStorageDirectory());
        }
        Logger.e("MainActivity onDestroy ");
        super.onDestroy();
    }

    @OnClick({R.id.activity_main_home_ll, R.id.activity_main_my_ll, R.id.activity_main_im_message,R.id.activity_main_show_page_ll})
    public void onClick(View v) {
        if (v.getId() == R.id.activity_main_home_ll) {
            tvTitle.setText("首页");
            imHome.setImageResource(R.mipmap.home_up);
            imMy.setImageResource(R.mipmap.icon_my_nor);
            imShowPage.setImageResource(R.mipmap.icon_play_nor);
            ll.setVisibility(View.VISIBLE);
            if (Constant.getMessage()) {
                messageSpot.setVisibility(View.VISIBLE);
            } else {
                messageSpot.setVisibility(View.GONE);
            }
            if (TAG_FALG == TAG_ACCOUNTMANAGER) {
                switchContent(mAMF, mainFragment, TAG_MAINFRAGMENT);
            } else {
                switchContent(showPageFragment, mainFragment, TAG_MAINFRAGMENT);
            }

        } else if (v.getId() == R.id.activity_main_my_ll) {
            tvTitle.setText("我");
            imHome.setImageResource(R.mipmap.home_on);
            imMy.setImageResource(R.mipmap.icon_my);
            imShowPage.setImageResource(R.mipmap.icon_play_nor);
            ll.setVisibility(View.GONE);
            if (TAG_FALG.equals(TAG_MAINFRAGMENT)) {
                switchContent(mainFragment, mAMF, TAG_ACCOUNTMANAGER);
            } else if (TAG_FALG.equals(TAG_SHOWPAGEFRAGMENT)) {
                switchContent(showPageFragment, mAMF, TAG_ACCOUNTMANAGER);
            }
        } else if (v.getId() == R.id.activity_main_ll_back) {
            Constant.setMessage(false);
            startActivity(MessageActivity.class);
        } else if (v.getId() == R.id.activity_main_show_page_ll) {
            tvTitle.setText("展业海报");
            ll.setVisibility(View.GONE);
            imShowPage.setImageResource(R.mipmap.icon_play);
            imHome.setImageResource(R.mipmap.home_on);
            imMy.setImageResource(R.mipmap.icon_my_nor);
            if (TAG_FALG == TAG_ACCOUNTMANAGER) {
                switchContent(mAMF, showPageFragment, TAG_SHOWPAGEFRAGMENT);
            } else if (TAG_MAINFRAGMENT == TAG_FALG) {
                switchContent(mainFragment, showPageFragment, TAG_SHOWPAGEFRAGMENT);
            }
        }
    }

    public void startActivity(Class cla) {
        Intent intent = new Intent(this, cla);
        startActivity(intent);
    }

    public int getBottomHeight() {
        bottomll.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        return bottomll.getMeasuredHeight();
    }

    private void initFragment() {

        mAMF = new AccountManagerFragment();
        mainFragment = new MainFragment();
        showPageFragment = new ShowPageFragment();
        TAG_FALG = TAG_MAINFRAGMENT;
        changeFragment(true, mainFragment, TAG_MAINFRAGMENT);
    }

    private void changeFragment(Fragment f, String tag) {
        changeFragment(false, f, tag);
    }

    private void changeFragment(boolean isadd, Fragment f, String tag) {
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (isadd) {
            ft.add(R.id.activity_main_fragment, f, tag);
        } else {
            ft.replace(R.id.activity_main_fragment, f);
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    private void switchContent(Fragment from, Fragment to, String tag) {
        if (null != from && null != to) {
            TAG_FALG = tag;
            FragmentTransaction transaction = fm.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.activity_main_fragment, to, tag).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    public void onShowDialog(String title, String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void onCloseDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onShowProgress(String title, String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressNumberFormat("%1d KB/%2d KB");
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void onSetProgress(int max, int Progress) {
        if (mProgressDialog != null) {
            mProgressDialog.setMax(max);
            mProgressDialog.setProgress(Progress);
        }
    }

    @Override
    public void onCloseProgress() {
        onCloseDialog();
    }

    @Override
    public void onPrompt(int type, String message) {
        Tools.makeText(message);
    }

    @Override
    public void onSetCountTotal(String countTotal) {
        EventBus.getDefault().post(new BusEvent(BusEvent.REFRESH_COUNTTOTAL, countTotal));
    }

    @Override
    public void installApk(String path) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", new File(path));
                i.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {

                i.setDataAndType(Uri.parse("file://" + path),
                        "application/vnd.android" + ".package-archive");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            this.startActivityForResult(i, 1001);
        } catch (Exception e) {
            Logger.e("安装失败");
            e.printStackTrace();
        }
    }

    @Override
    public void onShowAlertDialog(String title, String message, final boolean isForce) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.alertdialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (P != null) {
                            P.downAPK();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.alertdialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (isForce) {
                            MainActivity.this.finish();
                        }
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Tools.makeText("再次点击退出");
            clickTime = System.currentTimeMillis();
        } else {
            Logger.e("exit application");
            this.finish();
//            android.os.Process.killProcess(android.os.Process.myPid());

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            idUpdataAPK = true;
        }
    }
}
