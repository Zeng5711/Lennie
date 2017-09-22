package com.hxyd.dyt.vehiclemortgage.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.vehiclemortgage.entity.VehicleEvaluation;
import com.hxyd.dyt.vehiclemortgage.presenter.VehicleEvaluationP;
import com.hxyd.dyt.vehiclemortgage.presenter.in.VehicleEvaluationPI;
import com.hxyd.dyt.vehiclemortgage.view.VehicleMortgageActivity;
import com.hxyd.dyt.vehiclemortgage.view.in.VehicleEvaluationVI;
import com.hxyd.dyt.widget.ContainsEmojiEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by win7 on 2017/9/9.
 */

public class VehicleEvaluationFragment extends Fragment implements VehicleEvaluationVI {

    private VehicleMortgageActivity mActivity;
    private VehicleEvaluationPI p;

    private String businessId;

    @BindView(R.id.mortgage_infor_name)
    TextView mortgage_infor_name;

    @BindView(R.id.mortgage_infor_car_num)
    TextView mortgage_infor_car_num;

    @BindView(R.id.mortgage_infor_brand)
    TextView mortgage_infor_brand;

    @BindView(R.id.mortgage_infor_service_time)
    TextView mortgage_infor_service_time;

    @BindView(R.id.mortgage_infor_executor)
    TextView mortgage_infor_executor;

    @BindView(R.id.mortgage_infor_brand_tv)
    TextView mortgage_infor_brand_tv;

    @BindView(R.id.mortgage_infor_ll)
    LinearLayout ll;

    private int page = -1;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (VehicleMortgageActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessId = getArguments().getString("businessId");
        page = getArguments().getInt("page");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mortgage_information, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        p = new VehicleEvaluationP(this);

        p.queryMortgageInfo(businessId);


        if (page == 1) {
            ll.setVisibility(View.GONE);
        }


        mortgage_infor_brand_tv.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int brandWidth = Tools.getwidthPixels(getContext()) - mortgage_infor_brand_tv.getMeasuredWidth() - (int) (Tools.getDisplayDensity(getContext()) * 70);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(brandWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.alignWithParent = true;
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mortgage_infor_brand.setLayoutParams(layoutParams);
        mortgage_infor_brand.setGravity(Gravity.RIGHT);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.button2)
    public void onClick(View v) {

        mActivity.showVehicleMort();
    }

    @Override
    public void showDialog(String m) {
        mActivity.showProgressDialog(m);
    }

    @Override
    public void dismiss() {
        mActivity.dismiss();
    }

    @Override
    public void onErr(int type, String s) {
        Tools.makeText(s);
    }

    @Override
    public void setData(VehicleEvaluation evaluation) {
        if (evaluation != null && null != evaluation.getMortgageInfo()) {
            mortgage_infor_name.setText(evaluation.getMortgageInfo().getCust_name());
            mortgage_infor_car_num.setText(evaluation.getMortgageInfo().getPlate_no());
            mortgage_infor_brand.setText(evaluation.getMortgageInfo().getCar_brand());
            mortgage_infor_service_time.setText(evaluation.getMortgageInfo().getStart_date());
            mortgage_infor_executor.setText(evaluation.getMortgageInfo().getAssignee());
        } else {
            Tools.makeText("数据为空");
        }
    }
}
