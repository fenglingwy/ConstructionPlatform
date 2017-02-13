package com.powtronic.constructionplatform.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.fragment.WareListFragment;

import static android.R.attr.tag;

public class SearchDisplayActivity extends AppCompatActivity {

    private FragmentManager fm;
    private FragmentTransaction tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);

        initView();
    }

    private void initView() {
//        tag = getIntent().getIntExtra("tag", -1);
        fm = getSupportFragmentManager();
        tx = fm.beginTransaction();
            WareListFragment salefragment = new WareListFragment();
            tx.add(R.id.fl_search, salefragment, "salefragment");
        tx.commit();
    }
}
