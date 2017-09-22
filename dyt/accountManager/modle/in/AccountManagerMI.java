package com.hxyd.dyt.accountManager.modle.in;



import com.hxyd.dyt.accountManager.modle.OrderM;
import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.accountManager.modle.entity.ProductInfoList;
import com.hxyd.dyt.accountManager.modle.entity.Version;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.login.modle.entity.UserInfo;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/3/3.
 */

public interface AccountManagerMI {
    Observable<Object> logOut(Map<String, Object> map);
    Observable<Version> checkVersion(Map<String, Object> map);
    UserInfo getUserInfo();
    Observable<CountTotal> mainInfo(Map<String,Object> map);
    void onDestroy();
}
