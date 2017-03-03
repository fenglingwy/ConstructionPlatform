package com.powtronic.constructionplatform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.activity.ProductListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by pp on 2017/1/9.
 */

public class ProductMenuFragment extends BaseFragment{

    private View mView;

    @BindView(R.id.lv_product) ListView mLvProduct;
    private ArrayList<Map<String, String>> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_product, container,false);
      setTitleBar(mView,"我的设备",TITLEBAR_FRAGMENT_ONLY_TITLE);
        ButterKnife.bind(this,mView);
        initView();
        return mView;
    }

    private void initView() {
        list = new ArrayList<>();
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("name","待出售");
        list.add(map1);
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("name","待出租");
        list.add(map2);
        HashMap<String, String> map3 = new HashMap<>();
        map3.put("name","已出售");
        list.add(map3);
        HashMap<String, String> map4 = new HashMap<>();
        map4.put("name","已出租");
        list.add(map4);


        mLvProduct.setAdapter(new SimpleAdapter(getActivity(), list,R.layout.item_menu_product_menu,new String[]{"name"},
                new int[]{R.id.tv_item_name}));

    }

    @OnItemClick(R.id.lv_product)
    public void onItemClick(int position){
        Intent intent = new Intent(getActivity(), ProductListActivity.class);
//        if(position==2||position==3){
//            intent.putExtra("type",1);
//        }
        intent.putExtra("type",list.get(position).get("name"));
        startActivity(intent);
    }

}
