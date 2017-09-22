package com.hxyd.dyt.accountManager.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.GPSInstall;
import com.hxyd.dyt.accountManager.modle.entity.Task;
import com.hxyd.dyt.accountManager.presenter.TaskP;
import com.hxyd.dyt.accountManager.presenter.in.TaskPI;
import com.hxyd.dyt.accountManager.view.AccountTaskActivity;
import com.hxyd.dyt.accountManager.view.in.TaskVI;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.widget.RecycleViewDivider;
import com.hxyd.dyt.widget.adapter.accountManager.TaskAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/5/17.
 */

public class TaskFragment extends Fragment implements TaskVI {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_TYPE = "ARG_TYPE";
    private int mPage;
    private int mType;

    @BindView(R.id.task_tv)
    TextView tv;
    @BindView(R.id.task_recyclerview)
    RecyclerView mTaskRV;
    @BindView(R.id.task_SwipeRefreshLayout)
    SwipeRefreshLayout mOrderSRL;
    //    OrderAdapter mOAdapter;
    private TaskAdapter taskAdapter;
    private AccountTaskActivity mActivity;

    private LinearLayoutManager mLayoutManager;
    private List<Task.LoanListBean> loanListBeen = new ArrayList<>();
    private boolean isRefreshing = false;
    private boolean isRefres = true;
    private boolean isScro = true;
    private boolean isUPRefres = true;
    private int count = 0;
    private int countTotal = 0;
    private TaskPI P;
    private String isComplete = "";

    public static TaskFragment newInstance(int page, int type) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putInt(ARG_TYPE, type);
        TaskFragment pageFragment = new TaskFragment();
        pageFragment.setArguments(args);
        Logger.e("AccountTaskAdapter === >>newInstance  ");
        return pageFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AccountTaskActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        mType = getArguments().getInt(ARG_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        P = new TaskP(this);
        P.queryTaskListData(mPage, mType,1);


//        mOrderSRL.setColorSchemeResources(R.color.srl_color_1, R.color.srl_color_2,
//                R.color.srl_color_3, R.color.srl_color_4);
//        mOrderSRL.setProgressViewOffset(false, 0,
//                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
//        mOrderSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (isUPRefres) {
//                    isUPRefres = false;
//                    P.getAssessmentTask("1", isComplete);
//                    Logger.e("mPage = " + mPage);
//                } else {
//                    mOrderSRL.setRefreshing(false);
//                }
//            }
//        });


        mLayoutManager = new LinearLayoutManager(getContext());
        taskAdapter = new TaskAdapter(getContext(), mPage, mType);
        mTaskRV.setAdapter(taskAdapter);
        mTaskRV.setLayoutManager(mLayoutManager);
        mTaskRV.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.default_background)));
//        mTaskRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                    if (!mTaskRV.canScrollVertically(1) && loanListBeen.size() < countTotal) {
//                        isScro = false;
//                        if (isRefres) {
//                            isRefres = false;
//                            showFootView(2);
//                            count++;
//                            P.getAssessmentTask(getPageIndex(), isComplete);
//                        } else {
//                            isScro = true;
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//            }
//        });
    }


    @Override
    public void onResume() {
        super.onResume();
//        if (mPage == 0) {
//            isComplete = "0";
//        } else if (mPage == 1) {
//            isComplete = "1";
//        }
//        isUPRefres = false;
//        P.getAssessmentTask("1", isComplete);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSetInfolist(Task list) {
//        isScro = true;
//        countTotal = list.getCountTotal();
//        if (null != list && isUPRefres && list.getLoanList().size() > 0 && taskAdapter != null) {
//            loanListBeen.addAll(list.getLoanList());
//            taskAdapter.notifyDataSetChanged();
//        } else if (null != list && !isUPRefres && list.getLoanList().size() > 0 && taskAdapter != null) {
//            loanListBeen.clear();
//            count = 0;
//            loanListBeen.addAll(list.getLoanList());
//            taskAdapter.notifyDataSetChanged();
//        }
//
//        if (mOrderSRL != null && mOrderSRL.isRefreshing()) {
//            mOrderSRL.setRefreshing(false);
//            Tools.makeText("刷新成功！");
//        }
//        isUPRefres = true;
//
//        isRefres = true;
//        if (loanListBeen.size() > 5) {
//            if (loanListBeen.size() == countTotal) {
//                showFootView(1);
//            } else {
//                showFootView(0);
//            }
//        } else if (loanListBeen.size() == countTotal && loanListBeen.size() > 3) {
//            showFootView(1);
//        } else {
//            showFootView(0);
//        }
//
//        if (countTotal == 0) {
//            tv.setVisibility(View.VISIBLE);
//
//            mTaskRV.setVisibility(View.GONE);
//        } else if (countTotal > 0) {
//            if (mTaskRV.getVisibility() != View.VISIBLE) {
//                mTaskRV.setVisibility(View.VISIBLE);
//                tv.setVisibility(View.GONE);
//            }
//        }


//        dismiss();
    }

    @Override
    public void onErr(int type, String str) {
        Tools.makeText(str);
    }

    @Override
    public  void showDialg(String message) {
        mActivity.showProgressDialog(message);
    }

    @Override
    public  void dissm() {
        mActivity.dismiss();
    }

    @Override
    public void setGPSInstall(GPSInstall date) {
        if(date!=null&& date.getReturnList() !=null&& date.getReturnList().size()>0) {
            taskAdapter.setData(date.getReturnList());
        }else{
            Tools.makeText("数据为空！");
        }
    }

//    @Override
//    public void onErr(String str) {
//        Tools.makeText(str);
//        isUPRefres = true;
//        if (mOrderSRL.isRefreshing()) {
//            mOrderSRL.setRefreshing(false);
//        }
//    }

    private void showFootView(int start) {
        if (null != mTaskRV && mTaskRV.getLayoutManager() != null && taskAdapter != null) {
            taskAdapter.setShowRefresh(start);
            LinearLayoutManager linearManager = (LinearLayoutManager) mTaskRV.getLayoutManager();
            int lastItemPosition = linearManager.findLastVisibleItemPosition();
            taskAdapter.notifyItemChanged(lastItemPosition);
        }
    }

    private String getPageIndex() {

        String pageIndex = "1";

        if (count < countTotal) {
            count++;
            pageIndex = String.valueOf(count);
        } else if (count == countTotal) {
            pageIndex = String.valueOf(countTotal);
            count = 1;
        }


        return pageIndex;
    }
}
