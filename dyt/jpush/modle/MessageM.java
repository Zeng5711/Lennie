package com.hxyd.dyt.jpush.modle;

import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.ResponseFunc;
import com.hxyd.dyt.jpush.modle.entity.MessageList;
import com.hxyd.dyt.jpush.modle.in.MessageMI;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/3/17.
 */

public class MessageM implements MessageMI{
    @Override
    public Observable<MessageList> getMessageList(Map<String,Object> map) {
        return GitHubAPI.craetRetrofit().messageList( map).map(new ResponseFunc<MessageList>());
    }
}
