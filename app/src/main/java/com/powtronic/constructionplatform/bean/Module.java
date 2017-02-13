package com.powtronic.constructionplatform.bean;

import android.app.Activity;

public class Module {


    private String name;
    private int resId;
    private Class activityClass;

    public Module(String name, int resId, Class activityClass) {
        this.name = name;
        this.resId = resId;
        this.activityClass = activityClass;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public Class<Activity> getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class<Activity> activityClass) {
        this.activityClass = activityClass;
    }
}
