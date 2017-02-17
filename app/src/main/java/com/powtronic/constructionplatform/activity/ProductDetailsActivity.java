package com.powtronic.constructionplatform.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.powtronic.constructionplatform.Constants;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends BaseActivity {

    @BindView(R.id.wv_product_details)
    WebView wvProductDetails;
    private Product product;
    private WebAppInterface mAppInterfce;


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
        WebSettings settings = wvProductDetails.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);

        mAppInterfce = new WebAppInterface(this);
        wvProductDetails.addJavascriptInterface(mAppInterfce,"appInterface");
        wvProductDetails.setWebViewClient(new WC());

        wvProductDetails.loadUrl(Constants.DETAIL_URL);

//        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        wvProductDetails.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
    }


    class  WC extends WebViewClient{

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mAppInterfce.showDetail();
        }
    }

    class WebAppInterface{
        private Context mContext;
        public WebAppInterface(Context context){
            mContext = context;
        }

        @JavascriptInterface
        public  void showDetail(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("TAG", "run: ");
                    wvProductDetails.loadUrl("javascript:showDetail(30)");
                }
            });
        }

    }

}
