package com.powtronic.constructionplatform.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powtronic.constructionplatform.R;

import java.lang.reflect.Field;

public class BaseActivity extends FragmentActivity {
    public static final int TITLEBAR_SETTINGS = 1;
    public static final int TITLEBAR_BACK_ADD = 2;
    public static final int TITLEBAR_ONLY_BACK = 3;


    public void setTitleBar(String title, int barType) {
//        setStatusBar();
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        TextView tvRight = (TextView) findViewById(R.id.tv_right);
        tvTitle.setText(title);

        if (barType == TITLEBAR_SETTINGS) {
        } else if (barType == TITLEBAR_BACK_ADD) {
            ivBack.setVisibility(View.VISIBLE);
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText("添加");
        } else if (barType == TITLEBAR_ONLY_BACK) {
            ivBack.setVisibility(View.VISIBLE);
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
    }

    /**
     * 设置沉浸式状态栏
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final RelativeLayout linear_bar = (RelativeLayout) findViewById(R.id.nav);
            final int statusHeight = getStatusBarHeight();
            linear_bar.post(new Runnable() {
                @Override
                public void run() {
                    int titleHeight = linear_bar.getLayoutParams().height;
                    android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) linear_bar.getLayoutParams();
                    linear_bar.getLayoutParams().height = statusHeight + titleHeight;
                    linear_bar.setPadding(0, statusHeight, 0, 0);
                    linear_bar.setLayoutParams(params);
                }
            });
        }
    }

    /**
     * 获取状态栏的高度
     */
    protected int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
