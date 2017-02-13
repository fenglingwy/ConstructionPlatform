package com.powtronic.constructionplatform.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powtronic.constructionplatform.R;

import butterknife.OnClick;

/**
 * Created by pp on 2017/1/12.
 */

public class BaseFragment extends Fragment {

    public static final int TITLEBAR_FRAGMENT_ONLY_TITLE = 1;
    public static final int TITLEBAR_FRAGMENT_BACK_REFRESH = 2;
    public static final int TITLEBAR_FRAGMENT_ONLY_BACK = 3;


    public void setTitleBar(View view, String title, int barType) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ImageView ivBack = (ImageView) view.findViewById(R.id.iv_back);
        tvTitle.setText(title);


        if (barType == TITLEBAR_FRAGMENT_ONLY_TITLE) {

        } else if (barType == TITLEBAR_FRAGMENT_BACK_REFRESH) {
        } else if (barType == TITLEBAR_FRAGMENT_ONLY_BACK) {
            ivBack.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.iv_back)
    public void onBack(View v) {
        getActivity().onBackPressed();
    }

    public void onRight(View v) {

    }


}
