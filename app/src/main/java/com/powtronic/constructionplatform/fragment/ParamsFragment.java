package com.powtronic.constructionplatform.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.adapter.CurrentDataAdapter;
import com.powtronic.constructionplatform.bean.CurrentData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pp on 2017/2/17.
 */
public class ParamsFragment extends Fragment {

    @BindView(R.id.lv_params)
    ListView lvParams;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_params, container, false);
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    private void initView() {
        List<CurrentData> datas = new ArrayList<>();
        Bundle args = getArguments();
        int i = (args == null) ? 0 : 1;
        Log.d("TAG", "initViewPager:    "+ i);
        if (args != null && 1 == args.getInt("type", 0)) {
            datas.add(new CurrentData("商品名称", "塔式起重机"));
            datas.add(new CurrentData("商品编号", "ECS000001"));
            datas.add(new CurrentData("出厂时间", "2017-01-01"));
            datas.add(new CurrentData("购买单位", "万达集团"));
            datas.add(new CurrentData("联系人", "王健林"));
            datas.add(new CurrentData("联系电话", "16888888888"));
            datas.add(new CurrentData("使用地址", "万达广场"));
        } else {
            datas.add(new CurrentData("商品名称", "塔式起重机"));
            datas.add(new CurrentData("商品编号", "ECS000001"));
            datas.add(new CurrentData("上架时间", "2017-01-01"));
            datas.add(new CurrentData("商品重量", "20"));
            datas.add(new CurrentData("商品库存", "1"));
        }


        lvParams.setAdapter(new CurrentDataAdapter(getActivity(), datas));
    }

}
