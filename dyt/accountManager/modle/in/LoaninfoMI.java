package com.hxyd.dyt.accountManager.modle.in;

import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.accountManager.modle.entity.LoanInfo;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by win7 on 2017/3/9.
 */

public interface LoaninfoMI {
//    Observable<LoanInfo> queryLoanInfo(Map<String,Object> map);
    Observable<Object> saveLoanInfo(Map<String,Object> map);
    void onDestroy();
}
