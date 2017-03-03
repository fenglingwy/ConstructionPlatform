package com.powtronic.constructionplatform.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.adapter.CurrentDataAdapter;
import com.powtronic.constructionplatform.bean.CurrentData;
import com.powtronic.constructionplatform.bean.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    int range, weight, high, moment, angle, wind;
    private CurrentDataAdapter currentDataAdapter;

    Handler handler = new Handler();
    private List<CurrentData> datas;
    private Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, v);

        initView();
        return v;
    }

    private void initView() {
        Product product = (Product) getArguments().getSerializable("product");

        tvProductName.setText(product.getName());

        getData();
        currentDataAdapter = new CurrentDataAdapter(getActivity(), datas);
        lvData.setAdapter(currentDataAdapter);

        startThread();
    }

    private void getData() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.clear();
        datas.add(new CurrentData("工作幅度", range + ""));
        datas.add(new CurrentData("负载重量", weight + "T"));
        datas.add(new CurrentData("起重高度", high + "m"));
        datas.add(new CurrentData("起重力矩", moment + "N/m"));
        datas.add(new CurrentData("回转角度", angle + "度"));
        datas.add(new CurrentData("实时风速", wind + "级"));
    }

    private void startThread() {
        runnable = new Runnable() {
            @Override
            public void run() {
                range = new Random().nextInt(5);
                weight = new Random().nextInt(10);
                high = new Random().nextInt(100);
                moment = new Random().nextInt(100);
                angle = new Random().nextInt(360);
                wind = new Random().nextInt(5);
                getData();
                currentDataAdapter.notifyDataSetChanged();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
