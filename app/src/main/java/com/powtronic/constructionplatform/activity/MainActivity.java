package com.powtronic.constructionplatform.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.powtronic.constructionplatform.MyApplication;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.fragment.HomeFragment;
import com.powtronic.constructionplatform.fragment.MineFragment;
import com.powtronic.constructionplatform.fragment.ProductMenuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {

    private android.support.v4.app.FragmentManager fm;
    private android.support.v4.app.FragmentTransaction tx;
    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private ProductMenuFragment productFragment;

    @BindView(R.id.tv_home)
    TextView mTvHome;
    @BindView(R.id.tv_mine)
    TextView mTvMine;
    @BindView(R.id.tv_product)
    TextView mTvProduct;
    @BindView(R.id.btn_home)
    Button mBtnHome;
    @BindView(R.id.btn_product)
    Button mBtnProduct;
    @BindView(R.id.btn_mine)
    Button mBtnMine;
    private long exitTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new Update(this).update();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setSelector(mBtnHome, mTvHome);
        fm = getSupportFragmentManager();
        tx = fm.beginTransaction();
        if (homeFragment == null) homeFragment = new HomeFragment();
        tx.add(R.id.fl_main, homeFragment, "HOME");
        tx.commit();
    }


    private void setSelector(Button btn, TextView tv) {
        mBtnHome.setSelected(false);
        mBtnMine.setSelected(false);
        mBtnProduct.setSelected(false);
        mTvHome.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray));
        mTvProduct.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray));
        mTvMine.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray));
        tv.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlue));
        btn.setSelected(true);
    }

    @OnClick(value = {R.id.btn_home, R.id.tv_home, R.id.ll_home})
    public void toHome(View v) {
        tx = fm.beginTransaction();
        if (homeFragment == null) homeFragment = new HomeFragment();
        tx.replace(R.id.fl_main, homeFragment, "HOME");
        setSelector(mBtnHome, mTvHome);
        tx.commit();
    }

    @OnClick(value = {R.id.btn_product, R.id.tv_product, R.id.ll_product})
    public void toProduct(View v) {
        if (MyApplication.mUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 102);
            return;
        }

        tx = fm.beginTransaction();
        if (productFragment == null) productFragment = new ProductMenuFragment();
        tx.replace(R.id.fl_main, productFragment, "PRODUCT");
        setSelector(mBtnProduct, mTvProduct);
        tx.commit();
    }

    @OnClick(value = {R.id.btn_mine, R.id.tv_mine, R.id.ll_mine})
    public void toMine(View v) {
        tx = fm.beginTransaction();
        if (mineFragment == null) mineFragment = new MineFragment();
        tx.replace(R.id.fl_main, mineFragment, "MINE");
        setSelector(mBtnMine, mTvMine);
        tx.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == RESULT_OK && MyApplication.mUser != null) {
            Log.d("TAG", "onActivityResult: " + requestCode + "     resultCode:" + resultCode);
            tx = fm.beginTransaction();
            if (productFragment == null) productFragment = new ProductMenuFragment();
            tx.replace(R.id.fl_main, productFragment, "PRODUCT");
            setSelector(mBtnProduct, mTvProduct);
            tx.commit();
        }
    }

    //双击退出
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
