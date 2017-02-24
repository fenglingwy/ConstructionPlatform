package com.powtronic.constructionplatform.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.powtronic.constructionplatform.Constants;
import com.powtronic.constructionplatform.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pp on 2017/2/17.
 */
public class DetailFragment extends Fragment {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, v);

        initView();
        return v;
    }

    private void initView() {
        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);

//        mAppInterfce = new WebAppInterface(this);
//        wvProductDetails.addJavascriptInterface(mAppInterfce, "appInterface");
//        wvProductDetails.setWebViewClient(new WC());

        webView.loadUrl(Constants.DETAIL_URL);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

//    class WC extends WebViewClient {
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            mAppInterfce.showDetail();
//        }
//    }
//
//    class WebAppInterface {
//        private Context mContext;
//
//        public WebAppInterface(Context context) {
//            mContext = context;
//        }
//
//        @JavascriptInterface
//        public void showDetail() {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Log.d("TAG", "run: ");
//                    wvProductDetails.loadUrl("javascript:showDetail(30)");
//                }
//            });
//        }
//
//    }
//
}
