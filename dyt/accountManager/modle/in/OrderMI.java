package com.hxyd.dyt.accountManager.modle.in;


import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.entity.OrderInfo;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/3/14.
 */

public interface OrderMI {

    Observable<OrderInfo> getInfolist(Map<String,Object> map);

    Observable<OrderDefultInfo> getLoandetail(Map<String,Object> map);

}
