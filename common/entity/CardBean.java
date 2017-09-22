package com.hxyd.dyt.common.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by KyuYi on 2017/3/2.
 * E-Mail:kyu_yi@sina.com
 * 功能：
 */

public class CardBean implements IPickerViewData {
    String id="";
    String cardNo="";

    public CardBean(String id, String cardNo) {
        this.id = id;
        this.cardNo = cardNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public String getPickerViewText() {
        return cardNo;
    }

    @Override
    public String getCode() {
        return id;
    }
}
