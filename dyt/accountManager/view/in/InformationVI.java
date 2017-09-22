package com.hxyd.dyt.accountManager.view.in;

import com.hxyd.dyt.accountManager.modle.entity.Information;

/**
 * Created by win7 on 2017/4/20.
 */

public interface InformationVI {
    void setInformationData(Information information);
    void onErr(String str);
}
