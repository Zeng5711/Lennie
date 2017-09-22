package com.hxyd.dyt.common.entity;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by win7 on 2017/3/10.
 */

public class BaseData extends RealmObject{

    /**
     * code : 0
     * msg : 操作成功
     * data : {"sex":[{"value":"男","code":"1"},{"value":"女","code":"2"}],"loan_purpose":[{"value":"资金周转","code":"1"},{"value":"个人消费","code":"2"},{"value":"房屋装修","code":"3"},{"value":"培训教育","code":"5"},{"value":"其他","code":"6"}],"loan_status":[{"value":"草稿","code":"1"},{"value":"申请中","code":"2"},{"value":"审核中","code":"3"},{"value":"放款成功","code":"4"}],"image_category":[{"value":"身份证","code":"39_1"},{"value":"登记证","code":"30_2"},{"value":"驾驶证","code":"30_3"},{"value":"行驶证","code":"30_4"},{"value":"车辆图片","code":"31_5"},{"value":"其他资料","code":"28_6"}],"repayment_methods":[{"value":"先息后本","code":"1"},{"value":"等本等息","code":"2"}],"housing_Type":[{"value":"商业按揭房","code":"1"},{"value":"无按揭购房","code":"2"},{"value":"公积金按揭购房","code":"3"},{"value":"自建房","code":"4"},{"value":"租用","code":"5"},{"value":"亲属住房","code":"6"},{"value":"单位住房","code":"7"}],"marriage":[{"value":"已婚","code":"1"},{"value":"未婚","code":"2"},{"value":"离异","code":"3"},{"value":"丧偶","code":"4"}],"education":[{"value":"硕士或以上","code":"1"},{"value":"本科","code":"2"},{"value":"大专","code":"3"},{"value":"高中","code":"4"},{"value":"初中","code":"5"},{"value":"小学","code":"6"},{"value":"其他","code":"7"}],"product_types":[{"value":"全款押车","code":"1"},{"value":"全款不押车","code":"2"},{"value":"按揭押车","code":"3"}]}
     */
//    @PrimaryKey
    private long primary;
    private String version="";
    private RealmList<SexBean> sex; //性别
    private RealmList<LoanPurposeBean> loan_purpose;//借款用途
    private RealmList<LoanStatusBean> loan_status;//状态
    private RealmList<ImageCategoryBean> image_category;//
    private RealmList<RepaymentMethodsBean> repayment_methods;//还款方式
    private RealmList<HousingTypeBean> housing_Type;//房产类型
    private RealmList<MarriageBean> marriage;//婚姻状况
    private RealmList<EducationBean> education;//学历
    private RealmList<ProductTypesBean> product_types;//产品类型
    private RealmList<RepaymentPeriodhodsBean> repayment_periodhods;//借款周期
    private RealmList<CustomerRelationBean> customer_relation;//客户关系

    public long getPrimary() {
        return primary;
    }

    public void setPrimary(long primary) {
        this.primary = primary;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public RealmList<CustomerRelationBean> getCustomer_relation() {
        return customer_relation;
    }

    public void setCustomer_relation(RealmList<CustomerRelationBean> customer_relation) {
        this.customer_relation = customer_relation;
    }

    public RealmList<RepaymentPeriodhodsBean> getRepayment_periodhods() {
        return repayment_periodhods;
    }

    public void setRepayment_periodhods(RealmList<RepaymentPeriodhodsBean> repayment_periodhods) {
        this.repayment_periodhods = repayment_periodhods;
    }

    public RealmList<SexBean> getSex() {
        return sex;
    }

    public void setSex(RealmList<SexBean> sex) {
        this.sex = sex;
    }

    public RealmList<LoanPurposeBean> getLoan_purpose() {
        return loan_purpose;
    }

    public void setLoan_purpose(RealmList<LoanPurposeBean> loan_purpose) {
        this.loan_purpose = loan_purpose;
    }

    public RealmList<LoanStatusBean> getLoan_status() {
        return loan_status;
    }

    public void setLoan_status(RealmList<LoanStatusBean> loan_status) {
        this.loan_status = loan_status;
    }

    public RealmList<ImageCategoryBean> getImage_category() {
        return image_category;
    }

    public void setImage_category(RealmList<ImageCategoryBean> image_category) {
        this.image_category = image_category;
    }

    public RealmList<RepaymentMethodsBean> getRepayment_methods() {
        return repayment_methods;
    }

    public void setRepayment_methods(RealmList<RepaymentMethodsBean> repayment_methods) {
        this.repayment_methods = repayment_methods;
    }

    public RealmList<HousingTypeBean> getHousing_Type() {
        return housing_Type;
    }

    public void setHousing_Type(RealmList<HousingTypeBean> housing_Type) {
        this.housing_Type = housing_Type;
    }

    public RealmList<MarriageBean> getMarriage() {
        return marriage;
    }

    public void setMarriage(RealmList<MarriageBean> marriage) {
        this.marriage = marriage;
    }

    public RealmList<EducationBean> getEducation() {
        return education;
    }

    public void setEducation(RealmList<EducationBean> education) {
        this.education = education;
    }

    public RealmList<ProductTypesBean> getProduct_types() {
        return product_types;
    }

    public void setProduct_types(RealmList<ProductTypesBean> product_types) {
        this.product_types = product_types;
    }


}
