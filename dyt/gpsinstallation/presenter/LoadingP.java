package com.hxyd.dyt.gpsinstallation.presenter;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.Bitmaps;
import com.hxyd.dyt.common.uitl.Files;
import com.hxyd.dyt.gpsinstallation.entity.ImeiInfo;
import com.hxyd.dyt.gpsinstallation.entity.Loading;
import com.hxyd.dyt.gpsinstallation.modle.LoadingM;
import com.hxyd.dyt.gpsinstallation.modle.in.LoadingMI;
import com.hxyd.dyt.gpsinstallation.presenter.in.LoadingPI;
import com.hxyd.dyt.gpsinstallation.view.in.LoadingVI;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/9/14.
 */

public class LoadingP implements LoadingPI {

    private LoadingMI m;
    private LoadingVI v;

    public LoadingP(LoadingVI v) {
        this.v = v;
        m = new LoadingM();
    }

    @Override
    public void uploadImg(final int type, String businessId, String name, final String pathURL, String imeiId) {

        v.showDialog("正在努力加载中...");

        Map<String, Object> map = new HashMap<>();
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map = Constant.addMap(map);
        map.put("businessId", businessId);
        String fileName = "";
        if (positionImages == type) {
            map.put("groupId", "positionImages_" + imeiId);
            fileName = "positionImages_" + name + ".jpg";
        } else if (materialsImages == type) {
            map.put("groupId", "materialsImages");
            fileName = "materialsImages_" + name + ".jpg";
        } else if (contractImages == type) {
            map.put("groupId", "contractImages");
            fileName = "contractImages_" + name + ".jpg";
        } else if (carImages == type) {
            map.put("groupId", "carImages");
            fileName = "carImages_" + name + ".jpg";
        }

        final String path = Files.getStorageDirectory();
        Map<String, RequestBody> params = new HashMap<>();
        final String vPath = Bitmaps.compressImage(pathURL, path + fileName);
        File OD_2 = new File(vPath);
        params.put("imageFiles\";filename=\"" + fileName, getRequestBody(OD_2));

        m.uploadImg("http://192.168.5.16:9004/app/auth/task/uploadImg", map, params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        v.dismiss();
                        v.uploadImg(type);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.dismiss();
                        v.onErr(type, str);
                        v.deletImg(type, pathURL);
                    }
                });

    }

    @Override
    public void queryAllImgByParams(final int type, String businessId, String imeiId) {

        v.showDialog("正在努力加载中...");

        Map<String, Object> map = new HashMap<>();
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map = Constant.addMap(map);

        map.put("businessId", businessId);

        if (positionImages == type) {
            map.put("groupId", "positionImages_" + imeiId);
        } else if (materialsImages == type) {
            map.put("groupId", "materialsImages");
        } else if (contractImages == type) {
            map.put("groupId", "contractImages");
        } else if (carImages == type) {
            map.put("groupId", "carImages");
        }

        m.queryAllImgByParams(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Loading>() {
                    @Override
                    public void onNext(Loading data) {
                        v.dismiss();
                        v.setLoading(type, data);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.dismiss();
                        v.onErr(type, str);
                    }
                });

    }

    @Override
    public void deleteImg(final int type, String businessId, String name, String imeiId) {

        v.showDialog("正在努力加载中...");

        Map<String, Object> map = new HashMap<>();
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map = Constant.addMap(map);

        map.put("businessId", businessId);


        if (positionImages == type) {
            map.put("groupId", "positionImages_" + imeiId);
            map.put("imageName", "positionImages_" + name);
        } else if (materialsImages == type) {
            map.put("groupId", "materialsImages");
            map.put("imageName", "materialsImages_" + name);
        } else if (contractImages == type) {
            map.put("groupId", "contractImages");
            map.put("imageName", "contractImages_" + name);
        } else if (carImages == type) {
            map.put("groupId", "carImages");
            map.put("imageName", "carImages_" + name);
        }

        m.deleteImg(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        v.dismiss();
                        v.onErr(type, "删除成功");
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.dismiss();
                        v.onErr(type, str);
                    }
                });

    }

    @Override
    public void updateImeiInfo(String position, String imeiIds, String businessId) {

        v.showDialog("正在努力加载中...");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map = Constant.addMap(map);
        map.put("position", position);
        map.put("imeiId", imeiIds);
        map.put("businessId", businessId);

        m.updateImeiInfo(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<ImeiInfo>() {
                    @Override
                    public void onNext(ImeiInfo data) {
                        v.setImeiInfo(data);
                        v.dismiss();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.dismiss();
                        v.onErr(1, str);
                    }
                });

    }

    @Override
    public void updateLoanAudiByBussinessId(String businessId, String mortgageState) {
        v.showDialog("正在努力加载中...");
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map.put("businessId", businessId);
        map.put("mortgageState", mortgageState);
        map = Constant.addMap(map);

        m.updateLoanAudiByBussinessId(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        v.dismiss();
                        v.setImeiInfo(null);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.dismiss();
                        v.onErr(1, str);
                    }
                });

    }

    @Override
    public void saveCarEvaluation(String businessId, String isDormantLock,String dormantLockDesc ,
                                  String isAccidentApplyCar, String surfaceDesc,
                                  String assessmentPrice, String applyCarEvaluationDesc,
                                  String applyCarEvaluationResult, String taskId,String processInstanceId) {


        v.showDialog("正在努力加载中...");
        Map<String ,Object> map = new HashMap<>();
        map.put(Constant.TOKEN_MAP_KEY,Constant.getToken());
        map = Constant.addMap(map);
        map.put("businessId",businessId);
        map.put("isDormantLock",isDormantLock);
        map.put("dormantLockDesc",dormantLockDesc);
        map.put("isAccidentApplyCar",isAccidentApplyCar);
        map.put("surfaceDesc",surfaceDesc);
        map.put("assessmentPrice",assessmentPrice);
        map.put("applyCarEvaluationDesc",applyCarEvaluationDesc);
        map.put("applyCarEvaluationResult",applyCarEvaluationResult);
        map.put("taskId",taskId);
        map.put("processInstanceId",processInstanceId);

        m.saveCarEvaluation(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        v.dismiss();
                        v.setImeiInfo(null);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        v.dismiss();
                        v.onErr(1,str);
                    }
                });

    }

    private RequestBody getRequestBody(File file1) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), file1);
    }
}
