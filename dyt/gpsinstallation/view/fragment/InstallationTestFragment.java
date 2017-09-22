package com.hxyd.dyt.gpsinstallation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.hxyd.dyt.R;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.gpsinstallation.presenter.InstallationP;
import com.hxyd.dyt.gpsinstallation.presenter.in.InstallationPI;
import com.hxyd.dyt.gpsinstallation.view.GPSInstallActivity;
import com.hxyd.dyt.gpsinstallation.view.in.InstallationVI;
import com.hxyd.dyt.purplestar.utils.GPS3DUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by win7 on 2017/9/9.
 */

public class InstallationTestFragment extends Fragment implements GeocodeSearch.OnGeocodeSearchListener, InstallationVI {
    private GPSInstallActivity mActivity;

    @BindView(R.id.map)
    public MapView mMapView3D;

    private AMap aMap;

    private String taskId = "";
    private String processInstanceId = "";
    private String receiveDate = "";
    private String locationDate = "";
    private String imeiId = "";
    private double lon;
    private double lat;
    private boolean isLoad = false;
    private InstallationPI p;

    @BindView(R.id.receiveDate)
    TextView tReceiveDate;

    @BindView(R.id.locationDate)
    TextView tLocationDate;

    @BindView(R.id.locationtv)
    TextView locationtv;


    public void setData(String imeiId, String receiveDate, String locationDate, double lon, double lat) {
        this.imeiId = imeiId;
        this.receiveDate = receiveDate;
        this.locationDate = locationDate;
        this.lon = lon;
        this.lat = lat;
        if (isLoad) {
            setData();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (GPSInstallActivity) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskId = getArguments().getString("taskId");
        processInstanceId = getArguments().getString("processInstanceId");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.installation_test, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMapView3D.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView3D.getMap();
        }

        p = new InstallationP(this);

        GPS3DUtils.getInstance().addMarkersToMap(aMap, R.mipmap.location_query_icon_car_driving, lat, lon);

        setData();
        isLoad = true;
    }

    private void setData() {
        tReceiveDate.setText(receiveDate);
        tLocationDate.setText(locationDate);
        GPS3DUtils.getInstance().getAddressByLatlng(getContext(), new LatLng(lat, lon), this);
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView3D.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView3D.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView3D.onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int i) {
        if (result != null) {
            String formatAddress = result.getRegeocodeAddress().getFormatAddress();
            locationtv.setText(formatAddress);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @OnClick({R.id.Continue, R.id.dismantle, R.id.complete})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Continue:
                mActivity.showDeviceInfo();
                break;
            case R.id.dismantle:
                p.teardown(imeiId);
                break;
            case R.id.complete:
                p.completeTask(taskId, processInstanceId);
                break;
            default:
                break;

        }
    }

    @Override
    public void showDialog(String m) {
        mActivity.showProgressDialog(m);
    }

    @Override
    public void dismiss() {
        mActivity.dismiss();
    }

    @Override
    public void onErr(int type, String s) {
        Tools.makeText(s);
        if (type == 2) {
            mActivity.finish();
        } else if (type == 3) {
            mActivity.showDeviceInfo();
        }
    }
}
