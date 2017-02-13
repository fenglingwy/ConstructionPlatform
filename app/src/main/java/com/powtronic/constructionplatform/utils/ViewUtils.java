package com.powtronic.constructionplatform.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: admin
 * Description:
 * Date: 2016-06-28 08:09
 * Version: 1.0
 *
 */
public class ViewUtils {

    private ViewUtils(){}

    /**
     * 存放从布局中找到的View控件
     */
    private static List<View> views = new ArrayList<View>();

    /**
     * 加载View子控件到一个Map中
     *
     * @param view
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<String,String> loader(View view,Class<T> clazz){


        Map<String,String> items = new HashMap<String,String>();
        views.clear();

        findView((ViewGroup)view,clazz);
        getViews(items,clazz);

        return items;
    }

    public static <T> void hint(View view,Class<T> clazz,Map<String,String> errors){

        findView((ViewGroup)view,clazz);
        setViews(errors,clazz);

    }
    public static void Toast(Context context ,Map<String, String> errors){
        for(String s:errors.keySet()){
            Toast.makeText(context,errors.get(s),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 查找子控件
     * @param viewGroup
     * @param clazz
     * @param <T>
     */
    private static <T> void findView(ViewGroup viewGroup, Class<T> clazz){

        for(int i=0;i<viewGroup.getChildCount();i++){
            View view=viewGroup.getChildAt(i);
            if(view instanceof ViewGroup){
                findView((ViewGroup)view,clazz);
            }

            if(clazz.isInstance(view)){
                try{
                    String classBean = view.getContentDescription().toString();
                    if(classBean.startsWith(clazz.getSimpleName())){
                        views.add(view);
                    }
                }catch(Exception e){}
            }
        }

    }

    /**
     * 获取映射的map
     *
     * @param items
     * @param clazz
     * @param <T>
     */
    private static <T> void getViews(Map<String,String> items,Class<T> clazz){

        for(View view : views){
            if(clazz.isInstance(view)){
                try{
                    String classBean = view.getContentDescription().toString();
                    if(classBean.startsWith(clazz.getSimpleName())){
                        TextView textView = (TextView)view;
                        items.put(classBean.replace(clazz.getSimpleName()+"_",""),
                                textView.getText().toString());
                    }
                }catch(Exception e){}
            }
        }
    }


    /**
     * 错误消息回显
     *
     * @param errors
     * @param clazz
     * @param <T>
     */
    private static <T> void setViews(Map<String,String> errors,Class<T> clazz){

        for(View view:views){
            if(clazz.isInstance(view)){
                try{
                    String classBean = view.getContentDescription().toString();
                    if(classBean.startsWith(clazz.getSimpleName())){
                        TextView textView=(TextView)view;
                        String text=errors.get(classBean.replace(clazz.getSimpleName()+"_",""));
                        textView.setText(text);
                    }
                }catch(Exception e){}
            }
        }
    }
}
