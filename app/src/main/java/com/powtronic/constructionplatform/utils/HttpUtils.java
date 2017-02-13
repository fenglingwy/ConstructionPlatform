package com.powtronic.constructionplatform.utils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;

import java.util.Map;

/**
 * Created by pp on 2016/10/24.
 */
public  class HttpUtils {

    public static void post(String url, Map<String,String> params,AbsCallback callback){
        OkGo.post(url)
                .params( params)
                .execute(callback);
    }

}
