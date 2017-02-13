package com.powtronic.constructionplatform.bean;

/**
 * Created by pp on 2017/1/6.
 */

public class User {
    private String username;
    private String password;
    private String mobilePhone;
    private String realityName;
    private String address;
    private String introduction;
    private String phone;
    private String email;
    private String idcard;
    private String code;
    private int role;
    private String head_photo;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getRealityName() {
        return realityName;
    }

    public void setRealityName(String realityName) {
        this.realityName = realityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getHead_photo() {
        return head_photo;
    }

    public void setHead_photo(String head_photo) {
        this.head_photo = head_photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", realityName='" + realityName + '\'' +
                ", address='" + address + '\'' +
                ", introduction='" + introduction + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", idcard='" + idcard + '\'' +
                ", code='" + code + '\'' +
                ", role=" + role +
                ", head_photo='" + head_photo + '\'' +
                '}';
    }
}
