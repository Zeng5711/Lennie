package com.hxyd.dyt.accountManager.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.gjiazhe.wavesidebar.WaveSideBar;
import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.Contact;
import com.hxyd.dyt.accountManager.modle.entity.Province;
import com.hxyd.dyt.accountManager.modle.entity.VIN;
import com.hxyd.dyt.accountManager.presenter.JingZhenGuP;
import com.hxyd.dyt.accountManager.presenter.in.JingZhenGuPI;
import com.hxyd.dyt.accountManager.view.in.JingZhenGuVI;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.widget.adapter.accountManager.CityAdapter;
import com.hxyd.dyt.widget.adapter.accountManager.ContactsAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class JZGActivity extends BaseActivity implements JingZhenGuVI {

    @BindView(R.id.jzg_make)
    RecyclerView makeRV;

    @BindView(R.id.jzg_model)
    RecyclerView modelRV;

    @BindView(R.id.jzg_style)
    RecyclerView styleRV;

    @BindView(R.id.jzg_side_bar)
    WaveSideBar sideBar;

    private ContactsAdapter mMakeAdapter, mModelAdapter, mStyleAdapter;
    //    private LinearLayoutManager mLayoutManager;
    private ArrayList<Contact> makeList;

    private JingZhenGuPI P;

    private String makeName = "";
    private String modleName = "";
    private String styleName = "";
    private String styleId = "";
    private String provinceId = "";
    private String provinceName = "";
    private String cityId = "";
    private String cityName = "";

    @Override
    protected void onDestroy() {
        P.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        setIL(R.mipmap.back);

        isShowSpoy(false);
        isShowTR(false);
        isShowIR(false);

        P = new JingZhenGuP(this);
        makeRV.setLayoutManager(new LinearLayoutManager(this));
        modelRV.setLayoutManager(new LinearLayoutManager(this));
        boolean isShowCity = getIntent().getBooleanExtra("isShowCity", false);
        if (isShowCity) {
            setTitle("选择城市");
            sideBar.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(200,0,0,0);
            modelRV.setLayoutParams(layoutParams);
            HashMap<String, Object> map = new HashMap<String, Object>();
            P.getJingZhenGuM(JingZhenGuP.OPERATE_PROVINCE, map);
        } else {
            setTitle("选择车型");
            P.getJingZhenGuM(JingZhenGuP.OPERATE_MAKE, new HashMap<String, Object>());

            modelRV.setLayoutManager(new LinearLayoutManager(this));
            styleRV.setLayoutManager(new LinearLayoutManager(this));

//        sideBar.setPosition(WaveSideBar.POSITION_LEFT);
            sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
                @Override
                public void onSelectIndexItem(String index) {
                    if (makeList != null) {
                        for (int i = 0; i < makeList.size(); i++) {
                            if (makeList.get(i).getIndex().equals(index)) {
                                ((LinearLayoutManager) makeRV.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                                if (modelRV.getVisibility() != View.GONE) {
                                    modelRV.setVisibility(View.GONE);
                                }
                                if (styleRV.getVisibility() != View.GONE) {
                                    styleRV.setVisibility(View.GONE);
                                }
                                return;
                            }
                        }
                    }
                }
            });
        }


    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_jzg);
    }

    @Override
    public void onPrompt(int type, String message) {
        Tools.makeText(message);
    }

    @Override
    public void showDialog(int type, String title, String message) {
        showProgressDialog(message);
    }

    @Override
    public void colseDialog() {
        dismiss();
    }

    @Override
    public void setVIN(ArrayList<VIN> list) {

    }

    @Override
    public void setMake(ArrayList<Contact> list) {
        makeList = list;
        mMakeAdapter = new ContactsAdapter(list, new ContactsAdapter.ContactsonClickCallback() {

            @Override
            public void onClick(View v, Contact contact) {
                if (contact != null) {
                    modelRV.setVisibility(View.GONE);
                    styleRV.setVisibility(View.GONE);
                    String makeId = contact.getId();
                    makeName = contact.getName();
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("MakeId", makeId);
                    P.getJingZhenGuM(JingZhenGuP.OPERATE_MODEL, map);
                }
            }
        });
        makeRV.setAdapter(mMakeAdapter);
    }

    @Override
    public void setModel(ArrayList<Contact> list) {
        modelRV.setVisibility(View.VISIBLE);
        mModelAdapter = new ContactsAdapter(list, new ContactsAdapter.ContactsonClickCallback() {

            @Override
            public void onClick(View v, Contact contact) {
                if (contact != null) {
                    styleRV.setVisibility(View.GONE);
                    String modelId = contact.getId();
                    modleName = contact.getName();
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("ModelId", modelId);
                    P.getJingZhenGuM(JingZhenGuP.OPERATE_STYLE, map);
                }
            }
        });
        modelRV.setAdapter(mModelAdapter);
    }

    @Override
    public void setStyle(ArrayList<Contact> list) {
        styleRV.setVisibility(View.VISIBLE);
        mStyleAdapter = new ContactsAdapter(list, new ContactsAdapter.ContactsonClickCallback() {

            @Override
            public void onClick(View v, Contact contact) {
                if (contact != null) {
                    styleId = contact.getId();
                    styleName = contact.getName();
                    startAcrtivity();
                }
            }
        });
        styleRV.setAdapter(mStyleAdapter);
    }

    @Override
    public void setProvince(ArrayList<Province> list) {
        CityAdapter cityAdapter = new CityAdapter(list, new CityAdapter.onClickCallback() {
            @Override
            public void onClick(View v, Province cardBean) {
                Logger.e(cardBean.getCityName());
                modelRV.setVisibility(View.GONE);
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ProvinceId", cardBean.getCityId());
                provinceId = cardBean.getCityId();
                provinceName = cardBean.getCityName();
                P.getJingZhenGuM(JingZhenGuP.OPERATE_CITY, map);
            }
        });
        makeRV.setAdapter(cityAdapter);
    }

    @Override
    public void setCity(ArrayList<Province> list) {
        modelRV.setVisibility(View.VISIBLE);
        CityAdapter cityAdapter = new CityAdapter(list, new CityAdapter.onClickCallback() {
            @Override
            public void onClick(View v, Province cardBean) {
                cityId = cardBean.getCityId();
                cityName = cardBean.getCityName();
                startAcrtivityCity();
            }
        });
        modelRV.setAdapter(cityAdapter);
    }

    @Override
    public void setEstimate(String C2BPrices) {

    }



    private void startAcrtivity() {
        Intent intent = new Intent();
        intent.putExtra("styleId", styleId);
        intent.putExtra("styleName", styleName);
        intent.putExtra("modleName", modleName);
        intent.putExtra("makeName", makeName);
        this.setResult(10001, intent);
        finish();
    }

    private void startAcrtivityCity(){
        Intent intent = new Intent();
        intent.putExtra("provinceId", provinceId);
        intent.putExtra("provinceName", provinceName);
        intent.putExtra("cityId", cityId);
        intent.putExtra("cityName", cityName);
        this.setResult(10002, intent);
        finish();
    }
}
