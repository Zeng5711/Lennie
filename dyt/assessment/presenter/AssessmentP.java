package com.hxyd.dyt.assessment.presenter;

import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.accountManager.presenter.ADDataManager;
import com.hxyd.dyt.accountManager.presenter.in.ImageItemDeleteCall;
import com.hxyd.dyt.assessment.modle.AssessmentM;
import com.hxyd.dyt.assessment.modle.entity.CarData;
import com.hxyd.dyt.assessment.modle.in.AssessmentMI;
import com.hxyd.dyt.assessment.presenter.in.AssessmentPI;
import com.hxyd.dyt.assessment.view.in.AssessmentVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.ImageItem;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.Bitmaps;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Files;
import com.hxyd.dyt.common.uitl.Tools;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/5/25.
 */

public class AssessmentP implements AssessmentPI {

    private AssessmentMI M;
    private AssessmentVI V;

    public AssessmentP(AssessmentVI V) {
        this.V = V;
        this.M = new AssessmentM();
    }

    @Override
    public void saveAssessment(Map<String, Object> map) {

        V.showDialog(0, "", "正在努力上传中...");

        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map = Constant.addMap(map);

        M.saveAssessmentData(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {
                    @Override
                    public void onNext(Object data) {
                        V.onSuccess();
                        V.colseDialog();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        V.onPrompt(0, str);
                        V.colseDialog();
                    }
                });


    }

    @Override
    public void evaluateInformationDetails(Map<String, Object> map) {

        V.showDialog(0, "", "正在努力加载中...");

        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
        map = Constant.addMap(map);

        M.evaluateInformationDetails(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<CarData>() {
                    @Override
                    public void onNext(CarData data) {
                        V.colseDialog();
                        V.onSuccess(data);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        V.colseDialog();
                        V.onPrompt(0, str);
                    }
                });


    }

    private RequestBody getRequestBody(File file1) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), file1);
    }

    @Override
    public void onImgUploadNew(final String orderNo, final String url, final String Code, final String name, final int imageItem) {
        V.showDialog(0, "", "正在努力上传中...");
        new Thread(new Runnable() {
            @Override
            public void run() {

                final String path = Files.getStorageDirectory();
                Map<String, RequestBody> map = new HashMap<>();
                String fileName = Code + "_" + name + ".jpg";
                final String vPath = Bitmaps.compressImage(url, path + fileName);
                File OD_2 = new File(vPath);
                map.put("imageFiles\";filename=\"" + fileName, getRequestBody(OD_2));

                Map<String, Object> map2 = new HashMap<>();
                map2.put("loanId", orderNo);
                map2.put("token", Constant.getToken());
                map2 = Constant.addMap(map2);

                M.imgUpload(Constant.IMG_UPLOAD_URL, map2, map)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new FilterSubscriber<CountTotal>() {

                            @Override
                            public void onNext(CountTotal data) {
                                Logger.e("vPath = " + vPath + "\n this ok ");
                                V.colseDialog();
                                V.onPrompt(0, "上传成功!");
                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(String str) {
                                V.colseDialog();
                                Logger.e("vPath = " + vPath + "\n this err " + str);
                                V.onPrompt(0, "str");
                                EventBus.getDefault().post(new BusEvent(BusEvent.SEE_CAR_DATA_IMAGE_ERR, imageItem));
                            }
                        });
            }
        }).start();
    }

    @Override
    public void onDeleteImage(String orderNo,String systemType, String name, final ImageItemDeleteCall call) {
        Map<String, Object> map = new HashMap<>();
        map.put("loanId", orderNo);
        map.put("token", Constant.getToken());
        map.put("systemType",systemType);
        map.put("name", "53_1_AS_" + name);
        map = Constant.addMap(map);
        M.onDeleteImage(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Object>() {

                    @Override
                    public void onNext(Object data) {
                        call.onSuccess();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        call.onErr(str);
                    }
                });
    }

    @Override
    public void onDestroy() {
        M = null;
        V = null;
    }
}
