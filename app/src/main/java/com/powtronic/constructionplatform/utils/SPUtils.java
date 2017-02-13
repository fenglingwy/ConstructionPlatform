package com.powtronic.constructionplatform.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SPUtils {
    public static final String CONFIG_SP = "config_sp";/* config_sp.xml 文件  存放位置 ：/data/data/《包名》/shared_prefes*/
    private static SharedPreferences mSp;

    private static SharedPreferences getPreferences(Context context) {
        if (mSp == null) mSp = context.getSharedPreferences(CONFIG_SP, Context.MODE_PRIVATE);
        return mSp;
    }

    /**
     * 保存布尔数据 @param context @param key @param value
     */
    public static void writeBoolean(Context context, String key, boolean value) {
        SharedPreferences sp =   getPreferences(context);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 取布尔数据 ,返回的是false 默认值 @param context @param key @return
     */
    public static boolean readBoolean(Context context, String key) {
        SharedPreferences sp = getPreferences(context);
        return sp.getBoolean(key, false);
    }

    /**
     * 取布尔数据 ,默认返回的是传过来的值 @param context @param key @param defValue @return
     */
    public static boolean readBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = getPreferences(context);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存字符串 @param context @param key @param value
     */
    public static void writeString(Context context, String key, String value) {
        SharedPreferences sp = getPreferences(context);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 取字符串数据 ,默认返回的是 null @param context @param key @return
     */
    public static String readString(Context context, String key) {
        SharedPreferences sp = getPreferences(context);
        return sp.getString(key, null);
    }

    /**
     * 取字符串数据 ,返回的是传递过来的值 @param context @param key @param defValue @return
     */
    public static String readString(Context context, String key, String defValue) {
        SharedPreferences sp = getPreferences(context);
        return sp.getString(key, defValue);
    }

    /**
     * 存Int整数 @param context @param key @param value
     */
    public static void writeInt(Context context, String key, int value) {
        SharedPreferences sp = getPreferences(context);
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 取Int整数 ,默认返回的是 0 @param context @param key @return
     */
    public static int readInt(Context context, String key) {
        SharedPreferences sp = getPreferences(context);
        return sp.getInt(key, 0);
    }

    /**
     * 取Int整数 ,默认的是传递过来的值 @param context @param key @param defValue @return
     */
    public static int readInt(Context context, String key, int defValue) {
        SharedPreferences sp = getPreferences(context);
        return sp.getInt(key, defValue);
    }

    /**
     * 保存浮点型数据 @param context @param key @param value
     */
    public static void writeFloat(Context context, String key, Float value) {
        SharedPreferences sp = getPreferences(context);
        sp.edit().putFloat(key, value).commit();
    }

    /**
     * 取浮点型数据，默认返回的是0 @param context @param key @return
     */
    public static Float readFload(Context context, String key) {
        SharedPreferences sp = getPreferences(context);
        return sp.getFloat(key, 0);
    }

    /**
     * 取浮点型数据，默认的是传递过来的值 @param context @param key @param defValue @return
     */
    public static Float readFload(Context context, String key, Float defValue) {
        SharedPreferences sp = getPreferences(context);
        return sp.getFloat(key, defValue);
    }

    /**
     * 保存Long型数据 @param context @param key @param value
     */
    public static void writeLong(Context context, String key, Long value) {
        SharedPreferences sp = getPreferences(context);
        sp.edit().putLong(key, value).commit();
    }

    /**
     * 取Long型数据，默认返回的是0 @param context @param key @return
     */
    public static Long readLong(Context context, String key) {
        SharedPreferences sp = getPreferences(context);
        return sp.getLong(key, 0);
    }

    /**
     * 取Long型数据，默认的是传递过来的值 @param context @param key @param defValue @return
     */
    public static Long readFload(Context context, String key, Long defValue) {
        SharedPreferences sp = getPreferences(context);
        return sp.getLong(key, defValue);
    }

    /**
     * 保存Set<String>型数据 @param context @param key @param value
     */
    public static void writeStringSet(Context context, String key, Set<String> value) {
        SharedPreferences sp = getPreferences(context);
        sp.edit().putStringSet(key, value).commit();
    }

    /**
     * 取Set<String>型数据，默认返回的是new HashSet<String>() @param context @param key @return
     */
    public static Set<String> readStringSet(Context context, String key) {
        SharedPreferences sp = getPreferences(context);
        return sp.getStringSet(key, new HashSet<String>());
    }

    /**
     * 取Set<String>型数据，默认的是传递过来的值 @param context @param key @param defValue @return
     */
    public static Set<String> readStringSet(Context context, String key, Set<String> defValue) {
        SharedPreferences sp = getPreferences(context);
        return sp.getStringSet(key, defValue);
    }
}