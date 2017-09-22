package com.hxyd.dyt.jpush.presenter;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;
import com.hxyd.dyt.common.uitl.Sign;
import com.hxyd.dyt.jpush.modle.MessageM;
import com.hxyd.dyt.jpush.modle.entity.MessageList;
import com.hxyd.dyt.jpush.modle.in.MessageMI;
import com.hxyd.dyt.jpush.presenter.in.MessagePI;
import com.hxyd.dyt.jpush.view.in.MessageVI;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/3/17.
 */

public class MessageP implements MessagePI {

    private MessageMI MI;
    private MessageVI VI;

    public MessageP(MessageVI vi) {
        this.VI = vi;
        MI = new MessageM();
    }

    @Override
    public void getMessageList(String pageIndex) {
        if (MI != null && VI != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("token", Constant.getToken());
            map.put("pageIndex", pageIndex);
            map = Constant.addMap(map);

            MI.getMessageList(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<MessageList>() {

                        @Override
                        public void onNext(MessageList data) {
                            if(VI!=null) {
                                Logger.e("getMessageList =========>" + data.toString());
                                VI.onSccess(data.getCountTotal(), data.getMessageList());
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if(VI!=null) {
                                Logger.e(str);
                                VI.onErr(str);
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        MI = null;
        VI = null;
    }
}
