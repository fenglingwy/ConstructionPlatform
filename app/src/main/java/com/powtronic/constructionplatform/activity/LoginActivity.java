package com.powtronic.constructionplatform.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.powtronic.constructionplatform.Callback.SimpleCallback;
import com.powtronic.constructionplatform.Constants;
import com.powtronic.constructionplatform.MyApplication;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.HttpMsg;
import com.powtronic.constructionplatform.bean.User;
import com.powtronic.constructionplatform.utils.SPUtils;
import com.powtronic.constructionplatform.utils.VerityUtils;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText mEtUsername;
    private EditText mEtPsw;
    private Button mBtnLogin;
    private TextView mTvRegister;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        mEtUsername = (EditText) findViewById(R.id.et_username);
        mEtPsw = (EditText) findViewById(R.id.et_psw);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mTvRegister = (TextView) findViewById(R.id.tv_register);

        mBtnLogin.setOnClickListener(this);
        this.mTvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_register:
                showDialog();

                break;
        }

    }

    private void showDialog() {

        String[] strings = new String[]{"个人注册", "单位注册"};

        new AlertDialog.Builder(this)
                .setTitle("注册")
                .setSingleChoiceItems(strings, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                                if (i == 0) {

                                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class).putExtra("id", 101));
                                } else {
                                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class).putExtra("id", 102));
                                }
                            }
                        }
                ).show();
    }

    private void login() {
        user = new User(mEtUsername.getText().toString(), mEtPsw.getText().toString());
        ArrayList<String> errors = VerityUtils.loginVetity(user);
        if (errors.size() != 0) {
            Toast.makeText(this, errors.get(0), Toast.LENGTH_SHORT).show();
            return;
        }
        mBtnLogin.setEnabled(false);
        mBtnLogin.setText("登录中...");

        OkGo.post(Constants.LOGIN_URL)
                .params("params", new Gson().toJson(user))
                .execute(new SimpleCallback<HttpMsg>() {

                    @Override
                    public void onSuccess(HttpMsg data, Call call, Response response) {
                        Log.i("TAG", data + "");
                        if (!"success".equals(data.getResult())) {
                            Toast.makeText(LoginActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                            mBtnLogin.setEnabled(true);
                            mBtnLogin.setText("登录");
                            return;
                        }
                        Log.d("TAG", "onSuccess: "+data.getData());
                        User user = new Gson().fromJson(data.getData(), User.class);
                        MyApplication.mUser = user;

                        //本地缓存
                        SPUtils.writeString(LoginActivity.this, "username", user.getUsername());
                        SPUtils.writeString(LoginActivity.this, "password", user.getPassword());

                        //setResult
                        setResult(Activity.RESULT_OK, new Intent());
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.d("TAG", "onError: ");
                        Toast.makeText(LoginActivity.this, "网络连接失败!", Toast.LENGTH_SHORT).show();
                        mBtnLogin.setEnabled(true);
                        mBtnLogin.setText("登录");
                    }
                });
    }


}
