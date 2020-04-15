package com.yyy.wrsf.beans.ship;

public class ShipCompanyB {

    /**
     * transCompanyRecno : 13
     * transCompanyName : 测试公司莫
     * sendRegion : {"recNo":8,"transCompanyRecno":13,"shopCode":"DQ01","shopName":"丁桥网点","detailAdd":"丁桥园区","sendTel":"17858956195","recTel":"17858956195","recQu":",1444","person":{"recNo":45,"memberName":"测试0331","memberTel":"17858956333","memberSex":"男","memberPetname":"哈哈","passWord":"1406900ba9403d11849de12bca99f4fb","mail":"219213@qq.com","stopYesno":0,"password":"1406900ba9403d11849de12bca99f4fb","username":"17858956333","admin":false},"remark":"测试","shopStatus":1}
     * recRegion : {"recNo":7,"transCompanyRecno":13,"shopCode":"A01","shopName":"测试网点1","detailAdd":"111","sendTel":"17858956195","recTel":"17858956195","recQu":",1443,1446","person":{"recNo":44,"memberName":"测试0327","memberTel":"1785895666","memberSex":"男","memberPetname":"公有制","passWord":"4827d88219c9b33aae052c30aeb785b7","mail":"25213213@qq.com","stopYesno":0,"password":"4827d88219c9b33aae052c30aeb785b7","username":"1785895666","admin":false},"remark":"测试","shopStatus":1}
     * type : 1
     */

    private int transCompanyRecno;
    private String transCompanyName;
    private ShipRegionB sendRegion;
    private ShipRegionB recRegion;
    private String type;

    public int getTransCompanyRecno() {
        return transCompanyRecno;
    }

    public void setTransCompanyRecno(int transCompanyRecno) {
        this.transCompanyRecno = transCompanyRecno;
    }

    public String getTransCompanyName() {
        return transCompanyName;
    }

    public void setTransCompanyName(String transCompanyName) {
        this.transCompanyName = transCompanyName;
    }

    public ShipRegionB getSendRegion() {
        return sendRegion;
    }

    public void setSendRegion(ShipRegionB sendRegion) {
        this.sendRegion = sendRegion;
    }

    public ShipRegionB getRecRegion() {
        return recRegion;
    }

    public void setRecRegion(ShipRegionB recRegion) {
        this.recRegion = recRegion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
