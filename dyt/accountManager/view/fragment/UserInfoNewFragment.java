package com.hxyd.dyt.accountManager.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.BaseInfoBean;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.entity.UserInfoNew;
import com.hxyd.dyt.accountManager.presenter.ADDataManager;
import com.hxyd.dyt.accountManager.presenter.UserInfoNewP;
import com.hxyd.dyt.accountManager.presenter.in.UserInfoNewPI;
import com.hxyd.dyt.accountManager.view.SingleNewActivity;
import com.hxyd.dyt.accountManager.view.in.UserInfoNewVI;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.common.entity.CardBean;
import com.hxyd.dyt.common.entity.CityBean;
import com.hxyd.dyt.common.entity.CityBeanX;
import com.hxyd.dyt.common.entity.CustomerRelationBean;
import com.hxyd.dyt.common.entity.EducationBean;
import com.hxyd.dyt.common.entity.HousingTypeBean;
import com.hxyd.dyt.common.entity.MarriageBean;
import com.hxyd.dyt.common.entity.ProvinceBean;
import com.hxyd.dyt.common.entity.SexBean;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Tools;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by win7 on 2017/5/15.
 */

public class UserInfoNewFragment extends Fragment implements UserInfoNewVI {

    @BindView(R.id.userinfo_sex)
    TextView userinfo_sex;
    //    @BindView(R.id.userinfo_ID_card_valid)
//    TextView userinfo_ID_card_valid;
//    @BindView(R.id.userinfo_permanent_address)
//    TextView userinfo_permanent_address;
    @BindView(R.id.userinfo_marital_status)
    TextView userinfo_marital_status;
    @BindView(R.id.userinfo_education)
    TextView userinfo_education;
    @BindView(R.id.userinfo_house_type)
    TextView userinfo_house_type;
    @BindView(R.id.userinfo_work_unit_address)
    TextView userinfo_work_unit_address;
    @BindView(R.id.userinfo_customer_relationship)
    TextView userinfo_customer_relationship;
    @BindView(R.id.userinfo_customer_relationship2)
    TextView userinfo_customer_relationship2;
    @BindView(R.id.userinfo_customer_relationship3)
    TextView userinfo_customer_relationship3;

    //    @BindView(R.id.userinfo_home_household_address_details)
//    EditText userinfo_home_household_address_details;
    @BindView(R.id.userinfo_work_unit)
    EditText userinfo_work_unit;
    @BindView(R.id.userinfo_work_detailed_address)
    EditText userinfo_work_detailed_address;
    @BindView(R.id.userinfo_work_telephone)
    EditText userinfo_work_telephone;
    @BindView(R.id.userinfo_customer_contact)
    EditText userinfo_customer_contact;
    @BindView(R.id.userinfo_customer_party_phone)
    EditText userinfo_customer_party_phone;
    @BindView(R.id.userinfo_customer_contact2)
    EditText userinfo_customer_contact2;
    @BindView(R.id.userinfo_customer_party_phone2)
    EditText userinfo_customer_party_phone2;
    @BindView(R.id.userinfo_customer_contact3)
    EditText userinfo_customer_contact3;
    @BindView(R.id.userinfo_customer_party_phone3)
    EditText userinfo_customer_party_phone3;
    @BindView(R.id.coustomerinfo_spouseName)
    EditText coustomerinfo_spouseName;
    @BindView(R.id.coustomerinfo_spouseId)
    EditText coustomerinfo_spouseId;
    @BindView(R.id.coustomerinfo_spousePhone)
    EditText coustomerinfo_spousePhone;

    @BindView(R.id.customer_contact3)
    RelativeLayout customer_contact3;
    @BindView(R.id.customer_relationship3)
    RelativeLayout customer_relationship3;
    @BindView(R.id.customer_party_phone3)
    RelativeLayout customer_party_phone3;
    @BindView(R.id.v_bo_line)
    View v_bo_line;

    @BindView(R.id.userinfo_add_ll)
    public LinearLayout userinfo_add_ll;
    @BindView(R.id.spouseName_title)
    LinearLayout spouseName_title;

    @BindView(R.id.spouseName)
    public RelativeLayout spouseName;
    @BindView(R.id.spouseIdcard)
    public RelativeLayout spouseIdcard;
    @BindView(R.id.spousePhone)
    public RelativeLayout spousePhone;

    //    private TimePickerView pvCustomTime;
    private OptionsPickerView pvSex, pvMarital, pvEducation, pvuCustomer, pvCustomOptions, pvOptions;
    private ArrayList<CardBean> cardItem = new ArrayList<>();
    private ArrayList<CardBean> sexItem = new ArrayList<>();
    private ArrayList<CardBean> maritalItem = new ArrayList<>();
    private ArrayList<CardBean> educationItem = new ArrayList<>();
    private ArrayList<CardBean> customerItem = new ArrayList<>();
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<ProvinceBean>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<ProvinceBean>>> options3Items = new ArrayList<>();
    private static int CUSTOMER_RELATIONSHIP = 0;
    private static int CUSTOMER_RELATIONSHIP1 = 1;
    private static int CUSTOMER_RELATIONSHIP2 = 2;
    private static int CUSTOMER_RELATIONSHIP3 = 3;
    private int mCustomState = -1;
    private static int WORK_UNIT_ADDRESS = 1;
    private static int PERMANENT_ADDRESS = 2;
    private String mCPC = "";
    private String mCPP = "";
    private String mCPD = "";
    private String mCP = "";
    private String mCC = "";
    private String mCD = "";
    private String mPPC = "";
    private String mPPP = "";
    private String mPPD = "";
    private String mPP = "";
    private String mPC = "";
    private String mPD = "";
    private String education = "";
    private String marital = "";
    private String sex = "";
    private String house = "";
    private String relationship = "";
    private String relationship2 = "";
    private String relationship3 = "";

    private UserInfoNewPI P;
    private long clickTime = 0;
    private boolean isNextStep = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e("UserInfoNewFragment  onCreate ");
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userinfo_new, container, false);
        ButterKnife.bind(this, view);
        Logger.e("UserInfoNewFragment  onCreateView ");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.e("UserInfoNewFragment  onActivityCreated ");
        initTimePicker();
        initSex();
        initMarital();
        initEducation();
        initCustomOptionPicker();
        initCustomer();
        initOptionPicker();
        P = new UserInfoNewP(this);
        P.getBaseData();

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
        P.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({R.id.assessment_reult_back, R.id.assessment_reult_single
//            , R.id.ID_card_valid
            , R.id.sex
            , R.id.education
            , R.id.house_type
            , R.id.marital_status, R.id.customer_relationship
            , R.id.customer_relationship2, R.id.customer_relationship3
//            , R.id.permanent_address
            , R.id.userinfo_im_add
            , R.id.work_unit_address})
    public void onClick(View v) {

        if ((System.currentTimeMillis() - clickTime) > 500) {

            clickTime = System.currentTimeMillis();


            switch (v.getId()) {
                case R.id.assessment_reult_back:
                    Logger.e("UserInfoNewFragment  R.id.assessment_reult_back ");
                    EventBus.getDefault().post(new BusEvent(BusEvent.SINGLE_BACK));
                    break;
                case R.id.assessment_reult_single:
                    Logger.e("UserInfoNewFragment  R.id.assessment_reult_single ");
                    if (!check()) {
                        isNextStep = true;
                        submit();
                    }
                    break;
//                case R.id.ID_card_valid:
//                    Logger.e("UserInfoNewFragment  R.id.ID_card_valid ");
//                    Tools.inputMethodManager(getActivity().findViewById(R.id.ID_card_valid));
//                    if (pvCustomTime != null) {
//                        pvCustomTime.show();
//                    }
//                    break;
                case R.id.sex:
                    Logger.e("UserInfoNewFragment  R.id.sex ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.sex));
                    if (pvSex != null) {
                        pvSex.show();
                    }
                    break;
                case R.id.marital_status:
                    Logger.e("UserInfoNewFragment  R.id.marital_status ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.marital_status));
                    if (pvMarital != null) {
                        pvMarital.show();
                    }
                    break;
                case R.id.education:
                    Logger.e("UserInfoNewFragment  R.id.education ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.education));
                    if (pvEducation != null) {
                        pvEducation.show();
                    }
                    break;
                case R.id.house_type:
                    Logger.e("UserInfoNewFragment  R.id.house_type ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.house_type));
                    if (pvCustomOptions != null) {
                        pvCustomOptions.show();
                    }
                    break;
                case R.id.customer_relationship:
                    Logger.e("UserInfoNewFragment  R.id.customer_relationship ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.customer_relationship));

                    if (pvuCustomer != null) {
                        CUSTOMER_RELATIONSHIP = CUSTOMER_RELATIONSHIP1;
                        pvuCustomer.show();
                    }
                    break;
                case R.id.customer_relationship2:
                    Logger.e("UserInfoNewFragment  R.id.customer_relationship2 ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.customer_relationship2));

                    if (pvuCustomer != null) {
                        CUSTOMER_RELATIONSHIP = CUSTOMER_RELATIONSHIP2;
                        pvuCustomer.show();
                    }
                    break;
                case R.id.customer_relationship3:
                    Logger.e("UserInfoNewFragment  R.id.customer_relationship3 ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.customer_relationship3));

                    if (pvuCustomer != null) {
                        CUSTOMER_RELATIONSHIP = CUSTOMER_RELATIONSHIP3;
                        pvuCustomer.show();
                    }
                    break;
//                case R.id.permanent_address:
//                    Logger.e("UserInfoNewFragment  R.id.permanent_address ");
//                    Tools.inputMethodManager(getActivity().findViewById(R.id.permanent_address));
//                    if (pvOptions != null) {
//                        mCustomState = PERMANENT_ADDRESS;
//                        pvOptions.setSelectOptions(0, 0, 0);
//                        pvOptions.show();
//                    }
//                    break;
                case R.id.work_unit_address:
                    Logger.e("UserInfoNewFragment  R.id.work_unit_address ");
                    Tools.inputMethodManager(getActivity().findViewById(R.id.work_unit_address));
                    if (pvOptions != null) {
                        mCustomState = WORK_UNIT_ADDRESS;
                        pvOptions.setSelectOptions(0, 0, 0);
                        pvOptions.show();
                    }
                    break;
                case R.id.userinfo_im_add:
                    Logger.e("UserInfoNewFragment  R.id.userinfo_im_add ");
                    customer_contact3.setVisibility(View.VISIBLE);
                    customer_relationship3.setVisibility(View.VISIBLE);
                    customer_party_phone3.setVisibility(View.VISIBLE);
                    v_bo_line.setVisibility(View.VISIBLE);
                    userinfo_add_ll.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

    public String getCode(String name, List<CardBean> list) {

        for (CardBean cardBean : list) {
            if (name.equals(cardBean.getCardNo())) {
                return cardBean.getId();
            }
        }
        return "";
    }


    private void submit() {

        ((SingleNewActivity) getActivity()).showProgressDialog("正在努力上传中...");

        Logger.e("UserInfoNewFragment  submit ");
        UserInfoNew user = new UserInfoNew();

        user.setLoanInfoId(ADDataManager.getInstance().getLoanInfoId());
//        user.setIdCardValidityDates(userinfo_ID_card_valid.getText().toString().trim());
//        user.setHomePlaceProvince(mPPP);
//        user.setHomeplaceCity(mPPC);
//        user.setHomePlaceDistrict(mPPD);
//        user.setHomePlaceDetail(userinfo_home_household_address_details.getText().toString().trim());
        user.setSex(sex);
        user.setMarriage(marital);
        user.setEducation(education);
        user.setHousingType(house);
        user.setCompanyName(userinfo_work_unit.getText().toString().trim());
        user.setCompanyPlaceProvince(mCPP);
        user.setCompanyPlaceCity(mCPC);
        user.setCompanyPlaceDistrict(mCPD);
        user.setCompanyPlaceDetail(userinfo_work_detailed_address.getText().toString().trim());
        user.setCompanyPhone(userinfo_work_telephone.getText().toString().trim());
        user.setRename_01(userinfo_customer_contact.getText().toString().trim());
        user.setRephone_01(userinfo_customer_party_phone.getText().toString().trim());
        user.setRelationship_01(relationship);
        user.setRename_02(userinfo_customer_contact2.getText().toString().trim());
        user.setRephone_02(userinfo_customer_party_phone2.getText().toString().trim());
        user.setRelationship_02(relationship2);
        user.setRename_03(userinfo_customer_contact3.getText().toString().trim());
        user.setRephone_03(userinfo_customer_party_phone3.getText().toString().trim());
        user.setRelationship_03(relationship3);

        user.setSpouseName(coustomerinfo_spouseName.getText().toString().trim());
        user.setSpouseIdcard(coustomerinfo_spouseId.getText().toString().trim());
        user.setSpousePhone(coustomerinfo_spousePhone.getText().toString().trim());


        if (((SingleNewActivity) getActivity()).isEdit()) {

            BaseInfoBean bean = ADDataManager.getInstance().getDefultInfo().getBaseInfo();
//            bean.setIdCardValidityDates(userinfo_ID_card_valid.getText().toString().trim());
//            bean.setHomePlaceProvinceCode(mPPP);
//            bean.setHomePlaceCityCode(mPPC);
//            bean.setHomePlaceDistrictCode(mPPD);
//            bean.setHomePlaceProvinceValue(mPP);
//            bean.setHomePlaceCityValue(mPC);
//            bean.setHomePlaceDistrictValue(mPD);

//            bean.setHomePlaceDetail(userinfo_home_household_address_details.getText().toString().trim());
            bean.setSex(sex);
            bean.setSexValue(userinfo_sex.getText().toString().trim());
            bean.setMarriage(marital);
            bean.setMarriageValue(userinfo_marital_status.getText().toString().trim());
            bean.setEducation(education);
            bean.setEducationValue(userinfo_education.getText().toString().trim());
            bean.setHousingTypeCode(house);
            bean.setHousingTypeValue(userinfo_house_type.getText().toString().trim());
            bean.setCompanyName(userinfo_work_unit.getText().toString().trim());
            bean.setCompanyPlaceProvinceCode(mCPP);
            bean.setCompanyPlaceCityCode(mCPC);
            bean.setCompanyPlaceDistrictCode(mCPD);

            bean.setCompanyPlaceProvinceValue(mCP);
            bean.setCompanyPlaceCityValue(mCC);
            bean.setCompanyPlaceDistrictValue(mCD);

            bean.setCompanyPlaceDetail(userinfo_work_detailed_address.getText().toString().trim());
            bean.setCompanyPhone(userinfo_work_telephone.getText().toString().trim());
            bean.setRename_01(userinfo_customer_contact.getText().toString().trim());
            bean.setRephone_01(userinfo_customer_party_phone.getText().toString().trim());
            bean.setRelationship_01(relationship);
            bean.setRename_02(userinfo_customer_contact2.getText().toString().trim());
            bean.setRephone_02(userinfo_customer_party_phone2.getText().toString().trim());
            bean.setRelationship_02(relationship2);
            bean.setRename_03(userinfo_customer_contact3.getText().toString().trim());
            bean.setRephone_03(userinfo_customer_party_phone3.getText().toString().trim());
            bean.setRelationship_03(relationship3);
        }

        P.submit(user);

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {
        switch (event.event) {

            case BusEvent.USERINFONEWFRAGMENT_SAVE:
                isNextStep = false;
                submit();
                break;
            default:
                break;
        }
    }

    public void showAddContactIm() {
        if (customer_contact3.getVisibility() != View.GONE) {
            customer_contact3.setVisibility(View.GONE);
            customer_relationship3.setVisibility(View.GONE);
            customer_party_phone3.setVisibility(View.GONE);
            v_bo_line.setVisibility(View.GONE);
            userinfo_add_ll.setVisibility(View.VISIBLE);
        }
    }

    private boolean check() {
        Logger.e("UserInfoNewFragment  check ");
        if (TextUtils.isEmpty(userinfo_sex.getText().toString().trim())) {
            Tools.makeText("性别不能为空");
            return true;
        }
//        if (TextUtils.isEmpty(userinfo_ID_card_valid.getText().toString().trim())) {
//            Tools.makeText("身份证有效期不能为空");
//            return true;
//        }
//
//        if (!TextUtils.isEmpty(userinfo_ID_card_valid.getText().toString().trim())) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                Date date = sdf.parse(userinfo_ID_card_valid.getText().toString().trim());
//                long time = date.getTime();
//                String oldTime = sdf.format(System.currentTimeMillis());
//                long newTime = sdf.parse(oldTime).getTime();
//                if (time < newTime) {
//                    Tools.makeText("身份证有效期不能小于当前日期");
//                    return true;
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }

//        if (TextUtils.isEmpty(userinfo_permanent_address.getText().toString().trim())) {
//            Tools.makeText("户籍地址不能为空");
//            return true;
//        }
//
//        if (TextUtils.isEmpty(userinfo_home_household_address_details.getText().toString().trim())) {
//            Tools.makeText("户籍详细地址不能为空");
//            return true;
//        }
        if (TextUtils.isEmpty(userinfo_marital_status.getText().toString().trim())) {
            Tools.makeText("婚姻状况不能为空");
            return true;
        }
        if (TextUtils.isEmpty(userinfo_education.getText().toString().trim())) {
            Tools.makeText("学历不能为空");
            return true;
        }
        if (TextUtils.isEmpty(userinfo_house_type.getText().toString().trim())) {
            Tools.makeText("房产类型不能为空");
            return true;
        }
        if (TextUtils.isEmpty(userinfo_work_unit.getText().toString().trim())) {
            Tools.makeText("单位不能为空");
            return true;
        }

        if (TextUtils.isEmpty(userinfo_work_unit_address.getText().toString().trim())) {
            Tools.makeText("单位地址不能为空");
            return true;
        }

        if (!TextUtils.isEmpty(userinfo_work_telephone.getText().toString().trim())
                && userinfo_work_telephone.getText().toString().trim().length() <= 6) {
            Tools.makeText("请输入大于6位数的单位电话");
            return true;
        }


        if ("1".equals(marital)) {

            if (TextUtils.isEmpty(coustomerinfo_spouseName.getText().toString().trim())) {
                Tools.makeText("配偶姓名不能为空");
                return true;
            }


            if (TextUtils.isEmpty(coustomerinfo_spousePhone.getText().toString().trim())) {
                Tools.makeText("配偶电话不能为空");
                return true;
            }

            if (!TextUtils.isEmpty(coustomerinfo_spousePhone.getText().toString().trim())
                    && coustomerinfo_spousePhone.getText().toString().trim().length() != 11) {
                Tools.makeText("配偶-请输入11位电话号码");
                return true;
            }
        }

        if (TextUtils.isEmpty(userinfo_customer_contact.getText().toString().trim())) {
            Tools.makeText("客户联系人不能为空");
            return true;
        }
        if (TextUtils.isEmpty(userinfo_customer_relationship.getText().toString().trim())) {
            Tools.makeText("与客户关系不能为空");
            return true;
        }
        if (TextUtils.isEmpty(userinfo_customer_party_phone.getText().toString().trim())) {
            Tools.makeText("客户联系人电话不能为空");
            return true;
        }
        if (!TextUtils.isEmpty(userinfo_customer_party_phone.getText().toString().trim())
                && userinfo_customer_party_phone.getText().toString().length() < 11) {
            Tools.makeText("联系人-请输入11位电话号码");
            return true;
        }

        if (TextUtils.isEmpty(userinfo_customer_contact2.getText().toString().trim())) {
            Tools.makeText("客户联系人2不能为空");
            return true;
        }
        if (TextUtils.isEmpty(userinfo_customer_relationship2.getText().toString().trim())) {
            Tools.makeText("与客户关系2不能为空");
            return true;
        }
        if (TextUtils.isEmpty(userinfo_customer_party_phone2.getText().toString().trim())) {
            Tools.makeText("客户联系人2电话不能为空");
            return true;
        }
        if (!TextUtils.isEmpty(userinfo_customer_party_phone2.getText().toString().trim())
                && userinfo_customer_party_phone2.getText().toString().length() < 11) {
            Tools.makeText("联系人2-请输入11位电话号码");
            return true;
        }
        if (!TextUtils.isEmpty(userinfo_customer_contact3.getText().toString().trim())
                && TextUtils.isEmpty(userinfo_customer_relationship3.getText().toString().trim())) {
            Tools.makeText("与客户关系3不能为空");
            return true;
        }

        if (!TextUtils.isEmpty(userinfo_customer_contact3.getText().toString().trim())
                && TextUtils.isEmpty(userinfo_customer_party_phone3.getText().toString().trim())) {
            Tools.makeText("客户联系人3电话不能为空");
            return true;
        }

        if (!TextUtils.isEmpty(userinfo_customer_contact3.getText().toString().trim())
                && userinfo_customer_party_phone3.getText().toString().length() < 11) {
            Tools.makeText("联系人3-请输入11位电话号码");
            return true;
        }


        if (!TextUtils.isEmpty(userinfo_customer_relationship3.getText().toString().trim())
                && TextUtils.isEmpty(userinfo_customer_contact3.getText().toString().trim())) {
            Tools.makeText("客户联系人3不能为空");
            return true;
        }

        if (!TextUtils.isEmpty(userinfo_customer_relationship3.getText().toString().trim())
                && TextUtils.isEmpty(userinfo_customer_party_phone3.getText().toString().trim())) {
            Tools.makeText("客户联系人3电话不能为空");
            return true;
        }

        if (!TextUtils.isEmpty(userinfo_customer_relationship3.getText().toString().trim())
                && userinfo_customer_party_phone3.getText().toString().length() < 11) {
            Tools.makeText("联系人3-请输入11位电话号码");
            return true;
        }

        if (!TextUtils.isEmpty(userinfo_customer_party_phone3.getText().toString().trim())
                && TextUtils.isEmpty(userinfo_customer_contact3.getText().toString().trim())) {
            Tools.makeText("客户联系人3不能为空");
            return true;
        }

        if (!TextUtils.isEmpty(userinfo_customer_party_phone3.getText().toString().trim())
                && TextUtils.isEmpty(userinfo_customer_relationship3.getText().toString().trim())) {
            Tools.makeText("与客户关系3不能为空");
            return true;
        }

        if (!TextUtils.isEmpty(userinfo_customer_party_phone3.getText().toString().trim())
                && userinfo_customer_party_phone3.getText().toString().length() < 11) {
            Tools.makeText("联系人3-请输入11位电话号码");
            return true;
        }

        return false;
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 时间选择器
     */
    private void initTimePicker() {
//        pvCustomTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                userinfo_ID_card_valid.setText(getTime(date));
//            }
//        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)
//                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
//
//                    @Override
//                    public void customLayout(View v) {
//                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
//                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
//                        tvSubmit.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                pvCustomTime.returnData(tvSubmit);
//                            }
//                        });
//                        ivCancel.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                pvCustomTime.dismiss();
//                            }
//                        });
//                    }
//                })
//                .build();
    }

    /**
     * 性别选择器
     */
    private void initSex() {
        pvSex = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (sexItem.isEmpty()) {
                    return;
                }
                String tx = sexItem.get(options1).getPickerViewText();
                sex = sexItem.get(options1).getCode();
                userinfo_sex.setText(tx);
            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

    }

    /**
     * 婚姻选择器
     */
    private void initMarital() {
        pvMarital = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (maritalItem.isEmpty()) {
                    return;
                }
                String tx = maritalItem.get(options1).getPickerViewText();
                marital = maritalItem.get(options1).getCode();
                userinfo_marital_status.setText(tx);
                if ("1".equals(marital)) {
                    setSpouseVisibility(true);
                } else {
                    setSpouseVisibility(false);
                    coustomerinfo_spouseName.setText("");
                    coustomerinfo_spouseId.setText("");
                    coustomerinfo_spousePhone.setText("");
                }
            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

    }

    /**
     * 学历选择器
     */
    private void initEducation() {
        pvEducation = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (educationItem.isEmpty()) {
                    return;
                }
                String tx = educationItem.get(options1).getPickerViewText();
                education = educationItem.get(options1).getCode();
                userinfo_education.setText(tx);
            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

    }

    /**
     * 房产选择器
     */
    private void initCustomOptionPicker() {
        pvCustomOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = cardItem.get(options1).getPickerViewText();
                house = cardItem.get(options1).getCode();
                userinfo_house_type.setText(tx);
            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

    }

    /**
     * 客户关系选择器
     */
    private void initCustomer() {//条件选择器初始化，自定义布局
        pvuCustomer = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (customerItem.isEmpty()) {
                    return;
                }
                //返回的分别是三个级别的选中位置
                String tx = customerItem.get(options1).getPickerViewText();
                if (CUSTOMER_RELATIONSHIP == CUSTOMER_RELATIONSHIP1) {
                    userinfo_customer_relationship.setText(tx);
                    relationship = customerItem.get(options1).getCode();
                } else if (CUSTOMER_RELATIONSHIP == CUSTOMER_RELATIONSHIP2) {
                    userinfo_customer_relationship2.setText(tx);
                    relationship2 = customerItem.get(options1).getCode();
                } else if (CUSTOMER_RELATIONSHIP == CUSTOMER_RELATIONSHIP3) {
                    userinfo_customer_relationship3.setText(tx);
                    relationship3 = customerItem.get(options1).getCode();
                }

            }
        })
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置滚轮文字大小
                .build();

    }

    /**
     * 城市选择器
     */
    private void initOptionPicker() {//条件选择器初始化

        pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (options1Items.isEmpty() && options1Items == null) {
                    return;
                }
                String p = "";
                String c = "";
                String d = "";
                if (options1Items.size() > 0 && options1Items.get(options1) != null) {

                    p = options1Items.get(options1).getPickerViewText();

                    if (mCustomState == WORK_UNIT_ADDRESS) {
                        mCPP = options1Items.get(options1).getCode();
                        mCP = p;
                    } else if (mCustomState == PERMANENT_ADDRESS) {
                        mPPP = options1Items.get(options1).getCode();
                        mPP = p;
                    }

                }

                if (options2Items.size() > 0
                        && options2Items.get(options1) != null
                        && options2Items.get(options1).size() > 0
                        && options2Items.get(options1).get(options2) != null) {
                    c = options2Items.get(options1).get(options2).getPickerViewText();

                    if (mCustomState == WORK_UNIT_ADDRESS) {
                        mCPC = options2Items.get(options1).get(options2).getCode();
                        mCC = c;
                    } else if (mCustomState == PERMANENT_ADDRESS) {
                        mPPC = options2Items.get(options1).get(options2).getCode();
                        mPC = c;
                    }
                }

                if (options3Items.size() > 0
                        && options3Items.get(options1) != null && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2) != null && options3Items.get(options1).get(options2).size() > 0
                        && options3Items.get(options1).get(options2).get(options3) != null) {
                    d = options3Items.get(options1).get(options2).get(options3).getPickerViewText();

                    if (mCustomState == WORK_UNIT_ADDRESS) {
                        mCPD = options3Items.get(options1).get(options2).get(options3).getCode();
                        mCD = d;
                    } else if (mCustomState == PERMANENT_ADDRESS) {
                        mPPD = options3Items.get(options1).get(options2).get(options3).getCode();
                        mPD = d;
                    }
                } else {

                    if (mCustomState == WORK_UNIT_ADDRESS) {
                        mCPD = "";
                        mCD = "";
                    } else if (mCustomState == PERMANENT_ADDRESS) {
                        mPPD = "";
                        mPD = "";
                    }
                }

                //返回的分别是三个级别的选中位置
                String tx = p + c + d;
                if (p.equals(c)) {
                    tx = c + d;
                }


                if (mCustomState == WORK_UNIT_ADDRESS) {
                    userinfo_work_unit_address.setText(tx);
                } else if (mCustomState == PERMANENT_ADDRESS) {
//                    userinfo_permanent_address.setText(tx);
                }

            }
        })
                .setTitleText("城市选择")
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置滚轮文字大小
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .build();
    }

    @Override
    public void setBaseData(BaseData baseData) {
        if (null != baseData) {
            List<SexBean> sexBeen = baseData.getSex();
            if (sexBeen != null) {
                for (SexBean s : sexBeen) {
                    sexItem.add(new CardBean(s.getCode(), s.getValue()));
                }
            }
            List<MarriageBean> marriage = baseData.getMarriage();
            if (marriage != null) {
                for (MarriageBean m : marriage) {
                    maritalItem.add(new CardBean(m.getCode(), m.getValue()));
                }
            }
            List<EducationBean> education = baseData.getEducation();
            if (education != null) {
                for (EducationBean e : education) {
                    educationItem.add(new CardBean(e.getCode(), e.getValue()));
                }
            }
            List<HousingTypeBean> housing_Type = baseData.getHousing_Type();
            if (housing_Type != null) {
                for (HousingTypeBean h : housing_Type) {
                    cardItem.add(new CardBean(h.getCode(), h.getValue()));
                }
            }
            List<CustomerRelationBean> customerRelationBeen = baseData.getCustomer_relation();
            if (customerRelationBeen != null) {
                for (CustomerRelationBean cr : customerRelationBeen) {
                    customerItem.add(new CardBean(cr.getCode(), cr.getValue()));
                }
            }
            pvSex.setPicker(sexItem);
            pvMarital.setPicker(maritalItem);
            pvEducation.setPicker(educationItem);
            pvuCustomer.setPicker(customerItem);
            pvCustomOptions.setPicker(cardItem);//添加数据
        }
        P.getArealist();
    }

    @Override
    public void setArealist(List<AreaList> lists) {
        if (null != lists && !lists.isEmpty()) {
            for (AreaList areaList : lists) {
                options1Items.add(new ProvinceBean(areaList.getId(), areaList.getName(), "", ""));//省
                ArrayList<ProvinceBean> list = new ArrayList<ProvinceBean>();
                ArrayList<ArrayList<ProvinceBean>> ipList = new ArrayList<ArrayList<ProvinceBean>>();
                for (CityBeanX cityBeanX : areaList.getCity()) { //市
                    list.add(new ProvinceBean(cityBeanX.getId(), cityBeanX.getName(), "", ""));
                    ArrayList<ProvinceBean> list_03 = new ArrayList<>();
                    for (CityBean cityBean : cityBeanX.getCity()) {//区
                        list_03.add(new ProvinceBean(cityBean.getId(), cityBean.getName(), "", ""));
                    }
                    ipList.add(list_03);
                }
                options3Items.add(ipList);
                options2Items.add(list);
            }
            pvOptions.setPicker(options1Items, options2Items, options3Items);
        }

        if (((SingleNewActivity) getActivity()).isEdit()) {
            P.getOrderDefault(ADDataManager.getInstance().getLoanInfoId());
        }
        String s = ((SingleNewActivity) getActivity()).getBaseInfoBean().getSexValue();
        if (!TextUtils.isEmpty(s)) {
            userinfo_sex.setText(s);
            sex = getCode(s, sexItem);
        }
    }

    @Override
    public void setOrderDefault(OrderDefultInfo defultInfo) {
        if (defultInfo != null) {
            BaseInfoBean o = defultInfo.getBaseInfo();
            if (null != o) {

                userinfo_work_unit.setText(o.getCompanyName());
                userinfo_work_telephone.setText(o.getCompanyPhone());
                userinfo_work_detailed_address.setText(o.getCompanyPlaceDetail());

                userinfo_sex.setText(o.getSexValue());
                sex = o.getSex();
                userinfo_marital_status.setText(o.getMarriageValue());
                marital = o.getMarriage();
                if ("1".equals(marital)) {
                    setSpouseVisibility(true);
                }
                userinfo_education.setText(o.getEducationValue());
                education = o.getEducation();


                userinfo_customer_contact.setText(o.getRename_01());
                userinfo_customer_party_phone.setText(o.getRephone_01());
                userinfo_customer_relationship.setText(o.getRelationship_01Value());
                relationship = o.getRelationship_01();

                userinfo_customer_contact2.setText(o.getRename_02());
                userinfo_customer_party_phone2.setText(o.getRephone_02());
                userinfo_customer_relationship2.setText(o.getRelationship_02Value());
                relationship2 = o.getRelationship_02();

                if (!TextUtils.isEmpty(o.getRename_03()) || !TextUtils.isEmpty(o.getRephone_03()) || !TextUtils.isEmpty(o.getRelationship_03Value())) {
                    customer_contact3.setVisibility(View.VISIBLE);
                    customer_relationship3.setVisibility(View.VISIBLE);
                    customer_party_phone3.setVisibility(View.VISIBLE);
                    v_bo_line.setVisibility(View.VISIBLE);
                    userinfo_add_ll.setVisibility(View.GONE);
                }
                userinfo_customer_contact3.setText(o.getRename_03());
                userinfo_customer_party_phone3.setText(o.getRephone_03());
                userinfo_customer_relationship3.setText(o.getRelationship_03Value());
                relationship3 = o.getRelationship_03();


                String valueP = "";
                mCP = TextUtils.isEmpty(o.getCompanyPlaceProvinceValue()) ? "" : o.getCompanyPlaceProvinceValue();
                mCC = TextUtils.isEmpty(o.getCompanyPlaceCityValue()) ? "" : o.getCompanyPlaceCityValue();
                mCD = TextUtils.isEmpty(o.getCompanyPlaceDistrictValue()) ? "" : o.getCompanyPlaceDistrictValue();
                valueP = mCP + mCC + mCD;
                if (mCP.equals(mCC)) {
                    valueP = mCC + mCD;
                }
                mCPP = TextUtils.isEmpty(o.getCompanyPlaceProvinceCode()) ? "" : o.getCompanyPlaceProvinceCode();
                mCPC = TextUtils.isEmpty(o.getCompanyPlaceCityCode()) ? "" : o.getCompanyPlaceCityCode();
                mCPD = TextUtils.isEmpty(o.getCompanyPlaceDistrictCode()) ? "" : o.getCompanyPlaceDistrictCode();

                userinfo_work_unit_address.setText(valueP);
                userinfo_house_type.setText(o.getHousingTypeValue());
                house = o.getHousingTypeCode();
//                userinfo_ID_card_valid.setText(o.getIdCardValidityDates());


                String valuePP = "";
                String pp = TextUtils.isEmpty(o.getHomePlaceProvinceValue()) ? "" : o.getHomePlaceProvinceValue();
                String pc = TextUtils.isEmpty(o.getHomePlaceCityValue()) ? "" : o.getHomePlaceCityValue();
                String pd = TextUtils.isEmpty(o.getHomePlaceDistrictValue()) ? "" : o.getHomePlaceDistrictValue();
                valuePP = pp + pc + pd;
                if (pp.equals(pc)) {
                    valuePP = pc + pd;
                }
                mPPP = TextUtils.isEmpty(o.getHomePlaceProvinceCode()) ? "" : o.getHomePlaceProvinceCode();
                mPPC = TextUtils.isEmpty(o.getHomePlaceCityCode()) ? "" : o.getHomePlaceCityCode();
                mPPD = TextUtils.isEmpty(o.getHomePlaceDistrictCode()) ? "" : o.getHomePlaceDistrictCode();
//                userinfo_permanent_address.setText(valuePP);

//                userinfo_home_household_address_details.setText(o.getHomePlaceDetail());

                coustomerinfo_spouseName.setText(TextUtils.isEmpty(o.getSpouseName()) ? "" : o.getSpouseName());
                coustomerinfo_spouseId.setText(TextUtils.isEmpty(o.getSpouseIdcard()) ? "" : o.getSpouseIdcard());
                coustomerinfo_spousePhone.setText(TextUtils.isEmpty(o.getSpousePhone()) ? "" : o.getSpousePhone());

            }
        }

        String s = ((SingleNewActivity) getActivity()).getBaseInfoBean().getSexValue();
        if (!TextUtils.isEmpty(s)) {
            userinfo_sex.setText(s);
            sex = getCode(s, sexItem);
        }
    }

    @Override
    public void onErr(int type, String err) {
        Tools.makeText(err);
    }

    @Override
    public void nextStep() {
        Logger.e("UserInfoNewFragment  nextStep ");
        if (isNextStep) {
            EventBus.getDefault().post(new BusEvent(BusEvent.FRAGMENT_SUPPLEMENTARY, this));
        }
    }

    @Override
    public void onDismiss() {
        ((SingleNewActivity) getActivity()).dismiss();
    }

    private void setSpouseVisibility(boolean isShow) {
        if (isShow) {

            if (spouseName_title.getVisibility() != View.VISIBLE) {
                spouseName_title.setVisibility(View.VISIBLE);
            }

            if (spouseName.getVisibility() != View.VISIBLE) {
                spouseName.setVisibility(View.VISIBLE);
            }
            if (spouseIdcard.getVisibility() != View.VISIBLE) {
                spouseIdcard.setVisibility(View.VISIBLE);
            }
            if (spousePhone.getVisibility() != View.VISIBLE) {
                spousePhone.setVisibility(View.VISIBLE);
            }
        } else {

            if (spouseName_title.getVisibility() != View.GONE) {
                spouseName_title.setVisibility(View.GONE);
            }

            if (spouseName.getVisibility() != View.GONE) {
                spouseName.setVisibility(View.GONE);
            }
            if (spouseIdcard.getVisibility() != View.GONE) {
                spouseIdcard.setVisibility(View.GONE);
            }
            if (spousePhone.getVisibility() != View.GONE) {
                spousePhone.setVisibility(View.GONE);
            }
        }
    }
}
