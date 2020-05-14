package com.yyy.wrsf.beans;


import java.math.BigDecimal;
import java.util.Date;

public class PaymentB  {

    private String id;
    /**
     * 订单编号
     */
    private String contractNo;
    /**
     * 订单支付编号
     */
    private String paymentNum;
    /**
     * 支付方式
     */
    private String payType;
    /**
     * 支付金额
     */
    private BigDecimal payment;
    /**
     * 支付时间
     */
    private String payDate;
    /**
     * 付款二维码
     */
    private String qrCode;
    /**
     * 付款二维码有效时长
     */
    private String qrCodeValid;
    /**
     * 支付交易号
     */
    private String payNum;
    /**
     * 支付状态
     */
    private Integer payStatus;
    /**
     * 创建人
     */
    private Integer creator;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改时间
     */
    private String modifyDate;
    /**
     * 状态  0，删除 1，正常
     */
    private Integer isValid;

    /**
     * 第三方手续费
     */
    private BigDecimal sxFee;

    private String expireTime;

    /**
     * 商品名称
     */
    private String goodsName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPaymentNum() {
        return paymentNum;
    }

    public void setPaymentNum(String paymentNum) {
        this.paymentNum = paymentNum;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCodeValid() {
        return qrCodeValid;
    }

    public void setQrCodeValid(String qrCodeValid) {
        this.qrCodeValid = qrCodeValid;
    }

    public String getPayNum() {
        return payNum;
    }

    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public BigDecimal getSxFee() {
        return sxFee;
    }

    public void setSxFee(BigDecimal sxFee) {
        this.sxFee = sxFee;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}

