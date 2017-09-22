package com.hxyd.dyt.common.http;


import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.orhanobut.logger.Logger;

import de.greenrobot.event.EventBus;
import rx.functions.Func1;

/**
 * Created by win7 on 2017/2/20.
 */

public class ResponseFunc<T> implements Func1<Result<T>, T> {
    private static final String TAG = "ResponseFunc";

    @Override
    public T call(Result<T> tResult) {
        Logger.e("ResponseFunc = " + tResult.toString());
        if (tResult.code != 0) {
            Logger.e("ResponseFunc_Code:" + tResult.getCode());
            if(tResult.code == 101){
                Constant.setToken(null);
                EventBus.getDefault().post(new BusEvent(BusEvent.LOGIN_OUT));
            }
            throw new ApiException(tResult.getMsg());
        }
        return tResult.getData();
    }


}
