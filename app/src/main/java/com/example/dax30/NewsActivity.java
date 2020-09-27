package com.example.dax30;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.security.auth.callback.Callback;

public class NewsActivity extends AppCompatActivity {

    WebView webView;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        name = getIntent().getStringExtra("SHARE_NAME");

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new Callback());
        webView.loadUrl("https://news.google.com/search?for=" + name + "%20aktie&hl=de&gl=DE&ceid=DE%3Ade");
    }

    private class Callback extends WebViewClient {
        public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent event) {
            return false;
        }
    }
}