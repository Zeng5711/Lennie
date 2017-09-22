package com.hxyd.dyt.purplestar.view.in;

import com.hxyd.dyt.purplestar.modle.entity.RecorShow;

/**
 * Created by win7 on 2017/8/31.
 */

public interface RecordShowVI {

    void onShowDialog(String str);

    void onDissm();

    void onShowMessage(int type,String message);

    void showRecordShow(RecorShow recordShow);
}
