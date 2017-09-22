package com.hxyd.dyt.accountManager.view.in;

import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;

import java.util.List;

/**
 * Created by win7 on 2017/5/18.
 */

public interface UserInfoNewVI {

    void setBaseData(BaseData baseData);

    void setArealist(List<AreaList> list);

    void setOrderDefault(OrderDefultInfo defultInfo);

    void onErr(int type,String err);

    void nextStep();

    void onDismiss();
}
