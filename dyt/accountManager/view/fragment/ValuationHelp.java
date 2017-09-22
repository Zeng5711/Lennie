package com.hxyd.dyt.accountManager.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.Contact;
import com.hxyd.dyt.accountManager.modle.entity.Province;
import com.hxyd.dyt.accountManager.modle.entity.VIN;
import com.hxyd.dyt.accountManager.presenter.JingZhenGuP;
import com.hxyd.dyt.accountManager.presenter.in.JingZhenGuPI;
import com.hxyd.dyt.accountManager.view.JZGActivity;
import com.hxyd.dyt.accountManager.view.SingleNewActivity;
import com.hxyd.dyt.accountManager.view.ValuationActivity;
import com.hxyd.dyt.accountManager.view.in.JingZhenGuVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Files;
import com.hxyd.dyt.common.uitl.PermissionUtils;
import com.hxyd.dyt.common.uitl.Tools;
import com.intsig.idcardscan.sdk.IDCardScanSDK;
import com.intsig.idcardscan.sdk.ISCardScanActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by win7 on 2017/5/23.
 */

public class ValuationHelp extends Fragment implements JingZhenGuVI {

    @BindView(R.id.valuation_frame_number)
    EditText valuation_frame_number;
    @BindView(R.id.valuation_running_kilometers)
    EditText valuation_running_kilometers;

    @BindView(R.id.valuation_jd_scanning)
    ImageView valuation_jd_scanning;

//    @BindView(R.id.valuation_brand_query)
//    TextView valuation_brand_query;
    @BindView(R.id.valuation_brand)
    TextView valuation_brand;
    @BindView(R.id.valuation_boarding_time)
    TextView valuation_boarding_time;
    @BindView(R.id.vehicleinfo_city_up)
    TextView vehicleinfo_city_up;
    @BindView(R.id.tv_brand)
    TextView tv_brand;


    private String mCityId = "";
    private String mTrimId = "";
    private String mStyle = "";
    private String mModel = "";
    private String mMake = "";
    private long clickTime = 0;

    private JingZhenGuPI P;
    private TimePickerView pvCustomTime;

    private PermissionUtils.PermissionGrant permissionGrant;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        P = new JingZhenGuP(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.valuation_help, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCustomTimePicker();
//        valuation_frame_number.setText("LGBG22E05DY571707");
        valuation_frame_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                valuation_frame_number.removeTextChangedListener(this);//解除文字改变事件
                valuation_frame_number.setText(s.toString().toUpperCase());//转换
                valuation_frame_number.setSelection(s.toString().length());//重新设置光标位置
                valuation_frame_number.addTextChangedListener(this);//重新绑
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


        permissionGrant = new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(int requestCode, boolean isSuccess) {
                if (requestCode == PermissionUtils.CODE_CAMERA) {
                    if (isSuccess) {
                        Intent xd = new Intent(getActivity(), com.intsig.vlcardscansdk.ISCardScanActivity.class);
                        xd.putExtra(ISCardScanActivity.EXTRA_KEY_IMAGE_FOLDER, Files.getStorageDirectory());//
                        xd.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_MATCH, 0xffff0000);
                        xd.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_NORMAL, 0xFF40FF43);
                        xd.putExtra(ISCardScanActivity.EXTRA_KEY_APP_KEY, Constant.OCR_KEY);
                        xd.putExtra(ISCardScanActivity.EXTRA_KEY_TIPS, "请将行驶证放在相框内识别");
                        xd.putExtra(ISCardScanActivity.EXTRA_KEY_COMPLETECARD_IMAGE, IDCardScanSDK.OPEN_COMOLETE_CHECK);
                        startActivityForResult(xd, 10003);
                    } else {
                        ((ValuationActivity) ValuationHelp.this.getActivity()).showAlertDialog("相机权限被拒绝");
                    }
                }
            }
        };

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
        P.onDestroy();
        super.onDestroy();
    }

    @OnClick({R.id.valuation_bt_assessment,
            R.id.valuation_boarding_time_rl,
            R.id.customerinfo_city_up,
            R.id.valuation_brand,
            R.id.valuation_jd_scanning,
//            R.id.valuation_brand_query
    })
    public void onClick(View view) {

        if ((System.currentTimeMillis() - clickTime) > 500) {

            clickTime = System.currentTimeMillis();

            switch (view.getId()) {
                case R.id.valuation_boarding_time_rl:
                    Tools.inputMethodManager(getActivity().findViewById(R.id.valuation_boarding_time_rl));
                    if (pvCustomTime != null) {
                        pvCustomTime.show();
                    }
                    break;
                case R.id.valuation_bt_assessment:

                    Tools.inputMethodManager(getActivity().findViewById(R.id.valuation_bt_assessment));
                    //TrimId：车型Id
                    //BuyCarDate：上牌时间，格式 yyyy-MM-dd
                    //Mileage：行驶里程（公里）
                    //ColorId:车辆颜色
                    //CityId：城市Id
                    if (!check()) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("TrimId", mTrimId);
                        map.put("BuyCarDate", valuation_boarding_time.getText().toString().trim());
                        map.put("Mileage", valuation_running_kilometers.getText().toString().trim());
//                map.put("ColorId", "1");
                        map.put("CityId", mCityId);

                        P.getJingZhenGuObjectData(JingZhenGuP.OPERATE_ESTIMATE, map);
                    }

                    break;
                case R.id.customerinfo_city_up:
                    Tools.inputMethodManager(getActivity().findViewById(R.id.customerinfo_city_up));
                    Intent intent = new Intent(this.getContext(), JZGActivity.class);
                    intent.putExtra("isShowCity", true);
                    this.startActivityForResult(intent, 10002);
                    break;
                case R.id.valuation_brand:
                    Tools.inputMethodManager(getActivity().findViewById(R.id.valuation_brand));
                    this.startActivityForResult(
                            new Intent(this.getContext(), JZGActivity.class), 10001);
                    break;
                case R.id.valuation_jd_scanning:
                    Tools.inputMethodManager(getActivity().findViewById(R.id.valuation_jd_scanning));
                    if(Tools.isSupportABI()) {
                        PermissionUtils.getInstance().requestPermission((ValuationActivity) getActivity(), PermissionUtils.CODE_CAMERA, permissionGrant);
                    }else{
                        Tools.makeText("该手机不支持扫描功能，请手动输入！");
                    }
                    break;
//                case R.id.valuation_brand_query:
//                    String vin = valuation_frame_number.getText().toString().trim();
//                    if (!TextUtils.isEmpty(vin)) {
//                        if (vin.length() == 17) {
//                            getVIN(vin);
//                        } else {
//                            Tools.makeText("请输入正确的17位VIN码");
//                        }
//                    } else {
//                        Tools.makeText("请输入车架号");
//                    }
//                    break;
                default:
                    break;
            }
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(valuation_brand.getText().toString().trim())) {
            Tools.makeText("请选择车辆品牌");
            return true;
        }
        if (TextUtils.isEmpty(valuation_boarding_time.getText().toString().trim())) {
            Tools.makeText("请选择上牌时间");
            return true;
        }
        if (!TextUtils.isEmpty(valuation_boarding_time.getText().toString().trim())) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(valuation_boarding_time.getText().toString().trim());
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
            Tools.makeText("请选择上牌城市");
            return true;
        }
        if (TextUtils.isEmpty(valuation_running_kilometers.getText().toString().trim())) {
            Tools.makeText("请输入行驶公里数");
            return true;
        }

        return false;
    }


    private void getVIN(String vin) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("VIN", vin);
        P.getJingZhenGuM(JingZhenGuP.OPERATE_VIN, map);
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
            if (valuation_brand != null && tv_brand != null) {
                valuation_brand.setText(text);

                tv_brand.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                int w = Tools.getwidthPixels(getContext()) - tv_brand.getMeasuredWidth() - (int) (Tools.getDisplayDensity(getContext()) * 70);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.alignWithParent = true;
                layoutParams.setMargins(0, 0, (int) (Tools.getDisplayDensity(getContext()) * 25), 0);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                valuation_brand.setLayoutParams(layoutParams);
                int textlength = (int) valuation_brand.getPaint().measureText(text);
                if (textlength > w) {
                    valuation_brand.setGravity(Gravity.LEFT);
                } else {
                    valuation_brand.setGravity(Gravity.RIGHT);
                }
            }


        } else if (requestCode == 10002 && data != null) {
            mCityId = data.getStringExtra("cityId");
            String provinceName = data.getStringExtra("provinceName");
            String cityName = data.getStringExtra("cityName");
            if(vehicleinfo_city_up!=null) {
                vehicleinfo_city_up.setText(cityName);
            }
        }
        if (requestCode == 10003 && resultCode != 0) {
            com.intsig.vlcardscansdk.ResultData resultData = (com.intsig.vlcardscansdk.ResultData) data.getSerializableExtra(com.intsig.vlcardscansdk.ISCardScanActivity.EXTRA_KEY_RESULT_DATA);
            if (resultData != null) {
                String vin = resultData.getVin();//行驶证车辆识别代号
                String registerDate = resultData.getRegisterDate();//行驶证注册日期
                valuation_boarding_time.setText(registerDate);
                valuation_frame_number.setText(vin);
//                getVIN(vin);
            }
        }
    }

    private void initCustomTimePicker() {
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                valuation_boarding_time.setText(getTime(date));
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

    @Override
    public void onPrompt(int type, String message) {
        Tools.makeText(message);
    }

    @Override
    public void showDialog(int type, String title, String message) {
        ((ValuationActivity) getActivity()).showProgressDialog(message);
    }

    @Override
    public void colseDialog() {
        ((ValuationActivity) getActivity()).dismiss();
    }

    @Override
    public void setVIN(final ArrayList<VIN> list) {
        if (list != null) {
            //[{"MakeName":"雪佛兰","ModelId":"1835","ModelName":"雪佛兰景程","StyleYear":"2007","EnvironmentStandard":"国3","StyleName":"2.0L 自动 SX豪华版","Emission":"2.0","MakeId":"49","StyleId":"2164"}]
            final String[] cities = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                VIN vin = list.get(i);
                String ModelName = vin.getMakeName();
                String StyleYear = vin.getStyleYear();
                String StyleName = vin.getStyleName();
                cities[i] = ModelName + StyleYear + StyleName;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("请选择车型");
            builder.setItems(cities, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    VIN vin = list.get(which);
                    String ModelName = vin.getMakeName();
                    String StyleYear = vin.getStyleYear();
                    String StyleName = vin.getStyleName();
                    String StyleId = vin.getStyleId();
                    mTrimId = StyleId;
                    valuation_brand.setText(ModelName + StyleYear + StyleName);

                }
            });
            builder.setCancelable(false);
            builder.show();

        }
    }

    @Override
    public void setMake(ArrayList<Contact> list) {

    }

    @Override
    public void setModel(ArrayList<Contact> list) {

    }

    @Override
    public void setStyle(ArrayList<Contact> list) {

    }

    @Override
    public void setProvince(ArrayList<Province> list) {

    }

    @Override
    public void setCity(ArrayList<Province> list) {

    }

    @Override
    public void setEstimate(String C2BPrices) {
        ((ValuationActivity) getActivity()).switchContent(this,
                ((ValuationActivity) getActivity()).getVr(),
                ((ValuationActivity) getActivity()).TAG_VR);
        ((ValuationActivity) getActivity()).showClose();
        ((ValuationActivity) getActivity()).setC2BPrices(C2BPrices);
    }

    /**
     * 回调获取授权结果，判断是否授权
     * 权限开启结果检查 grantResults[0]==0=PackageManager.PERMISSION_GRANTED的话 权限开启成功
     * 权限开启结果检查 grantResults[0]==-1=PackageManager.PERMISSION_DENIED 权限开启失败
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length > 0) {
            PermissionUtils.requestPermissionsResult((ValuationActivity) getActivity(), requestCode, permissions, grantResults);
        }
    }
}
