package com.hxyd.dyt.accountManager.view;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;

import com.hxyd.dyt.R;

import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.Bitmaps;
import com.hxyd.dyt.common.uitl.Files;
import com.hxyd.dyt.common.uitl.Tools;
import com.intsig.idcardscan.sdk.IDCardScanSDK;
import com.intsig.idcardscan.sdk.ISCardScanActivity;
import com.intsig.idcardscan.sdk.ResultData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class testActivity extends Activity {

    static final int RESULTCODE_ID = 1009;
    static final int RESULTCODE_JD = 1008;
    static final int RESULTCODE_XD = 1007;

    @BindView(R.id.et_name)
    EditText mName;
    @BindView(R.id.et_phone)
    EditText mPhone;
    @BindView(R.id.im_image)
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

//        JingZhenGuPI P = new JingZhenGuP();
//
//        P.getJingZhenGuM(JingZhenGuP.OPERATE_MAKE,new HashMap<String,Object>());

    }

    @OnClick({R.id.id, R.id.jd, R.id.xd, R.id.bt_generate})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.id:
                Intent intent = new Intent(this, ISCardScanActivity.class);
                intent.putExtra(ISCardScanActivity.EXTRA_KEY_IMAGE_FOLDER, Files.getStorageDirectory());//
                intent.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_MATCH, 0xffff0000);
                intent.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_NORMAL, 0xff00ff00);
                intent.putExtra(ISCardScanActivity.EXTRA_KEY_APP_KEY, Constant.OCR_KEY);
                intent.putExtra(ISCardScanActivity.EXTRA_KEY_TIPS, "请将身份证放在相框内识别");
                intent.putExtra(ISCardScanActivity.EXTRA_KEY_COMPLETECARD_IMAGE, IDCardScanSDK.OPEN_COMOLETE_CHECK);
                startActivityForResult(intent, RESULTCODE_ID);
                break;
            case R.id.jd:
//                Intent jd = new Intent(this, com.intsig.dlcardscansdk.ISCardScanActivity.class);
//                jd.putExtra(ISCardScanActivity.EXTRA_KEY_IMAGE_FOLDER, Files.getStorageDirectory());//
//                jd.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_MATCH, 0xffff0000);
//                jd.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_NORMAL, 0xff00ff00);
//                jd.putExtra(ISCardScanActivity.EXTRA_KEY_APP_KEY, Constant.OCR_KEY);
//                jd.putExtra(ISCardScanActivity.EXTRA_KEY_TIPS, "请将驾驶证放在相框内识别");
//                jd.putExtra(ISCardScanActivity.EXTRA_KEY_COMPLETECARD_IMAGE, IDCardScanSDK.OPEN_COMOLETE_CHECK);
//                startActivityForResult(jd, RESULTCODE_JD);
                break;
            case R.id.xd:
                Intent xd = new Intent(this, com.intsig.vlcardscansdk.ISCardScanActivity.class);
                xd.putExtra(ISCardScanActivity.EXTRA_KEY_IMAGE_FOLDER, Files.getStorageDirectory());//
                xd.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_MATCH, 0xffff0000);
                xd.putExtra(ISCardScanActivity.EXTRA_KEY_COLOR_NORMAL, 0xff00ff00);
                xd.putExtra(ISCardScanActivity.EXTRA_KEY_APP_KEY, Constant.OCR_KEY);
                xd.putExtra(ISCardScanActivity.EXTRA_KEY_TIPS, "请将行驶证放在相框内识别");
                xd.putExtra(ISCardScanActivity.EXTRA_KEY_COMPLETECARD_IMAGE, IDCardScanSDK.OPEN_COMOLETE_CHECK);
                startActivityForResult(xd, RESULTCODE_XD);
                break;
            case R.id.bt_generate:
                String name = mName.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                imageView.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
                imageView.setDrawingCacheEnabled(false);
                Bitmap newBitmap = Bitmaps.drawTextAtBitmap(bitmap,name,80,80,phone,80,120,50);
                imageView.setImageBitmap(newBitmap);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tools.makeText("resultCode = " + resultCode);
        if (requestCode == RESULTCODE_ID && resultCode != 0) {
            ResultData resultData = (ResultData) data.getSerializableExtra(ISCardScanActivity.EXTRA_KEY_RESULT_DATA);
            if (resultData != null) {
                int complete = resultData.isComplete();
//                if (complete == ResultData.IMAGE_QUALITY_OK) {
                if (resultData.isFront()) {
                    String name = resultData.getName(); //身份证名字
                    String sex = resultData.getSex();//身份证性别
                    String national = resultData.getNational();//身份证民族
                    String birthday = resultData.getBirthday();//省份证出生日期
                    String address = resultData.getAddress();//身份证地址
                    String id = resultData.getId();//身份证ID
                    Tools.makeText("name = " + name
                            + "\" sex = " + sex
                            + "\" national = " + national
                            + "\" birthday = " + birthday
                            + "\" address = " + address
                            + "\" id = " + id
                    );
                } else {
                    String issueauthority = resultData.getIssueauthority();//身份证签发机关
                    String validity = resultData.getValidity();//身份证有效日期
                    Tools.makeText("issueauthority = " + issueauthority
                            + "\" validity = " + validity
                    );
                }
//                } else if (complete == ResultData.NO_SUPPORT_FEATURE) {
//                    Tools.makeText("不支持完整性判断");
//                } else if (complete == ResultData.IMAGE_SHIELD) {
//                    Tools.makeText("图像有遮挡");
//                } else if (complete == ResultData.IMAGE_AVATAR_REFLECTIVE) {
//                    Tools.makeText("身份证正面人物头像有反光");
//                } else if (complete == ResultData.IMAGE_AVATAR_SHIELD) {
//                    Tools.makeText("身份证正面人物头像有遮挡");
//                } else if (complete == ResultData.IMAGE_EMBLEM_SHIELD) {
//                    Tools.makeText("身份证反面国徽有遮挡");
//                }
            }

        } else if (requestCode == RESULTCODE_JD && resultCode != 0) {
//            com.intsig.dlcardscansdk.ResultData resultData = (com.intsig.dlcardscansdk.ResultData) data.getSerializableExtra(com.intsig.dlcardscansdk.ISCardScanActivity.EXTRA_KEY_RESULT_DATA);
//            if (resultData != null) {
//                String name = resultData.getName();//驾驶证名字
//                String sex = resultData.getSex();//驾驶证性别
//                String national = resultData.getNational();//驾驶证种族
//                String birthday = resultData.getBirthday();//驾驶证出生日期
//                String address = resultData.getAddress();//驾驶证地址
//                String id = resultData.getId();//驾驶证ID
//                String drivetypr = resultData.getDrivetype();//驾驶证准假车型
//                String validity = resultData.getValidity();//驾驶证有效期限
//                String validfor = resultData.getValidfor();//驾驶证有效结束日期
//                String validform = resultData.getValidfrom();//驾驶证有效起始日期
//                String issuedate = resultData.getIssuedate();//驾驶证初次领证日期
//                Tools.makeText("name = " + name
//                        + "\" sex = " + sex
//                        + "\" national = " + national
//                        + "\" birthday = " + birthday
//                        + "\" address = " + address
//                        + "\" id = " + id
//                        + "\" validity = " + validity
//                        + "\" validfor = " + validfor
//                        + "\" validform = " + validform
//                        + "\" issuedate = " + issuedate
//                        + "\" drivetypr = " + drivetypr
//
//                );
//            }
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

                Tools.makeText("plateNo = " + plateNo
                        + "\" type = " + type
                        + "\" owner = " + owner
                        + "\" address = " + address
                        + "\" usecharacter = " + usecharacter
                        + "\" model = " + model
                        + "\" vin = " + vin
                        + "\" engineNo = " + engineNo
                        + "\" registerDate = " + registerDate
                        + "\" issueDate = " + issueDate
                );
            }
        }
    }
}