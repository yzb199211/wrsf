package com.yyy.wrsf.mine.outlets;

public class OutleModel implements OutletItemListener {

    /**
     * detailAdd : 丁桥
     * person : 测试
     * recNo : 2
     * recQu : 朱桥
     * recTel : 17858956195
     * remark :
     * sendTel : 17858956195
     * shopCode : DQ02
     * shopName : 丁桥网点2
     * transCompanyRecno : 1
     */

    private String detailAdd;
    private String person;
    private int recNo;
    private String recQu;
    private String recTel;
    private String remark;
    private String sendTel;
    private String shopCode;
    private String shopName;
    private int transCompanyRecno;

    public String getDetailAdd() {
        return detailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        this.detailAdd = detailAdd;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public String getRecQu() {
        return recQu;
    }

    public void setRecQu(String recQu) {
        this.recQu = recQu;
    }

    public String getRecTel() {
        return recTel;
    }

    public void setRecTel(String recTel) {
        this.recTel = recTel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSendTel() {
        return sendTel;
    }

    public void setSendTel(String sendTel) {
        this.sendTel = sendTel;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getTransCompanyRecno() {
        return transCompanyRecno;
    }

    public void setTransCompanyRecno(int transCompanyRecno) {
        this.transCompanyRecno = transCompanyRecno;
    }

    @Override
    public OutletItem getOutlet() {
        OutletItem item = new OutletItem();
        item.setAddress(detailAdd);
        item.setPhone(sendTel);
        item.setTitle(shopName);
        item.setAddress(detailAdd);
        return item;
    }
}
