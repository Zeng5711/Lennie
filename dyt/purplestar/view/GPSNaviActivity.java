package com.hxyd.dyt.purplestar.view;

import android.os.Bundle;
import android.app.Activity;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.hxyd.dyt.R;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.purplestar.utils.GPS3DUtils;

public class GPSNaviActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsnavi);

        double lat = getIntent().getDoubleExtra("STARTLATLNG_LATITUDE",0);
        double lon = getIntent().getDoubleExtra("STARTLATLNG_LONGITUDE",0);
        if(lat==0&& lon==0){
            Tools.makeText("终点位置获取失败，请重试！");
            finish();
        }
        showProgressDialog("正在规划中...");
        mEndLatlng = new NaviLatLng(lat,lon);
        eList.add(mEndLatlng);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNavi.addAMapNaviListener(this);
        //设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(75);
        mAMapNaviView.setAMapNaviViewListener(this);

    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();

        GPS3DUtils.getInstance().startLocation(this, new GPS3DUtils.LocationListener() {
            @Override
            public void onCallback(double latitude, double longitude,String address) {
                dismiss();
                mStartLatlng = new NaviLatLng(latitude,longitude);
                sList.add(mStartLatlng);
                /**
                 * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
                 *
                 * @congestion 躲避拥堵
                 * @avoidhightspeed 不走高速
                 * @cost 避免收费
                 * @hightspeed 高速优先
                 * @multipleroute 多路径
                 *
                 *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
                 *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
                 */
                int strategy = 0;
                try {
                    //再次强调，最后一个参数为true时代表多路径，否则代表单路径
                    strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mAMapNavi.setCarNumber("京", "DFZ588");
                mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
            }

            @Override
            public void onErr() {
                dismiss();
                Tools.makeText("定位失败，请重新定位！");
                finish();
            }
        });
    }

    @Override
    public void onCalculateRouteSuccess(int[] ids) {
        super.onCalculateRouteSuccess(ids);
        mAMapNavi.startNavi(NaviType.GPS);
    }

}
