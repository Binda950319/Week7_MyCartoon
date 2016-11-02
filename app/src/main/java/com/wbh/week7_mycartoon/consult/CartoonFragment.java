package com.wbh.week7_mycartoon.consult;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.DongmanBean;
import com.wbh.week7_mycartoon.myasynctask.DownloadJsonTask;
import com.wbh.week7_mycartoon.myinterface.MyInterface;
import com.wbh.week7_mycartoon.url_path.Url;
import com.wbh.week7_mycartoon.utils.ParseJson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartoonFragment extends Fragment implements MyInterface.JsonCallBack, Handler.Callback {


    private PullToRefreshListView head_fefresh_layout;
    private List<DongmanBean> totalList = new ArrayList<>();
    private DongManAdapter adapter;
    private int page = 0;
    private Handler handler = new Handler(this);

    public CartoonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_headline, container, false);
        head_fefresh_layout = ((PullToRefreshListView) view.findViewById(R.id.head_fefresh_layout));
        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        adapter = new DongManAdapter(totalList, getActivity(), R.layout.home_rank_item_layout);
//        head_fefresh_layout.setAdapter(adapter);
//        new DownloadJsonTask(this).execute(Url.getCarttonPagingPath(page));
//        head_fefresh_layout.setMode(PullToRefreshBase.Mode.BOTH);
//        head_fefresh_layout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                handler.sendEmptyMessageDelayed(100, 2000);
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                handler.sendEmptyMessageDelayed(200, 2000);
//            }
//        });
//
//    }

    @Override
    public void jsonCallBack(String json) {
        List<DongmanBean> dongmanBeen = ParseJson.parseDongmanBean(json);
        totalList.addAll(dongmanBeen);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 100:
                head_fefresh_layout.onRefreshComplete();
                break;
            case 200:
                head_fefresh_layout.onRefreshComplete();
                page++;
                new DownloadJsonTask(this).execute(Url.getCarttonPagingPath(page));
                break;
        }
        return true;
    }
}
