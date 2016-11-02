package com.wbh.week7_mycartoon.consult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.url_path.Url;

public class HeadDetailActivity extends AppCompatActivity {

    private WebView headline_detail_wb;
    private Intent intent;
    private ImageView head_actionBar_iv;
    private TextView head_title_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_detail);
        headline_detail_wb = (WebView) findViewById(R.id.headline_detail_wb);
        head_actionBar_iv = (ImageView) findViewById(R.id.head_actionBar_iv);
        head_title_tv = (TextView) findViewById(R.id.head_title_tv);
        intent = getIntent();
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        head_title_tv.setText(title);

        headline_detail_wb.loadUrl(Url.getHeadDetailsPath(id));
        WebSettings settings = headline_detail_wb.getSettings();
        //支持js 语言
        settings.setJavaScriptEnabled(true);
        // 在当前WebView 内部展示加载的 Url
        headline_detail_wb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
//        headline_detail_wb.loadData(Url.getHeadDetailsPath(id), "text/html;charset=UTF-8", null);
        initClick();
    }

    private void initClick() {
        head_actionBar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
