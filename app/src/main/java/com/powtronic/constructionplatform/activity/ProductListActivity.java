package com.powtronic.constructionplatform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.powtronic.constructionplatform.Constants;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.adapter.ProductListAdapter;
import com.powtronic.constructionplatform.bean.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class ProductListActivity extends BaseActivity {

    @BindView(R.id.lv_product_list)
    ListView mLvProduct;
    @BindView(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;
    private ProductListAdapter adapter;
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle();
        products.add(new Product(0, "挖掘装载机", "10000", Constants.IMAGE_URL + "ware01.jpg"));
        products.add(new Product(1, "施工升降机", "8888", Constants.IMAGE_URL + "ware02.jpg"));
        products.add(new Product(2, "高空作业车", "88822", Constants.IMAGE_URL + "ware03.jpg"));
        products.add(new Product(3, "钢筋预应力机械", "218989", Constants.IMAGE_URL + "ware04.jpg"));
        products.add(new Product(4, "钢筋连接机械", "121245", Constants.IMAGE_URL + "ware05.jpg"));
        products.add(new Product(5, "振动桩锤", "689521", Constants.IMAGE_URL + "ware06.jpg"));
        products.add(new Product(6, "塔式起重机", "882188", Constants.IMAGE_URL + "ware07.jpg"));
        products.add(new Product(7, "沥青混凝土摊铺机", "652189", Constants.IMAGE_URL + "ware08.jpg"));

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
                        products.add(0, new Product(8, "new data", "111", Constants.IMAGE_URL + "new.jpg"));
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
                        products.add(new Product(8, "new data", "111", Constants.IMAGE_URL + "new.jpg"));
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
    public void onItemClick(int postion) {
        Product product = (Product) mLvProduct.getAdapter().getItem(postion);
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @OnItemLongClick(R.id.lv_product_list)
    public boolean onItemLongClick(int postion) {


        return false;
    }

    private void setTitle() {
        int id = getIntent().getIntExtra("id", -1);
        String text = "";
        if (id == 0) text = "待出售";
        if (id == 1) text = "待出租";
        if (id == 2) text = "已出售";
        if (id == 3) text = "已出租";
        setTitleBar(text, TITLEBAR_BACK_ADD);
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
            products.add(0,product);
            adapter.notifyDataSetChanged();


        }
    }

}
