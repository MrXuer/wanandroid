package us.xingkong.wanandroid.layout;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import us.xingkong.wanandroid.R;

public class Webview extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView webView;
    private ProgressBar progressBar;
    private String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar = findViewById(R.id.progress);
        webView = findViewById(R.id.webView);
        webView.setInitialScale(85);
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(false);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient());
        //bannerBean.data url = (bannerBean.data) getIntent().getSerializableExtra("url");
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
        //webView.loadUrl(url.getUrl());
        //webView.loadUrl(urlString);
        //Log.i("url", urlString);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestCode", String.valueOf(requestCode));
        switch (requestCode) {
            case 0:
                //bannerBean.data url0 = (bannerBean.data) data.getSerializableExtra("url");
                //urlString = url0.getUrl();
                urlString = data.getStringExtra("url");
                break;
            case 2:
                searchBean.datas url2 = (searchBean.datas) data.getSerializableExtra("url");
                urlString = url2.getLink();
                break;
        }
        webView.loadUrl(urlString);
        Log.i("url", urlString);
    }*/

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
        webView.destroy();
        urlString = "";

    }
}
