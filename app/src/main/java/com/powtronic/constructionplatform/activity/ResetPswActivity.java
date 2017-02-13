package com.powtronic.constructionplatform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.BaseRequest;
import com.powtronic.constructionplatform.Callback.SimpleCallback;
import com.powtronic.constructionplatform.Constants;
import com.powtronic.constructionplatform.MyApplication;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.HttpMsg;
import com.powtronic.constructionplatform.bean.User;

import okhttp3.Call;
import okhttp3.Response;

public class ResetPswActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEtOld;
    private EditText mEtNew1;
    private EditText mEtNew2;
    private Button mBtnSubmit;
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_psw);
        initView();
    }

    private void initView() {
        mEtOld = (EditText) findViewById(R.id.et_old_pwd);
        mEtNew1 = (EditText) findViewById(R.id.et_new_pwd1);
        mEtNew2 = (EditText) findViewById(R.id.et_new_pwd2);
        mBtnSubmit = (Button) findViewById(R.id.btn_reset);
        findViewById(R.id.iv_back).setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                User mUser = MyApplication.mUser;
                String oldPwd = mEtOld.getText().toString();
                String newPwd1 = mEtNew1.getText().toString();
                String newPwd2 = mEtNew2.getText().toString();
                if (!verify(mUser.getPassword(), oldPwd, newPwd1, newPwd2)) return;
                sendRequest(mUser.getMobilePhone(), newPwd1);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private boolean verify(String password, String oldPwd, String newPwd1, String newPwd2) {
        if (TextUtils.isEmpty(mEtOld.getText()) || TextUtils.isEmpty(mEtNew1.getText()) || TextUtils.isEmpty(mEtNew2.getText())) {
            Toast.makeText(this, "密码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!oldPwd.equals(password)) {
            Toast.makeText(this, "旧密码输入有误!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!newPwd1.equals(newPwd2)) {
            Toast.makeText(this, "新密码两次输入不一致!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (oldPwd.equals(newPwd1)) {
            Toast.makeText(this, "新密码与旧密码相同!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void sendRequest(String mobilePhone, String newPsw) {
        OkGo.post(Constants.RESET_PSW_URL)
                .params("params", " {\"mobilePhone\":\"" + mobilePhone + "\",\"password\":\"" + newPsw + "\"}")
                .execute(new SimpleCallback<HttpMsg>() {
                    @Override
                    public void onBefore(BaseRequest request) {
                        mBtnSubmit.setEnabled(false);
                        mBtnSubmit.setText("修改中...");
                    }

                    @Override
                    public void onSuccess(HttpMsg data, Call call, Response response) {
//                        Toast.makeText(ResetPswActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                        if ("success".equals(data.getResult())) {
                            MyApplication.mUser = null;
                            startActivity(new Intent(ResetPswActivity.this,MainActivity.class));
                            finish();
                            Toast.makeText(ResetPswActivity.this, "密码修改成功!请重新登录!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mBtnSubmit.setEnabled(true);
                        mBtnSubmit.setText("修改");
                        return;
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        Log.d("TAG", "onError: ");
                        Toast.makeText(ResetPswActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
                        mBtnSubmit.setEnabled(true);
                        mBtnSubmit.setText("修改");
                    }
                });
    }
}


