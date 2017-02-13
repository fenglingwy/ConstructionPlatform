package com.powtronic.constructionplatform.view;

import android.os.CountDownTimer;
import android.widget.TextView;


public class CountTimerView extends CountDownTimer {

    public static final int TIME_COUNT = 60000;
    private TextView btn;
    private String endStr;

    public CountTimerView(long millisInFuture, long countDownInterval, TextView btn, String endStr) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
        this.endStr = endStr;
    }

    public CountTimerView(TextView btn, String endStr) {
        super(TIME_COUNT, 1000);
        this.btn = btn;
        this.endStr = endStr;
    }

    public CountTimerView(TextView btn) {
        super(TIME_COUNT, 1000);
        this.btn = btn;
        this.endStr = "重新获取";
    }

    // 计时完毕时触发
    @Override
    public void onFinish() {

        btn.setText(endStr);
        btn.setEnabled(true);
    }

    // 计时过程显示
    @Override
    public void onTick(long millisUntilFinished) {
        btn.setEnabled(false);
        btn.setText(millisUntilFinished / 1000 + " 秒后重新发送");

    }
}
