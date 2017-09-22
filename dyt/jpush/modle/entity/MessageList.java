package com.hxyd.dyt.jpush.modle.entity;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by win7 on 2017/3/14.
 */

public class MessageList extends RealmObject{

    private String countTotal;
    private RealmList<MessageListBean> messageList;

    public String getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(String countTotal) {
        this.countTotal = countTotal;
    }

    public RealmList<MessageListBean> getMessageList() {
        return messageList;
    }

    public void setMessageList(RealmList<MessageListBean> messageList) {
        this.messageList = messageList;
    }


}
