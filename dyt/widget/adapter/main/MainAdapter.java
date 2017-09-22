package com.hxyd.dyt.widget.adapter.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.view.AccountTaskActivity;
import com.hxyd.dyt.accountManager.view.SingleNewActivity;
import com.hxyd.dyt.assessment.view.AssessmentActivity;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.SharedPrefsUtil;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.login.modle.entity.UserInfo;
import com.hxyd.dyt.purplestar.view.ContainerActivity;
import com.hxyd.dyt.purplestar.view.PurpleStarActivity;
import com.hxyd.dyt.widget.holder.main.MainViewHolder;
import com.tencent.cos.utils.SHA1Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2017/9/7.
 */

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private Context mContext;

    public MainAdapter(Context context) {
        mContext = context;
    }


    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mian_item, null);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {

//        if (SharedPrefsUtil.getValue(null, Constant.IS_GPS_PERMISSION, false)) {

        if (position == 0) {
            holder.im.setImageResource(R.mipmap.icon_list_info);
            holder.tv.setText("客户经理-录单");
        } else if (position == 1) {
            holder.im.setImageResource(R.mipmap.icon_visit);
            holder.tv.setText("风控家访");
        } else if (position == 2) {
            holder.im.setImageResource(R.mipmap.icon_gps);
            holder.tv.setText("GPS监控");
        } else if (position == 3) {
            holder.im.setImageResource(R.mipmap.icon_intr);
            holder.tv.setText("评估师");
        } else if (position == 4) {
            holder.im.setImageResource(R.mipmap.icon_inst);
            holder.tv.setText("GPS安装");
        } else if (position == 5) {
            holder.im.setImageResource(R.mipmap.icon_mort);
            holder.tv.setText("车辆抵押");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();

                if (position == 0) {
                    if (getPermissionKey("dytApi:ENTERING")) {
                        intent.setClass(mContext, SingleNewActivity.class);
                    } else {
                        tool();
                        return;
                    }
                } else if (position == 1) {
                    Tools.makeText("该功能正在开发中...");
                    return;
                } else if (position == 2) {
                    if (getPermissionKey("dytApi:GPS:MONITORING")) {
                        if (SharedPrefsUtil.getValue(null, Constant.ACCOUNT_NAME, "").equals("admin")) {
                            intent.setClass(mContext, PurpleStarActivity.class);
                        } else {
                            intent.setClass(mContext, ContainerActivity.class);
                            intent.putExtra("showData", true);
                            intent.putExtra("ACCOUNT_NAME", false);
                        }
                    } else {
                        tool();
                        return;
                    }
                } else if (position == 3) {
                    if (getPermissionKey("dytApi:ASSESS")) {
                        intent.putExtra("type", 3);
                        intent.setClass(mContext, AccountTaskActivity.class);
                    } else {
                        tool();
                        return;
                    }
                } else if (position == 4) {
                    if (getPermissionKey("dytApi:GPS")) {
                        intent.putExtra("type", 4);
                        intent.setClass(mContext, AccountTaskActivity.class);
                    } else {
                        tool();
                        return;
                    }
                } else if (position == 5) {
                    if (getPermissionKey("dytApi:GUARANTY")) {
                        intent.putExtra("type", 5);
                        intent.setClass(mContext, AccountTaskActivity.class);
                    } else {
                        tool();
                        return;
                    }
                }
                mContext.startActivity(intent);

            }
        });
//        } else {
//            if (position == 0) {
//                holder.im.setImageResource(R.mipmap.icon_list_info);
//                holder.tv.setText("客户经理-录单");
//            } else if (position == 1) {
//                holder.im.setImageResource(R.mipmap.icon_visit);
//                holder.tv.setText("风控家访");
//            } else if (position == 2) {
//                holder.im.setImageResource(R.mipmap.icon_intr);
//                holder.tv.setText("评估师");
//            } else if (position == 3) {
//                holder.im.setImageResource(R.mipmap.icon_inst);
//                holder.tv.setText("GPS安装");
//            } else if (position == 4) {
//                holder.im.setImageResource(R.mipmap.icon_mort);
//                holder.tv.setText("车辆抵押");
//            }
//
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//
//                    if (position == 0) {
//                        intent.setClass(mContext, SingleNewActivity.class);
//                    } else if (position == 1) {
//
//                        return;
//                    } else if (position == 2) {
//                        intent.putExtra("type", 3);
//                        intent.setClass(mContext, AccountTaskActivity.class);
//                    } else if (position == 3) {
//                        intent.putExtra("type", 4);
//                        intent.setClass(mContext, AccountTaskActivity.class);
//                    } else if (position == 4) {
//                        intent.putExtra("type", 5);
//                        intent.setClass(mContext, AccountTaskActivity.class);
//                    }
//                    mContext.startActivity(intent);
//
//                }
//            });
//        }

    }

    @Override
    public int getItemCount() {
            return 6;
    }

    private boolean getPermissionKey(String key) {
        boolean b = false;
        List<UserInfo.MenuPermissionBean> list = SharedPrefsUtil.getValue(Constant.MENUPE_RMISSION_BEAN, Constant.MENUPE_RMISSION_BEAN);
        Log.e("aaa","lsit" + list.toString());
        if (null != list && list.size() > 0 && !TextUtils.isEmpty(key)) {
            for (int i = 0; i < list.size(); i++) {
                UserInfo.MenuPermissionBean bean = list.get(i);
                if (key.equals(list.get(i).getPermissionKey())) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }

    private void tool() {
        Tools.makeText("您没有该权限");
    }

}
