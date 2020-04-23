package com.yyy.wrsf.enums.bill;

public enum BillFieldEnum {
    UNPAD("unpaid"), TOTAL("contractTotal");
    private String filed;

    BillFieldEnum(String filed) {
        this.filed = filed;
    }

    public String getFiled() {
        return filed;
    }

}
