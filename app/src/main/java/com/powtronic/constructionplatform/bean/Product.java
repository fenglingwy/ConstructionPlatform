package com.powtronic.constructionplatform.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pp on 2017/1/10.
 */

public class Product implements Serializable {
    private int _id;
    private String name;
    private String price;
    private String imgUrl;
    private String company;
    private String timestamp;
    private int product_type;
    private String buyer;
    private String phone;
    private String address;
    private int sales_type;
    private int user_id;

    private List<Param> params;

    public Product() {

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getProduct_type() {
        return product_type;
    }

    public void setProduct_type(int product_type) {
        this.product_type = product_type;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
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

    public int getSales_type() {
        return sales_type;
    }

    public void setSales_type(int sales_type) {
        this.sales_type = sales_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }


    public Product(int id, String name, String price, String imgUrl) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", company='" + company + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", product_type='" + product_type + '\'' +
                ", buyer='" + buyer + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", sales_type='" + sales_type + '\'' +
                ", user_id='" + user_id + '\'' +
                ", params=" + params +
                '}';
    }

    public static class Param implements Serializable {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Param{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
