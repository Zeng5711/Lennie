package com.hxyd.dyt.accountManager.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.presenter.ADDataManager;
import com.hxyd.dyt.accountManager.presenter.AssessmentResultP;
import com.hxyd.dyt.accountManager.view.SingleNewActivity;
import com.hxyd.dyt.accountManager.view.in.AssessmentResultVI;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.widget.AmountToBeApprovedView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by win7 on 2017/5/15.
 */

public class AssessmentResultFragment extends Fragment implements AssessmentResultVI {

    @BindView(R.id.assessment_reult_phome_num)
    TextView assessment_reult_phome_num;
    @BindView(R.id.amountToBeApprovedView)
    AmountToBeApprovedView approvedView;
    @BindView(R.id.assessment_reult_user_ass_num)
    TextView assessment_reult_user_ass_num;

    @BindView(R.id.assessment_reult_user_name)
    TextView assessment_reult_user_name;


    private AssessmentResultP P;
    private long clickTime = 0;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assessment_result, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (((SingleNewActivity) getActivity()).isEdit()) {
            P = new AssessmentResultP(this);
        }
        assessment_reult_user_name.setText(ADDataManager.getInstance().getName());
        assessment_reult_phome_num.setText(ADDataManager.getInstance().getPhone());
        assessment_reult_user_ass_num.setText(ADDataManager.getInstance().getValuationPrice());
        approvedView.setMoneyText(ADDataManager.getInstance().getAudiValuationPriceReall());

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
        EventBus.getDefault().unregister(this);
        if (P != null) {
            P.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({R.id.assessment_reult_back, R.id.assessment_reult_single, R.id.assessment_reult_im_phome})
    public void onClick(View v) {

        if ((System.currentTimeMillis() - clickTime) > 500) {
            clickTime = System.currentTimeMillis();

            switch (v.getId()) {
                case R.id.assessment_reult_back:
                    EventBus.getDefault().post(new BusEvent(BusEvent.SINGLE_BACK));
                    break;
                case R.id.assessment_reult_single:
//                if (TextUtils.isEmpty(ADDataManager.getInstance().getMessage())) {
                    EventBus.getDefault().post(new BusEvent(BusEvent.FRAGMENT_USERINFO_NEW, this));
//                } else {
//                    ((SingleNewActivity) getActivity()).showAlertDialog(ADDataManager.getInstance().getMessage(), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            EventBus.getDefault().post(new BusEvent(BusEvent.FRAGMENT_USERINFO_NEW, AssessmentResultFragment.this));
//                        }
//                    });
//                }
                    break;
                case R.id.assessment_reult_im_phome:
                    final String phomeNum = assessment_reult_phome_num.getText().toString().trim();
                    if (!TextUtils.isEmpty(phomeNum)) {
                        ((SingleNewActivity) getActivity()).showAlertDialog(phomeNum, "拨打", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phomeNum));
                                dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                AssessmentResultFragment.this.startActivity(dialIntent);
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {
        if (event.event == BusEvent.ASSESSMENTRESULT_GETORDERDEFAULT && P != null) {
            P.getOrderDefault(ADDataManager.getInstance().getLoanInfoId());
        }
    }


    @Override
    public void setMoneyText(String name, String phone, String money, String valuationPriceReall) {
        assessment_reult_user_name.setText(name);
        assessment_reult_phome_num.setText(phone);
        approvedView.setMoneyText(valuationPriceReall);
        assessment_reult_user_ass_num.setText(money);
    }
}
