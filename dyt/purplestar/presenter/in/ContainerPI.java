package com.hxyd.dyt.purplestar.presenter.in;

/**
 * Created by win7 on 2017/8/29.
 */

public interface ContainerPI {

    void getEquipmentList(boolean isShowDialog,boolean isSearch, String conditions,long pageIndex);

    void getEquipmentListForSuperManager(boolean isShowDialog,boolean isSearch,int orgId,String conditions,long pageIndex);

    void getAlarmList(boolean isShowDialog,boolean isSearch,int orgId,String conditions,int warningType,long pageIndex);

    void getAlarmScreeningList(int orgId);
}
