package com.yyy.wrsf.model.filter;

import java.io.Serializable;

public class MemberFilterModel implements Serializable {

    /**
     * birthday : 2020-03-24T04:37:54.114Z
     * companyId : 0
     * companyName : string
     * mail : string
     * memberCer : string
     * memberName : string
     * memberPetname : string
     * memberSex : string
     * memberTel : string
     * passWord : string
     * personCode : string
     * recNo : 0
     * roleId : 0
     * shopId : 0
     * stopYesno : 0
     * validate : string
     */

    private String birthday;
    private Integer companyId;
    private String companyName;
    private String mail;
    private String memberCer;
    private String memberName;
    private String memberPetname;
    private String memberSex;
    private String memberTel;
    private String passWord;
    private String personCode;
    private Integer recNo;
    private Integer roleId;
    private Integer shopId;
    private Integer stopYesno;
    private String validate;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMemberCer() {
        return memberCer;
    }

    public void setMemberCer(String memberCer) {
        this.memberCer = memberCer;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPetname() {
        return memberPetname;
    }

    public void setMemberPetname(String memberPetname) {
        this.memberPetname = memberPetname;
    }

    public String getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(String memberSex) {
        this.memberSex = memberSex;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getStopYesno() {
        return stopYesno;
    }

    public void setStopYesno(int stopYesno) {
        this.stopYesno = stopYesno;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }
}
