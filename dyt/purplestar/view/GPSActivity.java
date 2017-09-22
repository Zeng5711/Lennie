package com.hxyd.dyt.purplestar.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.hxyd.dyt.R;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.SharedPrefsUtil;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.purplestar.modle.entity.GPS;
import com.hxyd.dyt.purplestar.presenter.GPSP;
import com.hxyd.dyt.purplestar.presenter.in.GPSPI;
import com.hxyd.dyt.purplestar.utils.GPS3DUtils;
import com.hxyd.dyt.purplestar.view.in.GPSVI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import butterknife.BindView;
import butterknife.OnClick;

public class GPSActivity extends BaseActivity implements AMap.InfoWindowAdapter, GPSVI, GeocodeSearch.OnGeocodeSearchListener {


    @BindView(R.id.map)
    public MapView mMapView3D;

    private AMap aMap;

    @BindView(R.id.gps_traffic_conditions)
    CheckBox trafficConditions;

    @BindView(R.id.gps_alarm_map)
    ImageView alarmMap;

    @BindView(R.id.gps_ll)
    LinearLayout ll;

    private TextView snippetUi;
    private TextView snippetUiCall;

    private View infoContent;
    private PopupWindow mPop;
    private GPS.ReturnListBean bean;
    private GPSPI p;
    private String imeiId = "";
    private String orgName;
    private boolean isCallPolice;
    private int type;

    private double lat;
    private double lon;

    @BindView(R.id.gps_refresh)
    TextView gps_refresh;

    private Timer timer = new Timer();
    private TimerTask task;
    private int countDown = 29;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (null != gps_refresh) {
                    gps_refresh.setText(countDown + "秒后刷新位置");
                    if (countDown == 0) {
                        countDown = 30;
                        p.getLocationInfo(imeiId);
                    } else {
                        countDown--;
                    }
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        setIL(R.mipmap.back);
        isShowSpoy(false);
        isShowTR(false);

        setTitle("位置信息");

        if (SharedPrefsUtil.getValue(null, Constant.ACCOUNT_NAME, "").equals("admin")) {
            setIR(R.mipmap.location_query_icon_setting);
            getIR().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GPSActivity.this, LocationSettingActivity.class);
                    intent.putExtra("imeiId", imeiId);
                    intent.putExtra("type", getIntent().getIntExtra("type", -1));
                    startActivity(intent);
                }
            });
        } else {
            isShowIR(false);
        }

        isCallPolice = getIntent().getBooleanExtra("isCallPolice", false);
        orgName = getIntent().getStringExtra("orgName");
        imeiId = getIntent().getStringExtra("imeiId");

        if (isCallPolice) {
            ll.setVisibility(View.GONE);
        }

        p = new GPSP(this);
        p.getLocationInfo(imeiId);


//        mMapView3D = GPS3DUtils.getInstance().getMapView(this, 22.547634, 114.072214);
        mMapView3D.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView3D.getMap();
        }
        aMap.setInfoWindowAdapter(this);
        GPS3DUtils.getInstance().setUiSettings(aMap);
//        GPS3DUtils.getInstance().addMarkersToMap(aMap, R.mipmap.location_query_icon_car_driving, 22.547634, 114.072214);
//        showMarker();

//        fl.addView(mMapView3D, 0, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));


        trafficConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                GPS3DUtils.getInstance().setTraffic(aMap, isChecked);
            }
        });

    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_gps);
    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView3D.onDestroy();
        GPS3DUtils.getInstance().startLocation(null, null);
        GPS3DUtils.getInstance().getAddressByLatlng(null, null, null);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView3D.onResume();
        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                message.arg1 = countDown;
                mHandler.sendMessage(message);
            }
        };
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(task, 1000, 1000);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView3D.onPause();
        timer.cancel();
        timer = null;
        task = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView3D.onSaveInstanceState(outState);
    }

    @OnClick({R.id.gps_alarm_map, R.id.gps_traffic_conditions,
            R.id.gps_panoramic, R.id.gps_vehicle_position,
            R.id.gps_mobile_phone_position, R.id.gps_bg_right_1,
            R.id.gps_bg_right_2, R.id.gps_alarm_info_playback, R.id.gps_alarm_info_})
    public void onClivk(View v) {
        switch (v.getId()) {
            case R.id.gps_alarm_map:
                showSeletMapType();
                break;
            case R.id.gps_traffic_conditions:
                break;
            case R.id.gps_panoramic:
                break;
            case R.id.gps_mobile_phone_position:
                GPS3DUtils.getInstance().startLocation(this, new GPS3DUtils.LocationListener() {
                    @Override
                    public void onCallback(double latitude, double longitude,String address) {
                        GPS3DUtils.getInstance().upMarker(aMap, R.mipmap.alarm_info_, latitude, longitude);
                    }

                    @Override
                    public void onErr() {
                        Tools.makeText("定位失败，请重新定位！");
                    }
                });

                break;
            case R.id.gps_vehicle_position:
                showMarker(lat, lon);
                break;
            case R.id.gps_bg_right_1:
                GPS3DUtils.getInstance().setZoomIn(aMap);
                break;
            case R.id.gps_bg_right_2:
                GPS3DUtils.getInstance().setZoomOut(aMap);
                break;
            case R.id.gps_alarm_info_playback:

                Intent intent2 = new Intent(this, RecordShowActivity.class);
                intent2.putExtra("imeiId", imeiId);
                intent2.putExtra("type", type);
                intent2.putExtra("name", bean.getName());
                intent2.putExtra("color", bean.getColor());
                intent2.putExtra("numberPlates", bean.getCarNumber());
                startActivity(intent2);


                break;
            case R.id.gps_alarm_info_:
                Intent intent = new Intent(this, GPSNaviActivity.class);
                intent.putExtra("STARTLATLNG_LATITUDE", lat);
                intent.putExtra("STARTLATLNG_LONGITUDE", lon);
                startActivity(intent);
                break;

        }
    }

    private void showMarker(double latitude, double longitude) {

        Marker marker = GPS3DUtils.getInstance().upMarker(aMap, getCarColor(bean.getColor()), latitude, longitude);
//        marker.setPosition(new LatLng(latitude, longitude));
        marker.showInfoWindow();
    }

    private void showSeletMapType() {
        if (mPop == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.map_type, null, false);
            contentView.findViewById(R.id.map_type_1).setOnClickListener(mapTypeListener);
            contentView.findViewById(R.id.map_type_2).setOnClickListener(mapTypeListener);
            contentView.findViewById(R.id.map_type_3).setOnClickListener(mapTypeListener);
            contentView.findViewById(R.id.map_type_4).setOnClickListener(mapTypeListener);
            mPop = new PopupWindow(contentView, 100, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mPop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPop.setOutsideTouchable(true);
            mPop.setTouchable(true);
        }
        if (mPop.isShowing()) {
            mPop.dismiss();
        }
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        mPop.showAsDropDown(alarmMap, -alarmMap.getMeasuredWidth() - 50, -alarmMap.getMeasuredHeight() - 50);
    }

    private View.OnClickListener mapTypeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int type = -1;
            switch (v.getId()) {
                case R.id.map_type_1:
                    type = 1;
                    break;
                case R.id.map_type_2:
                    type = 2;
                    break;
                case R.id.map_type_3:
                    type = 3;
                    break;
                case R.id.map_type_4:
                    type = 4;
                    break;
                default:
                    break;
            }
            GPS3DUtils.getInstance().setMapType(aMap, type);
            if (mPop != null) {
                mPop.dismiss();
            }
        }
    };


    @Override
    public View getInfoWindow(Marker marker) {
        if (!isCallPolice) {
            infoContent = getLayoutInflater().inflate(
                    R.layout.map_infowindow, null);

            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            snippetUi = ((TextView) infoContent.findViewById(R.id.map_info_address));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 2 + 150, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 0, 0, 0);
            snippetUi.setLayoutParams(layoutParams);

            infoContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GPSActivity.this, DevicInfoActivity.class);
                    intent.putExtra("name", bean.getName());
                    intent.putExtra("carNum", bean.getCarNumber());
                    intent.putExtra("type", bean.getType());
                    intent.putExtra("model", bean.getVersionType());
                    intent.putExtra("imei", bean.getImeiId());
                    intent.putExtra("isim", bean.getSimId());
                    intent.putExtra("startTime", bean.getInstallDate());
                    intent.putExtra("stopTime", bean.getServiceDate());
                    intent.putExtra("phone", bean.getPhoneNum());
                    GPSActivity.this.startActivity(intent);
                }
            });

            if (bean != null) {

                GPS3DUtils.getInstance().getAddressByLatlng(this, new LatLng(lat, lon), this);

                ((TextView) infoContent.findViewById(R.id.map_info_name)).setText(bean.getName());
                ((TextView) infoContent.findViewById(R.id.map_info_vehicle_num)).setText(bean.getCarNumber());
//                ((TextView) infoContent.findViewById(R.id.map_info_city)).setText(orgName);
                ImageView imageView = ((ImageView) infoContent.findViewById(R.id.map_info_icon));
                if (type == 0) {
                    imageView.setImageResource(R.mipmap.vehicle_icon_wired);
                } else {
                    imageView.setImageResource(R.mipmap.alarm_info_icon_wireless);
                }
                ((TextView) infoContent.findViewById(R.id.map_info_state)).setText("【" + bean.getStatus() + "】");
                if (getColor(bean.getColor()) != -1) {
                    ((TextView) infoContent.findViewById(R.id.map_info_state)).setTextColor(getColor(bean.getColor()));
                }

                ((TextView) infoContent.findViewById(R.id.map_info_location)).setText(bean.getLocationDate() + "[定位]");
                ((TextView) infoContent.findViewById(R.id.map_info_receive)).setText(bean.getReceiveDate() + "[接受]");
                ((TextView) infoContent.findViewById(R.id.map_info_speed)).setText("速度：" + bean.getSpeed() + "KM/H");
                String x = "行驶";
                if (!bean.getAccState().equals("1")) {
                    x = "静止";
                }
                ((TextView) infoContent.findViewById(R.id.map_info_travel_time)).setText(x + bean.getStateLength());
                ((TextView) infoContent.findViewById(R.id.map_info_location_type)).setText("定位：GPS");
                ((TextView) infoContent.findViewById(R.id.map_info_acc)).setText("ACC：" + (bean.getAccState().equals("1") ? "开" : "关"));

            }

        } else {
            infoContent = getLayoutInflater().inflate(
                    R.layout.map_infowindow_call, null);
            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            snippetUiCall = ((TextView) infoContent.findViewById(R.id.map_info_call_address));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 2 + 150, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 0, 0, 0);
            snippetUiCall.setLayoutParams(layoutParams);

            if (bean != null) {
                ((TextView) infoContent.findViewById(R.id.map_info_call_name)).setText(bean.getName());
                ((TextView) infoContent.findViewById(R.id.map_info_call_vehicle_num)).setText(bean.getCarNumber());
                ImageView imageView = (ImageView) infoContent.findViewById(R.id.map_info_call_icon);
                if (type == 0) {
                    imageView.setImageResource(R.mipmap.vehicle_icon_wired);
                } else {
                    imageView.setImageResource(R.mipmap.alarm_info_icon_wireless);
                }
                ((TextView) infoContent.findViewById(R.id.map_info_call_state_t)).setText(bean.getStatus());
                ((TextView) infoContent.findViewById(R.id.map_info_call_state)).setText("【" + bean.getStatus() + "】");
                String str = bean.getLocationDate() + " " + bean.getSpeed() + "KM/H";
                ((TextView) infoContent.findViewById(R.id.map_info_call_location)).setText(str);

            }
        }


        return infoContent;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onShowDialog(String str) {
        showProgressDialog(str);
    }

    @Override
    public void onDissm() {
        dismiss();
    }

    @Override
    public void onShowMessage(int type, String message) {
        if (type == 1) {
            Tools.makeText(message);
        }
    }


    @Override
    public void setData(GPS gps) {

        if (gps != null && gps.getReturnList().size() > 0) {
            lat = gps.getReturnList().get(0).getLatitude();
            lon = gps.getReturnList().get(0).getLongitude();
            bean = gps.getReturnList().get(0);
            imeiId = bean.getImeiId();
            type = bean.getType();
            if (lat > 0 && lon > 0) {
                showMarker(lat, lon);
            } else {
                Tools.makeText("定位数据异常！");
                finish();
            }
        } else {
            Tools.makeText("定位数据为空！");
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {

        if (result != null) {
            String formatAddress = result.getRegeocodeAddress().getFormatAddress();
            if (!isCallPolice) {
                snippetUi.setText("地址:" + formatAddress);
            } else {
                snippetUiCall.setText("地址:" + formatAddress);
            }
        }
//        Log.e("formatAddress", "formatAddress:" + formatAddress);
//        Log.e("formatAddress", "rCode:" + rCode);
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    private int getCarColor(String color){
        int c = R.mipmap.green_car;
        if ("0".equals(color)) {
            c = R.mipmap.gray_car;
        } else if ("1".equals(color)) {
            c =R.mipmap.red_car;
        } else if ("2".equals(color)) {
            c = R.mipmap.green_car;
        } else if ("3".equals(color)) {
            c =R.mipmap.yellow_car;
        } else if ("4".equals(color)) {
            c =R.mipmap.blue_car;
        }
        return c;
    }

    private int getColor(String color) {
        int c = -1;
        if ("0".equals(color)) {
            c = Color.GRAY;
        } else if ("1".equals(color)) {
            c = Color.RED;
        } else if ("2".equals(color)) {
            c = Color.GREEN;
        } else if ("3".equals(color)) {
            c = Color.YELLOW;
        } else if ("4".equals(color)) {
            c = Color.BLUE;
        }
        return c;
    }
}
