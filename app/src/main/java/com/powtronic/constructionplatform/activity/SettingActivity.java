package com.powtronic.constructionplatform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powtronic.constructionplatform.MyApplication;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.Utils;
import com.powtronic.constructionplatform.utils.Update;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.ll_reset_psw)
    LinearLayout llResetPsw;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_update)
    LinearLayout llUpdate;
    @BindView(R.id.ll_logout)
    LinearLayout llLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitleBar("设置", TITLEBAR_ONLY_BACK);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvVersion.setText(Utils.getVersion(this));
        if (MyApplication.mUser != null) {
            llResetPsw.setVisibility(View.VISIBLE);
            llLogout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.ll_reset_psw, R.id.ll_update, R.id.ll_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_reset_psw:
                startActivity(new Intent(this, ResetPswActivity.class));
                break;
            case R.id.ll_update:
                new Update(SettingActivity.this).update();
                break;
            case R.id.ll_logout:
                MyApplication.mUser = null;
                finish();
                break;
        }
    }
}
