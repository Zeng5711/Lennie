package com.hxyd.dyt.accountManager.view.in;

import com.hxyd.dyt.accountManager.modle.entity.Contact;
import com.hxyd.dyt.accountManager.modle.entity.Make;
import com.hxyd.dyt.accountManager.modle.entity.Model;
import com.hxyd.dyt.accountManager.modle.entity.Province;
import com.hxyd.dyt.accountManager.modle.entity.Style;
import com.hxyd.dyt.accountManager.modle.entity.VIN;
import com.hxyd.dyt.common.entity.CardBean;

import java.util.ArrayList;

/**
 * Created by win7 on 2017/5/16.
 */

public interface JingZhenGuVI {

    void onPrompt(int type, String message);

    void showDialog(int type, String title, String message);

    void colseDialog();

    void setVIN(ArrayList<VIN> list);

    void setMake(ArrayList<Contact> list);

    void setModel(ArrayList<Contact> list);

    void setStyle(ArrayList<Contact> list);

    void setProvince(ArrayList<Province> list);
//
    void setCity(ArrayList<Province> list);
//
    void setEstimate(String C2BPrices);

}
