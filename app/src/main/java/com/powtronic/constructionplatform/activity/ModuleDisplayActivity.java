package com.powtronic.constructionplatform.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.fragment.SecurityFragment;
import com.powtronic.constructionplatform.fragment.SuperviseFragment;
import com.powtronic.constructionplatform.fragment.WareListFragment;

public class ModuleDisplayActivity extends FragmentActivity {

    private FragmentManager fm;
    private FragmentTransaction tx;
    private int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_display);

        initView();
    }

    private void initView() {
        tag = getIntent().getIntExtra("tag", -1);
        fm = getSupportFragmentManager();
        tx = fm.beginTransaction();
        if (tag == 4) {
            SuperviseFragment superviseFragment = new SuperviseFragment();
            tx.add(R.id.fl_search, superviseFragment, "superviseFragment");
        } else if( tag == 5){
            SecurityFragment securityFragment = new SecurityFragment();
            tx.add(R.id.fl_search, securityFragment, "securityFragment");
        }else {
            WareListFragment salefragment = new WareListFragment();
            tx.add(R.id.fl_search, salefragment, "salefragment");
        }
        tx.commit();
    }
}
