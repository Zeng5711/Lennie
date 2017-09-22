package com.hxyd.dyt.accountManager.presenter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.BaseInfoBean;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.entity.ProductInfoList;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by win7 on 2017/5/15.
 */

public class ADDataManager {

    private static class SingletonHolder {
        private static final ADDataManager INSTANCE = new ADDataManager();
    }

    public static final ADDataManager getInstance() {
        return ADDataManager.SingletonHolder.INSTANCE;
    }

    private List<AreaList> areaLists;
    private ProductInfoList productInfoList;
    private BaseData baseData;
    private OrderDefultInfo defultInfo;
    private BaseInfoBean baseInfoBean;
    private String loanInfoId = "";
    private String valuationPrice = "0";
    private TimePickerView pvCustomTime;
    private String name = "";
    private String phone = "";
    private String audiValuationPrice = "";
    private String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAudiValuationPriceReall() {
        return audiValuationPrice;
    }

    public void setAudiValuationPriceReall(String valuationPriceReall) {
        this.audiValuationPrice = valuationPriceReall;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDefultInfo(OrderDefultInfo defultInfo) {
        this.defultInfo = defultInfo;
    }

    public OrderDefultInfo getDefultInfo() {
        return defultInfo;
    }

    public String getLoanInfoId() {
        return loanInfoId;
    }

    public void setLoanInfoId(String loanInfoId) {
        this.loanInfoId = loanInfoId;
    }

    public String getValuationPrice() {
        return valuationPrice;
    }

    public void setValuationPrice(String valuationPrice) {
        this.valuationPrice = valuationPrice;
    }

    public void setAreaLists(List<AreaList> areaLists) {
        this.areaLists = areaLists;
    }

    public List<AreaList> getAreaLists() {
        return this.areaLists;
    }

    public void setProductInfoList(ProductInfoList productInfoList) {
        this.productInfoList = productInfoList;
    }

    public ProductInfoList getProductInfoList() {
        return this.productInfoList;
    }

    public void setBaseData(BaseData baseData) {
        this.baseData = baseData;
    }

    public BaseData getBaseData() {
        return this.baseData;
    }



    public void setBaseInfoBean(BaseInfoBean baseInfoBean) {
        this.baseInfoBean = baseInfoBean;
    }

    public BaseInfoBean getBaseInfoBean() {
        return this.baseInfoBean;
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public void showTimePicker() {
        if (pvCustomTime != null) {
            pvCustomTime.show();
        }
    }

    public void initTimePicker(Context context, final TextView view) {
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                view.setText(getTime(date));
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData(tvSubmit);
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .build();
    }

}
