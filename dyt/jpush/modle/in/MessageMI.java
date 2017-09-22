package com.hxyd.dyt.jpush.modle.in;

import com.hxyd.dyt.jpush.modle.entity.MessageList;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/3/17.
 */

public interface MessageMI {
    Observable<MessageList> getMessageList(Map<String,Object> map);
}
