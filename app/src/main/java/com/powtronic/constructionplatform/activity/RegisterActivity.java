package com.powtronic.constructionplatform.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.powtronic.constructionplatform.Callback.SimpleCallback;
import com.powtronic.constructionplatform.bean.Constants;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.HttpMsg;
import com.powtronic.constructionplatform.bean.User;
import com.powtronic.constructionplatform.bean.UserForm;
import com.powtronic.constructionplatform.utils.FormUtils;
import com.powtronic.constructionplatform.utils.ViewUtils;
import com.powtronic.constructionplatform.view.CountTimerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.btn_register)
    Button mBtnregister;
    @BindView(R.id.btn_get_code)
    Button mBtnGetCode;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.et_mobile_phone)
    EditText mEtMobilePhone;

    private UserForm mUserForm;
    private User mUser;
    private int mType;
    private Handler mHandler;
    private CountTimerView countTimerView;
    private String mobilePhone;

    private static String APPSECRET = "615d338b433820a97059685aa1a0b31b";
    private static String APPKEY = "1aedda4cbcdd4";
//    private static String APPKEY = "1baf21086f87a";
//    private static String APPSECRET = "122c771f6e6822eb49c8dbb48d98a404";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getIntent().getIntExtra("id", 0);
        if (mType == 101) {
            setContentView(R.layout.activity_register_personal);
            setTitleBar("个人注册", TITLEBAR_ONLY_BACK);
        } else {
            setContentView(R.layout.activity_register_company);
            setTitleBar("单位注册", TITLEBAR_ONLY_BACK);
        }
        ButterKnife.bind(this);
        initSDK();
    }

    /**
     * 获取验证码
     * @param v
     */
    @OnClick(R.id.btn_get_code)
    public void getCode(View v) {
        mobilePhone = mEtMobilePhone.getText().toString();
        if(TextUtils.isEmpty(mobilePhone)){
            Toast.makeText(this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        SMSSDK.getVerificationCode("+86", mobilePhone);
        countTimerView = new CountTimerView(mBtnGetCode);
        countTimerView.start();
    }

    @OnClick(R.id.btn_register)
    public void register(View v) {
        //校验
        if (!validate()) return;
        mUser = new User();
        FormUtils.form2Bean(mUserForm, mUser);
        mUser.setRole(mType);
        Log.d("TAG", "register: " + mUser);

        //手机验证
        submitCode();
    }

    private void initSDK() {
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        mHandler = new Handler(Looper.getMainLooper());

        EventHandler eventHandler = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功,返回校验的手机和国家代码
                        HashMap<String, Object> datas = (HashMap<String, Object>) data;
                        for (Map.Entry entry : datas.entrySet()) {
                            Log.d("TAG", entry.getKey() + "  " + entry.getValue());
                        }
                        sendRegister();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "验证码已发送!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    String json = ((Throwable) data).getMessage();
                    Log.d("TAG", "afterEvent: "+json);
                    String hint = null;
                    try {
                        JSONObject object = new JSONObject(json);
                        hint = object.getString("detail");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final String finalHint = hint;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (finalHint != null)
                                Toast.makeText(RegisterActivity.this, finalHint, Toast.LENGTH_SHORT).show();
                            mBtnregister.setEnabled(true);
                            mBtnregister.setText("注册");
                        }
                    });
                }
            }
        };
        // 验证回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }


    private void sendRegister() {

        OkGo.post(Constants.REGISTER_URL)
                .params("params", new Gson().toJson(mUser))
                .execute(new SimpleCallback<HttpMsg>() {

                    @Override
                    public void onSuccess(HttpMsg data, Call call, Response response) {
                        Log.i("TAG", data + "");
                        if ("success".equals(data.getResult())) {
                            Toast.makeText(RegisterActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        } else {
                            Toast.makeText(RegisterActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                            mBtnregister.setEnabled(true);
                            mBtnregister.setText("注册");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.d("TAG", "onError: ");
                        Toast.makeText(RegisterActivity.this, "网络连接异常!", Toast.LENGTH_SHORT).show();
                        mBtnregister.setEnabled(true);
                        mBtnregister.setText("注册");
                    }
                });
    }

    private void submitCode() {
        mBtnregister.setEnabled(false);
        mBtnregister.setText("注册中...");
        String code = mEtCode.getText().toString();
        mobilePhone = mEtMobilePhone.getText().toString();
        SMSSDK.submitVerificationCode("+86", mobilePhone, code);
    }


    private boolean validate() {

        Map<String, String> items = ViewUtils.loader(this.getWindow().getDecorView(), EditText.class);
        if (items == null) {
            return false;
        }

        //1，把表单的数据注入到RegisterForm
        mUserForm = FormUtils.load(items, UserForm.class);
        if (mUserForm == null) {
            return false;
        }
        //2，执行userForn的校验
        boolean isOk = mUserForm.validate();
        Log.d("TAG", mUserForm.toString());

        // 3，如果报错 读取userforn的map错误信息
        if (!isOk) {
            ViewUtils.Toast(this, mUserForm.getErrors());
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}
