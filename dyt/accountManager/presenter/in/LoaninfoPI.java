package com.hxyd.dyt.accountManager.presenter.in;

import com.hxyd.dyt.accountManager.modle.entity.LoanInfo;

/**
 * Created by win7 on 2017/3/9.
 */

public interface LoaninfoPI {
    void onDestroy();

    void onGetData();

    void onSubmit(LoanInfo loanInfo);
}
