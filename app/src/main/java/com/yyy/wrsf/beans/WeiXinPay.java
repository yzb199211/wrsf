package com.yyy.wrsf.beans;

import com.google.gson.annotations.SerializedName;

public class WeiXinPay {


    /**
     * package : Sign=WXPay
     * appid : wx5338ffd15f765314
     * sign : 1AE7E26FF84E737C7088A1618BFF6793
     * prepayid : wx151551557314116616b6310b1607647900
     * partnerid : 1492730162
     * noncestr : 1589529111322
     * timestamp : 1589529111
     */

//    @SerializedName("package")
    private String packAge;
    private String appid;
    private String sign;
    private String prepayid;
    private String partnerid;
    private String noncestr;
    private String timestamp;

    public String getPackageX() {
        return packAge;
    }

    public void setPackageX(String packageX) {
        this.packAge = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
