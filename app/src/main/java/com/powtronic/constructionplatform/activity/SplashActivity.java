package com.powtronic.constructionplatform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.powtronic.constructionplatform.R;

public class SplashActivity extends FragmentActivity implements View.OnClickListener {

    private Handler handler;
    private int count = 3;
    private TextView mTvJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        initView();
        delayEnterHome();
    }

    private void initView() {
        mTvJump = (TextView) findViewById(R.id.tv_splash);
        mTvJump.setOnClickListener(this);
    }

    /**
     * 延迟3秒后进入首页
     */
    private void delayEnterHome() {
        handler = new Handler();
        handler.postDelayed(new InnerRunnable(), 1000);
    }

    /**
     * 进入首页
     */
    private void enterHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 点击跳过
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        handler.removeCallbacksAndMessages(null);
        enterHome();
    }

    /**
     * 3跳过倒计时显示
     */
    class InnerRunnable implements Runnable {

        @Override
        public void run() {
            count -= 1;
            if (count <= 0) {
                enterHome();
                return;
            }
            mTvJump.setText(count + "s跳过");
            handler.postDelayed(this, 1000);
        }
    }
}
