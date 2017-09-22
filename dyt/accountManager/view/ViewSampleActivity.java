package com.hxyd.dyt.accountManager.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.Information;
import com.hxyd.dyt.accountManager.modle.entity.InformationItem;
import com.hxyd.dyt.accountManager.presenter.InformationP;
import com.hxyd.dyt.accountManager.presenter.in.InformationPI;
import com.hxyd.dyt.accountManager.view.in.InformationVI;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.uitl.Tools;

import com.hxyd.dyt.widget.adapter.accountManager.ImageInfoAdpterNew;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ViewSampleActivity extends BaseActivity implements InformationVI {

    @BindView(R.id.image_new_rv)
    public RecyclerView mRV;
    private ImageInfoAdpterNew mAdpter;
    private LinearLayoutManager mLinearLayoutManager;
    private HashMap<Integer, Map<String, String>> sampleData = new HashMap<Integer, Map<String, String>>();
    private Map<String, String> mapID = new HashMap<>();
    private Map<String, String> mapDD = new HashMap<>();
    private Map<String, String> mapJD = new HashMap<>();
    private Map<String, String> mapVD = new HashMap<>();
    private Map<String, String> mapXD = new HashMap<>();
    private Map<String, String> mapOD = new HashMap<>();

    private InformationPI PI;

    @Override
    protected void onDestroy() {
        PI.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        isShowTR(false);
        isShowIR(false);
        isShowSpoy(false);
        setIL(R.mipmap.back);
        setTitle("查看样例");
        showProgressDialog("正在努力加载中...");
        PI = new InformationP(this);
        PI.getInformation();
        mAdpter = new ImageInfoAdpterNew(this, false);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRV.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.imageinfo_new);
    }

    @Override
    public void setInformationData(Information information) {
        dismiss();
        if (information != null) {
            List<InformationItem> IDList = information.getIdentifyCard();
            if (IDList != null) {
                saveMap(mapID, IDList);
            }

            List<InformationItem> ODList = information.getOtherInformation();
            if (ODList != null) {
                saveMap(mapOD, ODList);
            }

            List<InformationItem> VDList = information.getVehicleInformation();
            if (VDList != null) {
                saveMap(mapVD, VDList);
            }

            List<InformationItem> DDList = information.getRegistration();
            if (DDList != null) {
                saveMap(mapDD, DDList);
            }

            List<InformationItem> XDList = information.getDrivingLicense();
            if (XDList != null) {
                saveMap(mapXD, XDList);
            }

            List<InformationItem> JDList = information.getDriverLicense();
            if (JDList != null) {
                saveMap(mapJD, JDList);
            }

            sampleData.put(0, mapID);
            sampleData.put(1, mapDD);
            sampleData.put(2, mapJD);
            sampleData.put(3, mapXD);
            sampleData.put(4, mapVD);
            sampleData.put(5, mapOD);

            mAdpter.setSampleData(sampleData);
            mRV.setAdapter(mAdpter);

        } else {
            Tools.makeText("图片信息为空");
        }

    }

    private void saveMap(Map<String, String> map, List<InformationItem> list) {
        if (list != null) {
            for (InformationItem item : list) {
                if (item != null) {
                    String name = item.getImageName();
                    String names[];
                    if (!TextUtils.isEmpty(name)) {
                        names = name.split("_");
                        map.put(names[2] + "_" + names[3], item.getImageUrl());

                    }
                }
            }
        }
    }

    @Override
    public void onErr(String str) {
        dismiss();
        Tools.makeText(str);
    }
}
