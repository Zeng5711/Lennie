package com.hxyd.dyt.purplestar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.trace.LBSTraceClient;
import com.amap.api.trace.TraceListener;
import com.amap.api.trace.TraceLocation;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.hxyd.dyt.R;
import com.hxyd.dyt.common.*;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.purplestar.modle.entity.RecorShow;
import com.hxyd.dyt.purplestar.presenter.RecordShowP;
import com.hxyd.dyt.purplestar.presenter.in.RecordShowPI;
import com.hxyd.dyt.purplestar.utils.GPS3DUtils;
import com.hxyd.dyt.purplestar.utils.TraceRePlay;
import com.hxyd.dyt.purplestar.view.in.RecordShowVI;
import com.hxyd.dyt.widget.PlayView;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordShowActivity extends com.hxyd.dyt.common.BaseActivity implements
        AMap.InfoWindowAdapter, RecordShowVI, GeocodeSearch.OnGeocodeSearchListener {

    private final static int AMAP_LOADED = 2;

    private int fastForward = 2000;
    private AMap mAMap;
    private Marker mOriginStartMarker, mOriginEndMarker, mOriginRoleMarker;
    private Polyline mOriginPolyline;

    private List<LatLng> mOriginLatLngList = new ArrayList<>();
//    private List<LatLng> mGraspLatLngList;

    private ExecutorService mThreadPool;
    private TraceRePlay mRePlay;
    private RecorShow mRecordShow;
    private String name;
    private String numberPlates;
    private String color;

    @BindView(R.id.map)
    MapView mMapView;

    @BindView(R.id.seekbar)
    SeekBar seekbar;

    @BindView(R.id.gps_alarm_map)
    ImageView alarmMap;

    @BindView(R.id.gps_traffic_conditions)
    CheckBox trafficConditions;

    @BindView(R.id.record_ll_custom)
    LinearLayout record_ll_custom;

    @BindView(R.id.custom_satr_time)
    TextView custom_satr_time;

    @BindView(R.id.custom_stop_time)
    TextView custom_stop_time;

    @BindView(R.id.record_fast_forward)
    TextView record_fast_forward;

    @BindView(R.id.playview)
    PlayView playview;

    @BindView(R.id.search_ll)
    LinearLayout search_ll;


    private String imeiId;
    private int type;
    private PopupWindow mPop;
    private RecordShowPI p;

    private TextView locationTV, speedTV;

    private static int CUSTOM_TAG = 0;
    private final static int SATR_TIME = 1;
    private final static int STOP_TIME = 2;
    private TimePickerView pvCustomTime;
    private int mFastForward = 1;
    private boolean isPlay = true;

    private boolean isZoom = false;

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        setIL(R.mipmap.back);
        isShowSpoy(false);
        isShowTR(false);
        isShowIR(false);
        isShowIR(false);
        setTitle("轨迹回放");

        imeiId = getIntent().getStringExtra("imeiId");
        type = getIntent().getIntExtra("type", -1);
        name = getIntent().getStringExtra("name");
        color = getIntent().getStringExtra("color");
        numberPlates = getIntent().getStringExtra("numberPlates");
        p = new RecordShowP(this);

//        mMapView = GPS3DUtils.getInstance().getMapView(this, 22.547634, 114.072214);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
//        fl.addView(mMapView, 0, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        int threadPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 3;
        mThreadPool = Executors.newFixedThreadPool(threadPoolSize);
        initMap();

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, final boolean fromUser) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.setThreadPriority(-1);
                        if (fromUser && mOriginRoleMarker != null) {
                            mOriginRoleMarker.setPositionNotUpdate(mOriginLatLngList.get(progress > 0 ? progress - 1 : 0));
                            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(mOriginLatLngList.get(progress > 0 ? progress - 1 : 0)));
                        }
                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            mRePlay.stopTrace();
                        }

                    }
                });

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            mRePlay.setI(seekBar.getProgress());
                            mRePlay.satrTrace();
                            mThreadPool.execute(mRePlay);
                        }

                    }
                });

            }
        });

        trafficConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                GPS3DUtils.getInstance().setTraffic(mAMap, isChecked);
            }
        });

        initCustomTimePicker();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        int top = search_ll.getTop() + getViewTop();

        boolean b = x <= seekbar.getRight() && x >= seekbar.getLeft() && y <= seekbar.getBottom() + top + seekbar.getTop() && y >= seekbar.getTop() + top;

        if (event.getAction() == MotionEvent.ACTION_DOWN && b) {

        }
        System.out.print("===" + b);

        return super.dispatchTouchEvent(event);
    }


    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_record_show);
    }

    private void initMap() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
//            mAMap.setOnMapLoadedListener(this);
            mAMap.setInfoWindowAdapter(this);
            GPS3DUtils.getInstance().setUiSettings(mAMap);
        }
    }

    @OnClick({R.id.record_fast_forward, R.id.gps_alarm_map, R.id.gps_bg_right_1, R.id.gps_bg_right_2, R.id.custom
            , R.id.custom_satr_rl, R.id.custom_stop_rl, R.id.custom_confirm, R.id.custom_cancel,
            R.id.last_day, R.id.last_time, R.id.last_three_days, R.id.playview})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_fast_forward:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            if (fastForward <= 2000 && fastForward > 1000) {
                                fastForward = fastForward - 500;
                                mFastForward++;
                            } else {
                                fastForward = 2000;
                                mFastForward = 1;
                            }
                            mRePlay.setIntervalMillisecond(fastForward);
                            record_fast_forward.setText("X " + mFastForward);
                        }
                    }
                });

                break;
            case R.id.gps_alarm_map:
                showSeletMapType();
                break;
            case R.id.gps_bg_right_1:
                isZoom = true;
                GPS3DUtils.getInstance().setZoomIn(mAMap);
                break;
            case R.id.gps_bg_right_2:
                isZoom = true;
                GPS3DUtils.getInstance().setZoomOut(mAMap);
                break;
            case R.id.custom:
                record_ll_custom.setVisibility(View.VISIBLE);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, ((LinearLayout) findViewById(R.id.record_ll)).getMeasuredHeight(), 0, 0);
                record_ll_custom.setLayoutParams(layoutParams);
                Date nowTime = new Date(System.currentTimeMillis());
                custom_satr_time.setText(getTime(nowTime));
                custom_stop_time.setText(getTime(nowTime));
                break;
            case R.id.custom_satr_rl:
                CUSTOM_TAG = SATR_TIME;
                if (pvCustomTime != null) {
                    pvCustomTime.show();
                }
                break;
            case R.id.custom_stop_rl:
                CUSTOM_TAG = STOP_TIME;
                if (pvCustomTime != null) {
                    pvCustomTime.show();
                }
                break;
            case R.id.custom_confirm:
                String satrTime = custom_satr_time.getText().toString();
                String stopTime = custom_stop_time.getText().toString();
                if (TextUtils.isEmpty(satrTime)) {
                    Tools.makeText("开始时间不能为空");
                    break;
                }
                if (TextUtils.isEmpty(stopTime)) {
                    Tools.makeText("结束时间不能为空");
                    break;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            mRePlay.stopTrace();
                        }
                    }
                });
                record_ll_custom.setVisibility(View.GONE);
                p.getTrackPlayback(imeiId, "N", satrTime, stopTime, type, -1);
                break;
            case R.id.custom_cancel:
                record_ll_custom.setVisibility(View.GONE);
                break;
            case R.id.last_time:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            mRePlay.stopTrace();
                        }
                    }
                });
                p.getTrackPlayback(imeiId, "Y", null, null, type, -1);
                break;
            case R.id.last_day:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            mRePlay.stopTrace();
                        }
                    }
                });
                long time = System.currentTimeMillis();
                p.getTrackPlayback(imeiId, "N", getTime(time, 1), getTime(new Date(time)), type, 1);
                break;
            case R.id.last_three_days:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            mRePlay.stopTrace();
                        }
                    }
                });
                long time2 = System.currentTimeMillis();
                p.getTrackPlayback(imeiId, "N", getTime(time2, 3), getTime(new Date(time2)), type, 3);
                break;
            case R.id.playview:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.setThreadPriority(-20);
                        if (mRePlay != null) {
                            if (isPlay) {
                                isPlay = false;
                                mRePlay.stopTrace();
                            } else {
                                isPlay = true;
                                mRePlay.setI(seekbar.getProgress() == seekbar.getMax() ? 0 : seekbar.getProgress());
                                mRePlay.satrTrace();
                                mThreadPool.execute(mRePlay);
                            }
                            playview.isPlay(isPlay);
                        }
                    }
                });

                break;
            default:
                break;
        }
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
            GPS3DUtils.getInstance().setMapType(mAMap, type);
            if (mPop != null) {
                mPop.dismiss();
            }
        }
    };

//    @Override AMap.OnMapLoadedListener,
//    public void onMapLoaded() {
//        Message msg = handler.obtainMessage();
//        msg.what = AMAP_LOADED;
//        handler.sendMessage(msg);
//    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AMAP_LOADED:
                    setupRecord();
                    break;
                default:
                    break;
            }
        }

    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mThreadPool != null) {
            mThreadPool.shutdownNow();
        }
    }

    private void startMove() {
        if (mRePlay != null) {
            mRePlay.stopTrace();
            SystemClock.sleep(200);
        }
        mRePlay = rePlayTrace(mOriginLatLngList, mOriginRoleMarker);
    }

    /**
     * 轨迹回放方法
     */
    private TraceRePlay rePlayTrace(List<LatLng> list, final Marker updateMarker) {
        TraceRePlay rePlay = new TraceRePlay(list, fastForward,
                new TraceRePlay.TraceRePlayListener() {

                    @Override
                    public void onTraceUpdating(int progress, LatLng latLng) {
                        if (mOriginRoleMarker != null && seekbar != null) {
                            seekbar.setProgress(progress);
//                            updateMarker.setPosition(latLng); // 更新小人实现轨迹回放
//                            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));


                            mOriginRoleMarker.remove();
                            mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                    latLng, isZoom ? mAMap.getCameraPosition().zoom : 19f, 30, 30)));
                            mOriginRoleMarker = mAMap.addMarker(new MarkerOptions().position(
                                    latLng).icon(
                                    BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                            .decodeResource(getResources(), getCarColor(color)))));
                            mOriginRoleMarker.showInfoWindow();


//                            if(locationTV!=null) {
//                                GPS3DUtils.getInstance().getAddressByLatlng(RecordShowActivity.this, latLng, RecordShowActivity.this);
//                                locationTV.setText(mRecordShow.getReturnList().get(progress).getCreateDate() + "[定位]");
//                                speedTV.setText("速度:" + mRecordShow.getReturnList().get(progress).getSpeed() + "KM/H");
//                            }
                        }
                    }

                    @Override
                    public void onTraceUpdateFinish() {

                    }
                });
        mThreadPool.execute(rePlay);
        return rePlay;
    }


    /**
     * 轨迹数据初始化
     */
    private void setupRecord() {
        // 轨迹纠偏初始化
//        LBSTraceClient mTraceClient = new LBSTraceClient(
//                getApplicationContext());

//        LatLng startLatLng = new LatLng(startLoc.getLatitude(),
//                startLoc.getLongitude());
//        LatLng endLatLng = new LatLng(endLoc.getLatitude(),
//                endLoc.getLongitude());
        seekbar.setMax(mOriginLatLngList.size());
        addOriginTrace(mOriginLatLngList.get(0), mOriginLatLngList.get(mOriginLatLngList.size() - 1), mOriginLatLngList);

//            List<TraceLocation> mGraspTraceLocationList = Util
//                    .parseTraceLocationList(recordList);
        // 调用轨迹纠偏，将mGraspTraceLocationList进行轨迹纠偏处理
//            mTraceClient.queryProcessedTrace(1, mGraspTraceLocationList,
//                    LBSTraceClient.TYPE_AMAP, this);

        startMove();

    }


    /**
     * 地图上添加原始轨迹线路及起终点、轨迹动画小人
     *
     * @param startPoint
     * @param endPoint
     * @param originList
     */
    private void addOriginTrace(LatLng startPoint, LatLng endPoint,
                                List<LatLng> originList) {

        mAMap.clear();
        if (mOriginPolyline != null) {
            mOriginPolyline.remove();
        }
        if (mOriginStartMarker != null) {
            mOriginStartMarker.remove();
        }
        if (mOriginEndMarker != null) {
            mOriginEndMarker.remove();
        }
        if (mOriginRoleMarker != null) {
            mOriginRoleMarker.remove();
        }

        mOriginPolyline = mAMap.addPolyline(new PolylineOptions().color(
                Color.BLUE).addAll(originList));
        mOriginStartMarker = mAMap.addMarker(new MarkerOptions().position(
                startPoint).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.start)));
        mOriginEndMarker = mAMap.addMarker(new MarkerOptions().position(
                endPoint).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.end)));

        try {
//            mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getBounds(),
//                    50));
            mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    startPoint, 19f, 30, 30)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mOriginRoleMarker = mAMap.addMarker(new MarkerOptions().position(
                startPoint).icon(
                BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), getCarColor(color)))));
        mOriginRoleMarker.showInfoWindow();
    }

    private TextView snippetUi;

    @Override
    public View getInfoWindow(Marker marker) {
        View infoContent = getLayoutInflater().inflate(
                R.layout.recor_show, null);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        ((TextView) infoContent.findViewById(R.id.map_info_name)).setText(name);
        ((TextView) infoContent.findViewById(R.id.map_info_vehicle_num)).setText(numberPlates);

        ImageView imageView = ((ImageView) infoContent.findViewById(R.id.map_info_icon));
        if (type == 0) {
            imageView.setImageResource(R.mipmap.vehicle_icon_wired);
        } else {
            imageView.setImageResource(R.mipmap.alarm_info_icon_wireless);
        }

        locationTV = ((TextView) infoContent.findViewById(R.id.map_info_location));
        locationTV.setText(mRecordShow.getReturnList().get(seekbar.getProgress()).getCreateDate() + "[定位]");
        speedTV = ((TextView) infoContent.findViewById(R.id.map_info_speed));
        speedTV.setText("速度:" + mRecordShow.getReturnList().get(seekbar.getProgress()).getSpeed() + "KM/H");

        double lat = Double.parseDouble(mRecordShow.getReturnList().get(seekbar.getProgress()).getLatitude());
        double lon = Double.parseDouble(mRecordShow.getReturnList().get(seekbar.getProgress()).getLongitude());
        GPS3DUtils.getInstance().getAddressByLatlng(this, new LatLng(lat, lon), this);
        snippetUi = ((TextView) infoContent.findViewById(R.id.map_info_address));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 2 + 150, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 0, 0, 0);
        snippetUi.setLayoutParams(layoutParams);


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
    public void showRecordShow(final RecorShow recordShow) {

        if (recordShow != null && recordShow.getReturnList() != null && recordShow.getReturnList().size() > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mRecordShow = recordShow;
                    Process.setThreadPriority(-20);
                    mOriginLatLngList.clear();
                    for (int i = 0; i < recordShow.getReturnList().size(); i++) {
                        RecorShow.ReturnListBean bean = recordShow.getReturnList().get(i);
                        double lat = Double.parseDouble(bean.getLatitude());
                        double lon = Double.parseDouble(bean.getLongitude());
                        LatLng latLng = new LatLng(lat, lon);
                        mOriginLatLngList.add(latLng);
                    }

                    seekbar.setProgress(0);
                    if (!playview.getPlay()) {
                        playview.isPlay(true);
                    }
                    fastForward = 2000;

                    Message msg = handler.obtainMessage();
                    msg.what = AMAP_LOADED;
                    handler.sendMessage(msg);

                }
            }).start();
        } else {
            Tools.makeText("没有回放数据！");
        }

    }


    private void initCustomTimePicker() {
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (CUSTOM_TAG == SATR_TIME) {
                    custom_satr_time.setText(getTime(date));
                } else if (CUSTOM_TAG == STOP_TIME) {
                    custom_stop_time.setText(getTime(date));
                }

            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData(tvSubmit);
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .build();
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private String getTime(long time, int data) {
        return getTime(new Date(time - (60 * 60 * 1000 * 24 * data)));

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int i) {
        if (result != null) {
            String formatAddress = result.getRegeocodeAddress().getFormatAddress();
            snippetUi.setText("地址:" + formatAddress);
        }
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
}
