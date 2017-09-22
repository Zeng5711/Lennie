package com.hxyd.dyt.purplestar.view;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.purplestar.modle.entity.AlarmScreen;
import com.hxyd.dyt.purplestar.modle.entity.Container;
import com.hxyd.dyt.purplestar.presenter.ContainerP;
import com.hxyd.dyt.purplestar.presenter.in.ContainerPI;
import com.hxyd.dyt.purplestar.view.in.ContainerVI;
import com.hxyd.dyt.widget.MyRecyclerView;
import com.hxyd.dyt.widget.RecycleViewDivider;
import com.hxyd.dyt.widget.SearchView;
import com.hxyd.dyt.widget.adapter.purplestar.CallPoliceScreenAdapter;
import com.hxyd.dyt.widget.adapter.purplestar.ContainerAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ContainerActivity extends BaseActivity implements ContainerVI {

    @BindView(R.id.container_rv_screen)
    MyRecyclerView rvScreen;

    @BindView(R.id.container_rv)
    MyRecyclerView rv;

    @BindView(R.id.container_bt)
    Button bt;

    @BindView(R.id.container_ll)
    LinearLayout ll;

    @BindView(R.id.container_fl)
    FrameLayout fl;

    @BindView(R.id.search_view)
    SearchView mSearchView;

    private ContainerAdapter mContainer;
    private CallPoliceScreenAdapter mCallPoliceScreen;

    private boolean isShowData;
    private int mSign = 0;

    private int CONTAINER_SIGN = 0;
    private int CALL_POLICE_SIGN = 1;
    private int SCREEN_SIGN = 2;
    private int SEARCH_SIGN = 3;

    private List<Container.ReturnListBean> returnList = new ArrayList<>();
    private List<Container.ReturnListBean> AlarmList = new ArrayList<>();
    private List<Container.ReturnListBean> SearchList = new ArrayList<>();

    private String title;
    private boolean isAdmin;
    private ContainerPI p;
    private int orgId = -1;
    private LinearLayoutManager mLayoutManager;
    private long returnCountTota = 0;
    private long alarCountTota = 0;
    private long searchCountTota = 0;

    private long returnPage = 1;
    private long alarPage = 1;
    private long searPage = 1;

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {


        setIL(R.mipmap.back);
        isShowSpoy(false);
        isShowTR(false);
        setIR(R.mipmap.alarm_icon_filter);
        isShowIR(false);
        getIL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        p = new ContainerP(this);

        getIR().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.getAlarmScreeningList(orgId);
            }
        });

        title = getIntent().getStringExtra("orgName");
        isShowData = getIntent().getBooleanExtra("showData", false);
        isAdmin = getIntent().getBooleanExtra("ACCOUNT_NAME", true);

        if (!isShowData) {
            setTitle("车辆列表");
            bt.setVisibility(View.GONE);
            mSign = SEARCH_SIGN;
        } else {
            setTitle(title + "风控平台");
            orgId = getIntent().getIntExtra("orgId", -1);
            getEquipmentList(true,false, orgId, null, 1);
        }

        mContainer = new ContainerAdapter(this);
        rv.setAdapter(mContainer);
        mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
//        rv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 40, getResources().getColor(R.color.default_black)));
        rv.setOnBottomCallback(new MyRecyclerView.OnBottomCallback() {
            @Override
            public void onBottom() {

                boolean isScreen = false;
                boolean isLoad = true;
                long pageNum = 0;
                if (mSign == CONTAINER_SIGN) {
                    returnPage++;
                    pageNum = returnPage;
                    isLoad = returnList.size() == returnCountTota;
                } else if (mSign == CALL_POLICE_SIGN) {
                    alarPage++;
                    pageNum = alarPage;
                    isLoad = AlarmList.size() == alarCountTota;
                }else if(mSign == SEARCH_SIGN){
                    searPage++;
                    pageNum = searPage;
                    isLoad = SearchList.size() == searchCountTota;
                }
                if (!isLoad) {
                    showFootView(2);
                    getEquipmentList(false,isScreen, orgId, null, pageNum);
                }else{
                    showFootView(1);
//                    Tools.makeText("已經是底部了");
                }
            }
        });
        mCallPoliceScreen = new CallPoliceScreenAdapter(this, new CallPoliceScreenAdapter.ScreenCallback() {

            @Override
            public void onCallback(int position) {
                mSign = CALL_POLICE_SIGN;
                isShowView(true);
                isShowIR(true);
                setTitle("报警列表");
                AlarmList.clear();
                p.getAlarmList(true,false, orgId, null, position, 1);
            }
        });
        rvScreen.setAdapter(mCallPoliceScreen);
        rvScreen.setLayoutManager(new LinearLayoutManager(this));

        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mSign == CONTAINER_SIGN || mSign == SEARCH_SIGN) {
                        showFootView(0);
                        getEquipmentList(true,true, orgId, mSearchView.getText().toString().trim(), 1);
                    } else if (mSign == CALL_POLICE_SIGN) {
                        showFootView(0);
                        p.getAlarmList(true,true, orgId, mSearchView.getText().toString().trim(), -1, 1);
                    }
                    mSearchView.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_container);
    }

    @OnClick(R.id.container_bt)
    public void onClick(View view) {
        AlarmList.clear();
        showFootView(0);
        p.getAlarmList(true,false, orgId, null, -1, 1);
    }

    private void isShowView(boolean isShow) {
        ll.setVisibility(isShow ? View.VISIBLE : View.GONE);
        fl.setVisibility(isShow ? View.VISIBLE : View.GONE);
        rvScreen.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onShowDialog(String str) {
        showProgressDialog(str);
    }

    @Override
    public void onDissm() {
        dismiss();
    }

    @Override
    public void onShowMessage(int type, String message) {
        if (type == 1) {
            Tools.makeText(message);
            showFootView(0);
        }
    }

    @Override
    public void setEquipmentList(int type, Container data, long countTota) {
        if (type == 1) {
            mSign = CONTAINER_SIGN;
            returnCountTota = countTota;
            returnList.addAll(data.getReturnList());
            mContainer.setCallPolice(false);
            notifyContainer(returnList);
            if (returnList.size() == 0) {
                Tools.makeText("门店数据为空！");
                finish();
            }
        } else if (type == 2) {
            alarCountTota = countTota;
            isShowIR(true);
            setTitle("报警列表");
            bt.setVisibility(View.GONE);
            mSign = CALL_POLICE_SIGN;
            AlarmList.addAll(data.getReturnList());
            mContainer.setCallPolice(true);
            notifyContainer(AlarmList);
            if (AlarmList.size() == 0) {
                Tools.makeText("报警数据为空！");
                back();
            }
        } else if (type == 0) {
            mSign = SEARCH_SIGN;
            searchCountTota = countTota;
            mContainer.setCallPolice(false);
            SearchList.clear();
            SearchList.addAll(data.getReturnList());
            notifyContainer(SearchList);
            if (SearchList.size() == 0) {
                Tools.makeText("没有搜索到相关数据，请确认搜索条件！");
            }
        }


    }

    @Override
    public void setAlarmScreen(AlarmScreen alarmScreen) {
        setTitle("报警筛选");
        isShowView(false);
        isShowIR(false);
        mSign = SCREEN_SIGN;
        mCallPoliceScreen.setData(alarmScreen.getReturnList());
    }

    private void notifyContainer(List<Container.ReturnListBean> returnList) {
        mContainer.setData(returnList);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        if (mSign == CONTAINER_SIGN || mSign == SEARCH_SIGN) {
            ContainerActivity.this.finish();
        } else if (mSign == CALL_POLICE_SIGN) {
            mContainer.setCallPolice(false);
            mSign = CONTAINER_SIGN;
            isShowIR(false);
            setTitle(title + "风控平台");
            if (isShowData) {
                bt.setVisibility(View.VISIBLE);
            }
            notifyContainer(returnList);
        } else if (mSign == SCREEN_SIGN) {
            mContainer.setCallPolice(true);
            isShowView(true);
            isShowIR(true);
            setTitle("报警列表");
            bt.setVisibility(View.GONE);
            mSign = CALL_POLICE_SIGN;
            notifyContainer(AlarmList);
        }
    }

    private void getEquipmentList(boolean isShowDialog,boolean isisSearch, int orgId, String conditions, long pageIndex) {
        if (isAdmin) {
            //getEquipmentListForSuperManager
            p.getEquipmentListForSuperManager(isShowDialog,isisSearch, orgId, conditions, pageIndex);
        } else {
            //getEquipmentList
            p.getEquipmentList(isShowDialog,isisSearch, conditions, pageIndex);
        }
    }

    private void showFootView(int start) {
        if (null != rv && rv.getLayoutManager() != null && mContainer != null) {
            mContainer.setShowRefresh(start);
            LinearLayoutManager linearManager = (LinearLayoutManager) rv.getLayoutManager();
            int lastItemPosition = linearManager.findLastVisibleItemPosition();
            mContainer.notifyItemChanged(lastItemPosition);
        }
    }
}
