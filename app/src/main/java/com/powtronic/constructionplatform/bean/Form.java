package com.powtronic.constructionplatform.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/6/24.
 */
public abstract class Form {
    protected Map<String, String> errors = new HashMap<>();
    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public abstract boolean validate();
}
