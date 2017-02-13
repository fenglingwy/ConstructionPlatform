package com.powtronic.constructionplatform.bean;

import android.text.TextUtils;

/**
 * Created by pp on 2016/10/21.
 */
public class UserForm extends Form{

    private String username;
    private String password;
    private String confirmPsw;
    private String mobilePhone;
    private String realityName;
    private String address;
    private String introduction;
    private String phone;
    private String email;
    private String idcard;
    private String code;
    private int role;


    @Override
    public boolean validate() {
        if(TextUtils.isEmpty(username)){
            errors.put("username", "用户名不能为空！");
            return false;
        }
        if (!username.matches("^[\\u4e00-\\u9fa5\\w]{1,16}$")) {
            errors.put("username", "用户名格式不正确！!");
            return false;
        }
        if(TextUtils.isEmpty(password)){
            errors.put("password", "密码不能为空！");
            return false;
        }
        if (!password.matches("^\\w{6,16}$")) {
            errors.put("password", "密码格式不正确！!");
            return false;
        }
        if(TextUtils.isEmpty(confirmPsw)){
            errors.put("confirmPsw", "密码确认不能为空！");
            return false;
        }
        if (!password.equals(confirmPsw)) {
            errors.put("confirmPsw", "两次密码输入不一致！!");
            return false;
        }
        if(TextUtils.isEmpty(mobilePhone)){
            errors.put("mobilePhone", "手机号不能为空！");
            return false;
        }
        if (!mobilePhone.matches("^\\d{11}$")) {
            errors.put("mobilePhone", "手机格式不正确！!");
            return false;
        }
        if(TextUtils.isEmpty(realityName)){
            errors.put("realityName", "真实姓名不能为空！");
            return false;
        }
        if (!realityName.matches("^[\\u4e00-\\u9fa5]{2,5}$")) {
            errors.put("realityName", "名字格式不正确！!");
            return false;
        }
        if(TextUtils.isEmpty(address)){
            errors.put("address", "地址不能为空！");
            return false;
        }
        if(TextUtils.isEmpty(phone)){
            errors.put("phone", "联系电话不能为空！");
            return false;
        }

        if(TextUtils.isEmpty(email)){
            errors.put("email", "email不能为空！");
            return false;
        }

        if(TextUtils.isEmpty(idcard)){
            errors.put("idcard", "证件号码不能为空！");
            return false;
        }
//        if (!idcard.matches("^[\\u4e00-\\u9fa5]{2,5}$")) {
//            errors.put("idcard", "名字格式不正确！!");
//            return false;
//        }

        if(TextUtils.isEmpty(code)){
            errors.put("code", "验证码不能为空！");
            return false;
        }
        return true;
    }

    //    public boolean validate() {
//        if (TextUtils.isEmpty(factoryCode)) {
//            errors.put("factoryCode", "工厂代码不能为空！");
//            return false;
//        }
//
//
////
////        if (TextUtils.isEmpty(nickname)) {
////            errors.put("nickname", "用户账户不能为空！");
////            return false;
////        }
//        // if (this.nickName.match("^\\d{11}$") == null) {
//        //     this.errors.set("nickName", "用户账户 11位数字！");
//        //     return false;
//        // }
//
//        if (TextUtils.isEmpty(password)) {
//            errors.put("password", "密码不能为空！");
//            return false;
//        }
//        if (!password.matches("^\\d{6,11}$")) {
//            errors.put("password", "密码 6-11位字符！");
//            return false;
//        }
//        return true;
//    }
//



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

    public String getConfirmPsw() {
        return confirmPsw;
    }

    public void setConfirmPsw(String confirmPsw) {
        this.confirmPsw = confirmPsw;
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

    @Override
    public String toString() {
        return "UserForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPsw='" + confirmPsw + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", realityName='" + realityName + '\'' +
                ", address='" + address + '\'' +
                ", introduction='" + introduction + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", idcard='" + idcard + '\'' +
                ", code='" + code + '\'' +
                ", role=" + role +
                '}';
    }


}
