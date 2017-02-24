package com.powtronic.constructionplatform.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
public class DataFragment extends Fragment {

    @BindView(R.id.lv_data)
    ListView lvData;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, v);

        initView();
        return v;
    }

    private void initView() {
        tvProductName.setText("塔式起重机");

        List<CurrentData> datas = new ArrayList<>();
        datas.add(new CurrentData("工作幅度", "1"));
        datas.add(new CurrentData("负载重量", "2T"));
        datas.add(new CurrentData("起重高度", "20m"));
        datas.add(new CurrentData("起重力矩", "20N/m"));
        datas.add(new CurrentData("回转角度", "5度"));
        datas.add(new CurrentData("实时风速", "2级"));
        lvData.setAdapter(new CurrentDataAdapter(getActivity(), datas));
    }
}
