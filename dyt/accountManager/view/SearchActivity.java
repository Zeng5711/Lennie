package com.hxyd.dyt.accountManager.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.widget.EditText;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.LoanListBean;
import com.hxyd.dyt.accountManager.modle.entity.OrderInfo;
import com.hxyd.dyt.accountManager.presenter.OrderP;
import com.hxyd.dyt.accountManager.presenter.in.OrderPI;
import com.hxyd.dyt.accountManager.view.in.OrderVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.widget.RecycleViewDivider;
import com.hxyd.dyt.widget.adapter.accountManager.OrderAdapter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements OrderVI {


    @BindView(R.id.ed_search_edit)
    EditText mEdit;
    @BindView(R.id.search_recyclerview)
    RecyclerView mRC;
    @BindString(R.string.progressdialog_title)
    String mDialogTitle;
    OrderAdapter mOAdapter;
    private ProgressDialog mProgressDialog;
    private OrderPI PI;

    @Override
    protected void onDestroy() {
        PI.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        PI = new OrderP(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(mDialogTitle);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

        mRC.setLayoutManager(new LinearLayoutManager(this));
        mRC.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 40, getResources().getColor(R.color.default_background)));
        SpannableString ss = new SpannableString("请输入车牌号、客户姓名、电话");//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(10, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mEdit.setHint(new SpannedString(ss));
    }

    @OnClick(R.id.im_search)
    public void search() {
        String str = mEdit.getText().toString().trim();
        mProgressDialog.show();
        PI.getInfolist(Constant.getToken(), TextUtils.isEmpty(str) ? "0" : "", str, true);

    }

    @OnClick(R.id.im_search_back)
    public void back() {
        finish();
    }

    @Override
    public void onSetInfolist(OrderInfo list) {
        if (null != list && list.getLoanList().size() > 0) {
            List<LoanListBean> loanListBeen = list.getLoanList();
            mOAdapter = new OrderAdapter(this, loanListBeen);
            mRC.setAdapter(mOAdapter);
        } else {
            mOAdapter = new OrderAdapter(this, null);
            mRC.setAdapter(mOAdapter);
            Tools.makeText("您没有可查询的订单");
        }
        mProgressDialog.dismiss();
    }

    @Override
    public void onErr(String str) {
        mProgressDialog.dismiss();
        Tools.makeText(str);
    }

    @Override
    public void showDialg(String message) {
        mProgressDialog.setMessage(message);
        mProgressDialog.show();

    }

    public void showAlertDialog(String str) {
        new AlertDialog.Builder(this)
                .setTitle(mDialogTitle)
                .setMessage(str)
                .setPositiveButton(R.string.alertdialog_positive_button, null)
                .create()
                .show();
    }
}
