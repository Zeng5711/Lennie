package com.hxyd.dyt.purplestar.view.in;

import com.hxyd.dyt.purplestar.modle.entity.PurpleStar;

/**
 * Created by win7 on 2017/8/29.
 */

public interface PurpleStarVI {

    void onShowDialog(String str);

    void onDissm();

    void onShowMessage(int type,String message);

    void setStoreList(PurpleStar data);
}
