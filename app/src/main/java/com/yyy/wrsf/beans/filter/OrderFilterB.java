package com.yyy.wrsf.beans.filter;

import java.io.Serializable;

public class OrderFilterB implements Serializable {
    private String contractNo;
    private Integer contractStatus;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }
}
