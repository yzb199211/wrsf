package com.yyy.wrsf.model;

public class LoginModel {

    /**
     * recNo : 32.0
     * name :
     * tel : 15868396230
     * sex :
     * petname :
     * mail :
     * companyName :
     * roleType : 1.0
     * token : 545c5ef4-e0d8-41d1-ad2d-461efc15be13
     * loginTime : 1.583894524057E12
     * expireTime : 1.583901724057E12
     */

    private double recNo;
    private String name;
    private String tel;
    private String sex;
    private String petname;
    private String mail;
    private String companyName;
    private double roleType;
    private String token;
    private double loginTime;
    private double expireTime;

    public int getRecNo() {
        return new Double(recNo).intValue();
    }

    public void setRecNo(double recNo) {
        this.recNo = recNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getRoleType() {
        return new Double(roleType).intValue();
    }

    public void setRoleType(double roleType) {
        this.roleType = roleType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLoginTime() {
        return new Double(loginTime).intValue();
    }

    public void setLoginTime(double loginTime) {
        this.loginTime = loginTime;
    }

    public int getExpireTime() {
        return new Double(expireTime).intValue();
    }

    public void setExpireTime(double expireTime) {
        this.expireTime = expireTime;
    }
}
