package com.powtronic.constructionplatform.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.Product;
import com.powtronic.constructionplatform.fragment.DetailFragment;
import com.powtronic.constructionplatform.fragment.ParamsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.powtronic.constructionplatform.R.id.tabLayout;

public class ProductDetailsActivity extends BaseActivity {


    @BindView(tabLayout)
    TabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager vp;

    private Product product;
    private MyPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setTitleBar("设备详情", TITLEBAR_ONLY_BACK);
        ButterKnife.bind(this);

        product = (Product) getIntent().getSerializableExtra("product");

        initViewPager();
        initTab();
        tab.setupWithViewPager(vp);
    }

    private void initTab() {
        tab.addTab(tab.newTab());
        tab.addTab(tab.newTab());
//        tab.addTab(tab.newTab());
    }

    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DetailFragment());
        fragments.add(new ParamsFragment());
//        fragments.add(new DataFragment());
        List<String> tabTitles = new ArrayList<>();
        tabTitles.add("图文详情");
        tabTitles.add("产品参数");
//        tabTitles.add("实时数据");

        adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, tabTitles);
        vp.setAdapter(adapter);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments;
        List<String> titles;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            this.titles = titles;
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }


}
