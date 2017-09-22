package com.hxyd.dyt.accountManager.view.in;

import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.entity.ProductInfoList;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;

import java.util.List;

/**
 * Created by win7 on 2017/3/13.
 */

public interface SingleVI {
    void onSetBaseData(BaseData baseData);
    void onSetArealist(List<AreaList> list);
    void onSetProductInfoList(ProductInfoList list);
    void onSetOrderDefault(OrderDefultInfo orderDefultInfo);
    void onGetOrderDefault();
    void onErr(String err);
    void onDismiss();
}
