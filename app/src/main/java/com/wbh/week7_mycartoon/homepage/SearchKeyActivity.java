package com.wbh.week7_mycartoon.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.Cartoon;
import com.wbh.week7_mycartoon.myadapter.RankAdapter;
import com.wbh.week7_mycartoon.myasynctask.DownloadJsonTask;
import com.wbh.week7_mycartoon.myinterface.MyInterface;
import com.wbh.week7_mycartoon.url_path.Url;
import com.wbh.week7_mycartoon.utils.ParseJson;
import com.wbh.week7_mycartoon.utils.RefreshLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class SearchKeyActivity extends Activity implements MyInterface.JsonCallBack {


    private String path = "http://a121.baopiqi.com/api/mh/getSearchCartoon.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&limit=20&page=0&key=%E4%BC%A0%E8%AF%B4";
    private ListView more_listView;
    private RefreshLayout more_refreshLayout;
    private List<Cartoon> totalList = new ArrayList<>();
    private RankAdapter adapter;
    private String key;
    private TextView text_tv;
    private Intent intent;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    more_refreshLayout.setRefreshing(false);
                    break;
                case 200:
                    more_refreshLayout.setLoadingState(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_more);
        text_tv = (TextView) findViewById(R.id.text_tv);
        more_listView = ((ListView) findViewById(R.id.more_listView));
        more_refreshLayout = ((RefreshLayout) findViewById(R.id.more_refreshLayout));
        initView();
    }





    //接收穿过来的key值并启动异步下载
    public void initView() {
        adapter = new RankAdapter(totalList, this, R.layout.home_rank_item_layout);
        more_listView.setAdapter(adapter);

        intent = getIntent();
        key = intent.getStringExtra("key").trim();
        text_tv.setText(key);
        try {
            //转码
            String decode = URLEncoder.encode(key, "utf-8");
            new DownloadJsonTask(this).execute(Url.getSearchCartoon(decode));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        more_refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(100, 3000);
            }
        });

        more_refreshLayout.setOnLoadingListener(new RefreshLayout.OnLoadingListener() {
            @Override
            public void onLoading() {
                handler.sendEmptyMessageDelayed(200, 3000);
            }
        });
        initListView();
    }

    //点击返回
    public void onClick(View view) {
        finish();
    }

    //listView的点击事件
    private void initListView() {
        more_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cartoon rankCartoon = totalList.get(position);
                intent = new Intent(SearchKeyActivity.this, CartoonActivity.class);
                String id_ = rankCartoon.getId();
                String name = rankCartoon.getName();
                String author = rankCartoon.getAuthor();
                String state = rankCartoon.getState();
                String icon = rankCartoon.getIcon();
                String introduction = rankCartoon.getIntroduction();
                intent.putExtra("id", id_);
                intent.putExtra("name", name);
                intent.putExtra("author", author);
                intent.putExtra("state", state);
                intent.putExtra("icon", icon);
                intent.putExtra("introduction", introduction);
                startActivity(intent);
            }
        });
    }

    @Override
    public void jsonCallBack(String json) {
        List<Cartoon> rankList = ParseJson.parseCartoon(json);
        if (rankList != null && rankList.size() > 0) {
            totalList.addAll(rankList);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(SearchKeyActivity.this, "没有相关内容", Toast.LENGTH_SHORT).show();
        }
    }
}