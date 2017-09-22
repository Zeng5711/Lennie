package com.hxyd.dyt.accountManager.view.in;

import com.hxyd.dyt.accountManager.modle.entity.ImageInfoBean;
import com.hxyd.dyt.common.entity.ImageCategoryBean;

import java.util.List;

/**
 * Created by win7 on 2017/3/10.
 */

public interface ImageInfoVI {
    void setImageCategoryBean(List<ImageCategoryBean> list);
    void setImageInfoBean(List<ImageInfoBean> o);
    void onSccess();
    void onErr(String str);
    void dismiss();
}
