package com.powtronic.constructionplatform.bean;

/**
 * Created by pp on 2017/2/15.
 */

public class ManagerDepartment {
    private int icon;
    private String name;
    private String phone;
    private String address;

    public ManagerDepartment(int icon, String name, String phone, String address) {
        this.icon = icon;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public ManagerDepartment() {
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
