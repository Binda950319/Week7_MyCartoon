package com.wbh.week7_mycartoon.consult;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;
import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.HeadLine;
import com.wbh.week7_mycartoon.myadapter.TitleAdapter;
import com.wbh.week7_mycartoon.myasynctask.DownloadJsonTask;
import com.wbh.week7_mycartoon.myasynctask.DownloadJsonTask2;
import com.wbh.week7_mycartoon.myinterface.MyInterface;
import com.wbh.week7_mycartoon.url_path.Url;
import com.wbh.week7_mycartoon.utils.ParseJson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadlineFragment extends Fragment implements MyInterface.JsonCallBack, MyInterface.JsonCallBack2 {


    private PullToRefreshListView head_fefresh_layout;
    private List<HeadLine> totalList = new ArrayList<>();
    private List<HeadLine> headList = new ArrayList<>();
    private List<HeadLine> totalHead = new ArrayList<>();

    private HeadlineAdapter adapter, headAdapter;
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
                    new DownloadJsonTask(HeadlineFragment.this).execute(Url.getHeadLinePath(page));
                    break;
                case 300:
                    consult_title_view.setCurrentItem(consult_title_view.getCurrentItem() + 1);
                    break;
            }
        }
    };
    private View headView;
    private ViewPager consult_title_view;
    private TextView consult_title_name_tv;
    private LinearLayout consult_dot_linear;
    private List<View> list = new ArrayList<>();
    private int prePosition;
    private boolean isRunning = true;
    private Intent intent;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {

            view = inflater.inflate(R.layout.fragment_headline, container, false);
            head_fefresh_layout = ((PullToRefreshListView) view.findViewById(R.id.head_fefresh_layout));
            headView = View.inflate(getActivity(), R.layout.consult_headtitle_headview, null);
            consult_title_view = ((ViewPager) headView.findViewById(R.id.consult_title_view));
            consult_title_name_tv = ((TextView) headView.findViewById(R.id.consult_title_name_tv));
            consult_dot_linear = ((LinearLayout) headView.findViewById(R.id.consult_dot_linear));
            //增加头布局
            head_fefresh_layout.getRefreshableView().addHeaderView(headView);
            adapter = new HeadlineAdapter(totalList, getActivity(), R.layout.headline_item_layout);
            head_fefresh_layout.setAdapter(adapter);
            new DownloadJsonTask(this).execute(Url.getHeadLinePath(page));


            new DownloadJsonTask2(this).execute(Url.getHeadLinePath(0));

            initRefresh();
            initListView();
            initViewPager();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

    private void initViewPager() {
        consult_title_view.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                consult_title_name_tv.setText(headList.get(position % list.size()).getTitle());
                // 监听小点的状态 改变
                consult_dot_linear.getChildAt(position % list.size()).setEnabled(true);
                consult_dot_linear.getChildAt(prePosition).setEnabled(false);
                prePosition = position % list.size();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        new Thread() {
            @Override
            public void run() {
                super.run();
                while (isRunning) {
                    try {
                        Thread.sleep(3000);
                        handler.sendEmptyMessage(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    private void initListView() {
        head_fefresh_layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HeadLine headLine = totalList.get(position - 2);
                String id_ = headLine.getId();
                Intent intent = new Intent(getActivity(), HeadDetailActivity.class);
                intent.putExtra("id", id_);
                intent.putExtra("title", headLine.getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    public void jsonCallBack(String json) {
        List<HeadLine> headLines = ParseJson.parseHeadLine(json);
        totalList.addAll(headLines);
        adapter.notifyDataSetChanged();
    }

    boolean flag = false;

    @Override
    public void jsonCallBack2(String json) {
        if (!flag) {
            flag = true;
            List<HeadLine> headLine = ParseJson.parseHeadLine(json);
            for (int i = 0; i < 6; i++) {
                headList.add(headLine.get(i));
            }
            initHeadView();
        }
    }

    //设置头布局
    private void initHeadView() {
        for (int i = 0; i < headList.size(); i++) {
            final ImageView image = new ImageView(getActivity());
            String pic = headList.get(i).getL_picture();
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getActivity()).load(pic).into(image);
            list.add(image);

            String id = headList.get(i).getId();
            //记住id
            image.setTag(id);
            //设置viewPager中每个imageView的点击监听
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getActivity(), HeadDetailActivity.class);
                    //获得点击的id
                    String id_ = (String) image.getTag();
                    intent.putExtra("id", id_);
                    startActivity(intent);
                }
            });

            View dot = new View(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(25, 25);
            lp.leftMargin = 10;
            dot.setLayoutParams(lp);
            dot.setBackgroundResource(R.drawable.dot_bg);
            dot.setEnabled(false);
            consult_dot_linear.addView(dot);
        }
        TitleAdapter adapter = new TitleAdapter(list);
        consult_title_view.setAdapter(adapter);
        consult_dot_linear.getChildAt(0).setEnabled(true);
        consult_title_name_tv.setText(headList.get(0).getTitle());
    }

    @Override
    public void onStop() {
        super.onStop();
        flag = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
