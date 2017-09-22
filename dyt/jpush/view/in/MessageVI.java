package com.hxyd.dyt.jpush.view.in;

import com.hxyd.dyt.jpush.modle.entity.MessageListBean;

import java.util.List;

/**
 * Created by win7 on 2017/3/17.
 */

public interface MessageVI {
    void onSccess(String str, List<MessageListBean> list);
    void onErr(String str);
}
