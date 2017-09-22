package com.hxyd.dyt.accountManager.view.in;

import com.hxyd.dyt.accountManager.modle.entity.BaseInfoBean;
import com.hxyd.dyt.accountManager.modle.entity.ProductInfoListBenam;
import com.hxyd.dyt.common.entity.BaseData;

import java.util.List;

/**
 * Created by win7 on 2017/5/18.
 */

public interface SupplementaryVI {
    void setBaseData(BaseData baseData);
    void setBaseInfoBean(BaseInfoBean o);
    void setProductInfoList(List<ProductInfoListBenam> lists);
    void onErr(int type,String err);
    void onDismiss();
    void nextStep();

}
