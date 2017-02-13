package com.powtronic.constructionplatform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powtronic.constructionplatform.R;

import butterknife.ButterKnife;

/**
 * Created by pp on 2017/1/20.
 */

public class SecurityFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supervise,container,false);
        ButterKnife.bind(this,view);
        setTitleBar(view,"安检单位",TITLEBAR_FRAGMENT_ONLY_BACK);
        return view;
    }
}
