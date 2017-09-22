package com.hxyd.dyt.jpush.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;


import com.hxyd.dyt.R;
import com.hxyd.dyt.jpush.modle.entity.MessageListBean;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.jpush.presenter.MessageP;
import com.hxyd.dyt.jpush.presenter.in.MessagePI;
import com.hxyd.dyt.jpush.view.in.MessageVI;
import com.hxyd.dyt.widget.RecycleViewDivider;
import com.hxyd.dyt.widget.adapter.accountManager.MessageAdpter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

public class MessageActivity extends BaseActivity implements MessageVI {

    @BindView(R.id.message_recyclerview)
    RecyclerView mRV;
    @BindString(R.string.title_name_message)
    String mTitle;
    private int count = 0;
    private int countTotal = 0;
    private boolean isRefresh = true;
    private boolean isScro = true;

    private MessageAdpter mAdpter;

    List<MessageListBean> mList = new ArrayList<>();
    private MessagePI PI;


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        isShowSpoy(false);
        isShowTR(false);
        isShowIR(false);
        setIL(R.mipmap.back);
        setTitle(mTitle);
        PI = new MessageP(this);
        showProgressDialog("正在努力加载数据中...");
        PI.getMessageList(String.valueOf(count));

        mAdpter = new MessageAdpter(this, mList);
        mRV.setAdapter(mAdpter);
        mRV.setLayoutManager(new LinearLayoutManager(this));
        mRV.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 40, getResources().getColor(R.color.default_background)));
        mRV.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() != null) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    if (isScro && newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition == mList.size() && mList.size() > 5) {
                        isScro = false;
                        if (isRefresh) {
                            showFootView(2);
                            count++;
                            PI.getMessageList(String.valueOf(count));
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
    }

    @Override
    public void onSccess(String str, List<MessageListBean> list) {
        isScro = true;
        isRefresh = false;
        if (null != list&&mAdpter!=null) {
            countTotal = Integer.parseInt(TextUtils.isEmpty(str) ? "0" : str);
            mList.addAll(list);
            mAdpter.notifyDataSetChanged();
            if (list.size() > 0) {
                if(countTotal == mList.size() && mList.size()>5){
                    showFootView(1);
                }else{
                    isRefresh = true;
                    showFootView(0);
                }
                Tools.makeText("更新成功");
            } else if (list.size() == 0) {
                if (countTotal == 0) {
                    Tools.makeText("您没有消息可查看!");
                } else {
                    showFootView(1);
                }
            }
        } else {
            if (mList.size() > 5) {
                showFootView(1);
            } else {
                showFootView(0);
            }
            Tools.makeText("您没有消息可查看!");
        }
        dismiss();

    }

    @Override
    public void onErr(String str) {
        Tools.makeText(str);
        dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PI.onDestroy();
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_message);
    }

    private void showFootView(int start) {
        if (null != mRV && mRV.getLayoutManager() != null&&mAdpter!=null) {
            mAdpter.setShowRefresh(start);
            LinearLayoutManager linearManager = (LinearLayoutManager) mRV.getLayoutManager();
            int lastItemPosition = linearManager.findLastVisibleItemPosition();
            mAdpter.notifyItemChanged(lastItemPosition);
        }
    }
}
