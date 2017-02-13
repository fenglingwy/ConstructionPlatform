package com.powtronic.constructionplatform.Callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.lzy.okgo.request.BaseRequest;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by pp on 2016/10/26.
 */

public abstract class DialogCallback<T> extends SimpleCallback<T> {
    private Context context;
    private ProgressDialog mDialog;


    public DialogCallback(Context context) {
        this.context = context;
        initDialog();
    }

    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        super.onAfter(t, e);
        mDialog.cancel();
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        mDialog.show();
    }

    private void initDialog() {
        mDialog = new ProgressDialog(context);
        mDialog.setMessage("数据加载中...");
        mDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        Toast.makeText(context, "网络异常!", Toast.LENGTH_SHORT).show();
    }
}
