package com.yyy.wrsf.beans;

import android.text.TextUtils;

public class WorkerB {

    /**
     * recNo : 29
     * memberName : 测试-停用2
     * memberTel : 17858956199
     * memberSex : 男
     * memberPetname : 测试
     * passWord : e10adc3949ba59abbe56e057f20f883e
     * mail :
     * stopYesno : 0
     * companyName : 测试
     * username : 17858956199
     * password : e10adc3949ba59abbe56e057f20f883e
     * admin : false
     */

    private int recNo;
    private String memberName;
    private String memberTel;
    private String memberSex;
    private String memberPetname;
    private String passWord;
    private String mail;
    private Integer stopYesno;
    private String companyName;
    private String username;
    private String password;
    private Integer roleId;

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public String getMemberName() {
        return TextUtils.isEmpty(memberName) ? "" : memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberTel() {
        return TextUtils.isEmpty(memberTel) ? "" : memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public String getMemberSex() {
        return TextUtils.isEmpty(memberSex) ? "" : memberSex;
    }

    public void setMemberSex(String memberSex) {
        this.memberSex = memberSex;
    }

    public String getMemberPetname() {
        return TextUtils.isEmpty(memberPetname) ? "" : memberPetname;
    }

    public void setMemberPetname(String memberPetname) {
        this.memberPetname = memberPetname;
    }

    public String getPassWord() {
        return TextUtils.isEmpty(passWord) ? "" : passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getMail() {
        return TextUtils.isEmpty(mail) ? "" : mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getStopYesno() {
        return stopYesno==null?0:stopYesno;
    }

    public void setStopYesno(int stopYesno) {
        this.stopYesno = stopYesno;
    }

    public String getCompanyName() {
        return TextUtils.isEmpty(companyName) ? "" : companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUsername() {
        return TextUtils.isEmpty(username)?"":username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return TextUtils.isEmpty(password)?"":password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId==null?-1:roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
