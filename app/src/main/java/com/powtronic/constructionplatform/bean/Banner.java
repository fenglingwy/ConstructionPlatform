package com.powtronic.constructionplatform.bean;

/**
 * Created by Ivan on 15/10/2.
 */
public class Banner {


    private  String name;
    private  int imgUrl;
    private  String description;

    public Banner(String name, int imgUrl, String description) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
