package com.hxyd.dyt.login.modle.entity;

import java.util.List;

/**
 * Created by win7 on 2017/3/7.
 */

public class UserInfo  {


    /**
     * accountName : 13431118834
     * alias : 2e2433ea432e8cdbc6a00b62f66192f7
     * token : cbc3ac284b94eec10c6c902bb7ccca7e
     * userId : 1093
     * userName : 吴宪加
     * menuPermission : [{"permissionKey":"dytApi:ASSESS","permissionName":"车辆评估","dimensionKey":"MENU","userId":78,"templateId":10,"tenantId":null,"recStatus":"A","createdBy":"system","createdDate":1505310275000,"updatedBy":"system","updatedDate":1505310275000},
     * {"permissionKey":"dytApi:RISK:VISIT","permissionName":"风控家访","dimensionKey":"MENU","userId":78,"templateId":10,"tenantId":null,"recStatus":"A","createdBy":"system","createdDate":1505815632000,"updatedBy":"system","updatedDate":1505815632000},
     * {"permissionKey":"dytApi:GPS:MONITORING","permissionName":"GPS监控","dimensionKey":"MENU","userId":78,"templateId":10,"tenantId":null,"recStatus":"A","createdBy":"system","createdDate":1505815632000,"updatedBy":"system","updatedDate":1505815632000},
     * {"permissionKey":"dytApi:GUARANTY","permissionName":"抵押登记","dimensionKey":"MENU","userId":78,"templateId":10,"tenantId":null,"recStatus":"A","createdBy":"system","createdDate":1505310275000,"updatedBy":"system","updatedDate":1505310275000},
     * {"permissionKey":"dytApi:ENTERING","permissionName":"录单功能","dimensionKey":"MENU","userId":78,"templateId":10,"tenantId":null,"recStatus":"A","createdBy":"system","createdDate":1505815632000,"updatedBy":"system","updatedDate":1505815632000},
     * {"permissionKey":"dytApi:GPS","permissionName":"GPS安装","dimensionKey":"MENU","userId":78,"templateId":10,"tenantId":null,"recStatus":"A","createdBy":"system","createdDate":1505310275000,"updatedBy":"system","updatedDate":1505310275000}]
     * roleName : 客户经理
     */

    private String accountName;
    private String alias;
    private String token;
    private String userId;
    private String userName;
    private String roleName;
    private List<MenuPermissionBean> menuPermission;
    private String isGpsPermission;


    public String getIsGpsPermission() {
        return isGpsPermission;
    }

    public void setIsGpsPermission(String isGpsPermission) {
        this.isGpsPermission = isGpsPermission;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<MenuPermissionBean> getMenuPermission() {
        return menuPermission;
    }

    public void setMenuPermission(List<MenuPermissionBean> menuPermission) {
        this.menuPermission = menuPermission;
    }


    public static class MenuPermissionBean {
        /**
         * permissionKey : dytApi:ASSESS
         * permissionName : 车辆评估
         * dimensionKey : MENU
         * userId : 78
         * templateId : 10
         * tenantId : null
         * recStatus : A
         * createdBy : system
         * createdDate : 1505310275000
         * updatedBy : system
         * updatedDate : 1505310275000
         */

        private String permissionKey;
        private String permissionName;
        private String dimensionKey;
        private int userId;
        private int templateId;
        private Object tenantId;
        private String recStatus;
        private String createdBy;
        private long createdDate;
        private String updatedBy;
        private long updatedDate;

        public String getPermissionKey() {
            return permissionKey;
        }

        public void setPermissionKey(String permissionKey) {
            this.permissionKey = permissionKey;
        }

        public String getPermissionName() {
            return permissionName;
        }

        public void setPermissionName(String permissionName) {
            this.permissionName = permissionName;
        }

        public String getDimensionKey() {
            return dimensionKey;
        }

        public void setDimensionKey(String dimensionKey) {
            this.dimensionKey = dimensionKey;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getTemplateId() {
            return templateId;
        }

        public void setTemplateId(int templateId) {
            this.templateId = templateId;
        }

        public Object getTenantId() {
            return tenantId;
        }

        public void setTenantId(Object tenantId) {
            this.tenantId = tenantId;
        }

        public String getRecStatus() {
            return recStatus;
        }

        public void setRecStatus(String recStatus) {
            this.recStatus = recStatus;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public long getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(long createdDate) {
            this.createdDate = createdDate;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public long getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(long updatedDate) {
            this.updatedDate = updatedDate;
        }
    }
}
