package com.powtronic.constructionplatform.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.fragment.MaintainAddFragment;
import com.powtronic.constructionplatform.fragment.MaintainListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaintainActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain);
        setTitleBar("设备维修", TITLEBAR_ONLY_BACK);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        initViewPager();
        initTab();
        tab.setupWithViewPager(vp);
    }

    private void initTab() {
        tab.addTab(tab.newTab());
        tab.addTab(tab.newTab());
    }

    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> tabTitles = new ArrayList<>();
        fragments.add(new MaintainAddFragment());
        fragments.add(new MaintainListFragment());

        tabTitles.add("添加任务");
        tabTitles.add("任务列表");

        PagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, tabTitles);
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
