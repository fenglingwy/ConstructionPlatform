package com.powtronic.constructionplatform.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.powtronic.constructionplatform.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class URLSetting extends AppCompatActivity {

    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_port)
    EditText etPort;
    @BindView(R.id.set)
    Button set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlsetting);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        etAddress.setText("10.0.0.199");
        etPort.setText("80");
    }

}
