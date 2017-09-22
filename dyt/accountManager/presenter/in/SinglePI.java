package com.hxyd.dyt.accountManager.presenter.in;

import com.hxyd.dyt.accountManager.modle.entity.CustomerInfo;

/**
 * Created by win7 on 2017/3/13.
 */

public interface SinglePI {
    void getBaseData();
    void getArealist();
    void getProductInfoList();
    void getOrderDefault(String orderNo);
    void onDestroy();
    void onSubmit(CustomerInfo c);
}
