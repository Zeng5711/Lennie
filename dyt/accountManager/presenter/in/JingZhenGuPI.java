package com.hxyd.dyt.accountManager.presenter.in;

import java.util.Map;

/**
 * Created by win7 on 2017/5/4.
 */

public interface JingZhenGuPI {

    void getJingZhenGuM(String Operate, Map<String, Object> map);

    void getJingZhenGuObjectData(String Operate, Map<String, Object> map);

    void onDestroy();
}
