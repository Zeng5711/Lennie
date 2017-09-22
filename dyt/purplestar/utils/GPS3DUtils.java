package com.hxyd.dyt.purplestar.utils;

import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Log;
import android.util.Pair;
import android.widget.CompoundButton;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
//import com.amap.api.services.core.LatLonPoint;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.hxyd.dyt.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by win7 on 2017/8/23.
 */

public class GPS3DUtils implements AMapLocationListener {

    private static class SingletonHodler {
        private static final GPS3DUtils INSTANCE = new GPS3DUtils();
    }

    public static GPS3DUtils getInstance() {
        return SingletonHodler.INSTANCE;
    }

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private LocationListener mListener;

    public static interface LocationListener {

        void onCallback(double latitude, double longitude,String address);

        void onErr();
    }

    public MapView getMapView(Context context, double latitude, double longitude) {
        LatLng centerBJPoint = new LatLng(latitude, longitude);
        AMapOptions mapOptions = new AMapOptions();
        mapOptions.camera(new CameraPosition(centerBJPoint, 10f, 0, 0));
        MapView mapView = new MapView(context, mapOptions);
        return mapView;
    }

    public void setUiSettings(AMap aMap) {
        UiSettings mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setAllGesturesEnabled(true);//所有手势
        mUiSettings.setScaleControlsEnabled(true);
        mUiSettings.setZoomControlsEnabled(false);
        aMap.setMapType(AMap.MAP_TYPE_NAVI);
        aMap.showBuildings(true);
    }

    public void setTraffic(AMap aMap, boolean b) {
        aMap.setTrafficEnabled(b);//显示实时路况图层，aMap是地图控制器对象。
    }

    public void setMapType(AMap aMap, int type) {
        switch (type) {
            case 1:
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                break;
            case 2:
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
                break;
            case 3:
                aMap.setMapType(AMap.MAP_TYPE_NAVI);
                break;
            case 4:
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);//夜景地图模式
            default:
                break;

        }

    }


    public void setZoomIn(AMap aMap) {
        changeCamera(aMap, CameraUpdateFactory.zoomIn(), null);
    }

    public void setZoomOut(AMap aMap) {
        changeCamera(aMap, CameraUpdateFactory.zoomOut(), null);
    }


    private void changeCamera(AMap aMap, CameraUpdate update, AMap.CancelableCallback callback) {
        aMap.animateCamera(update, 1000, callback);
    }

    public Marker addMarkersToMap(AMap aMap, int drawableId, double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                latLng, 18, 30, 30)));
        aMap.clear();
        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(drawableId))
                .position(new LatLng(latitude, longitude))
                .draggable(true);
        return aMap.addMarker(markerOption);
    }


    public Marker upMarker(AMap aMap, int drawableId, double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                latLng, 18, 30, 30)));
        aMap.clear();
        if (drawableId == 0) {
            return aMap.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        } else {
            return aMap.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(drawableId)));
        }

    }

    public void startLocation(Context context, LocationListener listener) {

        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(context);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

        }
        mListener = listener;
        if (mListener != null) {
            mlocationClient.startLocation();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
//                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                amapLocation.getLatitude();//获取纬度
//                amapLocation.getLongitude();//获取经度
//                amapLocation.getAccuracy();//获取精度信息
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(amapLocation.getTime());
//                df.format(date);//定位时间
                if (mListener != null) {
                    mListener.onCallback(amapLocation.getLatitude(), amapLocation.getLongitude(),amapLocation.getAddress());
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                if (mListener != null) {
                    mListener.onErr();
                }
            }
            mlocationClient.stopLocation();
        }
    }

    public void setSmoothMoveMarker(AMap mAMap, List<LatLng> points) {
        // 获取轨迹坐标点
//    List<LatLng> points = readLatLngs();
        LatLngBounds bounds = new LatLngBounds(points.get(0), points.get(points.size() - 2));
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        SmoothMoveMarker smoothMarker = new SmoothMoveMarker(mAMap);
        // 设置滑动的图标
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.location_query_icon_car_driving));

        LatLng drivePoint = points.get(0);
        Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points, drivePoint);
        points.set(pair.first, drivePoint);
        List<LatLng> subList = points.subList(pair.first, points.size());

        // 设置滑动的轨迹左边点
        smoothMarker.setPoints(subList);
        // 设置滑动的总时间
        smoothMarker.setTotalDuration(40);
        // 开始滑动
        smoothMarker.startSmoothMove();
    }

//    private void setfromandtoMarker(AMap aMap,LatLonPoint mStartPoint,LatLonPoint mEndPoint) {
//        aMap.addMarker(new MarkerOptions()
//                .position(convertToLatLng(mStartPoint))
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
//        aMap.addMarker(new MarkerOptions()
//                .position(convertToLatLng(mEndPoint))
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));
//    }

//    /**
//     * 把LatLonPoint对象转化为LatLon对象
//     */
//    public  LatLng convertToLatLng(LatLonPoint latLonPoint) {
//        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
//    }

    private GeocodeSearch geocoderSearch;

    public void getAddressByLatlng(Context context, LatLng latLng, GeocodeSearch.OnGeocodeSearchListener listener) {
        if (geocoderSearch == null && null!= context && latLng!=null && listener!=null) {
            geocoderSearch = new GeocodeSearch(context);
            geocoderSearch.setOnGeocodeSearchListener(listener);
            LatLonPoint lp = new LatLonPoint(latLng.latitude, latLng.longitude);
            RegeocodeQuery query = new RegeocodeQuery(lp, 500f, GeocodeSearch.AMAP);
            geocoderSearch.getFromLocationAsyn(query);
        }
        if (listener == null && geocoderSearch!=null) {
            geocoderSearch.setOnGeocodeSearchListener(null);
        }
    }

}
