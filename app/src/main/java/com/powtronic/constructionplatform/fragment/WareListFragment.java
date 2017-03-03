package com.powtronic.constructionplatform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.powtronic.constructionplatform.Callback.DialogCallback;
import com.powtronic.constructionplatform.bean.Constants;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.activity.ProductDetailsActivity;
import com.powtronic.constructionplatform.activity.SearchActivity;
import com.powtronic.constructionplatform.adapter.SaleAdapter;
import com.powtronic.constructionplatform.bean.HttpMsg;
import com.powtronic.constructionplatform.bean.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


public class WareListFragment extends Fragment implements SaleAdapter.OnItemClickListener {


    @BindView(R.id.left)
    ImageView left;
    @BindView(R.id.right)
    ImageView right;
    @BindView(R.id.rv_sale)
    RecyclerView mRvSale;
    @BindView(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;
    private ArrayList<Product> products = new ArrayList<>();
    private SaleAdapter saleAdapter;
    private String keyword;
    private int product_type;
    private int sales_type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            keyword = bundle.getString("keyword");
            product_type = bundle.getInt("product_type", -1);
            sales_type = bundle.getInt("sales_type", -1);
        }

        View view = inflater.inflate(R.layout.fragment_sale_list, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        left.setImageResource(R.drawable.arrow_back);
        initRecyclerView();
        initRefreshView();
        getData();
    }

    private void initRecyclerView() {

        //设置固定大小
        mRvSale.setHasFixedSize(true);
        //创建网格布局
        GridLayoutManager girdLayoutManager = new GridLayoutManager(getActivity(), 2);
        //给RecyclerView设置布局管理器
        mRvSale.setLayoutManager(girdLayoutManager);

        saleAdapter = new SaleAdapter(getActivity(), products);
        saleAdapter.setmOnItemClickListener(this);
        mRvSale.setAdapter(saleAdapter);
    }

    private void initRefreshView() {
        materialRefreshLayout.setWaveShow(true);
        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                        products.add(0, new Product(8, "new data", "111", "upLoading/photo/new.jpg"));
                        saleAdapter.notifyItemInserted(0);
                        mRvSale.scrollToPosition(0);
                    }
                }, 1000);
                materialRefreshLayout.finishRefreshLoadMore();
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                Toast.makeText(getActivity(), "end", Toast.LENGTH_LONG).show();
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();
                        saleAdapter.data.add(new Product(8, "new data", "111", "upLoading/photo/new.jpg"));
                        saleAdapter.notifyItemInserted(saleAdapter.data.size());
                        mRvSale.scrollToPosition(saleAdapter.data.size() - 1);
                    }
                }, 1000);
                materialRefreshLayout.finishRefresh();
            }

            @Override
            public void onfinish() {
                Toast.makeText(getActivity(), "finish", Toast.LENGTH_LONG).show();
            }

        });
    }


    @Override
    public void onItemClick(View view, int position) {
        Product product = products.get(position);
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra("product", product);
        intent.putExtra("type", "待出租");
        startActivity(intent);
    }


    @OnClick({R.id.left, R.id.right, R.id.ll_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                getActivity().onBackPressed();
                break;
            case R.id.right:
                break;
            case R.id.ll_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("product_type", product_type);
                intent.putExtra("sales_type", sales_type);
                startActivity(intent);
                break;
        }
    }

    public void getData() {
        Map<String, String> params = new HashMap<>();
        if (product_type != -1) params.put("product_type", product_type + "");
        if (sales_type != -1) params.put("sales_type", sales_type + "");
        if (keyword != null) params.put("keyword", keyword);

        OkGo.get(Constants.GET_DATA_URL).params(params).execute(new DialogCallback<HttpMsg>(getActivity()) {
            @Override
            public void onSuccess(HttpMsg httpMsg, Call call, Response response) {
                String data = httpMsg.getData();
                Gson gson = new Gson();
                ArrayList<Product> datas = gson.fromJson(data, new TypeToken<List<Product>>() {
                }.getType());
                products.clear();
                products.addAll(datas);
                for (Product pro : datas) {
                    Log.d("TAG", "onSuccess: " + pro);
                }
                saleAdapter.notifyDataSetChanged();
            }
        });
    }
}
