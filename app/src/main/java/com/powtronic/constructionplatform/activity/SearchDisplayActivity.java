package com.powtronic.constructionplatform.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.fragment.WareListFragment;

public class SearchDisplayActivity extends AppCompatActivity {

    private FragmentManager fm;
    private FragmentTransaction tx;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);
        keyword = getIntent().getStringExtra("keyword");

        initView();
    }

    private void initView() {
//        tag = getIntent().getIntExtra("tag", -1);
        fm = getSupportFragmentManager();
        tx = fm.beginTransaction();
        WareListFragment wareListFragment = new WareListFragment();
        tx.add(R.id.fl_module_display, wareListFragment, "wareListFragment");
        Bundle bundle = new Bundle();
        bundle.putString("keyword",keyword);
        wareListFragment.setArguments(bundle);
        tx.commit();
    }
}
