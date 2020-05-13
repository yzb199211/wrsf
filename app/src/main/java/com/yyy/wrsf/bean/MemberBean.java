package com.yyy.wrsf.bean;

import android.text.TextUtils;

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
    private String validate;
    private List<MemberAuthorityBean> roles;
    private MemberRoleBean memberRoleBo;

    public String getUsername() {
        return TextUtils.isEmpty(username) ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return TextUtils.isEmpty(token) ? "" : token;
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
        return TextUtils.isEmpty(brithday) ? "" : brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getCompanyName() {
        return TextUtils.isEmpty(companyName) ? "" : companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMail() {
        return TextUtils.isEmpty(mail) ? "" : mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMemberName() {
        return TextUtils.isEmpty(memberName) ? "" : memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPetname() {
        return TextUtils.isEmpty(memberPetname) ? "" : memberPetname;
    }

    public void setMemberPetname(String memberPetname) {
        this.memberPetname = memberPetname;
    }

    public String getMemberSex() {
        return TextUtils.isEmpty(memberSex) ? "" : memberSex;
    }

    public void setMemberSex(String memberSex) {
        this.memberSex = memberSex;
    }

    public String getMemberTel() {
        return TextUtils.isEmpty(memberTel) ? "" : memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public String getPassWord() {
        return TextUtils.isEmpty(passWord) ? "" : passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getRecNo() {
        return recNo==null?-1:recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public int getRoleType() {
        return roleType==null?-1:roleType;
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

    public String getValidate() {
        return TextUtils.isEmpty(validate) ? "" : validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    @Override
    public String getPickerViewText() {
        return memberName;
    }
}
