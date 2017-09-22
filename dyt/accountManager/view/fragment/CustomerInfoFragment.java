package com.hxyd.dyt.accountManager.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.BaseInfoBean;
import com.hxyd.dyt.accountManager.modle.entity.Contact;
import com.hxyd.dyt.accountManager.modle.entity.CustomerInfo;
import com.hxyd.dyt.accountManager.modle.entity.LoanInfoID;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.entity.Province;
import com.hxyd.dyt.accountManager.modle.entity.VIN;
import com.hxyd.dyt.accountManager.presenter.ADDataManager;
import com.hxyd.dyt.accountManager.presenter.CustomerInfoP;
import com.hxyd.dyt.accountManager.presenter.JingZhenGuP;
import com.hxyd.dyt.accountManager.presenter.in.JingZhenGuPI;
import com.hxyd.dyt.accountManager.presenter.in.SinglePI;
import com.hxyd.dyt.accountManager.view.JZGActivity;
import com.hxyd.dyt.accountManager.view.SingleNewActivity;
import com.hxyd.dyt.accountManager.view.in.CustomerInfoVI;
import com.hxyd.dyt.accountManager.view.in.JingZhenGuVI;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.CardBean;
import com.hxyd.dyt.common.entity.CityBean;
import com.hxyd.dyt.common.entity.CityBeanX;
import com.hxyd.dyt.common.entity.ProvinceBean;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.purplestar.utils.GPS3DUtils;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by win7 on 2017/5/15.
 */

public class CustomerInfoFragment extends Fragment implements CustomerInfoVI {


    @BindView(R.id.coustomerinfo_home_address)
    TextView coustomerinfo_home_address;
    @BindView(R.id.coustomerinfo_boarding_time)
    TextView coustomerinfo_boarding_time;
    @BindView(R.id.vehicleinfo_city_up)
    TextView vehicleinfo_city_up;
    @BindView(R.id.coustomerinfo_brand)
    TextView coustomerinfo_brand;

    @BindView(R.id.coustomerinfo_name)
    EditText coustomerinfo_name;
    @BindView(R.id.coustomerinfo_ID)
    EditText coustomerinfo_ID;
    @BindView(R.id.coustomerinfo_phome)
    EditText coustomerinfo_phome;
    @BindView(R.id.coustomerinfo_home_detailed_address)
    EditText coustomerinfo_home_detailed_address;
//    @BindView(R.id.coustomerinfo_spouseName)
//    EditText coustomerinfo_spouseName;
//    @BindView(R.id.coustomerinfo_spouseId)
//    EditText coustomerinfo_spouseId;
//    @BindView(R.id.coustomerinfo_spousePhone)
//    EditText coustomerinfo_spousePhone;
    @BindView(R.id.coustomerinfo_frame_number)
    EditText coustomerinfo_frame_number;
    @BindView(R.id.vehicleinfo_running_kilometers)
    EditText vehicleinfo_running_kilometers;


    @BindView(R.id.coustomerinfo_id_scanning)
    ImageView coustomerinfo_id_scanning;
    @BindView(R.id.coustomerinfo_jd_scanning)
    ImageView coustomerinfo_jd_scanning;

    @BindView(R.id.brand)
    RelativeLayout rlBrand;

    @BindView(R.id.tv_brand)
    TextView tv_brand;

    //    private OptionsPickerView pvProvince;
    private TimePickerView pvCustomTime;
    private OptionsPickerView pvOptions;
    private ArrayList<CardBean> provinceItem = new ArrayList<>();
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<ProvinceBean>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<ProvinceBean>>> options3Items = new ArrayList<>();
    private String mLPC = "";
    private String mLPP = "";
    private String mLPD = "";

    private String mTrimId = "";
    private String mMake = "";
    private String mStyle = "";
    private String mModel = "";
    private String mCityId = "";

    private SinglePI P;
    //    private JingZhenGuPI JP;
    private String loanInfoId = "";
    private long clickTime = 0;
    private int brandWidth;

    private double latitude = 0.0;
    private double longitude = 0.0;
    private String address = "";

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
        View view = inflater.inflate(R.layout.customerinfo, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        P = new CustomerInfoP(this);
        P.getBaseData();
        initOptionPicker();
        initCustomTimePicker();

        GPS3DUtils.getInstance().startLocation(getContext(), new GPS3DUtils.LocationListener() {

            @Override
            public void onCallback(double lat, double lon, String add) {
                latitude = lat;
                longitude = lon;
                address = add;
            }

            @Override
            public void onErr() {
                Tools.makeText("定位失败，请重新定位！");
            }
        });

//        initProvince();
//        coustomerinfo_frame_number.setText("LGBG22E05DY571707");
        coustomerinfo_frame_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                coustomerinfo_frame_number.removeTextChangedListener(this);//解除文字改变事件
                coustomerinfo_frame_number.setText(s.toString().toUpperCase());//转换
                coustomerinfo_frame_number.setSelection(s.toString().length());//重新设置光标位置
                coustomerinfo_frame_number.addTextChangedListener(this);//重新绑
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


        tv_brand.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        brandWidth = Tools.getwidthPixels(getContext()) - tv_brand.getMeasuredWidth() - (int) (Tools.getDisplayDensity(getContext()) * 70);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(brandWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.alignWithParent = true;
        layoutParams.setMargins(0, 0, (int) (Tools.getDisplayDensity(getContext()) * 25), 0);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        coustomerinfo_brand.setLayoutParams(layoutParams);

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
        EventBus.getDefault().unregister(this);
        P.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({R.id.coustomerinfo_bt_assessment
            , R.id.home_address
            , R.id.coustomerinfo_id_scanning
            , R.id.coustomerinfo_jd_scanning
            , R.id.customerinfo_boarding_time
            , R.id.customerinfo_city_up
            // , R.id.coustomerinfo_brand_query
            , R.id.brand})
    public void onClick(View v) {
        if ((System.currentTimeMillis() - clickTime) > 500) {
            clickTime = System.currentTimeMillis();
            switch (v.getId()) {
                case R.id.coustomerinfo_bt_assessment:

                    if (!check()) {
                        submit();
                    }
                    break;
                case R.id.home_address:

                    Tools.inputMethodManager(getActivity().findViewById(R.id.home_address));
                    if (pvOptions != null) {
                        pvOptions.setSelectOptions(0, 0, 0);
                        pvOptions.show();
                    }
                    break;
                case R.id.customerinfo_boarding_time:

                    Tools.inputMethodManager(getActivity().findViewById(R.id.customerinfo_boarding_time));
                    if (pvCustomTime != null) {
                        pvCustomTime.show();
                    }
                    break;
                case R.id.customerinfo_city_up:
//                Tools.inputMethodManager(getActivity().findViewById(R.id.customerinfo_city_up));
//                if (pvProvince != null) {
//                    pvProvince.show();
//                }

                    Intent intent = new Intent(this.getContext(), JZGActivity.class);
                    intent.putExtra("isShowCity", true);
                    this.startActivityForResult(intent, 10002);

                    break;
                case R.id.coustomerinfo_id_scanning:

                    if (Tools.isSupportABI()) {
                        EventBus.getDefault().post(new BusEvent(BusEvent.SHOW_OCR_ID));
                    } else {
                        Tools.makeText("该手机不支持扫描功能，请手动输入！");
                    }
                    break;
                case R.id.coustomerinfo_jd_scanning:
                    Logger.e(" CustomerInfoFragment   R.id.coustomerinfo_jd_scanning:");
                    if (Tools.isSupportABI()) {
                        EventBus.getDefault().post(new BusEvent(BusEvent.SHOW_OCR_XD));
                    } else {
                        Tools.makeText("该手机不支持扫描功能，请手动输入！");
                    }
                    break;
//                case R.id.coustomerinfo_brand_query:
//                    Logger.e(" CustomerInfoFragment   R.id.coustomerinfo_brand_query:");
//                    String vin = coustomerinfo_frame_number.getText().toString().trim();
//                    if (!TextUtils.isEmpty(vin)) {
//                        if (vin.length() == 17) {
//                            EventBus.getDefault().post(new BusEvent(BusEvent.LOAD_VIN, vin));
//                        } else {
//                            Tools.makeText("请输入正确的17位VIN码");
//                        }
//                    } else {
//                        Tools.makeText("请输入车架号");
//                    }
//                    break;
                case R.id.brand:

                    this.startActivityForResult(
                            new Intent(this.getContext(), JZGActivity.class), 10001);
                    break;
                default:
                    break;
            }
        }
    }

    private void submit() {

        CustomerInfo c = new CustomerInfo();
        c.setLoanInfoId(ADDataManager.getInstance().getLoanInfoId());
        c.setName(coustomerinfo_name.getText().toString().trim());
        c.setIdCard(coustomerinfo_ID.getText().toString().trim());
        c.setPhone(coustomerinfo_phome.getText().toString().trim());
        c.setLivingPlaceProvince(mLPP);
        c.setLivingPlaceCity(mLPC);
        c.setLivingPlaceDistrict(mLPD);
        c.setLivingPlaceDetail(coustomerinfo_home_detailed_address.getText().toString().trim());
//        c.setSpouseName(coustomerinfo_spouseName.getText().toString().trim());
//        c.setSpouseIdcard(coustomerinfo_spouseId.getText().toString().trim());
//        c.setSpousePhone(coustomerinfo_spousePhone.getText().toString().trim());
        c.setCarFrameNo(coustomerinfo_frame_number.getText().toString().trim());
        c.setBrand(coustomerinfo_brand.getText().toString());
//        if (!TextUtils.isEmpty(mModel) && !TextUtils.isEmpty(mStyle)) {
//        c.setCarModel(mModel + " " + mStyle);
//        }
        c.setBuyCarDate(coustomerinfo_boarding_time.getText().toString().trim());
        c.setMileage(vehicleinfo_running_kilometers.getText().toString().trim());
        c.setCityId(mCityId);
        c.setTrimId(mTrimId);
        c.setCityName(vehicleinfo_city_up.getText().toString().trim());


        c.setLongitude(longitude);
        c.setLatitude(latitude);
        c.setFormattedAddress(address);

        if (((SingleNewActivity) getActivity()).isEdit()) {
            BaseInfoBean bean = ADDataManager.getInstance().getDefultInfo().getBaseInfo();
            bean.setName(c.getName());
            bean.setIdCard(c.getIdCard());
            bean.setPhone(c.getPhone());
            bean.setLivingPlaceProvinceCode(c.getLivingPlaceProvince());
            bean.setLivingPlaceCityCode(c.getLivingPlaceCity());
            bean.setLivingPlaceDistrictCode(c.getLivingPlaceDistrict());
            bean.setLivingPlaceDetail(c.getLivingPlaceDetail());
            bean.setSpouseName(c.getSpouseName());
            bean.setSpouseIdcard(c.getSpouseIdcard());
            bean.setSpousePhone(c.getSpousePhone());
            bean.setCarFrameNo(c.getCarFrameNo());
            bean.setBrand(c.getBrand());
//            if (!TextUtils.isEmpty(mModel) && !TextUtils.isEmpty(mStyle)) {
//            bean.setCarModel(c.getCarModel());
//            }
            bean.setRegisterDate(c.getBuyCarDate());
            bean.setMileage(c.getMileage());
            bean.setCityId(c.getCityId());
            bean.setTrimId(c.getTrimId());
            bean.setCityName(c.getCityName());
        }


        P.onSubmit(c);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001 && data != null) {
            mTrimId = data.getStringExtra("styleId");
            mStyle = data.getStringExtra("styleName");
            mModel = data.getStringExtra("modleName");
            mMake = data.getStringExtra("makeName");
            String text = mMake + " " + mModel + " " + mStyle;
            coustomerinfo_brand.setText(text);

            int textlength = (int) coustomerinfo_brand.getPaint().measureText(text);
            if (textlength > brandWidth) {
                coustomerinfo_brand.setGravity(Gravity.LEFT);
            } else {
                coustomerinfo_brand.setGravity(Gravity.RIGHT);
            }

        } else if (requestCode == 10002 && data != null) {
            mCityId = data.getStringExtra("cityId");
            String provinceName = data.getStringExtra("provinceName");
            String cityName = data.getStringExtra("cityName");
            vehicleinfo_city_up.setText(cityName);
        }

    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {
        switch (event.event) {

            default:
                break;
        }
    }

    public void setOCRID(String name, String id) {
        coustomerinfo_name.setText(TextUtils.isEmpty(name) ? "" : name);
        coustomerinfo_ID.setText(TextUtils.isEmpty(id) ? "" : id);
    }

    public void setOCRXD(String vin, String registerDate) {
        coustomerinfo_frame_number.setText(TextUtils.isEmpty(vin) ? "" : vin);
        coustomerinfo_boarding_time.setText(TextUtils.isEmpty(registerDate) ? "" : registerDate);

    }

    public void setMake(String make, String model, String style, String trimId) {
        mTrimId = trimId;
        mMake = make;
        mModel = model;
        mStyle = style;
        String str = mMake + " " + model + " " + style;
        coustomerinfo_brand.setText(TextUtils.isEmpty(str) ? "" : str);
    }


    @Override
    public void showDial(String meaage) {
        ((SingleNewActivity) getActivity()).showProgressDialog(meaage);
    }

    @Override
    public void onSetArealist(List<AreaList> lists) {
        if (lists != null && !lists.isEmpty()) {
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

    }


    @Override
    public void onErr(int type, String err) {

        Tools.makeText(err);
        if (CustomerInfoP.PROMPT_ONE == type) {
            if (this != null && getActivity() != null) {
                ((SingleNewActivity) getActivity()).erroBack(this);
            }
        }


    }

    @Override
    public void onDismiss() {

        ((SingleNewActivity) getActivity()).dismiss();
    }

    @Override
    public void onLoadProvince() {
        if (((SingleNewActivity) getActivity()).isEdit()) {
            P.getOrderDefault(ADDataManager.getInstance().getLoanInfoId());
        }
    }

    @Override
    public void onShowAcAssessmentResult() {

        ADDataManager.getInstance().setName(coustomerinfo_name.getText().toString().trim());
        ADDataManager.getInstance().setPhone(coustomerinfo_phome.getText().toString().trim());

        if (!TextUtils.isEmpty(ADDataManager.getInstance().getMessage())) {
            ((SingleNewActivity) getActivity()).showAlertDialog(ADDataManager.getInstance().getMessage(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        EventBus.getDefault().post(new BusEvent(BusEvent.FRAGMENT_ASSESSMENT_RESULT, this));

    }

    @Override
    public void setOrderDefault(OrderDefultInfo defultInfo) {

        if (defultInfo != null) {
            BaseInfoBean o = defultInfo.getBaseInfo();
            if (o != null) {
                coustomerinfo_name.setText(o.getName());
                coustomerinfo_ID.setText(o.getIdCard());
                coustomerinfo_phome.setText(o.getPhone());


                String valueL = "";
                String lp = TextUtils.isEmpty(o.getLivingPlaceProvinceValue()) ? "" : o.getLivingPlaceProvinceValue();
                String lc = TextUtils.isEmpty(o.getLivingPlaceCityValue()) ? "" : o.getLivingPlaceCityValue();
                String ld = TextUtils.isEmpty(o.getLivingPlaceDistrictValue()) ? "" : o.getLivingPlaceDistrictValue();
                valueL = lp + lc + ld;
                if (lp.equals(lc)) {
                    valueL = lc + ld;
                }

                mLPP = TextUtils.isEmpty(o.getLivingPlaceProvinceCode()) ? "" : o.getLivingPlaceProvinceCode();
                mLPC = TextUtils.isEmpty(o.getLivingPlaceCityCode()) ? "" : o.getLivingPlaceCityCode();
                mLPD = TextUtils.isEmpty(o.getLivingPlaceDistrictCode()) ? "" : o.getLivingPlaceDistrictCode();
                coustomerinfo_home_address.setText(valueL);

                coustomerinfo_home_detailed_address.setText(o.getLivingPlaceDetail());
//
//                coustomerinfo_spouseName.setText(TextUtils.isEmpty(o.getSpouseName()) ? "" : o.getSpouseName());
//                coustomerinfo_spouseId.setText(TextUtils.isEmpty(o.getSpouseIdcard()) ? "" : o.getSpouseIdcard());
//                coustomerinfo_spousePhone.setText(TextUtils.isEmpty(o.getSpousePhone()) ? "" : o.getSpousePhone());

                mCityId = o.getCityId();
                vehicleinfo_city_up.setText(o.getCityName());
                mTrimId = o.getTrimId();
                coustomerinfo_frame_number.setText(o.getCarFrameNo());
                String make = o.getBrand();
//                String model = o.getCarModel();
//                String style = "";
//                if (!TextUtils.isEmpty(model) && !model.equals(" ") && model.length() > 3 && model.contains(" ")) {
//                    String sp[] = model.split(" ");
//                    if (sp.length > 2) {
//                        model = sp[0];
//                        style = sp[1];
//                    }
//                } else {
//                    model = "";
//                }
//                coustomerinfo_brand.setText(make + model + style);
                coustomerinfo_brand.setText(make);
                int textlength = (int) coustomerinfo_brand.getPaint().measureText(make);
                if (textlength > brandWidth) {
                    coustomerinfo_brand.setGravity(Gravity.LEFT);
                } else {
                    coustomerinfo_brand.setGravity(Gravity.RIGHT);
                }


                coustomerinfo_boarding_time.setText(o.getRegisterDate());
                String mileage = o.getMileage();
                if (!TextUtils.isEmpty(mileage) && mileage.equals("0.00")) {
                    mileage = "0";
                    vehicleinfo_running_kilometers.setText(mileage);
                } else {
                    String str = "";
                    if (mileage.contains(".") && mileage.length() >= 4) {
                        str = mileage.substring(mileage.indexOf(".") + 1, mileage.length());
                        if (str.equals("00")) {
                            vehicleinfo_running_kilometers.setText(mileage.substring(0, mileage.indexOf(".")));
                        } else {
                            vehicleinfo_running_kilometers.setText(mileage);
                        }
                    } else {
                        vehicleinfo_running_kilometers.setText(mileage);
                    }

                }

            }

            if (((SingleNewActivity) getActivity()).isEdit()) {
                EventBus.getDefault().post(new BusEvent(BusEvent.ASSESSMENTRESULT_GETORDERDEFAULT));
            }
        }

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

//    private void initProvince() {
//        pvProvince = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                if (provinceItem.isEmpty()) {
//                    return;
//                }
//                String tx = provinceItem.get(options1).getPickerViewText();
//                mCityId = provinceItem.get(options1).getCode();
//                vehicleinfo_city_up.setText(tx);
//            }
//        })
//                .setDividerType(WheelView.DividerType.WRAP)
//                .setTextColorCenter(Color.BLACK)
//                .setContentTextSize(20)
//                .build();
//
//    }


    private void initCustomTimePicker() {
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                coustomerinfo_boarding_time.setText(getTime(date));
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


    private boolean check() {

        if (TextUtils.isEmpty(coustomerinfo_name.getText().toString().trim())) {
            Tools.makeText("姓名不能为空");
            return true;
        }
        if (TextUtils.isEmpty(coustomerinfo_ID.getText().toString().trim())) {
            Tools.makeText("身份证不能为空");
            return true;
        }

        if (!TextUtils.isEmpty(coustomerinfo_ID.getText().toString().trim()) && coustomerinfo_ID.getText().toString().trim().length() != 15
                && coustomerinfo_ID.getText().toString().trim().length() != 18) {
            Tools.makeText("请输入15、18位身份证号");
            return true;
        }
        if (TextUtils.isEmpty(coustomerinfo_phome.getText().toString().trim())) {
            Tools.makeText("联系电话不能为空");
            return true;
        }

        if (!TextUtils.isEmpty(coustomerinfo_phome.getText().toString().trim()) && coustomerinfo_phome.getText().toString().length() < 11) {
            Tools.makeText("请输入11位电话号码");
            return true;
        }
        if (TextUtils.isEmpty(coustomerinfo_home_address.getText().toString().trim())) {
            Tools.makeText("现居地址不能为空");
            return true;
        }
        if (TextUtils.isEmpty(coustomerinfo_home_detailed_address.getText().toString().trim())) {
            Tools.makeText("现居详细地址不能为空");
            return true;
        }

//        if (!TextUtils.isEmpty(coustomerinfo_spouseName.getText().toString().trim())
//                && TextUtils.isEmpty(coustomerinfo_spouseId.getText().toString().trim())) {
//            Tools.makeText("配偶身份证不能为空");
//            return true;
//        }
//
//        if (!TextUtils.isEmpty(coustomerinfo_spouseId.getText().toString().trim())
//                && coustomerinfo_spouseId.getText().toString().trim().length() != 15
//                && coustomerinfo_spouseId.getText().toString().trim().length() != 18) {
//            Tools.makeText("请输入15、18为身份证号");
//            return true;
//        }

//        if (!TextUtils.isEmpty(coustomerinfo_spouseName.getText().toString().trim())
//                && TextUtils.isEmpty(coustomerinfo_spousePhone.getText().toString().trim())) {
//            Tools.makeText("配偶电话不能为空");
//            return true;
//        }
//
//        if (!TextUtils.isEmpty(coustomerinfo_spouseName.getText().toString().trim())
//                && coustomerinfo_spousePhone.getText().toString().trim().length() != 11) {
//            Tools.makeText("请输入11位电话");
//            return true;
//        }


//        if (!TextUtils.isEmpty(coustomerinfo_spouseId.getText().toString().trim())
//                && TextUtils.isEmpty(coustomerinfo_spouseName.getText().toString().trim())) {
//            Tools.makeText("配偶姓名不能为空");
//            return true;
//        }

//        if (!TextUtils.isEmpty(coustomerinfo_spouseId.getText().toString().trim())
//                && TextUtils.isEmpty(coustomerinfo_spousePhone.getText().toString().trim())) {
//            Tools.makeText("配偶电话不能为空");
//            return true;
//        }

//        if (!TextUtils.isEmpty(coustomerinfo_spouseId.getText().toString().trim())
//                && coustomerinfo_spousePhone.getText().toString().trim().length() != 11) {
//            Tools.makeText("请输入11位电话");
//            return true;
//        }

//        if (!TextUtils.isEmpty(coustomerinfo_spousePhone.getText().toString().trim())
//                && coustomerinfo_spousePhone.getText().toString().trim().length() != 11) {
//            Tools.makeText("请输入11位电话");
//            return true;
//        }
//
//        if (!TextUtils.isEmpty(coustomerinfo_spousePhone.getText().toString().trim())
//                && TextUtils.isEmpty(coustomerinfo_spouseName.getText().toString().trim())) {
//            Tools.makeText("配偶姓名不能为空");
//            return true;
//        }

//        if (!TextUtils.isEmpty(coustomerinfo_spousePhone.getText().toString().trim())
//                && TextUtils.isEmpty(coustomerinfo_spouseId.getText().toString().trim())) {
//            Tools.makeText("配偶身份证不能为空");
//            return true;
//        }
//
//        if (!TextUtils.isEmpty(coustomerinfo_spousePhone.getText().toString().trim())
//                && coustomerinfo_spouseId.getText().toString().trim().length() != 15
//                && coustomerinfo_spouseId.getText().toString().trim().length() != 18) {
//            Tools.makeText("请输入15、18为身份证号");
//            return true;
//        }

//        if (TextUtils.isEmpty(coustomerinfo_frame_number.getText().toString().trim())) {
//            Tools.makeText("车架号不能为空");
//            return true;
//        }

        if (!TextUtils.isEmpty(coustomerinfo_frame_number.getText().toString().trim())
                && coustomerinfo_frame_number.getText().toString().trim().length() != 17) {
            Tools.makeText(" 请输入正确的17位车架号");
            return true;
        }

        if (TextUtils.isEmpty(coustomerinfo_brand.getText().toString().trim())) {
            Tools.makeText("车辆品牌不能为空");
            return true;
        }
        if (TextUtils.isEmpty(coustomerinfo_boarding_time.getText().toString().trim())) {
            Tools.makeText("上牌时间不能为空");
            return true;
        }

        if (!TextUtils.isEmpty(coustomerinfo_boarding_time.getText().toString().trim())) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(coustomerinfo_boarding_time.getText().toString().trim());
                long time = date.getTime();
                String oldTime = sdf.format(System.currentTimeMillis());
                long newTime = sdf.parse(oldTime).getTime();
                if (time > newTime) {
                    Tools.makeText("上牌时间不能大于当前时间");
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (TextUtils.isEmpty(vehicleinfo_city_up.getText().toString().trim())) {
            Tools.makeText("上牌城市不能为空");
            return true;
        }

        if (TextUtils.isEmpty(vehicleinfo_running_kilometers.getText().toString().trim())) {
            Tools.makeText("行驶公里数不能为空");
            return true;
        }

//        if (vehicleinfo_running_kilometers.getText().toString().trim().substring(0, 1).equals("0")
//                || vehicleinfo_running_kilometers.getText().toString().trim().substring(0, 1).equals(".")
//                || vehicleinfo_running_kilometers.getText().toString().trim().substring(
//                vehicleinfo_running_kilometers.getText().toString().trim().length() - 1).equals(".")) {
//            Tools.makeText("请输入合法行驶公里数");
//            return true;
//        }
//
//        if ((vehicleinfo_running_kilometers.getText().toString().trim().substring(0, 1).equals("0")
//                && vehicleinfo_running_kilometers.getText().toString().trim().length() == 1)
//                || Double.parseDouble(vehicleinfo_running_kilometers.getText().toString().trim()) == 0) {
//            Tools.makeText("行驶公里数不能等于0公里");
//            return true;
//        }

        return false;
    }


    private void initOptionPicker() {

        pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if (options1Items == null) {
                    return;
                }

                if (options1Items.isEmpty()) {
                    return;
                }


                String p = "";
                String c = "";
                String d = "";
                if (options1Items.size() > 0 && options1Items.get(options1) != null) {

                    p = options1Items.get(options1).getPickerViewText();
                    mLPP = options1Items.get(options1).getCode();
                }

                if (options2Items.size() > 0
                        && options2Items.get(options1) != null
                        && options2Items.get(options1).size() > 0
                        && options2Items.get(options1).get(options2) != null) {
                    c = options2Items.get(options1).get(options2).getPickerViewText();
                    mLPC = options2Items.get(options1).get(options2).getCode();
                }

                if (options3Items.size() > 0
                        && options3Items.get(options1) != null && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2) != null && options3Items.get(options1).get(options2).size() > 0
                        && options3Items.get(options1).get(options2).get(options3) != null) {
                    d = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                    mLPD = options3Items.get(options1).get(options2).get(options3).getCode();
                } else {
                    mLPD = "";
                }

                //返回的分别是三个级别的选中位置
                String tx = p + c + d;
                if (p.equals(c)) {
                    tx = c + d;
                }

                coustomerinfo_home_address.setText(tx);


            }
        })
                .setTitleText("城市选择")
                .setDividerType(WheelView.DividerType.WRAP)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置滚轮文字大小
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .build();
    }

}
