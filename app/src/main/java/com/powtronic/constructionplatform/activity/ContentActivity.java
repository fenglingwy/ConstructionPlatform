package com.powtronic.constructionplatform.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.fragment.PersonalFragment;

public class ContentActivity extends FragmentActivity {

    private FragmentManager fm;
    private FragmentTransaction tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initView();
    }
    private void initView() {
        fm = getSupportFragmentManager();
        tx = fm.beginTransaction();
        PersonalFragment personalFragment = new PersonalFragment();
        tx.add(R.id.fl_content, personalFragment, "personal");
        tx.commit();
    }
}
