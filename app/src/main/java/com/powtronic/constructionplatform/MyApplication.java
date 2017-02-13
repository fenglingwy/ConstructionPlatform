package com.powtronic.constructionplatform;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.powtronic.constructionplatform.bean.User;

/**
 * Created by pp on 2017/1/6.
 */

public class MyApplication extends Application {
    public static User mUser;


    public static MyApplication mContext;




    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //OkGo初始化
        OkGo.init(this);
        try {
            OkGo.getInstance()
                    .setConnectTimeout(15000)  //全局的连接超时时间
                    .setReadTimeOut(15000)     //全局的读取超时时间
                    .setWriteTimeOut(15000)    //全局的写入超时时间
                    .setCacheMode(CacheMode.NO_CACHE)
                    .setRetryCount(0);   //设置全局公共参数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
