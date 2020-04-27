package com.yyy.wrsf.beans.filter;

import java.io.Serializable;

public class OrderFilterB implements Serializable {
    private String contractNo;
    private Integer contractStatus;
    private Integer noticeYesNo;
    private Integer daiStatus;

    public Integer getDaiStatus() {
        return daiStatus;
    }

    public void setDaiStatus(Integer daiStatus) {
        this.daiStatus = daiStatus;
    }

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

    public Integer getNoticeYesNo() {
        return noticeYesNo;
    }

    public void setNoticeYesNo(Integer noticeYesNo) {
        this.noticeYesNo = noticeYesNo;
    }
}
