package com.hxyd.dyt.accountManager.modle.in;

import com.hxyd.dyt.accountManager.modle.entity.LoanInfoID;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.common.http.Result;
import com.hxyd.dyt.login.modle.entity.UserInfo;

import java.util.List;
import java.util.Map;


import rx.Observable;

/**
 * Created by win7 on 2017/3/9.
 */

public interface UserInfoMI {
//    Observable<UserInfo> queryCustomerInfo( String loanInfoId ,String token);
    Observable<LoanInfoID> saveCustomerInfo(Map<String,Object> map);
    void saveRealm(UserInfo userInfo);
    void onDestroy();

}
