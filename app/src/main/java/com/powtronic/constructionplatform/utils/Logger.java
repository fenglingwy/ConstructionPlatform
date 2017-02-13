package com.powtronic.constructionplatform.utils;

import android.util.Log;

/**
 * Created by pp on 2017/1/6.
 */

public class Logger {

    /**
     * Log输出的控制开关
     */
    public static boolean isShowLog = true;

    public static void i(String tag, String msg) {
        if (!isShowLog) {
            return;
        }
        Log.i(tag, msg);
    }
}
