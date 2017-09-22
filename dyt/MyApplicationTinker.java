package com.hxyd.dyt;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by win7 on 2017/3/27.
 */

public class MyApplicationTinker extends TinkerApplication {

    public MyApplicationTinker() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.hxyd.dyt.MyAppliaction",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
