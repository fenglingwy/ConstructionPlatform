package com.powtronic.constructionplatform.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.powtronic.constructionplatform.bean.Constants;
import com.powtronic.constructionplatform.MyApplication;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.activity.ContentActivity;
import com.powtronic.constructionplatform.activity.LoginActivity;
import com.powtronic.constructionplatform.activity.SettingActivity;
import com.powtronic.constructionplatform.bean.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by pp on 2017/1/9.
 */

public class MineFragment extends Fragment {

    private View mView;


    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.iv_head)
    de.hdodenhof.circleimageview.CircleImageView mIvHead;
    @BindView(R.id.lv_mine)
    ListView mLvMine;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, mView);

        initView();

        return mView;
    }

    private void initView() {
        showUser();

        ArrayList<Map<String, String>> list = new ArrayList<>();
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("content", "设置");
        list.add(map1);


        mLvMine.setAdapter(new SimpleAdapter(getActivity(), list, R.layout.item_lv_mine, new String[]{"content"},
                new int[]{R.id.tv_content}));
    }

    @OnClick(value = {R.id.iv_head, R.id.tv_username})
    public void toLoginActivity(View view) {
        if (MyApplication.mUser == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent, 101);
        } else {
            Intent intent = new Intent(getActivity(), ContentActivity.class);
            startActivity(intent);
        }
    }


    @OnItemClick(R.id.lv_mine)
    public void lvMine(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            showUser();
        }
    }


    private void showUser() {
        User user = MyApplication.mUser;

        if (user == null) {
            mIvHead.setImageResource(R.mipmap.default_head);
            mTvUsername.setText("点击登陆");

        } else {
//            if(!TextUtils.isEmpty(user.getLogo_url()))
//                Picasso.with(getActivity()).load(Uri.parse(user.getLogo_url())).into(mImageHead);
            mTvUsername.setText(user.getUsername());
            if (TextUtils.isEmpty(user.getHead_photo())) {
                mIvHead.setImageResource(R.mipmap.default_head);
            } else {
                Log.d("TAG", "showUser: "+Constants.IMAGE_URL_ + user.getHead_photo());
                Picasso.with(getActivity()).load(Constants.IMAGE_URL_ + user.getHead_photo().replace("\\","/")).into(mIvHead);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showUser();
    }
}
