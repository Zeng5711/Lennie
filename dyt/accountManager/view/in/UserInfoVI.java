package com.hxyd.dyt.accountManager.view.in;

import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;

import java.util.List;

/**
 * Created by win7 on 2017/3/9.
 */

public interface UserInfoVI {

    void onSccess(String loanInfoId);

    void onErr(String str);

    void onSetData();
}
