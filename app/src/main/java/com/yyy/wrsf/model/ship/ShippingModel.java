package com.yyy.wrsf.model.ship;

public class ShippingModel {

    /**
     * baoAsk : 0
     * baoPrice : 0
     * contractNo : string
     * contractStatus : 0
     * contractTotal : 0
     * createDate : 2020-03-23T01:53:49.609Z
     * daiPrice : 0
     * daiTotal : 0
     * deliverType : 0
     * density : 0
     * goodsName : string
     * noticeYesNo : 0
     * packNumber : 0
     * picTotal : 0
     * pickDate : 2020-03-23T01:53:49.609Z
     * platMemberName : string
     * platMemberRecNo : 0
     * platMemberTel : string
     * price : 0
     * qianPrice : 0
     * qianType : 0
     * recDetail : string
     * recName : string
     * recRegion : 0
     * recTel : string
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
     * updateDate : 2020-03-23T01:53:49.609Z
     * weight : 0
     */

    private int baoAsk;
    private int baoPrice;//保价价格
    private String contractNo;
    private int contractStatus;
    private long contractTotal;//总金额
    private String createDate;
    private int daiPrice;
    private int daiTotal;//代收货款
    private int deliverType;
    private long density;
    private String goodsName;
    private int noticeYesNo;
    private long packNumber;//数量
    private int picTotal;//提货价格
    private String pickDate;
    private String platMemberName;
    private int platMemberRecNo;
    private String platMemberTel;
    private int price;//单价
    private int qianPrice;//签单费
    private int qianType;
    private String recDetail;
    private String recName;
    private int recRegion;
    private String recTel;
    private String sendDetail;
    private String sendName;
    private int sendRegion;
    private String sendTel;
    private long sendTotal;//送货费
    private int sendType;
    private long size;
    private int transCompanyRecNo;
    private int transCustomerMonthRecNo;
    private int transRecShopRecNo;
    private int transShopRecNo;
    private long transTotal;//运费
    private int transType;
    private String updateDate;
    private long weight;//重量

    public int getBaoAsk() {
        return baoAsk;
    }

    public void setBaoAsk(int baoAsk) {
        this.baoAsk = baoAsk;
    }

    public int getBaoPrice() {
        return baoPrice;
    }

    public void setBaoPrice(int baoPrice) {
        this.baoPrice = baoPrice;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public int getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(int contractStatus) {
        this.contractStatus = contractStatus;
    }

    public long getContractTotal() {
        return contractTotal;
    }

    public void setContractTotal(long contractTotal) {
        this.contractTotal = contractTotal;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getDaiPrice() {
        return daiPrice;
    }

    public void setDaiPrice(int daiPrice) {
        this.daiPrice = daiPrice;
    }

    public int getDaiTotal() {
        return daiTotal;
    }

    public void setDaiTotal(int daiTotal) {
        this.daiTotal = daiTotal;
    }

    public int getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(int deliverType) {
        this.deliverType = deliverType;
    }

    public long getDensity() {
        return density;
    }

    public void setDensity(long density) {
        this.density = density;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getNoticeYesNo() {
        return noticeYesNo;
    }

    public void setNoticeYesNo(int noticeYesNo) {
        this.noticeYesNo = noticeYesNo;
    }

    public long getPackNumber() {
        return packNumber;
    }

    public void setPackNumber(long packNumber) {
        this.packNumber = packNumber;
    }

    public int getPicTotal() {
        return picTotal;
    }

    public void setPicTotal(int picTotal) {
        this.picTotal = picTotal;
    }

    public String getPickDate() {
        return pickDate;
    }

    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }

    public String getPlatMemberName() {
        return platMemberName;
    }

    public void setPlatMemberName(String platMemberName) {
        this.platMemberName = platMemberName;
    }

    public int getPlatMemberRecNo() {
        return platMemberRecNo;
    }

    public void setPlatMemberRecNo(int platMemberRecNo) {
        this.platMemberRecNo = platMemberRecNo;
    }

    public String getPlatMemberTel() {
        return platMemberTel;
    }

    public void setPlatMemberTel(String platMemberTel) {
        this.platMemberTel = platMemberTel;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQianPrice() {
        return qianPrice;
    }

    public void setQianPrice(int qianPrice) {
        this.qianPrice = qianPrice;
    }

    public int getQianType() {
        return qianType;
    }

    public void setQianType(int qianType) {
        this.qianType = qianType;
    }

    public String getRecDetail() {
        return recDetail;
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
        return recTel;
    }

    public void setRecTel(String recTel) {
        this.recTel = recTel;
    }

    public String getSendDetail() {
        return sendDetail;
    }

    public void setSendDetail(String sendDetail) {
        this.sendDetail = sendDetail;
    }

    public String getSendName() {
        return sendName;
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
        return sendTel;
    }

    public void setSendTel(String sendTel) {
        this.sendTel = sendTel;
    }

    public long getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(long sendTotal) {
        this.sendTotal = sendTotal;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
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

    public long getTransTotal() {
        return transTotal;
    }

    public void setTransTotal(long transTotal) {
        this.transTotal = transTotal;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }
}
