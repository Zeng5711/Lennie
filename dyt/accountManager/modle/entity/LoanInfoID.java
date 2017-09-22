package com.hxyd.dyt.accountManager.modle.entity;

/**
 * Created by win7 on 2017/3/15.
 */

public class LoanInfoID {


    /**
     * loanInfoId : 892
     * message : 建议信息
     *  valuationPriceReall : 精真估返回的金额，单位万
     * valuationPrice : 评估价页面显示用万（精真估的80%）
     */

    private String loanInfoId;
    private String message;
    private String valuationPriceReall;
    private String valuationPrice;
    private String audiValuationPrice;

    public String getAudiValuationPrice() {
        return audiValuationPrice;
    }

    public void setAudiValuationPrice(String audiValuationPrice) {
        this.audiValuationPrice = audiValuationPrice;
    }

    public String getLoanInfoId() {
        return loanInfoId;
    }

    public void setLoanInfoId(String loanInfoId) {
        this.loanInfoId = loanInfoId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getValuationPriceReall() {
        return valuationPriceReall;
    }

    public void setValuationPriceReall(String valuationPriceReall) {
        this.valuationPriceReall = valuationPriceReall;
    }

    public String getValuationPrice() {
        return valuationPrice;
    }

    public void setValuationPrice(String valuationPrice) {
        this.valuationPrice = valuationPrice;
    }
}
