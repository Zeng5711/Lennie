package com.hxyd.dyt.purplestar.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.entity.ImageItem;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.purplestar.presenter.ModifyUploadP;
import com.hxyd.dyt.purplestar.presenter.in.ModifyUploadPI;
import com.hxyd.dyt.purplestar.view.LocationSettingActivity;
import com.hxyd.dyt.purplestar.view.fragment.in.ModifyUploadVI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by win7 on 2017/8/28.
 */

public class ModifyUploadTimeFragment extends Fragment implements ModifyUploadVI {

    private Dialog mDialog;
    private int type;
    private String imeiId;
    private int state = -1;

    private int Semih = 0;
    private int oneHours = 1;
    private int towHours = 2;

    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.image)
    ImageView iv;
    private LocationSettingActivity mActivity;
    private ModifyUploadPI p;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (LocationSettingActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type", -1);
        imeiId = getArguments().getString("imeiId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modify_upload_time_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        p= new ModifyUploadP(this);

    }

    @OnClick({R.id.modify_upload_rl, R.id.modify_upload_bt})
    public void onClick(View v) {
        if (v.getId() == R.id.modify_upload_rl) {
            if (type == 1) {
                showDialog();
            } else {
                Tools.makeText("只有无限设备才可使用");
            }
        } else if (v.getId() == R.id.modify_upload_bt) {
            p.updateUploadTimek(imeiId,state);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showDialog() {
        if (mDialog == null) {
            mDialog = new Dialog(getContext(), R.style.dialog_bottom_full);//.create();

            Window window = mDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.popupAnimation);
            View view = View.inflate(getContext(), R.layout.bottom_dailog, null);

            TextView tv1 = (TextView) view.findViewById(R.id.bottom_tv1);
            TextView tv2 = (TextView) view.findViewById(R.id.bottom_tv2);
            TextView tv3 = (TextView) view.findViewById(R.id.bottom_tv3);

            tv1.setText("半小时");
            tv2.setText("1小时");
            tv3.setText("2小时");
            tv3.setTextColor(getContext().getResources().getColor(R.color.text_hin_color));
            view.findViewById(R.id.iv_userinfo_takepic).setBackgroundColor(Color.WHITE);
            view.findViewById(R.id.iv_userinfo_takepic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    state = Semih;
                    iv.setVisibility(View.GONE);
                    time.setText("半小时");
                    mDialog.dismiss();
                }
            });
            view.findViewById(R.id.iv_userinfo_choosepic).setBackgroundColor(Color.WHITE);
            view.findViewById(R.id.iv_userinfo_choosepic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    state = oneHours;
                    time.setText("1小时");
                    iv.setVisibility(View.GONE);
                    mDialog.dismiss();
                }
            });
            view.findViewById(R.id.iv_userinfo_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    state = towHours;
                    time.setText("2小时");
                    iv.setVisibility(View.GONE);
                    mDialog.dismiss();
                }
            });
            window.setContentView(view);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setCancelable(true);
        }
        mDialog.show();
    }

    @Override
    public void onShowDialog(String str) {
        mActivity.showProgressDialog(str);
    }

    @Override
    public void onDissm() {
        mActivity.dismiss();
    }

    @Override
    public void onShowMessage(int type, String message) {
        Tools.makeText(message);
    }
}
