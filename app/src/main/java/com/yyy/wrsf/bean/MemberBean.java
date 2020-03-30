package com.yyy.wrsf.bean;

import com.yyy.yyylibrary.wheel.interfaces.IPickerViewData;

import java.util.List;

public class MemberBean implements IPickerViewData {
    private String brithday;
    private String companyName;
    private String mail;
    private String memberName;
    private String memberPetname;
    private String memberSex;
    private String memberTel;
    private String passWord;
    private String username;
    private String token;
    private Integer recNo;
    private Integer roleType;
    private Integer stopYesno;
    private List<MemberAuthorityBean> roles;
    private MemberRoleBean memberRoleBo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public List<MemberAuthorityBean> getRoles() {
        return roles;
    }

    public void setRoles(List<MemberAuthorityBean> roles) {
        this.roles = roles;
    }

    public MemberRoleBean getMemberRoleBo() {
        return memberRoleBo;
    }

    public void setMemberRoleBo(MemberRoleBean memberRoleBo) {
        this.memberRoleBo = memberRoleBo;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
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

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public int getStopYesno() {
        return stopYesno;
    }

    public void setStopYesno(int stopYesno) {
        this.stopYesno = stopYesno;
    }

    @Override
    public String getPickerViewText() {
        return memberName;
    }
}
