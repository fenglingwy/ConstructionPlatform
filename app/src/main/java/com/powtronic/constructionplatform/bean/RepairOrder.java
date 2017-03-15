package com.powtronic.constructionplatform.bean;

/**
 * Created by pp on 2017/3/13.
 */

public class RepairOrder {
    private int _id;
    private String companyName;
    private String companyAddress;
    private String contact;
    private String phone;
    private String maintainer;
    private String question;
    private String method;
    private String result;
    private String status;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(String maintainer) {
        this.maintainer = maintainer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RepairOrder{" +
                "_id=" + _id +
                ", companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", maintainer='" + maintainer + '\'' +
                ", question='" + question + '\'' +
                ", method='" + method + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
