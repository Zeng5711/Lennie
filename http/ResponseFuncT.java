package com.hxyd.dyt.common.http;


import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.orhanobut.logger.Logger;

import java.util.List;

import de.greenrobot.event.EventBus;
import rx.functions.Func1;

/**
 * Created by win7 on 2017/3/13.
 */

public class ResponseFuncT <T> implements Func1<ResultT<T>, List<T>> {
    @Override
    public List<T> call(ResultT<T> listResultT) {
        Logger.e("ResponseFuncT ===> " +listResultT.toString());
        if(listResultT.code!=0){

            if(listResultT.code == 101){
                Constant.setToken(null);
                EventBus.getDefault().post(new BusEvent(BusEvent.LOGIN_OUT));
            }
            throw new ApiException(listResultT.getMsg());
        }
        return listResultT.getData();
    }

}
