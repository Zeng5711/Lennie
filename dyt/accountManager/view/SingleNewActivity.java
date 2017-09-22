package com.hxyd.dyt.accountManager.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.BaseInfoBean;
import com.hxyd.dyt.accountManager.modle.entity.Contact;
import com.hxyd.dyt.accountManager.modle.entity.Province;
import com.hxyd.dyt.accountManager.modle.entity.VIN;
import com.hxyd.dyt.accountManager.presenter.ADDataManager;
import com.hxyd.dyt.accountManager.presenter.JingZhenGuP;
import com.hxyd.dyt.accountManager.presenter.in.JingZhenGuPI;
import com.hxyd.dyt.accountManager.view.fragment.AssessmentResultFragment;
import com.hxyd.dyt.accountManager.view.fragment.CustomerInfoFragment;
import com.hxyd.dyt.accountManager.view.fragment.ImageInfoFragment;
import com.hxyd.dyt.accountManager.view.fragment.SingleHomeFragment;
import com.hxyd.dyt.accountManager.view.fragment.SupplementaryFragment;
import com.hxyd.dyt.accountManager.view.fragment.UserInfoNewFragment;
import com.hxyd.dyt.accountManager.view.in.JingZhenGuVI;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.entity.CardBean;
import com.hxyd.dyt.common.entity.ImageItem;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Files;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.common.uitl.PermissionUtils;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.common.uitl.UriTools;
import com.hxyd.dyt.purplestar.utils.GPS3DUtils;
import com.intsig.idcardscan.sdk.IDCardScanSDK;
import com.intsig.idcardscan.sdk.ISCardScanActivity;
import com.intsig.idcardscan.sdk.ResultData;
import com.orhanobut.logger.Logger;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class SingleNewActivity extends BaseActivity implements JingZhenGuVI {

    @BindView(R.id.single)
    RelativeLayout sigle;
    @BindView(R.id.single_background)
    ImageView singleBackground;
    @BindView(R.id.single_fragment)
    LinearLayout singleFragment;

    @BindString(R.string.title_name_single)
    String mTitle;

    private ImgSelConfig config;
    private ImageLoader imageLoader;
    //    private Uri mImageFileUri;
    private PermissionUtils.PermissionGrant permissionGrant;

    private FragmentManager fm;
    private static final String TAG_CUSTOMERINFO = "CUSTOMERINFO";
    private static final String TAG_SINGLE_HOME = "SINGLE_HOME";
    private static final String TAG_IMAGEINFO_NEW = "IMAGEINFO_NEW";
    private static final String TAG_SUPPLEMENTARY = "SUPPLEMENTARY";
    private static final String TAG_ASSESSMENT_RESULT = "ASSESSMENT_RESULT";
    private static final String TAG_USERINFO_NEW = "USERINFO_NEW";
    private static final int RESULTCODE_ID = 1009;
    private static final int RESULTCODE_XD = 1007;
    private static final int STARTCAMERA = 1;
    private static final int STARTLMAGESELECTOR = 2;
    private String mStype = "";
    private int mPosition = 0;
    private int currentItem;

    private CustomerInfoFragment mCIF;
    //    private SingleHomeFragment mSHF;
    private UserInfoNewFragment mUIF;
    private ImageInfoFragment mIIF;
    private SupplementaryFragment mSF;
    private AssessmentResultFragment mARF;
    private BaseInfoBean baseInfoBean = new BaseInfoBean();
    private JingZhenGuPI P;

    private static String currentTAG = "";
    private boolean isEdit = false;



    public boolean isEdit() {
        return isEdit;
    }

    public BaseInfoBean getBaseInfoBean() {
        return baseInfoBean;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        Logger.e("SingleNewActivity  initAllMembersView===>");
        isShowTR(false);
        isShowIR(false);
        isShowSpoy(false);
        isCkeckBack(false);
        setIL(R.mipmap.back);
        getIL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentBack(currentTAG);
            }
        });
        setTitle(mTitle);

        getTR().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentTAG.equals(TAG_USERINFO_NEW)) {
                    EventBus.getDefault().post(new BusEvent(BusEvent.USERINFONEWFRAGMENT_SAVE));
                } else if (currentTAG.equals(TAG_SUPPLEMENTARY)) {
                    EventBus.getDefault().post(new BusEvent(BusEvent.SUPPLEMENTARYFRAGMENT_SAVE));
                } else if (currentTAG.equals(TAG_IMAGEINFO_NEW)) {
                    Intent intent = new Intent(SingleNewActivity.this, ViewSampleActivity.class);
                    SingleNewActivity.this.startActivity(intent);
                }


            }
        });

        ADDataManager.getInstance().setLoanInfoId("");
        P = new JingZhenGuP(this);
        initFragment();

        imageLoader = new ImageLoader() {

            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                ImageloadTools.load(context, path, imageView);
            }
        };
        permissionGrant = new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(int requestCode, boolean isSuccess) {
                if (requestCode == PermissionUtils.CODE_CAMERA) {
                    if (isSuccess) {
                        startCamera();
                    } else {
                        SingleNewActivity.this.showAlertDialog("相机权限被拒绝");
                    }
                }
            }
        };

    }

    private void initFragment() {
        mCIF = new CustomerInfoFragment();
//        mSHF = new SingleHomeFragment();
        mUIF = new UserInfoNewFragment();
        mIIF = new ImageInfoFragment();
        mSF = new SupplementaryFragment();
        mARF = new AssessmentResultFragment();

        currentTAG = TAG_SINGLE_HOME;
        changeFragment(true, mCIF, TAG_SINGLE_HOME);


        String orderNo = getIntent().getStringExtra("orderNo");
        if (!TextUtils.isEmpty(orderNo)) {
            isEdit = true;
            ADDataManager.getInstance().setLoanInfoId(orderNo);
            currentTAG = TAG_ASSESSMENT_RESULT;
            switchContent(mCIF, mARF, TAG_ASSESSMENT_RESULT);
        }
    }

    public void erroBack(Fragment f) {
        String tag = null;
        if (f != null) {
            if (f instanceof CustomerInfoFragment) {
                tag = TAG_CUSTOMERINFO;
            }
            fragmentBack(tag);
//            removeFragment(f);
        }
    }

    public void removeFragment(Fragment f) {
        if (f != null) {
            FragmentTransaction ft = getMyFragmentManager().beginTransaction();
            ft.remove(f);
            ft.commit();
        }
    }

    private FragmentManager getMyFragmentManager() {
        if (fm == null) {
            fm = getSupportFragmentManager();
        }
        return fm;
    }

    private void changeFragment(boolean isadd, Fragment f, String tag) {
        if (f != null) {
            FragmentTransaction ft = getMyFragmentManager().beginTransaction();
            if (isadd) {
                ft.add(R.id.single_fragment, f, tag);
            } else {
                ft.replace(R.id.single_fragment, f, tag);
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }


    private void switchContent(Fragment from, Fragment to, String tag) {
        if (null != from && null != to) {
            FragmentTransaction transaction = getMyFragmentManager().beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.single_fragment, to, tag).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_single_new);
    }

    @Override
    protected void onResume() {
        Logger.e("SingleNewActivity === onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Logger.e("SingleNewActivity === onDestroy");
        EventBus.getDefault().unregister(this);
        ADDataManager.getInstance().setLoanInfoId("");
        ADDataManager.getInstance().setMessage("");
        ADDataManager.getInstance().setAudiValuationPriceReall("");
        ADDataManager.getInstance().setValuationPrice("");
        ADDataManager.getInstance().setPhone("");
        ADDataManager.getInstance().setName("");
        ADDataManager.getInstance().setAreaLists(null);
        ADDataManager.getInstance().setBaseInfoBean(null);
        ADDataManager.getInstance().setDefultInfo(null);
        ADDataManager.getInstance().setProductInfoList(null);
        baseInfoBean = null;
        P.onDestroy();
        if (isEdit) {
            EventBus.getDefault().post(new BusEvent(BusEvent.ORDERDEFAULT_LOANDETAIL));
        }

        Logger.e("SingleNewActivity  onDestroy===>");
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {
        switch (event.event) {
            case BusEvent.FRAGMENT_CUSTOMERINFO:
                sigle.setVisibility(View.GONE);
                currentTAG = TAG_CUSTOMERINFO;
//                mCIF = new CustomerInfoFragment();
                switchContent(event.f, mCIF, TAG_CUSTOMERINFO);
                isShowTR(false);
                break;
//            case BusEvent.FRAGMENT_SINGLE_HOME:
//                sigle.setVisibility(View.GONE);
//                currentTAG = TAG_SINGLE_HOME;
//                switchContent(event.f, mSHF, TAG_SINGLE_HOME);
//                break;
            case BusEvent.FRAGMENT_IMAGEINFO_NEW:
                sigle.setVisibility(View.VISIBLE);
                currentTAG = TAG_IMAGEINFO_NEW;
                singleBackground.setImageResource(R.mipmap.single_new_iamge);
//                mIIF = new ImageInfoFragment();
                switchContent(event.f, mIIF, TAG_IMAGEINFO_NEW);
                isShowTR(true);
                setTR("查看样例");
                break;
            case BusEvent.FRAGMENT_SUPPLEMENTARY:
                sigle.setVisibility(View.VISIBLE);
                singleBackground.setImageResource(R.mipmap.single_new_supplementary);
                currentTAG = TAG_SUPPLEMENTARY;
                if (mSF != null && isEdit) {
                    removeFragment(mSF);
                    mSF = null;
                    mSF = new SupplementaryFragment();
                }
                switchContent(event.f, mSF, TAG_SUPPLEMENTARY);
//                isShowTR(true);
//                setTR("保存");
                break;
            case BusEvent.FRAGMENT_ASSESSMENT_RESULT:
                sigle.setVisibility(View.GONE);
                currentTAG = TAG_ASSESSMENT_RESULT;
                mARF = new AssessmentResultFragment();
                switchContent(event.f, mARF, TAG_ASSESSMENT_RESULT);
                isShowTR(false);
                break;
            case BusEvent.FRAGMENT_USERINFO_NEW:
                sigle.setVisibility(View.VISIBLE);
                singleBackground.setImageResource(R.mipmap.single_new_user);
                currentTAG = TAG_USERINFO_NEW;
                if (mUIF != null && isEdit) {
                    removeFragment(mUIF);
                    mUIF = null;
                    mUIF = new UserInfoNewFragment();
                }
                switchContent(event.f, mUIF, TAG_USERINFO_NEW);
                isShowTR(true);
                setTR("保存");
                break;
            case BusEvent.SHOW_OCR_ID:


                PermissionUtils.getInstance().requestPermission(this, PermissionUtils.CODE_CAMERA, new PermissionUtils.PermissionGrant() {
                    @Override
                    public void onPermissionGranted(int requestCode, boolean isSuccess) {
                        if (requestCode == PermissionUtils.CODE_CAMERA) {
                            if (isSuccess) {
                                Intent intent = new Intent(SingleNewActivity.this, ISCardScanActivity.class);
                                intent.putExtra(ISCardScanActivity.EXTRA_KEY_IMAGE_FOLDER, Files.getStorageDirectory());//
                                intent.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_MATCH, 0xffff0000);
                                intent.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_NORMAL, 0xff00ff00);
                                intent.putExtra(ISCardScanActivity.EXTRA_KEY_APP_KEY, Constant.OCR_KEY);
                                intent.putExtra(ISCardScanActivity.EXTRA_KEY_TIPS, "请将身份证放在相框内识别");
                                intent.putExtra(ISCardScanActivity.EXTRA_KEY_COMPLETECARD_IMAGE, IDCardScanSDK.OPEN_COMOLETE_CHECK);
                                startActivityForResult(intent, RESULTCODE_ID);
                            } else {
                                SingleNewActivity.this.showAlertDialog("相机权限被拒绝");
                            }
                        }
                    }
                });

                break;
            case BusEvent.SHOW_OCR_XD:

                PermissionUtils.getInstance().requestPermission(this, PermissionUtils.CODE_CAMERA, new PermissionUtils.PermissionGrant() {
                    @Override
                    public void onPermissionGranted(int requestCode, boolean isSuccess) {
                        if (requestCode == PermissionUtils.CODE_CAMERA) {
                            if (isSuccess) {
                                Intent xd = new Intent(SingleNewActivity.this, com.intsig.vlcardscansdk.ISCardScanActivity.class);
                                xd.putExtra(ISCardScanActivity.EXTRA_KEY_IMAGE_FOLDER, Files.getStorageDirectory());//
                                xd.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_MATCH, 0xffff0000);
                                xd.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_NORMAL, 0xff00ff00);
                                xd.putExtra(ISCardScanActivity.EXTRA_KEY_APP_KEY, Constant.OCR_KEY);
                                xd.putExtra(ISCardScanActivity.EXTRA_KEY_TIPS, "请将行驶证放在相框内识别");
                                xd.putExtra(ISCardScanActivity.EXTRA_KEY_COMPLETECARD_IMAGE, IDCardScanSDK.OPEN_COMOLETE_CHECK);
                                startActivityForResult(xd, RESULTCODE_XD);
                            } else {
                                SingleNewActivity.this.showAlertDialog("相机权限被拒绝");
                            }
                        }
                    }
                });

                break;
//            case BusEvent.LOAD_VIN:
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("VIN", event.para);
//                P.getJingZhenGuM(JingZhenGuP.OPERATE_VIN, map);
//                break;
            case BusEvent.STARTIMAGESELECTOR:
                if (null != event.imageItem) {
                    mStype = event.imageItem.stype;
                    mPosition = event.imageItem.position;
                    currentItem = event.imageItem.currentItem;
                    startLmageselector(event.imageItem.num);
                }
                break;
            case BusEvent.STARTCAMERA:
                if (null != event.imageItem) {
                    mStype = event.imageItem.stype;
                    mPosition = event.imageItem.position;
                    currentItem = event.imageItem.currentItem;
                    PermissionUtils.getInstance().requestPermission(this, PermissionUtils.CODE_CAMERA, permissionGrant);
                }
                break;
            case BusEvent.IMAGE_PREVIEW:
                if (event.imageItem != null) {
                    showImageDetail(event.imageItem);
                }
                break;
            case BusEvent.SINGLE_SCCESS:
                showSingleSccessDial();
                break;
            case BusEvent.SINGLE_BACK:
                fragmentBack(currentTAG);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            fragmentBack(currentTAG);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void fragmentBack(String tag) {
        if (tag.equals(TAG_CUSTOMERINFO)) {
//            if (isEdit) {
            this.finish();
//            } else {
//                currentTAG = TAG_SINGLE_HOME;
//                sigle.setVisibility(View.GONE);
//                switchContent(mCIF, mSHF, TAG_SINGLE_HOME);
//            }
        } else if (tag.equals(TAG_SINGLE_HOME)) {
            this.finish();
        } else if (tag.equals(TAG_IMAGEINFO_NEW)) {
            currentTAG = TAG_SUPPLEMENTARY;
            singleBackground.setImageResource(R.mipmap.single_new_supplementary);
            switchContent(mIIF, mSF, TAG_SUPPLEMENTARY);
            isShowTR(true);
            setTR("保存");
        } else if (tag.equals(TAG_SUPPLEMENTARY)) {
            currentTAG = TAG_USERINFO_NEW;
            singleBackground.setImageResource(R.mipmap.single_new_user);
            switchContent(mSF, mUIF, TAG_USERINFO_NEW);
        } else if (tag.equals(TAG_ASSESSMENT_RESULT)) {
            sigle.setVisibility(View.GONE);
            currentTAG = TAG_CUSTOMERINFO;
            switchContent(mARF, mCIF, TAG_CUSTOMERINFO);
        } else if (tag.equals(TAG_USERINFO_NEW)) {
            currentTAG = TAG_ASSESSMENT_RESULT;
            sigle.setVisibility(View.GONE);
            switchContent(mUIF, mARF, TAG_ASSESSMENT_RESULT);
            isShowTR(false);
        } else {
            this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULTCODE_ID && resultCode != 0) {
            ResultData resultData = (ResultData) data.getSerializableExtra(ISCardScanActivity.EXTRA_KEY_RESULT_DATA);
            if (resultData != null) {
                if (resultData.isFront()) {
                    String name = resultData.getName(); //身份证名字
                    String sex = resultData.getSex();//身份证性别
                    String national = resultData.getNational();//身份证民族
                    String birthday = resultData.getBirthday();//省份证出生日期
                    String address = resultData.getAddress();//身份证地址
                    String id = resultData.getId();//身份证ID
//                    Tools.makeText("name = " + name
//                            + "\" sex = " + sex
//                            + "\" national = " + national
//                            + "\" birthday = " + birthday
//                            + "\" address = " + address
//                            + "\" id = " + id
//                    );
                    baseInfoBean.setIdCard(id);
                    baseInfoBean.setName(name);
                    baseInfoBean.setSexValue(sex);
                    baseInfoBean.setHomePlaceDetail(address);
                    if (mCIF != null) {
                        mCIF.setOCRID(name, id);
                    }
                } else {
                    String issueauthority = resultData.getIssueauthority();//身份证签发机关
                    String validity = resultData.getValidity();//身份证有效日期
//                    Tools.makeText("issueauthority = " + issueauthority
//                            + "\" validity = " + validity
//                    );
                    baseInfoBean.setIdCardValidityDates(validity);
                }
            }

        } else if (requestCode == RESULTCODE_XD && resultCode != 0) {
            com.intsig.vlcardscansdk.ResultData resultData = (com.intsig.vlcardscansdk.ResultData) data.getSerializableExtra(com.intsig.vlcardscansdk.ISCardScanActivity.EXTRA_KEY_RESULT_DATA);
            if (resultData != null) {
                String plateNo = resultData.getPlateNo();//行驶证号牌号码
                String type = resultData.getType();//行驶证车辆类型
                String owner = resultData.getOwner();//行驶证所有人
                String address = resultData.getAddress();//行驶证地址
                String usecharacter = resultData.getUseCharacter();//行驶证使用性质
                String model = resultData.getModel();//行驶证品牌型号
                String vin = resultData.getVin();//行驶证车辆识别代号
                String engineNo = resultData.getEngineNo();//行驶证发动机号
                String registerDate = resultData.getRegisterDate();//行驶证注册日期
                String issueDate = resultData.getIssueDate();//行驶证发证日期

//                Tools.makeText("plateNo = " + plateNo
//                        + "\" type = " + type
//                        + "\" owner = " + owner
//                        + "\" address = " + address
//                        + "\" usecharacter = " + usecharacter
//                        + "\" model = " + model
//                        + "\" vin = " + vin
//                        + "\" engineNo = " + engineNo
//                        + "\" registerDate = " + registerDate
//                        + "\" issueDate = " + issueDate
//                );
                baseInfoBean.setCarFrameNo(vin);
                baseInfoBean.setLicencePlate(plateNo);
                baseInfoBean.setCarModel(model);
                baseInfoBean.setCarEngineNo(engineNo);
                baseInfoBean.setCertificateDate(issueDate);
                baseInfoBean.setRegisterDate(registerDate);
                if (mCIF != null) {
                    mCIF.setOCRXD(vin, registerDate);
                }
//                EventBus.getDefault().post(new BusEvent(BusEvent.LOAD_VIN, vin));
            }
        } else if (requestCode == STARTCAMERA && resultCode == -1) {
            if (UriTools.mImageFileUri != null) {
                ImageItem imageItem = new ImageItem();
                imageItem.position = mPosition;
                imageItem.stype = mStype;
                imageItem.currentItem = currentItem;
                imageItem.url = UriTools.mImageFileUri.getPath();
                EventBus.getDefault().post(new BusEvent(BusEvent.IMAGEINFO_SHOWCAMERA, imageItem));
            }
        } else if (requestCode == STARTLMAGESELECTOR && data != null) {
            ImageItem imageItem = new ImageItem();
            imageItem.position = mPosition;
            imageItem.stype = mStype;
            imageItem.currentItem = currentItem;
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            imageItem.list = pathList;
            EventBus.getDefault().post(new BusEvent(BusEvent.IMAGEINFO_SHOWIMAGESELECTOR, imageItem));
        } else if (requestCode == PhotoPreview.REQUEST_CODE) {
            ArrayList<String> list = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            if (list != null && !list.isEmpty() && list.size() > 0) {
                String key = data.getStringExtra(PhotoPreview.EXTRA_KEY);
                int position = data.getIntExtra(PhotoPreview.EXTRA_POSITION, -1);
                ImageItem item = new ImageItem();
                item.list = list;
                item.stype = key;
                item.position = position;
                EventBus.getDefault().post(new BusEvent(BusEvent.IMAGE_PREVIEW_DELETE, item));
            }
        }

    }


    @Override
    public void onPrompt(int type, String message) {
        Tools.makeText(message);
    }

    @Override
    public void showDialog(int type, String title, String message) {
        showProgressDialog(message);
    }

    @Override
    public void colseDialog() {
        dismiss();
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

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请选择车型");
            builder.setItems(cities, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Tools.makeText("which = " + which);
                    if (mCIF != null) {
                        VIN vin = list.get(which);
                        String ModelName = vin.getMakeName();
                        String StyleYear = vin.getStyleYear();
                        String StyleName = vin.getStyleName();
//                        String make = ModelName + StyleYear + "_" + StyleName;
                        String StyleId = vin.getStyleId();
                        mCIF.setMake(ModelName, StyleYear, StyleName, StyleId);
                    }
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

    }


    private void startCamera() {
        UriTools.mImageFileUri = UriTools.getOutFileUri(UriTools.TYPE_FILE_IMAGE);//得到一个File Uri
        if (UriTools.mImageFileUri != null) {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, UriTools.mImageFileUri);
            this.startActivityForResult(intent, STARTCAMERA);
        } else {
            Tools.makeText("请假插入SD卡！");
        }
    }

    private void startLmageselector(int num) {
        config = new ImgSelConfig.Builder(this, imageLoader)
                // 是否多选, 默认true
//                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
//                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.parseColor("#333333"))
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#FFFFFF"))
                // 返回图标ResId
                .backResId(R.mipmap.back)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.parseColor("#333333"))
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#FFFFFF"))
                // 裁剪大小。needCrop为true的时候配置
//                .cropSize(1, 1, 200, 200)
//                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(false)
                // 最大选择图片数量，默认9
                .maxNum(num)
                .build();

// 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, STARTLMAGESELECTOR);
    }

    private void showImageDetail(ImageItem item) {
        PhotoPreview.builder()
                .setPhotos((ArrayList<String>) item.list)
                .setAction(1)
                .setCurrentItem(item.currentItem)
                .setShowActiononly()
                .setKey(item.stype)
                .setPosition(item.position)
                .isShowDelete(item.isShowDelete)
                .start(this);
    }

    /**
     * 回调获取授权结果，判断是否授权
     * 权限开启结果检查 grantResults[0]==0=PackageManager.PERMISSION_GRANTED的话 权限开启成功
     * 权限开启结果检查 grantResults[0]==-1=PackageManager.PERMISSION_DENIED 权限开启失败
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length > 0) {
            PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults);
        }
    }

    private void showSingleSccessDial() {
        View view = View.inflate(this, R.layout.single_sccess, null);
        final Dialog mDialog = new AlertDialog.Builder(this).setCancelable(false).setView(view).show();
        view.findViewById(R.id.single_sccess_).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();

                if (isEdit) {
                    EventBus.getDefault().post(new BusEvent(BusEvent.ORDER_DEFAULT_FINSH));
                } else {
                    Intent intent = new Intent(SingleNewActivity.this, OrderActivity.class);
                    SingleNewActivity.this.startActivity(intent);
                }

                SingleNewActivity.this.finish();
            }
        });


    }
}
