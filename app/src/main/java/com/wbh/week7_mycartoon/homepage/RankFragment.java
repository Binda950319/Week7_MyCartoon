package com.wbh.week7_mycartoon.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class RankFragment extends Fragment implements MyInterface.JsonCallBack {

    private ListView rank_listView;
    private RefreshLayout refreshLayout;
    private List<Cartoon> totalList = new ArrayList<>();
    private RankAdapter adapter;
    private int pageNum = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    refreshLayout.setRefreshing(false);
                    break;
                case 200:
                    refreshLayout.setLoadingState(false);
                    pageNum++;
                    new DownloadJsonTask(RankFragment.this).execute(Url.getRankUrl(pageNum));
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_rank, container, false);
        rank_listView = ((ListView) view.findViewById(R.id.rank_listView));
        refreshLayout = ((RefreshLayout) view.findViewById(R.id.refreshLayout));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new RankAdapter(totalList, getActivity(), R.layout.home_rank_item_layout);
        rank_listView.setAdapter(adapter);
        new DownloadJsonTask(this).execute(Url.getRankUrl(pageNum));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(100, 3000);
            }
        });

        refreshLayout.setOnLoadingListener(new RefreshLayout.OnLoadingListener() {
            @Override
            public void onLoading() {
                handler.sendEmptyMessageDelayed(200, 3000);
            }
        });
        initListView();
    }

    //listView
    private void initListView() {
        rank_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cartoon rankCartoon = totalList.get(position);
                Intent intent = new Intent(getActivity(), CartoonActivity.class);
                String id_ = rankCartoon.getId();
                String name = rankCartoon.getName();
                String author = rankCartoon.getAuthor();
                String state = rankCartoon.getState();
                String icon = rankCartoon.getIcon();
                String theme = rankCartoon.getTheme();
                String ranking = rankCartoon.getRanking();
                String introduction = rankCartoon.getIntroduction();
                intent.putExtra("id", id_);//715
                intent.putExtra("name", name);
                intent.putExtra("author", author);
                intent.putExtra("state", state);
                intent.putExtra("icon", icon);
                intent.putExtra("theme", theme);
                intent.putExtra("ranking", ranking);
                intent.putExtra("introduction", introduction);
                // 传入一个标记,便于返回
                intent.putExtra("tag", 2);
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
        }

    }
}
