package com.powtronic.constructionplatform.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.powtronic.constructionplatform.Callback.DialogCallback;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.adapter.ProductListAdapter;
import com.powtronic.constructionplatform.bean.Constants;
import com.powtronic.constructionplatform.bean.HttpMsg;
import com.powtronic.constructionplatform.bean.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import okhttp3.Call;
import okhttp3.Response;

public class ProductListActivity extends BaseActivity {

    @BindView(R.id.lv_product_list)
    ListView mLvProduct;
    @BindView(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;
    private ProductListAdapter adapter;
    private ArrayList<Product> products = new ArrayList<>();
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        type = getIntent().getStringExtra("type");

        ButterKnife.bind(this);
        initView();
        getData();
    }

    private void initView() {
        setTitle();
        adapter = new ProductListAdapter(this, products);
        mLvProduct.setAdapter(adapter);

        initRefreshView();
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
                        products.add(0, new Product(8, "new data", "10000", "upLoading\\photo\\new.jpg"));
                        adapter.notifyDataSetChanged();
                        materialRefreshLayout.finishRefresh();
                        mLvProduct.smoothScrollToPosition(0);
                    }
                }, 1000);
                materialRefreshLayout.finishRefreshLoadMore();
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();
                        products.add(new Product(8, "new data", "111", "upLoading\\photo\\new.jpg"));
                        adapter.notifyDataSetChanged();
                        mLvProduct.smoothScrollToPosition(products.size());
                    }
                }, 1000);
                materialRefreshLayout.finishRefresh();
            }

            @Override
            public void onfinish() {
                Toast.makeText(ProductListActivity.this, "finish", Toast.LENGTH_LONG).show();
            }

        });
    }


    @OnItemClick(R.id.lv_product_list)
    public void onItemClick(int position) {
        Product product = (Product) mLvProduct.getAdapter().getItem(position);
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("product", product);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    @OnItemLongClick(R.id.lv_product_list)
    public boolean onItemLongClick(int position) {

        showNoticeDialog(position);
        return true;
    }

    private void showNoticeDialog(final int position) {
        // 构造对话框
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确认删除该产品!")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        products.remove(position);
                        adapter.notifyDataSetChanged();
//                        mLvProduct.smoothScrollToPosition(0);
                    }
                }).show();
    }

    private void setTitle() {
        setTitleBar(type, TITLEBAR_BACK_ADD);
    }

    @OnClick(R.id.tv_right)
    public void add() {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivityForResult(intent, 202);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Product product = (Product) data.getSerializableExtra("data");
            products.add(0, product);
            adapter.notifyDataSetChanged();


        }
    }

    public void getData(){
        //TODO 分类搜索数据
        String url = "";
        GetRequest request=null;
        switch (type){
            case "待出售":
                request = OkGo.get(Constants.GET_DATA_URL);
                break;
            case "待出租":
                request = OkGo.get(Constants.GET_DATA_URL);
                break;
            case "已出售":
               request = OkGo.get(Constants.GET_SOLD_DATA_URL)
                        .params("user_id", 1)
                        .params("sales_type", 1);
                break;
            case "已出租":
                request = OkGo.get(Constants.GET_SOLD_DATA_URL)
                        .params("user_id", 1)
                        .params("sales_type", 2);
                break;
        }

        request.execute(new DialogCallback<HttpMsg>(this) {
            @Override
            public void onSuccess(HttpMsg httpMsg, Call call, Response response) {
                if(httpMsg==null){
                    Toast.makeText(ProductListActivity.this,"网络异常!",Toast.LENGTH_SHORT).show();
                    return;
                }
                String data = httpMsg.getData();
                Gson gson = new Gson();
                ArrayList<Product> datas = gson.fromJson(data, new TypeToken<List<Product>>() {
                }.getType());
                products.clear();
                products.addAll(datas);
                for(Product pro:datas){
                    Log.d("TAG", "onSuccess: " + pro);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

}
