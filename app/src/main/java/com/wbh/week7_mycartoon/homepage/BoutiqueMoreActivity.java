package com.wbh.week7_mycartoon.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.Cartoon;
import com.wbh.week7_mycartoon.myadapter.RankAdapter;
import com.wbh.week7_mycartoon.myasynctask.DownloadJsonTask;
import com.wbh.week7_mycartoon.myinterface.MyInterface;
import com.wbh.week7_mycartoon.url_path.Url;
import com.wbh.week7_mycartoon.utils.ParseJson;
import com.wbh.week7_mycartoon.utils.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/25.
 */
public class BoutiqueMoreActivity extends Activity implements MyInterface.JsonCallBack {

    private ListView more_listView;
    private RefreshLayout more_refreshLayout;
    private List<Cartoon> totalList = new ArrayList<>();
    private RankAdapter adapter;
    private int pageNum = 0;
    private int id = 1;
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
                    pageNum++;
                    new DownloadJsonTask(BoutiqueMoreActivity.this).execute(Url.getRecommendMore(pageNum, id));
                    break;
            }
        }
    };
    private TextView text_tv;
    private Intent intent;
    private ImageView more_actionBar_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_more);
        text_tv = (TextView) findViewById(R.id.text_tv);
        more_actionBar_iv = (ImageView) findViewById(R.id.more_actionBar_iv);
        more_listView = ((ListView) findViewById(R.id.more_listView));
        more_refreshLayout = ((RefreshLayout) findViewById(R.id.more_refreshLayout));

        intent = getIntent();
        //接收从BoutiqueFragment传过来的id和头标题
        id = intent.getIntExtra("id", 1);
        String text = intent.getStringExtra("text");
        more_listView.setTag(text);
        text_tv.setText(text);

        initView();
        initClick();
    }

    //点击返回主界面
    private void initClick() {
        more_actionBar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initView() {
        adapter = new RankAdapter(totalList, this, R.layout.home_rank_item_layout);
        more_listView.setAdapter(adapter);


        new DownloadJsonTask(this).execute(Url.getRecommendMore(pageNum, id));

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


    //listView
    private void initListView() {
        more_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cartoon rankCartoon = totalList.get(position);
                intent = new Intent(BoutiqueMoreActivity.this, CartoonActivity.class);
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
                String text = (String) more_listView.getTag();
                intent.putExtra("tag", 3);
                intent.putExtra("text", text);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void jsonCallBack(String json) {
        List<Cartoon> rankList = ParseJson.parseCartoon(json);
        totalList.addAll(rankList);
        adapter.notifyDataSetChanged();
    }
}
