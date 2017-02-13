package com.powtronic.constructionplatform.Callback;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.$Gson$Types;
import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by pp on 2016/11/7.
 */

public abstract class SimpleCallback<T> extends AbsCallback<T> {

    public Type mType;

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    public SimpleCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }


    @Override
    public T convertSuccess(Response response) throws Exception {
        Gson gson = new Gson();
        String str = response.body().string();
        Log.i("TAG", "response:  "+str);
        T s = null;
        try {
            s = gson.fromJson(str, mType);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return s;
    }
}
