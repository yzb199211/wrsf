package com.yyy.wrsf.beans;

import android.text.TextUtils;

import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.beans.ship.ShipRegionB;
import com.yyy.wrsf.utils.StringUtil;

public class OrderBean {

    /**
     * baoAsk : 0
     * baoPrice : 0
     * contractConfirmYesno : 0
     * contractDate : 2020-03-27T00:52:26.825Z
     * contractNo : string
     * contractPerson : 0
     * contractStatus : 0
     * contractTotal : 0
     * createDate : 2020-03-27T00:52:26.825Z
     * daiPrice : 0
     * daiTotal : 0
     * deliverType : 0
     * density : 0
     * fixTotal : 0
     * goodsName : string
     * mixTotal : 0
     * noticeYesNo : 0
     * otherTotal : 0
     * packNumber : 0
     * paid : 0
     * payStatus : 0
     * payType : 0
     * picTotal : 0
     * pickDate : 2020-03-27T00:52:26.825Z
     * plantMemberName : string
     * plantMemberRecNo : 0
     * plantMemberTel : string
     * price : 0
     * qianPrice : 0
     * qianType : 0
     * recDetail : string
     * recName : string
     * recRegion : 0
     * recTel : string
     * remark : string
     * sendDetail : string
     * sendName : string
     * sendRegion : 0
     * sendTel : string
     * sendTotal : 0
     * sendType : 0
     * size : 0
     * transCompanyRecNo : 0
     * transCustomerMonthRecNo : 0
     * transRecShopRecNo : 0
     * transShopRecNo : 0
     * transTotal : 0
     * transType : 0
     * unpaid : 0
     * updateDate : 2020-03-27T00:52:26.825Z
     * weight : 0
     */

    private double baoAsk;
    private double baoPrice;
    private int contractConfirmYesno;
    private String companyName;
    private String contractDate;
    private String contractNo;
    private int contractPerson;
    private int contractStatus;
    private double contractTotal;
    private Double discountPrice;
    private String createDate;
    private double daiPrice;
    private double daiTotal;
    private int deliverType;
    private double density;
    private double fixTotal;
    private String goodsName;
    private double mixTotal;
    private int noticeYesNo;
    private double otherTotal;
    private int packNumber;
    private double paid;
    private int payStatus;
    private int payType;
    private double picTotal;
    private String pickDate;
    private String plantMemberName;
    private int plantMemberRecNo;
    private String plantMemberTel;
    private String price;
    private double qianPrice;
    private int qianType;
    private String recDetail;
    private String recName;
    private int recRegion;
    private String recTel;
    private String remark;
    private String sendDetail;
    private String sendName;
    private int sendRegion;
    private String sendTel;
    private double sendTotal;
    private int sendType;
    private String sendAdd;
    private String recAdd;
    private double size;
    private int transCompanyRecNo;
    private int transCustomerMonthRecNo;
    private int transRecShopRecNo;
    private int transShopRecNo;
    private double transTotal;
    private int transType;
    private double unpaid;
    private String updateDate;
    private int weight;
    private String sendAddDetail;
    private String recAddDetail;


    public String getSendAddDetail() {
        return sendAddDetail;
    }

    public void setSendAddDetail(String sendAddDetail) {
        this.sendAddDetail = sendAddDetail;
    }

    public String getRecAddDetail() {
        return recAddDetail;
    }

    public void setRecAddDetail(String recAddDetail) {
        this.recAddDetail = recAddDetail;
    }

    public String getCompanyName() {
        return TextUtils.isEmpty(companyName) ? "" : companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getBaoAsk() {
        return baoAsk;
    }

    public void setBaoAsk(double baoAsk) {
        this.baoAsk = baoAsk;
    }

    public double getBaoPrice() {
        return baoPrice;
    }

    public void setBaoPrice(double baoPrice) {
        this.baoPrice = baoPrice;
    }

    public int getContractConfirmYesno() {
        return contractConfirmYesno;
    }

    public void setContractConfirmYesno(int contractConfirmYesno) {
        this.contractConfirmYesno = contractConfirmYesno;
    }

    public String getContractDate() {
        return TextUtils.isEmpty(contractDate) ? "" : contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public int getContractPerson() {
        return contractPerson;
    }

    public void setContractPerson(int contractPerson) {
        this.contractPerson = contractPerson;
    }

    public int getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(int contractStatus) {
        this.contractStatus = contractStatus;
    }

    public double getContractTotal() {
        return discountPrice!=null?discountPrice:0;
    }
    public double getContractTotalDetail() {
        return contractTotal;
    }

    public void setContractTotal(double contractTotal) {
        this.contractTotal = contractTotal;
    }

    public String getCreateDate() {
        return TextUtils.isEmpty(createDate) ? "" : createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public double getDaiPrice() {
        return daiPrice;
    }

    public void setDaiPrice(double daiPrice) {
        this.daiPrice = daiPrice;
    }

    public double getDaiTotal() {
        return daiTotal;
    }

    public void setDaiTotal(double daiTotal) {
        this.daiTotal = daiTotal;
    }

    public int getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(int deliverType) {
        this.deliverType = deliverType;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getFixTotal() {
        return fixTotal;
    }

    public void setFixTotal(double fixTotal) {
        this.fixTotal = fixTotal;
    }

    public String getGoodsName() {
        return TextUtils.isEmpty(goodsName) ? "" : goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getMixTotal() {
        return mixTotal;
    }

    public void setMixTotal(double mixTotal) {
        this.mixTotal = mixTotal;
    }

    public int getNoticeYesNo() {
        return noticeYesNo;
    }

    public void setNoticeYesNo(int noticeYesNo) {
        this.noticeYesNo = noticeYesNo;
    }

    public double getOtherTotal() {
        return otherTotal;
    }

    public void setOtherTotal(double otherTotal) {
        this.otherTotal = otherTotal;
    }

    public int getPackNumber() {
        return packNumber;
    }

    public void setPackNumber(int packNumber) {
        this.packNumber = packNumber;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public double getPicTotal() {
        return picTotal;
    }

    public void setPicTotal(double picTotal) {
        this.picTotal = picTotal;
    }

    public String getPickDate() {
        return TextUtils.isEmpty(pickDate) ? "" : pickDate;
    }

    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }

    public String getPlantMemberName() {
        return TextUtils.isEmpty(plantMemberName) ? "" : plantMemberName;
    }

    public void setPlantMemberName(String plantMemberName) {
        this.plantMemberName = plantMemberName;
    }

    public int getPlantMemberRecNo() {
        return plantMemberRecNo;
    }

    public void setPlantMemberRecNo(int plantMemberRecNo) {
        this.plantMemberRecNo = plantMemberRecNo;
    }

    public String getPlantMemberTel() {
        return TextUtils.isEmpty(plantMemberTel) ? "" : plantMemberTel;
    }

    public void setPlantMemberTel(String plantMemberTel) {
        this.plantMemberTel = plantMemberTel;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getQianPrice() {
        return qianPrice;
    }

    public void setQianPrice(double qianPrice) {
        this.qianPrice = qianPrice;
    }

    public int getQianType() {
        return qianType;
    }

    public String getQianTypeName() {
        switch (qianType) {
            case 1:
                return BaseApplication.getInstance().getString(R.string.sign_back_none);
            case 2:
                return BaseApplication.getInstance().getString(R.string.sign_back_paper);
            case 3:
                return BaseApplication.getInstance().getString(R.string.sign_back_electronic);
            default:
                return BaseApplication.getInstance().getString(R.string.sign_back_none);
        }
    }

    public void setQianType(int qianType) {
        this.qianType = qianType;
    }

    public String getRecDetail() {
        return TextUtils.isEmpty(recDetail) ? "" : recDetail;
    }

    public void setRecDetail(String recDetail) {
        this.recDetail = recDetail;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public int getRecRegion() {
        return recRegion;
    }

    public void setRecRegion(int recRegion) {
        this.recRegion = recRegion;
    }

    public String getRecTel() {
        return TextUtils.isEmpty(recTel) ? "" : recTel;
    }

    public void setRecTel(String recTel) {
        this.recTel = recTel;
    }

    public String getRemark() {
        return TextUtils.isEmpty(remark) ? "" : recTel;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSendDetail() {
        return sendDetail;
    }

    public void setSendDetail(String sendDetail) {
        this.sendDetail = sendDetail;
    }

    public String getSendName() {
        return TextUtils.isEmpty(sendName) ? "" : sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public int getSendRegion() {
        return sendRegion;
    }

    public void setSendRegion(int sendRegion) {
        this.sendRegion = sendRegion;
    }

    public String getSendTel() {
        return TextUtils.isEmpty(sendTel) ? "" : sendTel;
    }

    public void setSendTel(String sendTel) {
        this.sendTel = sendTel;
    }

    public double getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(double sendTotal) {
        this.sendTotal = sendTotal;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getTransCompanyRecNo() {
        return transCompanyRecNo;
    }

    public void setTransCompanyRecNo(int transCompanyRecNo) {
        this.transCompanyRecNo = transCompanyRecNo;
    }

    public int getTransCustomerMonthRecNo() {
        return transCustomerMonthRecNo;
    }

    public void setTransCustomerMonthRecNo(int transCustomerMonthRecNo) {
        this.transCustomerMonthRecNo = transCustomerMonthRecNo;
    }

    public int getTransRecShopRecNo() {
        return transRecShopRecNo;
    }

    public void setTransRecShopRecNo(int transRecShopRecNo) {
        this.transRecShopRecNo = transRecShopRecNo;
    }

    public int getTransShopRecNo() {
        return transShopRecNo;
    }

    public void setTransShopRecNo(int transShopRecNo) {
        this.transShopRecNo = transShopRecNo;
    }

    public double getTransTotal() {
        return transTotal;
    }

    public void setTransTotal(double transTotal) {
        this.transTotal = transTotal;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public double getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(double unpaid) {
        this.unpaid = unpaid;
    }

    public String getUpdateDate() {
        return TextUtils.isEmpty(updateDate)?"":updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getSendAdd() {
        return TextUtils.isEmpty(sendAdd) ? "" : sendAdd;
    }

    public void setSendAdd(String sendAdd) {
        this.sendAdd = sendAdd;
    }

    public String getRecAdd() {
        return TextUtils.isEmpty(recAdd) ? "" : recAdd;
    }

    public void setRecAdd(String recAdd) {
        this.recAdd = recAdd;
    }

    public double getValueAdd() {
        return StringUtil.add(qianPrice, StringUtil.add(baoPrice, daiPrice));
    }
}
