//package com.hxyd.dyt.purplestar.utils;
//
//import android.content.Context;
//
//import com.amap.api.maps2d.AMap;
//import com.amap.api.maps2d.AMapOptions;
//import com.amap.api.maps2d.MapView;
//import com.amap.api.maps2d.UiSettings;
//import com.amap.api.maps2d.model.CameraPosition;
//import com.amap.api.maps2d.model.LatLng;
//
///**
// * Created by win7 on 2017/8/23.
// */
//
//public class GPS2DUtils {
//
//    private static class SingletonHodler{
//        private static final GPS2DUtils INSTANCE = new GPS2DUtils();
//    }
//
//    public static final GPS2DUtils getInstance(){
//        return SingletonHodler.INSTANCE;
//    }
//
//    private AMap aMap;
//
//
//    public MapView getMapView(Context context,double latitude, double longitude){
//        // 定义北京市经纬度坐标（此处以北京坐标为例）
//        LatLng centerBJPoint = new LatLng(latitude,longitude);
//        // 定义了一个配置 AMap 对象的参数类
//        AMapOptions mapOptions = new AMapOptions();
//        // 设置了一个可视范围的初始化位置
//        // CameraPosition 第一个参数： 目标位置的屏幕中心点经纬度坐标。
//        // CameraPosition 第二个参数： 目标可视区域的缩放级别
//        // CameraPosition 第三个参数： 目标可视区域的倾斜度，以角度为单位。
//        // CameraPosition 第四个参数： 可视区域指向的方向，以角度为单位，从正北向顺时针方向计算，从0度到360度
//        mapOptions.camera(new CameraPosition(centerBJPoint, 10f, 0, 0));
//        MapView mapView = new MapView(context,mapOptions);
//        aMap = mapView.getMap();
//        return mapView;
//    }
//
//    public void setUiSettings(){
//        UiSettings mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
////        mUiSettings.setZoomControlsEnabled(true);//缩放按钮
////        mUiSettings.setZoomPosition(0);
//        mUiSettings.setAllGesturesEnabled(true);//所有手势
//        mUiSettings.setScaleControlsEnabled(true);
////        mUiSettings.setMyLocationButtonEnabled(true);//显示默认的定位按钮
////        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置
//    }
//
//    public void setTraffic(){
//        aMap.setTrafficEnabled(true);//显示实时路况图层，aMap是地图控制器对象。
//    }
//
//}
