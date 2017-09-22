package com.hxyd.dyt.accountManager.view.in;

import com.hxyd.dyt.accountManager.modle.entity.OrderInfo;

import java.util.List;

/**
 * Created by win7 on 2017/3/14.
 */

public interface OrderVI {
    void onSetInfolist(OrderInfo list);
    void onErr(String str);
    void showDialg(String message);
}
