package com.wbh.week7_mycartoon.consult;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.DuanBean;
import com.wbh.week7_mycartoon.myasynctask.DownloadJsonTask;
import com.wbh.week7_mycartoon.myinterface.MyInterface;
import com.wbh.week7_mycartoon.url_path.Url;
import com.wbh.week7_mycartoon.utils.ParseJson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DuanFragment extends Fragment implements MyInterface.JsonCallBack {


    private PullToRefreshListView head_fefresh_layout;
    private List<DuanBean> totalList = new ArrayList<>();
    private DuanZiAdapter adapter;
    private int page = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    head_fefresh_layout.onRefreshComplete();
                    break;
                case 200:
                    head_fefresh_layout.onRefreshComplete();
                    page++;
                    new DownloadJsonTask(DuanFragment.this).execute(Url.getJokePadingPath(page));
                    break;
            }
        }
    };
    private View itemView;
    private TextView con_duanzi_comment;
    private RelativeLayout duanzi_rl;
    private boolean isClicked = false;
    private RadioButton duanzi_comment_draw;

    public DuanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_headline, container, false);
        head_fefresh_layout = ((PullToRefreshListView) view.findViewById(R.id.head_fefresh_layout));
        itemView = View.inflate(getActivity(), R.layout.duanzi_item_layout, null);
        con_duanzi_comment = ((TextView) itemView.findViewById(R.id.con_duanzi_comment));
        duanzi_rl = ((RelativeLayout) itemView.findViewById(R.id.duanzi_rl));
        duanzi_comment_draw = ((RadioButton) itemView.findViewById(R.id.duanzi_comment_draw));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new DuanZiAdapter(totalList, getActivity(), R.layout.duanzi_item_layout);
        head_fefresh_layout.setAdapter(adapter);
        new DownloadJsonTask(this).execute(Url.getJokePadingPath(page));

        initRefresh();
        initClick();
    }

    private void initClick() {
        duanzi_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duanzi_comment_draw.setChecked(false);
            }
        });
    }

    private void initRefresh() {
        head_fefresh_layout.setMode(PullToRefreshBase.Mode.BOTH);
        head_fefresh_layout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                handler.sendEmptyMessageDelayed(100, 2000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                handler.sendEmptyMessageDelayed(200, 2000);
            }
        });
    }


    @Override
    public void jsonCallBack(String json) {
        List<DuanBean> duanBeen = ParseJson.parseDuanBean(json);
        totalList.addAll(duanBeen);
        adapter.notifyDataSetChanged();
    }
}
