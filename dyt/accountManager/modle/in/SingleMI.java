package com.hxyd.dyt.accountManager.modle.in;

import com.hxyd.dyt.accountManager.modle.entity.LoanInfoID;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.entity.ProductInfoList;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/3/13.
 */

public interface SingleMI {
    BaseData getBaseData();

    List<AreaList> getArealist();

    ProductInfoList getProductInfoList();

    OrderDefultInfo getOrderDefultInfo(String orderNo);

    Observable<BaseData> getSelectData(Map<String,Object> map);

    Observable<List<AreaList>> getAreaList(Map<String,Object> map);

    Observable<ProductInfoList> getProductInfoList(Map<String,Object> map);

    void saveBaseData(BaseData baseData);

    void saveAreaList(List<AreaList> areaList);

    void saveProductInfoList(ProductInfoList productInfoLists);

    void onDestroy();

    String getBaseDataVersion();

    String getArealistVersion();

    String getProductInfoListVersion();

    Observable<LoanInfoID> recordLoan(Map<String, Object> map);

    Observable<LoanInfoID> customerInformationInput(Map<String, Object> map);

    Observable<LoanInfoID> loanAndCarMessageInput(Map<String, Object> map);
}
