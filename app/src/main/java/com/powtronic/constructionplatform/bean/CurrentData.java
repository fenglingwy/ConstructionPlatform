package com.powtronic.constructionplatform.bean;

/**
 * Created by pp on 2017/2/17.
 */

public class CurrentData {
   public String name;
   public String value;

    public CurrentData(String name, String value) {
        this.name = name;
        this.value = value;
    }

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
}
