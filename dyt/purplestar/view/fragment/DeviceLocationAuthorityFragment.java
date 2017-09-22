package com.hxyd.dyt.purplestar.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.purplestar.modle.entity.DeviceLocation;
import com.hxyd.dyt.purplestar.presenter.DeviceLocationP;
import com.hxyd.dyt.purplestar.presenter.in.DeviceLocationPI;
import com.hxyd.dyt.purplestar.view.LocationSettingActivity;
import com.hxyd.dyt.purplestar.view.fragment.in.DeviceLocationVI;
import com.hxyd.dyt.widget.RecycleViewDivider;
import com.hxyd.dyt.widget.adapter.purplestar.DeviceLocationAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by win7 on 2017/8/28.
 */

public class DeviceLocationAuthorityFragment extends Fragment implements DeviceLocationVI {

    @BindView(R.id.RecyclerView)
    RecyclerView rv;

    @BindView(R.id.device_l_a_ll)
    LinearLayout ll;

    @BindView(R.id.device_l_a_name)
    TextView name;

    private DeviceLocationPI p;
    private LocationSettingActivity mActivity;
    private DeviceLocationAdapter mAdapter;
    private String mEquipmentId;
    private String imeiId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (LocationSettingActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_location_authority, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        p = new DeviceLocationP(this);
        imeiId = getArguments().getString("imeiId");
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.default_background)));
    }

    @OnClick({R.id.device_l_a_rl, R.id.device_l_a_bt})
    public void onClick(View v) {
        if (v.getId() == R.id.device_l_a_rl) {
            p.getqueryAllUser();
        } else if (v.getId() == R.id.device_l_a_bt) {
            p.equipmentAuthorize(mEquipmentId,imeiId);
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
        if (type == 1) {
            Tools.makeText(message);
        }else if(type == 2){
            name.setText(">");
            Tools.makeText(message);
        }
    }

    @Override
    public void setDeviceLocation(DeviceLocation deviceLocation) {
        if (deviceLocation.getUserList().size() > 0) {
            ll.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            mActivity.setTitle("人员选择");
            mAdapter = new DeviceLocationAdapter(getContext(), deviceLocation.getUserList(),
                    new DeviceLocationAdapter.DeviceLocationCallback() {
                        @Override
                        public void onCallback(String userChnName, String equipmentId) {
                            mActivity.setTitle("开启设备定位权限");
                            ll.setVisibility(View.VISIBLE);
                            rv.setVisibility(View.GONE);
                            mEquipmentId = equipmentId;
                            name.setText(userChnName);

                        }
                    });
            rv.setAdapter(mAdapter);
        } else {
            Tools.makeText("沒有数据！");
        }
    }
}
