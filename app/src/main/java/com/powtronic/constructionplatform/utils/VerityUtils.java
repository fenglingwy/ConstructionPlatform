package com.powtronic.constructionplatform.utils;

import android.text.TextUtils;

import com.powtronic.constructionplatform.bean.User;

import java.util.ArrayList;

/**
 * Created by pp on 2017/1/5.
 */

public class VerityUtils {
    public static ArrayList<String> loginVetity(User user){
        ArrayList<String> errors = new ArrayList<>();
        if(TextUtils.isEmpty(user.getUsername())){
            errors.add("用户名不能为空!");
            return errors;
        }
//        if (!user.getUsername().matches("^\\d{6,15}$")) {
//            errors.add( "用户名不正确");
//            return errors;
//        }

        if(TextUtils.isEmpty(user.getPassword())){
            errors.add("密码不能为空!");
            return errors;
        }
        if (!user.getPassword().matches("^\\w{6,15}$")) {
            errors.add( "密码格式不正确!");
            return errors;
        }
        return errors;
    }
}
