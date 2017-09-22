package com.hxyd.dyt.accountManager.view.in;

import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;

/**
 * Created by win7 on 2017/3/14.
 */

public interface OrderDefaultVI {
    void setData(OrderDefultInfo orderDefultInfo);
    void onErr(String str);
}
