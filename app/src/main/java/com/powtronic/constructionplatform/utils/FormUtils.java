package com.powtronic.constructionplatform.utils;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by admin on 2016/6/24.
 */
public class FormUtils {

    public static <T> T load(Map<String, String> items, Class<T> classze) {
        try {
            T form = classze.newInstance();
            for (Map.Entry<String, String> entry : items.entrySet()) {
                Field f = classze.getDeclaredField(entry.getKey());
                f.setAccessible(true);
                f.set(form, entry.getValue());
            }
            return form;

        } catch (InstantiationException e) {
            Log.e("FORM",e.getMessage());

        } catch (IllegalAccessException e) {
            Log.e("FORM",e.getMessage());
        } catch (NoSuchFieldException e) {
            Log.e("FORM",e.getMessage());
        }
        return null;

    }

    public static  void form2Bean(Object src,Object dest){
        Field[] fields=dest.getClass().getDeclaredFields();
        for(Field destField:fields){
            try {
            String name=destField.getName();
                Log.i("FORM","field: "+name);
                Field srcField=src.getClass().getDeclaredField(name);
                destField.setAccessible(true);
                srcField.setAccessible(true);
                destField.set(dest,srcField.get(src));
            } catch (NoSuchFieldException e) {
              Log.e("FORM",e.getMessage());
            } catch (IllegalAccessException e) {
                Log.e("FORM",e.getMessage());
            }
        }

    }
}
