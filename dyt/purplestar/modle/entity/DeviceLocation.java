package com.hxyd.dyt.purplestar.modle.entity;

import java.util.List;

/**
 * Created by win7 on 2017/9/1.
 */

public class DeviceLocation {


    private List<UserListBean> userList;

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean {
        /**
         * userAccount : admin
         * userChnName : 管理员
         */

        private String userAccount;
        private String userChnName;

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserChnName() {
            return userChnName;
        }

        public void setUserChnName(String userChnName) {
            this.userChnName = userChnName;
        }
    }
}
