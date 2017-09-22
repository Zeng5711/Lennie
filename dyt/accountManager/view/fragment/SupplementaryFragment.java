package com.hxyd.dyt.accountManager.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.BaseInfoBean;
import com.hxyd.dyt.accountManager.modle.entity.PeriodListBean;
import com.hxyd.dyt.accountManager.modle.entity.ProductInfoListBenam;
import com.hxyd.dyt.accountManager.modle.entity.RepaymentMethodListBean;
import com.hxyd.dyt.accountManager.modle.entity.Supplementary;
import com.hxyd.dyt.accountManager.modle.entity.VersionListBean;
import com.hxyd.dyt.accountManager.presenter.ADDataManager;
import com.hxyd.dyt.accountManager.presenter.SupplementaryP;
import com.hxyd.dyt.accountManager.presenter.in.SupplementaryPI;
import com.hxyd.dyt.accountManager.view.SingleNewActivity;
import com.hxyd.dyt.accountManager.view.in.SupplementaryVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.common.entity.CardBean;
import com.hxyd.dyt.common.entity.LoanPurposeBean;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Tools;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by win7 on 2017/5/15.
 */

public class SupplementaryFragment extends Fragment implements SupplementaryVI {


    @BindView(R.id.loaninfo_usage_of_loan)
    TextView loaninfo_usage_of_loan;
    @BindView(R.id.loaninfo_product_type)
    TextView loaninfo_product_type;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.loaninfo_repayment_method)
    TextView loaninfo_repayment_method;
    @BindView(R.id.loaninfo_loan_cycle)
    TextView loaninfo_loan_cycle;
//    @BindView(R.id.vehicleinfo_date_of_production)
//    TextView vehicleinfo_date_of_production;
    @BindView(R.id.vehicleinfo_date_of_issue)
    TextView vehicleinfo_date_of_issue;
    @BindView(R.id.vehicleinfo_scd_tv2)
    TextView vehicleinfo_scd_tv2;


    @BindView(R.id.vehicleinfo_body_color)
    EditText vehicleinfo_body_color;
    @BindView(R.id.loaninfo_loan_amount)
    EditText loaninfo_loan_amount;
    @BindView(R.id.vehicleinfo_license_plate)
    EditText vehicleinfo_license_plate;
    @BindView(R.id.vehicleinfo_frame_number)
    EditText vehicleinfo_frame_number;
//    @BindView(R.id.vehicleinfo_fuel)
//    EditText vehicleinfo_fuel;
    @BindView(R.id.vehicleinfo_engine_number)
    EditText vehicleinfo_engine_number;
//    @BindView(R.id.vehicleinfo_new_unit_price)
//    EditText vehicleinfo_new_unit_price;
    @BindView(R.id.vehicleinfo_scd)
    EditText vehicleinfo_scd;
    @BindView(R.id.vehicleinfo_model)
    EditText vehicleinfo_model;

    private ArrayList<CardBean> usageItem = new ArrayList<>();
    private ArrayList<CardBean> productItem = new ArrayList<>();
    private ArrayList<CardBean> loanItem = new ArrayList<>();
    private ArrayList<CardBean> repaymentItem = new ArrayList<>();
    private ArrayList<CardBean> versionItem = new ArrayList<>();
    private List<ProductInfoListBenam> mList;
    private OptionsPickerView pvUsage_of_loan, pvProduct_type, pvLoan_cycle, pvRepayment_method, pvVersion;
    private TimePickerView pvCustomTime;
    private static int DATA_STATER = 0;
    private static int DATA_PRODUCTION = 1;
    private static int DATA_ISSUE = 2;
    private String mCarInfoId = "";
    private SupplementaryPI P;
    private long clickTime = 0;
    private boolean isNextStep = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e("SupplementaryFragment  onCreate ");
        EventBus.getDefault().register(this);
        P = new SupplementaryP(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.supplementary, container, false);
        ButterKnife.bind(this, view);
        Logger.e("SupplementaryFragment  onCreateView ");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.e("SupplementaryFragment  onActivityCreated ");
        initCPUsage();
        initCPproduct();
        initVersion();
        initCPRepayment();
        initCustomTimePicker();
        initCPLoan();
        P.getBaseData();

        vehicleinfo_license_plate.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                vehicleinfo_license_plate.removeTextChangedListener(this);//解除文字改变事件
                vehicleinfo_license_plate.setText(s.toString().toUpperCase());//转换
                vehicleinfo_license_plate.setSelection(s.toString().length());//重新设置光标位置
                vehicleinfo_license_plate.addTextChangedListener(this);//重新绑
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });


        vehicleinfo_scd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.vehicleinfo_scd) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;

            }
        });

        vehicleinfo_scd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString()) && vehicleinfo_scd.getText().length() <= 100) {

                    if ((100 - vehicleinfo_scd.getText().length()) <= 10) {
                        SpannableStringBuilder builder = new SpannableStringBuilder(vehicleinfo_scd_tv2.getText().toString());
                        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
                        builder.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        builder.setSpan(redSpan, 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        vehicleinfo_scd_tv2.setText(builder);
                    } else {
                        vehicleinfo_scd_tv2.setTextColor(getContext().getResources().getColor(R.color.view_line));
                    }
                    int i = 100 - vehicleinfo_scd.getText().length();
                    vehicleinfo_scd_tv2.setText(i + "/100");
                } else {
                    vehicleinfo_scd_tv2.setText("100/100");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.e("SupplementaryFragment  onActivityCreated ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.e("SupplementaryFragment  onActivityCreated ");
    }

    @Override
    public void onDestroy() {
        Logger.e("SupplementaryFragment onDestroy ");
        EventBus.getDefault().unregister(this);
        P.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({R.id.assessment_reult_back, R.id.assessment_reult_single
            , R.id.usage_of_loan, R.id.loaninfo_product_type
            , R.id.version, R.id.repayment_method
            , R.id.loan_cycle
//            , R.id.date_of_production
            , R.id.date_of_issue})
    public void onClick(View v) {

        if ((System.currentTimeMillis() - clickTime) > 500) {

            clickTime = System.currentTimeMillis();


            switch (v.getId()) {
                case R.id.assessment_reult_back:
                    Logger.e("SupplementaryFragment R.id.assessment_reult_back ");
                    EventBus.getDefault().post(new BusEvent(BusEvent.SINGLE_BACK));
                    break;
                case R.id.assessment_reult_single:
                    Logger.e("SupplementaryFragment R.id.assessment_reult_single ");
                    if (!check()) {
                        isNextStep = true;

                        submit();
                    }
//                EventBus.getDefault().post(new BusEvent(BusEvent.FRAGMENT_IMAGEINFO_NEW, this));
                    break;
                case R.id.usage_of_loan:
                    Logger.e("SupplementaryFragment R.id.usage_of_loan ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.usage_of_loan));
                    if (null != pvUsage_of_loan) {
                        pvUsage_of_loan.show();
                    }
                    break;
                case R.id.loaninfo_product_type:
                    Logger.e("SupplementaryFragment R.id.loaninfo_product_type ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.loaninfo_product_type));
                    if (null != pvProduct_type) {
                        if (!productItem.isEmpty()) {
                            pvProduct_type.show();
                        } else {
                            Tools.makeText("基础数据为空");
                        }
                    }
                    break;
                case R.id.version:
                    Logger.e("SupplementaryFragment R.id.version ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.version));
                    if (pvVersion != null) {
                        if (!versionItem.isEmpty()) {
                            pvVersion.show();
                        } else {
                            Tools.makeText("请先选择产品类型");
                        }
                    }

                    break;
                case R.id.repayment_method:
                    Logger.e("SupplementaryFragment R.id.repayment_method ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.repayment_method));
                    if (pvRepayment_method != null) {
                        if (!repaymentItem.isEmpty()) {
                            pvRepayment_method.show();
                        } else {
                            Tools.makeText("请先选择版本");
                        }
                    }
                    break;
                case R.id.loan_cycle:
                    Logger.e("SupplementaryFragment R.id.loan_cycle ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.loan_cycle));
                    if (pvLoan_cycle != null) {
                        if (!loanItem.isEmpty()) {
                            pvLoan_cycle.show();
                        } else {
                            Tools.makeText("请先选择还款方式");
                        }
                    }
                    break;
//                case R.id.date_of_production:
//                    Logger.e("SupplementaryFragment R.id.date_of_production ");
//                    Tools.inputMethodManager(getActivity().findViewById(R.id.date_of_production));
//                    if (pvCustomTime != null) {
//                        DATA_STATER = DATA_PRODUCTION;
//                        pvCustomTime.show();
//                    }
//                    break;
                case R.id.date_of_issue:
                    Logger.e("SupplementaryFragment R.id.date_of_issue ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.date_of_issue));
                    if (pvCustomTime != null) {
                        DATA_STATER = DATA_ISSUE;
                        pvCustomTime.show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void submit() {
        ((SingleNewActivity) getActivity()).showProgressDialog("正在努力上传中...");
        Logger.e("SupplementaryFragment submit ");
        Supplementary s = new Supplementary();
        s.setLoanInfoId(ADDataManager.getInstance().getLoanInfoId());
        s.setLoanPurpose(getCode(loaninfo_usage_of_loan.getText().toString().trim(), usageItem)); //借款用途
        s.setLoanMount(loaninfo_loan_amount.getText().toString().trim());//

        s.setVersions(getCode(version.getText().toString().trim(), versionItem));//
        s.setProductTypes(getCode(loaninfo_product_type.getText().toString().trim(), productItem));//产品类型
        s.setRepaymentMethods(getCode(loaninfo_repayment_method.getText().toString().trim(), repaymentItem));//还款方式
        s.setRepaymentPeriodhods(getCode(loaninfo_loan_cycle.getText().toString().trim(), loanItem));//借款周期
//        s.setAssess(vehicleinfo_new_unit_price.getText().toString().trim());//新车单价
        s.setCarFrameNo(vehicleinfo_frame_number.getText().toString().trim());//车架号
//        s.setCarInfoId(mCarInfoId);//车辆信息Id
        s.setLicencePlate(vehicleinfo_license_plate.getText().toString().trim());//车牌号
//        s.setProduceDate(vehicleinfo_date_of_production.getText().toString().trim());//出厂日期
        String vechicle = vehicleinfo_scd.getText().toString().trim();
        if (!TextUtils.isEmpty(vechicle)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(vechicle);
            vechicle = m.replaceAll("");
        }
        s.setSurfaceConditionDescription(vechicle);
        s.setModel(vehicleinfo_model.getText().toString().trim());
//        s.setCarFuel(vehicleinfo_fuel.getText().toString().trim());
        s.setCarColor(vehicleinfo_body_color.getText().toString().trim());
        s.setCarEngineNo(vehicleinfo_engine_number.getText().toString().trim());
        s.setCertificateDate(vehicleinfo_date_of_issue.getText().toString().trim());


        if (((SingleNewActivity) getActivity()).isEdit()) {
            BaseInfoBean bean = ADDataManager.getInstance().getDefultInfo().getBaseInfo();

            bean.setLoanInfoId(ADDataManager.getInstance().getLoanInfoId());
            bean.setLoanPurposeCode(getCode(loaninfo_usage_of_loan.getText().toString().trim(), usageItem)); //借款用途
            bean.setLoanPurposeValue(loaninfo_usage_of_loan.getText().toString().trim());
            bean.setLoanMount(loaninfo_loan_amount.getText().toString().trim());//

            bean.setVersions(getCode(version.getText().toString().trim(), versionItem));//
            bean.setProductTypesCode(getCode(loaninfo_product_type.getText().toString().trim(), productItem));//产品类型
            bean.setProductTypesValue(loaninfo_product_type.getText().toString().trim());
            bean.setRepaymentMethods(getCode(loaninfo_repayment_method.getText().toString().trim(), repaymentItem));//还款方式
            bean.setRepaymentMethodsValue(loaninfo_repayment_method.getText().toString().trim());
            bean.setRepaymentPeriodhods(getCode(loaninfo_loan_cycle.getText().toString().trim(), loanItem));//借款周期
//            bean.setAssess(vehicleinfo_new_unit_price.getText().toString().trim());//新车单价
            bean.setCarFrameNo(vehicleinfo_frame_number.getText().toString().trim());//车架号
//        s.setCarInfoId(mCarInfoId);//车辆信息Id
            bean.setLicencePlate(vehicleinfo_license_plate.getText().toString().trim());//车牌号
//            bean.setProduceDate(vehicleinfo_date_of_production.getText().toString().trim());//出厂日期
            String vechicle2 = vehicleinfo_scd.getText().toString().trim();
            if (!TextUtils.isEmpty(vechicle2)) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(vechicle);
                vechicle2 = m.replaceAll("");
            }
            bean.setSurfaceConditionDescription(vechicle2);
            bean.setCarModel(vehicleinfo_model.getText().toString().trim());
//            bean.setCarFuel(vehicleinfo_fuel.getText().toString().trim());
            bean.setCarColor(vehicleinfo_body_color.getText().toString().trim());
            bean.setCarEngineNo(vehicleinfo_engine_number.getText().toString().trim());
            bean.setCertificateDate(vehicleinfo_date_of_issue.getText().toString().trim());

        }

        P.submit(s);

    }

    private boolean check() {
        Logger.e("SupplementaryFragment check ");
        if (TextUtils.isEmpty(loaninfo_usage_of_loan.getText().toString().trim())) {
            Tools.makeText("借款用途不能为空");
            return true;
        }
        if (TextUtils.isEmpty(loaninfo_loan_amount.getText().toString().trim())) {
            Tools.makeText("借款金额不能为空");
            return true;
        }

        if (loaninfo_loan_amount.getText().toString().trim().substring(0, 1).equals("0")
                && loaninfo_loan_amount.getText().toString().trim().length() > 1
                && !loaninfo_loan_amount.getText().toString().trim().contains(".")) {
            Tools.makeText("请输入合法借款金额");
            return true;
        }

        if (Double.parseDouble(loaninfo_loan_amount.getText().toString().trim()) < 1
                || (loaninfo_loan_amount.getText().toString().trim().contains(".")
                && loaninfo_loan_amount.getText().toString().trim().substring(0, 1).equals("0"))) {
            Tools.makeText("借款金额须大于0元");
            return true;
        }


        if (TextUtils.isEmpty(loaninfo_product_type.getText().toString().trim())) {
            Tools.makeText("产品类型不能为空");
            return true;
        }
        if (TextUtils.isEmpty(version.getText().toString().trim())) {
            Tools.makeText("版本不能为空");
            return true;
        }
        if (TextUtils.isEmpty(loaninfo_repayment_method.getText().toString().trim())) {
            Tools.makeText("还款方式不能为空");
            return true;
        }
        if (TextUtils.isEmpty(loaninfo_loan_cycle.getText().toString().trim())) {
            Tools.makeText("贷款周期不能为空");
            return true;
        }

        if (TextUtils.isEmpty(vehicleinfo_license_plate.getText().toString().trim())) {
            Tools.makeText("车牌号码不能为空");
            return true;
        }


        if (TextUtils.isEmpty(vehicleinfo_frame_number.getText().toString().trim())) {
            Tools.makeText("车架号不能为空");
            return true;
        }

        if (!TextUtils.isEmpty(vehicleinfo_frame_number.getText().toString().trim()) && vehicleinfo_frame_number.getText().toString().trim().length() != 17) {
            Tools.makeText("请输入正确的17位车架号");
            return true;
        }

//        if (TextUtils.isEmpty(vehicleinfo_date_of_production.getText().toString().trim())) {
//            Tools.makeText("出厂日期不能为空");
//            return true;
//        }
//
//        if (!TextUtils.isEmpty(vehicleinfo_date_of_production.getText().toString().trim())) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                Date date = sdf.parse(vehicleinfo_date_of_production.getText().toString().trim());
//                long time = date.getTime();
//                String oldTime = sdf.format(System.currentTimeMillis());
//                long newTime = sdf.parse(oldTime).getTime();
//                if (time > newTime) {
//                    Tools.makeText("出厂日期不能大于当前日期");
//                    return true;
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }


//        if (TextUtils.isEmpty(vehicleinfo_fuel.getText().toString().trim())) {
//            Tools.makeText("燃料不能为空");
//            return true;
//        }

        if (TextUtils.isEmpty(vehicleinfo_body_color.getText().toString().trim())) {
            Tools.makeText("颜色不能为空");
            return true;
        }

        if (TextUtils.isEmpty(vehicleinfo_model.getText().toString().trim())) {
            Tools.makeText("型号不能为空");
            return true;
        }

        if (TextUtils.isEmpty(vehicleinfo_engine_number.getText().toString().trim())) {
            Tools.makeText("发动机号不能为空");
            return true;
        }

        if (TextUtils.isEmpty(vehicleinfo_date_of_issue.getText().toString().trim())) {
            Tools.makeText("发证日期不能为空");
            return true;
        }


        if (!TextUtils.isEmpty(vehicleinfo_date_of_issue.getText().toString().trim())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(vehicleinfo_date_of_issue.getText().toString().trim());
                long time = date.getTime();
                String oldTime = sdf.format(System.currentTimeMillis());
                long newTime = sdf.parse(oldTime).getTime();
                if (time > newTime) {
                    Tools.makeText("发证日期不能大于当前日期");
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


//        if (TextUtils.isEmpty(vehicleinfo_new_unit_price.getText().toString().trim())) {
//            Tools.makeText("新车购置价不能为空");
//            return true;
//        }
//
//        if (vehicleinfo_new_unit_price.getText().toString().trim().substring(0, 1).equals("0")
//                && vehicleinfo_new_unit_price.getText().toString().trim().length() > 1
//                && !vehicleinfo_new_unit_price.getText().toString().trim().contains(".")) {
//            Tools.makeText("请输入合法新车购置价");
//            return true;
//        }
//
//        if (Double.parseDouble(vehicleinfo_new_unit_price.getText().toString().trim()) < 1
//                || (vehicleinfo_new_unit_price.getText().toString().trim().contains(".")
//                && vehicleinfo_new_unit_price.getText().toString().trim().substring(0, 1).equals("0"))) {
//            Tools.makeText("新车购置价不等小于0元");
//            return true;
//        }

        if (TextUtils.isEmpty(vehicleinfo_scd.getText().toString().trim())) {
            Tools.makeText("车辆表面不能为空");
            return true;
        }

        return false;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {
        switch (event.event) {
            case BusEvent.SUPPLEMENTARYFRAGMENT_SAVE:
                isNextStep = false;
                submit();
                break;
            default:
                break;
        }
    }

    //借款用途
    private void initCPUsage() {
        pvUsage_of_loan = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (usageItem == null) {
                    return;
                }

                if (usageItem.isEmpty()) {
                    return;
                }
                //返回的分别是三个级别的选中位置
                String tx = usageItem.get(options1).getPickerViewText();
                loaninfo_usage_of_loan.setText(tx);
            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置滚轮文字大小
                .build();

    }

    //产品类型
    private void initCPproduct() {
        pvProduct_type = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (productItem == null) {
                    return;
                }

                if (productItem.isEmpty()) {
                    return;
                }
                //返回的分别是三个级别的选中位置
                Constant.setProductType(productItem.get(options1).getCode());
                String tx = productItem.get(options1).getPickerViewText();
                loaninfo_product_type.setText(tx);
                loaninfo_repayment_method.setText("");
                loaninfo_loan_cycle.setText("");
                version.setText("");
                setVersionItem(tx);

            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置滚轮文字大小
                .build();

    }

    //版本
    private void initVersion() {
        pvVersion = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (versionItem == null) {
                    return;
                }

                if (versionItem.isEmpty()) {
                    return;
                }
                //返回的分别是三个级别的选中位置
                String tx = versionItem.get(options1).getPickerViewText();
                version.setText(tx);
                setRepaymentItem(loaninfo_product_type.getText().toString(), tx);
            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置滚轮文字大小
                .build();

    }

    //还款方式
    private void initCPRepayment() {
        pvRepayment_method = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (repaymentItem == null) {
                    return;
                }

                if (repaymentItem.isEmpty()) {
                    return;
                }
                //返回的分别是三个级别的选中位置
                String tx = repaymentItem.get(options1).getPickerViewText();
                loaninfo_repayment_method.setText(tx);
                setLoanItem(loaninfo_product_type.getText().toString(), version.getText().toString(), tx);

            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置滚轮文字大小
                .build();

    }

    //借款周期
    private void initCPLoan() {
        pvLoan_cycle = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (loanItem == null) {
                    return;
                }

                if (loanItem.isEmpty()) {
                    return;
                }
                //返回的分别是三个级别的选中位置
                String tx = loanItem.get(options1).getPickerViewText();
                loaninfo_loan_cycle.setText(tx);
            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置滚轮文字大小
                .build();

    }

    private void setVersionItem(String str) {
        List<VersionListBean> listV = getrepaymentMethod(str);
        if (null != listV) {
            versionItem.clear();
            for (VersionListBean vl : listV) {
                versionItem.add(new CardBean(vl.getVersionCode(), vl.getVersionValues()));
            }
            if (!versionItem.isEmpty()) {
                pvVersion.setPicker(versionItem);
            }
        }
    }

    private void setRepaymentItem(String str1, String str2) {
        List<RepaymentMethodListBean> rl = getRepaymentMethodListBean(str1, str2);
        if (rl != null && !TextUtils.isEmpty(str2)) {
            repaymentItem.clear();
            for (RepaymentMethodListBean r : rl) {
                repaymentItem.add(new CardBean(r.getRepaymentMethodCode(), r.getRepaymentMethodValues()));
            }
            if (!repaymentItem.isEmpty()) {
                pvRepayment_method.setPicker(repaymentItem);
            }
        }
    }

    private void setLoanItem(String s1, String s2, String s3) {
        List<PeriodListBean> list = getPeriodListBean(s1, s2, s3);
        if (list != null && !TextUtils.isEmpty(s3)) {
            loanItem.clear();
            for (PeriodListBean p : list) {
                loanItem.add(new CardBean(p.getPeriodCode(), p.getPeriodValues()));
            }
            if (!loanItem.isEmpty()) {
                pvLoan_cycle.setPicker(loanItem);
            }
        }
    }

    private List<VersionListBean> getrepaymentMethod(String value) {
        if (null != mList && !TextUtils.isEmpty(value)) {
            for (ProductInfoListBenam p : mList) {
                if (p.getProductTypeValues().trim().equals(value.trim())) {
                    return p.getVersionList();
                }
            }
        }
        return null;
    }

    private List<RepaymentMethodListBean> getRepaymentMethodListBean(String value, String value2) {
        List<VersionListBean> listBeen = getrepaymentMethod(value);
        if (null != listBeen && !TextUtils.isEmpty(value2)) {
            for (VersionListBean v : listBeen) {
                if (v.getVersionValues().trim().equals(value2.trim())) {
                    return v.getRepaymentMethodList();
                }
            }
        }
        return null;
    }

    private List<PeriodListBean> getPeriodListBean(String value, String value2, String value3) {
        List<RepaymentMethodListBean> listBeen = getRepaymentMethodListBean(value, value2);
        if (null != listBeen && !TextUtils.isEmpty(value3)) {
            for (RepaymentMethodListBean r : listBeen) {
                if (r.getRepaymentMethodValues().trim().equals(value3.trim())) {
                    return r.getPeriodList();
                }
            }
        }
        return null;
    }


    @Override
    public void setBaseData(BaseData baseData) {
        if (null != baseData) {

            List<LoanPurposeBean> usage = baseData.getLoan_purpose();//借款用途
            for (LoanPurposeBean lp : usage) {
                usageItem.add(new CardBean(lp.getCode(), lp.getValue()));
            }

            if (!usageItem.isEmpty()) {
                pvUsage_of_loan.setPicker(usageItem);
            }

        }
        P.getProductInfoList();
    }

    @Override
    public void setBaseInfoBean(BaseInfoBean o) {
        if (null != o) {

            String pt = o.getProductTypesValue();//贷款产品
            String v = o.getVersions();//贷款版本
            String rm = o.getRepaymentMethodsValue();//还款方式
            String rp = o.getRepaymentPeriodhods();//贷款周期
            Constant.setProductType(o.getProductTypesCode());
            setVersionItem(pt);
            setRepaymentItem(pt, v);
            setLoanItem(pt, v, rm);

            loaninfo_usage_of_loan.setText(o.getLoanPurposeValue());
            String loanMount = o.getLoanMount();
            if (!TextUtils.isEmpty(loanMount) && loanMount.equals("0.00")) {
                loanMount = "0";
                loaninfo_loan_amount.setText(loanMount);
            } else {
                String str = "";
                if (loanMount.contains(".") && loanMount.length() >= 4) {
                    str = loanMount.substring(loanMount.indexOf(".") + 1, loanMount.length());
                    if (str.equals("00")) {
                        loaninfo_loan_amount.setText(loanMount.substring(0, loanMount.indexOf(".")));
                    } else {
                        loaninfo_loan_amount.setText(loanMount);
                    }
                } else {
                    loaninfo_loan_amount.setText(loanMount);
                }
            }
            version.setText(v);
            loaninfo_product_type.setText(pt);
            loaninfo_repayment_method.setText(rm);
            loaninfo_loan_cycle.setText(getValue(rp, loanItem));

//            String assess = o.getAssess();
//            if (!TextUtils.isEmpty(assess) && assess.equals("0.00")) {
//                assess = "0";
//                vehicleinfo_new_unit_price.setText(assess);
//            } else {
//
//                String str = "";
//                if (assess.contains(".") && assess.length() >= 4) {
//                    str = assess.substring(assess.indexOf(".") + 1, assess.length());
//                    if (str.equals("00")) {
//                        vehicleinfo_new_unit_price.setText(assess.substring(0, assess.indexOf(".")));
//                    } else {
//                        vehicleinfo_new_unit_price.setText(assess);
//                    }
//                } else {
//                    vehicleinfo_new_unit_price.setText(assess);
//                }
//            }

            vehicleinfo_frame_number.setText(o.getCarFrameNo());
            vehicleinfo_license_plate.setText(o.getLicencePlate());
//            vehicleinfo_date_of_production.setText(o.getProduceDate() + "");
            vehicleinfo_model.setText(o.getCarModel());
//            String money = o.getEvaluationPrice();

            vehicleinfo_scd.setText(o.getSurfaceConditionDescription());//车辆表面情况
            int i = 100 - o.getSurfaceConditionDescription().length();
            vehicleinfo_scd_tv2.setText(i + "/100");

            vehicleinfo_engine_number.setText(o.getCarEngineNo());
            vehicleinfo_body_color.setText(o.getCarColor());
//            vehicleinfo_fuel.setText(o.getCarFuel());
            vehicleinfo_date_of_issue.setText(o.getCertificateDate());

            mCarInfoId = o.getCarInfoId();

            BaseInfoBean bean = ((SingleNewActivity) getActivity()).getBaseInfoBean();
            if (!TextUtils.isEmpty(bean.getLicencePlate())) {
                vehicleinfo_license_plate.setText(bean.getLicencePlate());
            }

            if (!TextUtils.isEmpty(bean.getCarEngineNo())) {
                vehicleinfo_engine_number.setText(bean.getCarEngineNo());
            }

            if (!TextUtils.isEmpty(bean.getCertificateDate())) {
                vehicleinfo_date_of_issue.setText(bean.getCertificateDate());
            }

            if (!TextUtils.isEmpty(bean.getCarModel())) {
                vehicleinfo_model.setText(bean.getCarModel());
            }

            if (!TextUtils.isEmpty(bean.getCarFrameNo())) {
                vehicleinfo_frame_number.setText(bean.getCarFrameNo());
            }


        }
    }

    @Override
    public void setProductInfoList(List<ProductInfoListBenam> lists) {
        if (null != lists) {
            mList = lists;
            for (ProductInfoListBenam p : lists) {
                productItem.add(new CardBean(p.getProductTypeCode(), p.getProductTypeValues()));
            }
            if (!productItem.isEmpty()) {
                pvProduct_type.setPicker(productItem);
            }

        }

        if (((SingleNewActivity) getActivity()).isEdit()) {
            P.getBaseInfoBean(ADDataManager.getInstance().getLoanInfoId());
        } else {
            BaseInfoBean bean = ((SingleNewActivity) getActivity()).getBaseInfoBean();
            vehicleinfo_license_plate.setText(bean.getLicencePlate());
            vehicleinfo_engine_number.setText(bean.getCarEngineNo());
            vehicleinfo_date_of_issue.setText(bean.getCertificateDate());
            vehicleinfo_model.setText(bean.getCarModel());
            vehicleinfo_frame_number.setText(bean.getCarFrameNo());
        }
    }

    @Override
    public void onErr(int type, String err) {
        Tools.makeText(err);
    }

    @Override
    public void onDismiss() {
        ((SingleNewActivity) getActivity()).dismiss();
    }

    @Override
    public void nextStep() {
        if(isNextStep) {
            Logger.e("SupplementaryFragment nextStep ");
            EventBus.getDefault().post(new BusEvent(BusEvent.FRAGMENT_IMAGEINFO_NEW, this));
        }
    }

    private String getCode(String name, List<CardBean> list) {

        for (CardBean cardBean : list) {
            if (name.trim().equals(cardBean.getCardNo().trim())) {
                return cardBean.getId();
            }
        }
        return "";
    }

    private String getValue(String name, List<CardBean> list) {

        for (CardBean cardBean : list) {
            if (name.trim().equals(cardBean.getId().trim())) {
                return cardBean.getCardNo();
            }
        }
        return "";
    }

    private void initCustomTimePicker() {
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (DATA_STATER == DATA_PRODUCTION) {
//                    vehicleinfo_date_of_production.setText(getTime(date));
                } else if (DATA_STATER == DATA_ISSUE) {
                    vehicleinfo_date_of_issue.setText(getTime(date));
                }
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData(tvSubmit);
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
