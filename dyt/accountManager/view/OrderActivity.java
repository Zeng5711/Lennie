package com.hxyd.dyt.accountManager.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.LoanListBean;
import com.hxyd.dyt.accountManager.modle.entity.OrderInfo;
import com.hxyd.dyt.accountManager.presenter.OrderP;
import com.hxyd.dyt.accountManager.presenter.in.OrderPI;
import com.hxyd.dyt.accountManager.view.in.OrderVI;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.widget.RecycleViewDivider;
import com.hxyd.dyt.widget.adapter.accountManager.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import de.greenrobot.event.EventBus;

public class OrderActivity extends BaseActivity implements OrderVI {

    @BindView(R.id.order_recyclerview)
    RecyclerView mOrderRV;
    @BindView(R.id.order_SwipeRefreshLayout)
    SwipeRefreshLayout mOrderSRL;
    OrderAdapter mOAdapter;
    @BindString(R.string.title_name_order)
    String mTitle;
    @BindColor(R.color.default_background)
    public int dividerColor;
//    private boolean isRefreshing = false;

    private boolean isRefres = true;
    private boolean isScro = true;
    private boolean isUPRefres = true;

    private OrderPI PI;
    private LinearLayoutManager mLayoutManager;
    private List<LoanListBean> loanListBeen = new ArrayList<>();
    private int count = 0;
    private int countTotal = 0;

    @Override
    protected void onDestroy() {
        PI.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        String token = Constant.getToken();
//        if (TextUtils.isEmpty(token)) {
//            showAlertDialog("登录失效，请重新登录！", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    Constant.setToken(null);
//                    EventBus.getDefault().post(new BusEvent(BusEvent.LOGIN_OUT));
//                    OrderActivity.this.finish();
//                }
//            });
//        } else {

            PI = new OrderP(this);

            setIL(R.mipmap.back);
            setIR(R.mipmap.search);
            setTitle(mTitle);
            isShowSpoy(false);
            isShowTR(false);
            getIR().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderActivity.this, SearchActivity.class);
                    OrderActivity.this.startActivity(intent);
                }
            });

//            showProgressDialog("正在努力加载中...");

            mOrderSRL.setColorSchemeResources(R.color.srl_color_1, R.color.srl_color_2,
                    R.color.srl_color_3, R.color.srl_color_4);
            mOrderSRL.setProgressViewOffset(false, 0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            mOrderSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (isUPRefres) {
                        isUPRefres = false;
                        PI.getInfolist(Constant.getToken(), "0", "", false);
                    } else {
                        mOrderSRL.setRefreshing(false);
                    }
                }
            });


            mLayoutManager = new LinearLayoutManager(this);
            mOAdapter = new OrderAdapter(this, loanListBeen);
            mOrderRV.setAdapter(mOAdapter);
            mOrderRV.setLayoutManager(mLayoutManager);
            mOrderRV.addItemDecoration(new RecycleViewDivider(
                    this, LinearLayoutManager.VERTICAL, 40, getResources().getColor(R.color.default_background)));
            mOrderRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        if (mOrderRV != null && !mOrderRV.canScrollVertically(1) && loanListBeen.size() < countTotal) {
                            isScro = false;
                            if (isRefres) {
                                isRefres = false;
                                showFootView(2);
                                count++;
                                PI.getInfolist(Constant.getToken(), String.valueOf(count), "", true);
                            } else {
                                isScro = true;
                            }
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                }
            });
//        }
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_order);
    }

    @Override
    public void onSetInfolist(OrderInfo list) {
        isScro = true;
        if (null != list && isUPRefres && list.getLoanList() != null && list.getLoanList().size() > 0 && mOAdapter != null) {
            countTotal = Integer.parseInt(TextUtils.isEmpty(list.getCountTotal()) ? "0"
                    : list.getCountTotal());
            loanListBeen.addAll(list.getLoanList());
            mOAdapter.notifyDataSetChanged();
        } else if (null != list && !isUPRefres && list.getLoanList() != null && list.getLoanList().size() > 0 && mOAdapter != null) {
            loanListBeen.clear();
            count = 0;
            loanListBeen.addAll(list.getLoanList());
            mOAdapter.notifyDataSetChanged();
        }

        if (mOrderSRL != null && mOrderSRL.isRefreshing()) {
            mOrderSRL.setRefreshing(false);
        }
        isUPRefres = true;

        isRefres = true;
        if (loanListBeen.size() > 5) {
            if (loanListBeen.size() == countTotal) {
                showFootView(1);
            } else {
                showFootView(0);
            }
        } else if (loanListBeen.size() == countTotal && loanListBeen.size() > 3) {
            showFootView(1);
        } else {
            showFootView(0);
        }

        if (list.getLoanList().size() == 0) {
            showAlertDialog("目前您没有订单", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    OrderActivity.this.finish();
                }
            });
        }
        dismiss();
    }

    @Override
    public void onErr(String str) {
        dismiss();
        Tools.makeText(str);

        if (mOrderSRL != null && mOrderSRL.isRefreshing()) {
            mOrderSRL.setRefreshing(false);
        }
    }

    @Override
    public void showDialg(String message) {
        showProgressDialog(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PI != null) {
            isUPRefres = false;
            PI.getInfolist(Constant.getToken(), "0", "", true);
        }
    }

    private void showFootView(int start) {
        if (null != mOrderRV && mOrderRV.getLayoutManager() != null && mOAdapter != null) {
            mOAdapter.setShowRefresh(start);
            LinearLayoutManager linearManager = (LinearLayoutManager) mOrderRV.getLayoutManager();
            int lastItemPosition = linearManager.findLastVisibleItemPosition();
            mOAdapter.notifyItemChanged(lastItemPosition);
        }
    }
}
