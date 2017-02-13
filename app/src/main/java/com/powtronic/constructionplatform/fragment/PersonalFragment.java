package com.powtronic.constructionplatform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powtronic.constructionplatform.MyApplication;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pp on 2017/1/12.
 */

public class PersonalFragment extends BaseFragment {

    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.ll_username)
    LinearLayout llUsername;
    @BindView(R.id.tv_reality_name)
    TextView tvRealityName;
    @BindView(R.id.ll_reality_name)
    LinearLayout llRealityName;
    @BindView(R.id.tv_mobile_phone)
    TextView tvMobilePhone;
    @BindView(R.id.ll_mobile_phone)
    LinearLayout llMobilePhone;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.ll_id)
    LinearLayout llId;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_info, container, false);
        ButterKnife.bind(this, view);
        setTitleBar(view, "我的资料", TITLEBAR_FRAGMENT_ONLY_BACK);
        initView();
        return view;
    }

    private void initView() {
        User mUser = MyApplication.mUser;
        tvAddress.setText(mUser.getAddress());
        tvEmail.setText(mUser.getEmail());
        tvId.setText(mUser.getIdcard());
        tvMobilePhone.setText(mUser.getMobilePhone());
        tvPhone.setText(mUser.getPhone());
        tvRealityName.setText(mUser.getRealityName());
        tvUsername.setText(mUser.getUsername());
    }

    @OnClick({R.id.ll_head, R.id.ll_username, R.id.ll_reality_name, R.id.ll_mobile_phone, R.id.ll_id,
            R.id.ll_phone, R.id.ll_email, R.id.ll_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head:

                break;
            case R.id.ll_username:
                break;
            case R.id.ll_reality_name:
                break;
            case R.id.ll_mobile_phone:
                break;
            case R.id.ll_id:
                break;
            case R.id.ll_phone:
                break;
            case R.id.ll_email:
                break;
            case R.id.ll_address:
                break;
        }
    }
}
