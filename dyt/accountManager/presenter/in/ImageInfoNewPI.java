package com.hxyd.dyt.accountManager.presenter.in;

import com.hxyd.dyt.common.entity.ImageCategoryBean;
import com.hxyd.dyt.common.entity.ImageItem;

import java.util.List;
import java.util.Map;

/**
 * Created by win7 on 2017/4/5.
 */

public interface ImageInfoNewPI {
    void getBaseData();
    void getOrderDefult(String orderNo);
    void onImgUploadNew(final String url, final String Code,final String name,ImageItem imageItem,boolean isDelete);
    void onDestroy();
    void onDeleteImage(String name,ImageItemDeleteCall call);
    void onSyncappdata(double lon,double lat,String add);
}
