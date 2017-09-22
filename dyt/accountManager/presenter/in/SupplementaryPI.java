package com.hxyd.dyt.accountManager.presenter.in;

import com.hxyd.dyt.accountManager.modle.entity.Supplementary;

/**
 * Created by win7 on 2017/5/18.
 */

public interface SupplementaryPI {
    void getBaseData();
    void getBaseInfoBean(String id);
    void getProductInfoList();
    void submit(Supplementary supplementary);
    void onDestroy();
}
