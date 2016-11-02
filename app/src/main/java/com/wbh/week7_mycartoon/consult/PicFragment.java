package com.wbh.week7_mycartoon.consult;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.PicBean;
import com.wbh.week7_mycartoon.myasynctask.DownloadJsonTask;
import com.wbh.week7_mycartoon.myinterface.MyInterface;
import com.wbh.week7_mycartoon.url_path.Url;
import com.wbh.week7_mycartoon.utils.ParseJson;

import java.util.ArrayList;
import java.util.List;

public class PicFragment extends Fragment implements MyInterface.JsonCallBack {
    private View view;
    private PullToRefreshGridView pic_refresh_layout;
    private GridView pic_gridView;
    private ArrayList<PicBean> totalList = new ArrayList<>();
    private PicAdapter adapter;
    private int page = 0;
    private ArrayList<String> pics = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    pic_refresh_layout.onRefreshComplete();
                    break;
                case 200:
                    pic_refresh_layout.onRefreshComplete();
                    page++;
                    new DownloadJsonTask(PicFragment.this).execute(Url.getPicturePagingPath(page));
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Bing", "onCreateView: ");
//        if (view == null) {//每次执行到onCreateView时防止listView会重复加载数据
            view = inflater.inflate(R.layout.fragment_pic, container, false);
            pic_refresh_layout = ((PullToRefreshGridView) view.findViewById(R.id.pic_refresh_layout));
            adapter = new PicAdapter(totalList, getActivity(), R.layout.pic_item_layout);
            pic_refresh_layout.setAdapter(adapter);
            new DownloadJsonTask(this).execute(Url.getPicturePagingPath(page));
            pic_refresh_layout.setMode(PullToRefreshBase.Mode.BOTH);
            pic_refresh_layout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                    handler.sendEmptyMessageDelayed(100, 2000);
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                    handler.sendEmptyMessageDelayed(200, 2000);
                }
            });

            initClick();
//        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Bing", "onActivityCreated: ");
    }

    private void initClick() {

        pic_refresh_layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PicDetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("pics", pics);
                startActivity(intent);
            }
        });
    }

    @Override
    public void jsonCallBack(String json) {
        List<PicBean> picBean = ParseJson.parsePicBean(json);
        totalList.addAll(picBean);
        adapter.notifyDataSetChanged();

        for (int i = 0; i < picBean.size(); i++) {
            pics.add(picBean.get(i).getPicture_x());
        }
    }
}
