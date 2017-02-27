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
import com.powtronic.constructionplatform.Constants;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.activity.ProductDetailsActivity;
import com.powtronic.constructionplatform.activity.SearchActivity;
import com.powtronic.constructionplatform.adapter.SaleAdapter;
import com.powtronic.constructionplatform.bean.HttpMsg;
import com.powtronic.constructionplatform.bean.Product;

import java.util.ArrayList;
import java.util.List;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

//        products.add(new Product(0, "挖掘装载机", "10000", Constants.IMAGE_URL_+"upLoading/photo/ware01.jpg"));
//        products.add(new Product(1, "施工升降机", "8888", Constants.IMAGE_URL_+"upLoading/photo/ware02.jpg"));
//        products.add(new Product(2, "高空作业车", "88822",  Constants.IMAGE_URL_+"upLoading/photo/ware03.jpg"));
//        products.add(new Product(3, "钢筋预应力机械", "218989", Constants.IMAGE_URL_+"upLoading/photo/ware04.jpg"));
//        products.add(new Product(4, "钢筋连接机械", "121245",  Constants.IMAGE_URL_+"upLoading/photo/ware05.jpg"));
//        products.add(new Product(5, "振动桩锤", "689521",  Constants.IMAGE_URL_+"upLoading/photo/ware06.jpg"));
//        products.add(new Product(6, "塔式起重机", "882188",  Constants.IMAGE_URL_+"upLoading/photo/ware07.jpg"));
//        products.add(new Product(7, "沥青混凝土摊铺机", "652189", Constants.IMAGE_URL_+"upLoading/photo/ware08.jpg"));
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
                        products.add(0,new Product(8, "new data", "111","upLoading/photo/new.jpg"));
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
        startActivity(intent);
    }


    @OnClick({R.id.left, R.id.right,R.id.ll_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                getActivity().onBackPressed();
                break;
            case R.id.right:
                break;
            case R.id.ll_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type", 201);
                startActivity(intent);
                break;
        }
    }

    public void getData(){
        OkGo.get(Constants.GET_DATA_URL).execute(new DialogCallback<HttpMsg>(getActivity()) {
            @Override
            public void onSuccess(HttpMsg httpMsg, Call call, Response response) {
                String data = httpMsg.getData();
                Gson gson = new Gson();
                ArrayList<Product> datas = gson.fromJson(data, new TypeToken<List<Product>>() {
                }.getType());
                products.clear();
                products.addAll(datas);
                for(Product pro:datas){
                    Log.d("TAG", "onSuccess: " + pro);
                }
                saleAdapter.notifyDataSetChanged();
            }
        });
    }
}
