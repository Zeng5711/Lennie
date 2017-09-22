package com.hxyd.dyt.accountManager.presenter.in;

import com.hxyd.dyt.common.entity.ImageCategoryBean;

import java.util.List;
import java.util.Map;

/**
 * Created by win7 on 2017/3/10.
 */

public interface ImageInfoPI {
    void onImgReadAll();
    void onImgUpload(Map<String,String> map, List<ImageCategoryBean> list);
    void onDestroy();
}
