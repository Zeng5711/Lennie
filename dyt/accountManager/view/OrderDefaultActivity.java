package com.hxyd.dyt.accountManager.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.presenter.OrderDefaultP;
import com.hxyd.dyt.accountManager.presenter.in.OrderDefaultPI;
import com.hxyd.dyt.accountManager.view.in.OrderDefaultVI;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.SharedPrefsUtil;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.widget.adapter.accountManager.OrderDefaultAdapter;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import me.iwf.photopicker.PhotoPreview;

public class OrderDefaultActivity extends BaseActivity implements OrderDefaultVI {

    @BindView(R.id.order_defalut)
    RecyclerView mOrderDefalut;
    @BindString(R.string.title_name_order_defeult)
    String mTitle;

    OrderDefaultAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    private String isLocked;
    private String orderNo;
    private OrderDefaultPI PI;
    private int lastPosition = 0;
    private int lastOffset = 0;
    private boolean isRefresh = true;



    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);


        PI = new OrderDefaultP(this);

        loandetail();


        setIL(R.mipmap.back);
        isShowSpoy(false);
        isShowIR(false);
        setTitle(mTitle);
        setTR("编辑");


        isLocked = getIntent().getStringExtra("isLocked");
        getTR().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLocked.equals("Y")) {
                    Tools.makeText("不可编辑");
                } else {
                    isRefresh = true;
                    SharedPrefsUtil.putValue(null, Constant.ISORDERDEFAULT, true);
                    Intent intent = new Intent(OrderDefaultActivity.this, SingleNewActivity.class);
                    intent.putExtra("orderNo", orderNo);
                    OrderDefaultActivity.this.startActivity(intent);
                }
            }
        });

        if (isLocked.equals("Y")) {
            isShowTR(false);
        }

        mLinearLayoutManager = new LinearLayoutManager(this);
        mOrderDefalut.setLayoutManager(mLinearLayoutManager);
        mOrderDefalut.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mOrderDefalut != null && mOrderDefalut.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });
        mAdapter = new OrderDefaultAdapter(this, null);
        mOrderDefalut.setAdapter(mAdapter);

    }

    /**
     * 记录RecyclerView当前位置
     */
    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mOrderDefalut.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if (mOrderDefalut.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) mOrderDefalut.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        PI.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_order_default);
    }

    @Override
    public void setData(OrderDefultInfo orderDefultInfo) {
        dismiss();
        if (mAdapter != null) {
            mAdapter.setOrderDefultInfo(orderDefultInfo);
            mAdapter.notifyDataSetChanged();
            scrollToPosition();
            if (orderDefultInfo != null && orderDefultInfo.getBaseInfo() != null
                    && !TextUtils.isEmpty(orderDefultInfo.getBaseInfo().getBackReason())
                    && orderDefultInfo.getBaseInfo().getLoanStatus().equals("0")) {
                showAlertDialog("回退原因", orderDefultInfo.getBaseInfo().getBackReason());
            }

            if (orderDefultInfo != null && orderDefultInfo.getBaseInfo() != null
                    && (orderDefultInfo.getBaseInfo().getLoanStatus().equals("3")
                    || orderDefultInfo.getBaseInfo().getLoanStatus().equals("2")
                    || orderDefultInfo.getBaseInfo().getLoanStatus().equals("4"))) {
                isShowTR(false);
            }
        }

    }

    @Override
    public void onErr(String str) {
        dismiss();
        Tools.makeText(str);
    }

    private void loandetail(){
        if (isRefresh) {
            showProgressDialog("正在努力加载中...");
            orderNo = getIntent().getStringExtra("orderNo");
            PI.getLoandetail(Constant.getToken(), orderNo);
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {
        switch (event.event) {
            case BusEvent.SHOW_SPACE_IMAGE_DETAIL_ACTIVITY:
                isRefresh = false;
                startPhotoActivity(this, event.paraInt, (ArrayList<String>) event.object, (ImageView) event.view);
                break;
            case BusEvent.ORDER_DEFAULT_FINSH:
                this.finish();
                break;
            case BusEvent.ORDERDEFAULT_LOANDETAIL:
                loandetail();
                break;
            default:
                break;
        }
    }

    private void showImageDetail(String datas, ImageView imageView) {
        ArrayList<String> photoPaths = new ArrayList<>();
        photoPaths.add(datas);
        PhotoPreview.builder()
                .setPhotos(photoPaths)
                .setAction(1)
                .setCurrentItem(1)
                .setShowActiononly()
                .start(this);
    }

    public void startPhotoActivity(Context context, int position, ArrayList<String> list, ImageView imageView) {
        Intent intent = new Intent(context, DragPhotoActivity.class);
        int location[] = new int[2];

        imageView.getLocationOnScreen(location);
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", imageView.getHeight());
        intent.putExtra("width", imageView.getWidth());
        intent.putExtra("imageurl", list);
        intent.putExtra("position", position);
        context.startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
