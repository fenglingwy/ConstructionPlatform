package com.powtronic.constructionplatform.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends BaseActivity {

    @BindView(R.id.wv_product_details)
    WebView wvProductDetails;
    private Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setTitleBar("设备详情",TITLEBAR_ONLY_BACK);
        ButterKnife.bind(this);

        product = (Product) getIntent().getSerializableExtra("product");

        initView();
    }

    private void initView() {
        wvProductDetails.loadUrl("http://112.124.22.238:8081/course_api/wares/detail.html");

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wvProductDetails.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
