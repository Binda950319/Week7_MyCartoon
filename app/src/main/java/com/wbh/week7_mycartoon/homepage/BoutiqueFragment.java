package com.wbh.week7_mycartoon.homepage;


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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.BoutiqueTitle;
import com.wbh.week7_mycartoon.javabean.Cartoon;
import com.wbh.week7_mycartoon.javabean.SortBean;
import com.wbh.week7_mycartoon.myadapter.BoutiqueTitleAdapter;
import com.wbh.week7_mycartoon.myadapter.GridViewBoutiqueAdapter;
import com.wbh.week7_mycartoon.myadapter.GridViewNewsAdapter;
import com.wbh.week7_mycartoon.myadapter.GridViewRankAdapter;
import com.wbh.week7_mycartoon.myadapter.GridViewSortAdapter;
import com.wbh.week7_mycartoon.myasynctask.DownloadJsonTask;
import com.wbh.week7_mycartoon.myinterface.MyInterface;
import com.wbh.week7_mycartoon.utils.ParseJson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment implements MyInterface.JsonCallBack {

    public static final String BOUTIQUE_URL = "http://a121.baopiqi.com/api/mh/getCartoonHomePageAll.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7";

    private ViewPager boutique_title_view;
    private List<View> list = new ArrayList<>();
    private List<Cartoon> cartoonList = new ArrayList<>();
    private List<Cartoon> cartoonRankList = new ArrayList<>();
    private List<Cartoon> cartoonNewsList = new ArrayList<>();
    private List<SortBean> cartoonSortList = new ArrayList<>();
    private TextView boutique_title_name_tv;
    private LinearLayout boutique_dot_linear;
    private int prePosition;
    private boolean isRunning = true;
    private GridViewBoutiqueAdapter boutiqueAdapter;
    private GridViewRankAdapter rankAdapter;
    private GridViewNewsAdapter newsAdapter;
    private GridViewSortAdapter sortAdapter;
    private GridView boutique_gridView, sort_gridView;
    private GridView ranking_gridView;
    private GridView news_gridView;
    private TextView advice_more, ranking_more, news_more;
    private Intent intent;
    private boolean isAdded = false;
    private List<BoutiqueTitle> boutiqueTitles;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    boutique_title_view.setCurrentItem(boutique_title_view.getCurrentItem() + 1);
                    break;
                case 200:
                    boutique_title_view.setCurrentItem(boutique_title_view.getCurrentItem() - 1);
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_boutique, container, false);
        boutique_title_view = ((ViewPager) view.findViewById(R.id.boutique_title_view));
        boutique_title_name_tv = ((TextView) view.findViewById(R.id.boutique_title_name_tv));
        boutique_dot_linear = ((LinearLayout) view.findViewById(R.id.boutique_dot_linear));
        boutique_gridView = ((GridView) view.findViewById(R.id.boutique_gridView));
        ranking_gridView = ((GridView) view.findViewById(R.id.ranking_gridView));
        news_gridView = ((GridView) view.findViewById(R.id.news_gridView));
        sort_gridView = ((GridView) view.findViewById(R.id.sort_gridView));
        advice_more = ((TextView) view.findViewById(R.id.advice_more));
        ranking_more = ((TextView) view.findViewById(R.id.ranking_more));
        news_more = ((TextView) view.findViewById(R.id.news_more));
        intent = new Intent(getActivity(), BoutiqueMoreActivity.class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new DownloadJsonTask(this).execute(BOUTIQUE_URL);
        initViewPager();
        initGridView();
        initCheckedGridView();
        clickAddMoreData();

    }

    //点击更多
    private void clickAddMoreData() {
        //将id和头标题传到BoutiqueMoreActivity中
        advice_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("id", 1);
                intent.putExtra("text", "今日推荐");
                startActivity(intent);
            }
        });
        ranking_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("id", 2);
                intent.putExtra("text", "今日热门");
                startActivity(intent);

            }
        });
        news_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("id", 3);
                intent.putExtra("text", "今日最新");
                startActivity(intent);
            }
        });
    }

    //实例化适配器,以及为gridView设置适配器
    private void initGridView() {
        boutiqueAdapter = new GridViewBoutiqueAdapter(cartoonList, getActivity(), R.layout.gridview_item_layout);
        boutique_gridView.setAdapter(boutiqueAdapter);
        rankAdapter = new GridViewRankAdapter(cartoonRankList, getActivity(), R.layout.gridview_item_layout);
        ranking_gridView.setAdapter(rankAdapter);
        newsAdapter = new GridViewNewsAdapter(cartoonNewsList, getActivity(), R.layout.gridview_item_layout);
        news_gridView.setAdapter(newsAdapter);
        sortAdapter = new GridViewSortAdapter(cartoonSortList, getActivity(), R.layout.gridview_ciritem_layout);
        sort_gridView.setAdapter(sortAdapter);
    }

    //gridView中的点击监听
    private void initCheckedGridView() {
        boutique_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cartoon cartoon = cartoonList.get(position);
                sendInfo(cartoon, position);
            }
        });
        ranking_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cartoon cartoon = cartoonRankList.get(position);
                sendInfo(cartoon, position);
            }
        });
        news_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cartoon cartoon = cartoonNewsList.get(position);
                sendInfo(cartoon, position);
            }
        });
        sort_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getActivity(), SearchKeyActivity.class);
                String key = cartoonSortList.get(position).getName();
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });
    }

    //传数据到CartoonActivity中
    private void sendInfo(Cartoon cartoon, int position) {
        intent = new Intent(getActivity(), CartoonActivity.class);
        String id_ = cartoon.getId();
        String name = cartoon.getName();
        String author = cartoon.getAuthor();
        String state = cartoon.getState();
        String icon = cartoon.getIcon();
        String theme = cartoon.getTheme();
        String ranking = cartoon.getRanking();
        String introduction = cartoon.getIntroduction();
        intent.putExtra("id", id_);
        intent.putExtra("name", name);
        intent.putExtra("author", author);
        intent.putExtra("state", state);
        intent.putExtra("icon", icon);
        intent.putExtra("theme", theme);
        intent.putExtra("ranking", ranking);
        intent.putExtra("introduction", introduction);
        //设置标记,便于返回
        intent.putExtra("tag", 1);
        startActivity(intent);

    }
    //viewPager的滑动监听
    private void initViewPager() {
        boutique_title_view.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                if (isAdded) {
                    boutique_dot_linear.getChildAt(position % (list.size() / 2)).setEnabled(true);
                    boutique_dot_linear.getChildAt(prePosition).setEnabled(false);
                    prePosition = position % (list.size() / 2);
                } else {

                    boutique_title_name_tv.setText(boutiqueTitles.get(position).getName());
                    // 监听小点的状态 改变
                    boutique_dot_linear.getChildAt(position % list.size()).setEnabled(true);
                    boutique_dot_linear.getChildAt(prePosition).setEnabled(false);
                    prePosition = position % list.size();
                }
                //viewPager里的点击事件
                boutique_title_view.getChildAt(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BoutiqueTitle boutiqueTitle = boutiqueTitles.get(position);
                        intent = new Intent(getActivity(), CartoonActivity.class);

                        String id_ = boutiqueTitle.getId();
                        String name = boutiqueTitle.getName();
                        String author = boutiqueTitle.getAuthor();
                        String state = boutiqueTitle.getState();
                        String icon = boutiqueTitle.getIcon();
                        String theme = boutiqueTitle.getTheme();
                        String ranking = boutiqueTitle.getRanking();
                        String introduction = boutiqueTitle.getIntroduction();
                        intent.putExtra("id", id_);
                        intent.putExtra("name", name);
                        intent.putExtra("author", author);
                        intent.putExtra("state", state);
                        intent.putExtra("icon", icon);
                        intent.putExtra("theme", theme);
                        intent.putExtra("ranking", ranking);
                        intent.putExtra("introduction", introduction);
                        startActivity(intent);
                    }
                });
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
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(100);
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    @Override
    public void jsonCallBack(String json) {
        boutiqueTitles = ParseJson.parseBoutiqueTitle(json);
        if (boutiqueTitles != null && boutiqueTitles.size() > 0) {
            initView(boutiqueTitles);
        }

        List<Cartoon> cartoons = ParseJson.parseGridBoutique(json);
        if (boutiqueTitles != null && boutiqueTitles.size() > 0) {
            cartoonList.addAll(cartoons);
            boutiqueAdapter.notifyDataSetChanged();
        }
        List<Cartoon> cartoonsRank = ParseJson.parseGridRanking(json);
        if (boutiqueTitles != null && boutiqueTitles.size() > 0) {
            cartoonRankList.addAll(cartoonsRank);
            rankAdapter.notifyDataSetChanged();
        }
        List<Cartoon> cartoonsNews = ParseJson.parseGridNews(json);
        if (boutiqueTitles != null && boutiqueTitles.size() > 0) {
            cartoonNewsList.addAll(cartoonsNews);
            newsAdapter.notifyDataSetChanged();
        }
        List<SortBean> sortBeen = ParseJson.parseSortBean(json);
        if (boutiqueTitles != null && boutiqueTitles.size() > 0) {
            cartoonSortList.addAll(sortBeen);
            sortAdapter.notifyDataSetChanged();
        }
    }

    //头布局设置
    private void initView(List<BoutiqueTitle> boutiqueTitles) {
        for (int i = 0; i < boutiqueTitles.size(); i++) {
            ImageView image = new ImageView(getActivity());
            String cover = boutiqueTitles.get(i).getCover();
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getActivity()).load(cover).into(image);

            list.add(image);

            View dot = new View(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(25, 25);
            lp.leftMargin = 10;
            dot.setLayoutParams(lp);
            dot.setBackgroundResource(R.drawable.dot_bg);
            dot.setEnabled(true);
            boutique_dot_linear.addView(dot);
        }

        BoutiqueTitleAdapter adapter = new BoutiqueTitleAdapter(list);
        boutique_title_view.setAdapter(adapter);
        boutique_dot_linear.getChildAt(1).setEnabled(false);
        boutique_title_name_tv.setText(boutiqueTitles.get(0).getName());
    }
}
