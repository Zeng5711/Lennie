package com.hxyd.dyt.widget.adapter.accountManager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.ImageInfoBean;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.widget.holder.accountManager.FinalproposalViewHolder;
import com.hxyd.dyt.widget.holder.accountManager.ImageInfoOrderViewHolder;
import com.hxyd.dyt.widget.holder.accountManager.LoanInfoViewHolder;
import com.hxyd.dyt.widget.holder.accountManager.UserInfoViewHolder;
import com.hxyd.dyt.widget.holder.accountManager.VehicleInfoViewHolder;

import java.util.List;

/**
 * Created by win7 on 2017/3/14.
 */

public class OrderDefaultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM_TYPE_FINALPROPOSA = 0;
    private static final int ITEM_TYPE_USERINFO = 1;
    private static final int ITEM_TYPE_LOANINFO = 2;
    private static final int ITEM_TYPE_VEHICLEINFO = 3;
    private static final int ITEM_TYPE_IMAGEINFO = 4;


    private Context mContext;
    private OrderDefultInfo mOD;
    private int viewNum = 4;

    private int id = 0;
    private int dd = 0;
    private int jd = 0;
    private int xd = 0;
    private int od = 0;
    private int vd = 0;

    public OrderDefaultAdapter(Context context, OrderDefultInfo orderDefultInfo) {
        mContext = context;
        this.mOD = orderDefultInfo;
    }

    public void setOrderDefultInfo(OrderDefultInfo orderDefultInfo) {
        id = 0;
        dd = 0;
        jd = 0;
        xd = 0;
        od = 0;
        vd = 0;
        this.mOD = orderDefultInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view;
        switch (viewType) {
            case ITEM_TYPE_USERINFO:
                view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
                viewHolder = new UserInfoViewHolder(view);
                break;
            case ITEM_TYPE_LOANINFO:
                view = LayoutInflater.from(mContext).inflate(R.layout.loan_item, parent, false);
                viewHolder = new LoanInfoViewHolder(view);
                break;
            case ITEM_TYPE_VEHICLEINFO:
                view = LayoutInflater.from(mContext).inflate(R.layout.vehicle_item, parent, false);
                viewHolder = new VehicleInfoViewHolder(view);
                break;
            case ITEM_TYPE_IMAGEINFO:
                view = LayoutInflater.from(mContext).inflate(R.layout.image_item_order, parent, false);
                viewHolder = new ImageInfoOrderViewHolder(view);
                break;
            case ITEM_TYPE_FINALPROPOSA:
                view = LayoutInflater.from(mContext).inflate(R.layout.final_proposal_item, parent, false);
                viewHolder = new FinalproposalViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (mOD == null) {
            return;
        }

        if (holder instanceof UserInfoViewHolder) {
            //mOD
            ((UserInfoViewHolder) holder).name.setText(mOD.getBaseInfo().getName());

            String id = mOD.getBaseInfo().getIdCard();
            if (!TextUtils.isEmpty(id)) {
                if (id.length() > 15) {
                    id = id.substring(0, 6) + "********" + id.substring(id.length() - 4, id.length());
                } else if (id.length() == 15) {
                    id = id.substring(0, 6) + "*****" + id.substring(id.length() - 4, id.length());
                }
            }

            ((UserInfoViewHolder) holder).ID.setText(id);
            ((UserInfoViewHolder) holder).sex.setText(mOD.getBaseInfo().getSexValue());
//            ((UserInfoViewHolder) holder).marriage.setText(mOD.getBaseInfo().getMarriageValue());
//            ((UserInfoViewHolder) holder).education.setText(mOD.getBaseInfo().getEducationValue());
            ((UserInfoViewHolder) holder).phone.setText(mOD.getBaseInfo().getPhone());
//            String livingPP = mOD.getBaseInfo().getLivingPlaceProvinceValue();
//            String livingPC = mOD.getBaseInfo().getLivingPlaceCityValue();
//            String livingPD = mOD.getBaseInfo().getLivingPlaceDistrictValue();
//            String living = "";
//            if (livingPP.equals(livingPC)) {
//                living = livingPC + livingPD;
//            } else {
//                living = livingPP + livingPC + livingPD;
//            }

//            ((UserInfoViewHolder) holder).livingPlaceDistrict.setText(living);
//            ((UserInfoViewHolder) holder).companyName.setText(mOD.getBaseInfo().getCompanyName());
//            ((UserInfoViewHolder) holder).companyPhone.setText(mOD.getBaseInfo().getCompanyPhone());
//            String companyPP = mOD.getBaseInfo().getCompanyPlaceProvinceValue();
//            String companyPC = mOD.getBaseInfo().getCompanyPlaceCityValue();
//            String companyPD = mOD.getBaseInfo().getCompanyPlaceDistrictValue();
//            String company = "";
//            if (companyPP.equals(companyPC)) {
//                company = companyPC + companyPD;
//            } else {
//                company = companyPP + companyPC + companyPD;
//            }
//
//            ((UserInfoViewHolder) holder).companyPlaceDistrict.setText(company);

        } else if (holder instanceof LoanInfoViewHolder) {
            ((LoanInfoViewHolder) holder).loanPurpose.setText(TextUtils.isEmpty(mOD.getBaseInfo().getLoanPurposeValue()) ? "" : mOD.getBaseInfo().getLoanPurposeValue().trim().equals("null") ? "" : mOD.getBaseInfo().getLoanPurposeValue().trim());
            ((LoanInfoViewHolder) holder).productTypes.setText(TextUtils.isEmpty(mOD.getBaseInfo().getProductTypesValue()) ? "" : mOD.getBaseInfo().getProductTypesValue().trim().equals("null") ? "" : mOD.getBaseInfo().getProductTypesValue().trim());
            ((LoanInfoViewHolder) holder).repaymentMethods.setText(TextUtils.isEmpty(mOD.getBaseInfo().getRepaymentMethodsValue()) ? "" : mOD.getBaseInfo().getRepaymentMethodsValue().trim().equals("null") ? "" : mOD.getBaseInfo().getRepaymentMethodsValue().trim());

            String loanMount = TextUtils.isEmpty(mOD.getBaseInfo().getLoanMount()) ? "" : mOD.getBaseInfo().getLoanMount().trim().equals("null") ? "" : mOD.getBaseInfo().getLoanMount().trim();
//            if (loanMount.equals("0.0")) {
//                loanMount = "0";
//            }
            ((LoanInfoViewHolder) holder).loanMount.setText(loanMount);
            ((LoanInfoViewHolder) holder).repaymentPeriodhods.setText(TextUtils.isEmpty(mOD.getBaseInfo().getRepaymentPeriodhods().trim()) ? "" : mOD.getBaseInfo().getRepaymentPeriodhods().trim().equals("null") ? "" : mOD.getBaseInfo().getRepaymentPeriodhods().trim() + "个月");
        } else if (holder instanceof VehicleInfoViewHolder) {
            String assess = mOD.getBaseInfo().getAssess();
//            if (!TextUtils.isEmpty(assess) && assess.equals("0.0")) {
//                assess = "0";
//            }
//            ((VehicleInfoViewHolder) holder).assess.setText(assess);
            ((VehicleInfoViewHolder) holder).brand.setText(mOD.getBaseInfo().getBrand());
            ((VehicleInfoViewHolder) holder).carFrameNo.setText(mOD.getBaseInfo().getCarFrameNo());
//            ((VehicleInfoViewHolder) holder).carModel.setText(mOD.getBaseInfo().getCarModel());
            ((VehicleInfoViewHolder) holder).licencePlate.setText(mOD.getBaseInfo().getLicencePlate());
            String mileage = mOD.getBaseInfo().getMileage();
//            if (!TextUtils.isEmpty(mileage) && mileage.equals("0.0")) {
//                mileage = "0";
//            }
            ((VehicleInfoViewHolder) holder).mileage.setText(mileage);
//            ((VehicleInfoViewHolder) holder).produceDate.setText(mOD.getBaseInfo().getProduceDate() + "");
            String evaluationPrice = mOD.getBaseInfo().getValuationPrice();
//            if (!TextUtils.isEmpty(evaluationPrice) && evaluationPrice.equals("0.0")) {
//                evaluationPrice = "0";
//            }
            ((VehicleInfoViewHolder) holder).evaluationPrice.setText(evaluationPrice);
//            ((VehicleInfoViewHolder) holder).surfaceConditionDescription.setText(mOD.getBaseInfo().getSurfaceConditionDescription());

            ((VehicleInfoViewHolder) holder).carEngineNo.setText(mOD.getBaseInfo().getCarEngineNo());
            ((VehicleInfoViewHolder) holder).carColor.setText(mOD.getBaseInfo().getCarColor());
//            ((VehicleInfoViewHolder) holder).carFuel.setText(mOD.getBaseInfo().getCarFuel());
//            ((VehicleInfoViewHolder) holder).certificateDate.setText(mOD.getBaseInfo().getCertificateDate());
//            ((VehicleInfoViewHolder) holder).registerDate.setText(mOD.getBaseInfo().getRegisterDate());

        } else if (holder instanceof ImageInfoOrderViewHolder) {
            ImageInfoAdpter imageInfoAdpter = new ImageInfoAdpter(mContext, false);
            ((ImageInfoOrderViewHolder) holder).recyclerView.setAdapter(imageInfoAdpter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            ((ImageInfoOrderViewHolder) holder).recyclerView.setLayoutManager(linearLayoutManager);
            setImageInfoBean(mOD.getImageInfo(), imageInfoAdpter);
        } else if (holder instanceof FinalproposalViewHolder) {
            String audiAmount = mOD.getBaseInfo().getAudiAmount();
//            if (!TextUtils.isEmpty(audiAmount) && audiAmount.equals("0.0")) {
//                audiAmount = "0";
//            }
            ((FinalproposalViewHolder) holder).audiAmount.setText(audiAmount);
            ((FinalproposalViewHolder) holder).audiProduct.setText(mOD.getBaseInfo().getAudiProduct());

            String audiTerm = mOD.getBaseInfo().getAudiTerm();
//            if (!TextUtils.isEmpty(audiTerm) && audiTerm.equals("0.0")) {
//                audiTerm = "0";
//            }
            ((FinalproposalViewHolder) holder).audiTerm.setText(audiTerm);
        }
    }

    public void setImageInfoBean(List<ImageInfoBean> o, ImageInfoAdpter mAdpter) {
        for (ImageInfoBean infoBean : o) {
            String category = infoBean.getCategory();
            String str[] = category.split("_");
            String s = str[str.length - 1];
            int type = -1;
            if (s.equals("ID")) {
                type = ImageInfoAdpter.id;
            } else if (s.equals("RD")) {
                type = ImageInfoAdpter.dd;
            } else if (s.equals("DL")) {
                type = ImageInfoAdpter.jd;
            } else if (s.equals("DRL")) {
                type = ImageInfoAdpter.xd;
            } else if (s.equals("OI")) {
                type = ImageInfoAdpter.od;
            } else if (s.equals("VI")) {
                type = ImageInfoAdpter.vd;
            }
            mAdpter.setData(type, Constant.BASE_URL + infoBean.getOriginalUrl());
        }
        mAdpter.refreshState();
    }


    @Override
    public int getItemCount() {
        return viewNum;
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        switch (position % viewNum) {
            case 0:
                type = ITEM_TYPE_FINALPROPOSA;
                break;
            case 1:
                type = ITEM_TYPE_LOANINFO;
                break;
            case 2:
                type = ITEM_TYPE_USERINFO;
                break;
            case 3:
                type = ITEM_TYPE_VEHICLEINFO;
                break;
            case 4:
                type = ITEM_TYPE_IMAGEINFO;
                break;
        }
        return type;
    }
}
