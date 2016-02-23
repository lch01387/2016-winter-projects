package com.example.administrator.noticelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class WebPage extends AppCompatActivity {
    Intent in_get;
    String url;

    private static final FrameLayout.LayoutParams ZOOM_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,  Gravity.BOTTOM );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

        in_get = getIntent();
        url = in_get.getStringExtra("URL");

        WebView browser=(WebView)findViewById(R.id.webkit);
        browser.loadUrl(url);
        browser.getSettings().setBuiltInZoomControls(true);
        browser.setWebViewClient(new WebViewClient());
        browser.setInitialScale(150);
    }
}