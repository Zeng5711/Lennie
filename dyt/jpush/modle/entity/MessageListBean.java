package com.hxyd.dyt.jpush.modle.entity;

import io.realm.RealmObject;

/**
 * Created by win7 on 2017/3/14.
 */

public class MessageListBean extends RealmObject {
        /**
         * customerName : 客户姓名
         * content : 消息内容
         * pullTime : 推送时间
         */

        private String customerName;
        private String content;
        private String pullTime;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPullTime() {
            return pullTime;
        }

        public void setPullTime(String pullTime) {
            this.pullTime = pullTime;
        }
}
