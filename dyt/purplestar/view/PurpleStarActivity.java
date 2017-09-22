package com.hxyd.dyt.purplestar.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.hxyd.dyt.R;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.purplestar.modle.entity.PurpleStar;
import com.hxyd.dyt.purplestar.presenter.PurpleStarP;
import com.hxyd.dyt.purplestar.presenter.in.PurpleStarPI;
import com.hxyd.dyt.purplestar.view.in.PurpleStarVI;
import com.hxyd.dyt.widget.RecycleViewDivider;
import com.hxyd.dyt.widget.adapter.purplestar.PurpleStarAdapter;


import butterknife.BindView;

public class PurpleStarActivity extends BaseActivity implements PurpleStarVI {


    @BindView(R.id.Total)
    TextView Total;

    @BindView(R.id.On_line)
    TextView On_line;

    @BindView(R.id.Invalid)
    TextView Invalid;

    @BindView(R.id.Off_line)
    TextView Off_line;

    @BindView(R.id.purple_rv)
    RecyclerView rv;

    private PurpleStarAdapter mPurple;

    private PurpleStarPI p;

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        setTitle("紫米星监控平台");
        setIL(R.mipmap.back);
        setIR(R.mipmap.search);
        isShowSpoy(false);
        isShowTR(false);

        p = new PurpleStarP(this);
        p.getStoreList();
        getIR().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PurpleStarActivity.this, ContainerActivity.class);
                startActivity(intent);
            }
        });




    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_purple_star);
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
        }
    }

    @Override
    public void setStoreList(PurpleStar data) {
        if (data != null) {
            Total.setText(data.getSum());
            On_line.setText(data.getOnline());
            Off_line.setText(data.getOffline());
            Invalid.setText(data.getInvalid());

            mPurple = new PurpleStarAdapter(this, data.getReturnList());
            rv.setAdapter(mPurple);
            rv.setLayoutManager(new LinearLayoutManager(this));
//            rv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 30, getResources().getColor(R.color.default_black)));
        } else {
            Tools.makeText("数据为空！");
        }
    }


}
