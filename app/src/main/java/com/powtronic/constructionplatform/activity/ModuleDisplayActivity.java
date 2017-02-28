package com.powtronic.constructionplatform.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.fragment.LeaseFragment;
import com.powtronic.constructionplatform.fragment.SafeFragment;
import com.powtronic.constructionplatform.fragment.SuperviseFragment;
import com.powtronic.constructionplatform.fragment.WareListFragment;

public class ModuleDisplayActivity extends FragmentActivity {

    private FragmentManager fm;
    private FragmentTransaction tx;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_display);

        initView();
    }

    private void initView() {
        tag = getIntent().getStringExtra("tag");
        fm = getSupportFragmentManager();
        tx = fm.beginTransaction();
//             final String[] strings = {"sale","rent","lease","part","supervise","safe"};
        if("lease".equals(tag)){
            LeaseFragment leaseFragment = new LeaseFragment();
            tx.add(R.id.fl_module_display, leaseFragment, "leaseFragment");
        } else if ("supervise".equals(tag)) {
            SuperviseFragment superviseFragment = new SuperviseFragment();
            tx.add(R.id.fl_module_display, superviseFragment, "superviseFragment");
        } else if("safe".equals(tag)){
            SafeFragment safeFragment = new SafeFragment();
            tx.add(R.id.fl_module_display, safeFragment, "safeFragment");
        }else {
            WareListFragment wareListFragment = new WareListFragment();
            tx.add(R.id.fl_module_display, wareListFragment, "saleFragment");
            Bundle bundle = new Bundle();
            bundle.putString("tag",tag);
            wareListFragment.setArguments(bundle);
        }
        tx.commit();
    }
}
