package com.hxyd.dyt.accountManager.presenter;

import com.hxyd.dyt.accountManager.modle.ImageInfoM;
import com.hxyd.dyt.accountManager.modle.SingleM;
import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.in.ImageInfoMI;
import com.hxyd.dyt.accountManager.modle.in.SingleMI;
import com.hxyd.dyt.accountManager.presenter.in.ImageInfoNewPI;
import com.hxyd.dyt.accountManager.presenter.in.ImageItemDeleteCall;
import com.hxyd.dyt.accountManager.view.in.ImageInfoVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.BaseData;
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
 * Created by win7 on 2017/4/5.
 */

public class ImageInfoNewP implements ImageInfoNewPI {

    private ImageInfoMI MI;
    private ImageInfoVI VI;
    private SingleMI M;

    public ImageInfoNewP(ImageInfoVI vi) {
        this.VI = vi;
        MI = new ImageInfoM();
        M = new SingleM();
    }

    @Override
    public void getBaseData() {
        if (M != null && VI != null) {
            BaseData baseData = ADDataManager.getInstance().getBaseData();
//            if (baseData == null) {
//                baseData = M.getBaseData();
//                ADDataManager.getInstance().setBaseData(baseData);
//            }
            if (baseData != null) {
                VI.setImageCategoryBean(baseData.getImage_category());
            }
        }
    }

    @Override
    public void getOrderDefult(String orderNo) {
        if (M != null && VI != null) {
            OrderDefultInfo defultInfo = ADDataManager.getInstance().getDefultInfo();
//            if (defultInfo == null) {
//                defultInfo = M.getOrderDefultInfo(orderNo);
//                ADDataManager.getInstance().setDefultInfo(defultInfo);
//            }
            if (defultInfo != null) {
                VI.setImageInfoBean(defultInfo.getImageInfo());
            }
        }
    }

    @Override
    public void onImgUploadNew(final String url, final String Code, final String name, final ImageItem imageItem, final boolean isDelete) {
        if (MI != null && VI != null) {
            final String path = Files.getStorageDirectory();
            Map<String, RequestBody> map = new HashMap<>();
            String fileName = Code + "_" + name + ".jpg";
            final String vPath = Bitmaps.compressImage(url, path + fileName);
            File OD_2 = new File(vPath);
            map.put("imageFiles\";filename=\"" + fileName, getRequestBody(OD_2));

//            final String vPath2 = Bitmaps.compressImage(url, path + fileName);
//            File OD_3 = new File(vPath2);
//            map.put("imageFiles\";filename=\"" + fileName, getRequestBody(OD_3));


            Map<String, Object> map2 = new HashMap<>();
            map2.put("loanId", ADDataManager.getInstance().getLoanInfoId());
            map2.put("token", Constant.getToken());
            map2 = Constant.addMap(map2);

            MI.imgUpload(Constant.IMG_UPLOAD_URL, map2, map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<CountTotal>() {

                        @Override
                        public void onNext(CountTotal data) {
                            Logger.e("vPath = " + vPath + "\n this ok ");
                            if (VI != null) {
                                VI.dismiss();
                                Tools.makeText("上传成功!");
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (VI != null) {
                                VI.dismiss();
                                Logger.e("vPath = " + vPath + "\n this err " + str);
                                VI.onErr(str);
                                EventBus.getDefault().post(new BusEvent(BusEvent.IAMGE_ITEM_RESH_STATE, imageItem));
                            }
                        }
                    });
        }

    }

    private RequestBody getRequestBody(File file1) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), file1);
    }

    @Override
    public void onDestroy() {
        MI.onDestroy();
        M.onDestroy();
        MI = null;
        M = null;
        VI = null;
    }

    @Override
    public void onDeleteImage(String name, final ImageItemDeleteCall call) {
        if (MI != null && VI != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("loanId", ADDataManager.getInstance().getLoanInfoId());
            map.put("token", Constant.getToken());
            map.put("name", name);
            map = Constant.addMap(map);
            MI.onDeleteImage(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<Object>() {

                        @Override
                        public void onNext(Object data) {
                            if (call != null) {
                                call.onSuccess();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {

                            if (call != null) {
                                call.onErr(str);
                            }
                        }
                    });
        }
    }

    @Override
    public void onSyncappdata(double lon,double lat,String add) {
        if (MI != null && VI != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("loanId", ADDataManager.getInstance().getLoanInfoId());
            map.put("token", Constant.getToken());
            map.put("latitude",lat);
            map.put("longitude",lon);
            map.put("formattedAddress",add);
            map = Constant.addMap(map);
            MI.onSyncappdata(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<Object>() {
                        @Override
                        public void onNext(Object data) {
                            if (VI != null) {
                                VI.dismiss();
                                VI.onSccess();
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (VI != null) {
                                VI.dismiss();
                                VI.onErr(str);
                            }
                        }
                    });
        }
    }
}
