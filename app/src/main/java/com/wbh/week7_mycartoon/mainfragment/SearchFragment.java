package com.wbh.week7_mycartoon.mainfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.homepage.CartoonActivity;
import com.wbh.week7_mycartoon.homepage.SearchKeyActivity;
import com.wbh.week7_mycartoon.javabean.Cartoon;
import com.wbh.week7_mycartoon.myadapter.GridViewBoutiqueAdapter;
import com.wbh.week7_mycartoon.myasynctask.DownloadJsonTask;
import com.wbh.week7_mycartoon.myasynctask.DownloadJsonTask2;
import com.wbh.week7_mycartoon.myinterface.MyInterface;
import com.wbh.week7_mycartoon.search.HotSearchAdapter;
import com.wbh.week7_mycartoon.url_path.Url;
import com.wbh.week7_mycartoon.utils.ParseJson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements MyInterface.JsonCallBack, MyInterface.JsonCallBack2 {


    private EditText search_key_et;
    private RelativeLayout search_bt;
    private GridView search_gridView, search_select_gridView;
    private TextView search_change_tv;
    private List<String> nameList = new ArrayList<>();
    private HotSearchAdapter adapter;
    private int pageNum = 0;
    private List<Cartoon> cartoonList = new ArrayList<>();
    private GridViewBoutiqueAdapter boutiqueAdapter;
    private Intent intent;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        search_key_et = ((EditText) view.findViewById(R.id.search_key_et));
        search_bt = ((RelativeLayout) view.findViewById(R.id.search_bt));
        search_gridView = ((GridView) view.findViewById(R.id.search_gridView));
        search_change_tv = ((TextView) view.findViewById(R.id.search_change_tv));
        search_select_gridView = ((GridView) view.findViewById(R.id.search_select_gridView));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new HotSearchAdapter(nameList, getActivity(), R.layout.hotsearch_item_layout);
        search_gridView.setAdapter(adapter);
        new DownloadJsonTask(this).execute(Url.HOT_SEARCH_URL);


        boutiqueAdapter = new GridViewBoutiqueAdapter(cartoonList, getActivity(), R.layout.gridview_item_layout);
        search_select_gridView.setAdapter(boutiqueAdapter);
        new DownloadJsonTask2(this).execute(Url.getRecommendMore(pageNum, 4));
        initChangeData();
        initSearch();
    }


    private void initSearch() {
        //编辑框搜索
        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = search_key_et.getText().toString();
                if (!TextUtils.isEmpty(key)) {
                    intent = new Intent(getActivity(), SearchKeyActivity.class);
                    intent.putExtra("key", key);
                    startActivity(intent);
                    search_key_et.setTag("");
                } else {
                    Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //热词搜索
        search_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getActivity(), SearchKeyActivity.class);
                String key = nameList.get(position);
                intent.putExtra("key", key);
                startActivity(intent);

            }
        });


    }

    private void initChangeData() {
        search_select_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cartoon cartoon = cartoonList.get(position);
                intent = new Intent(getActivity(), CartoonActivity.class);
                String id_ = cartoon.getId();
                String name = cartoon.getName();
                String author = cartoon.getAuthor();
                String state = cartoon.getState();
                String icon = cartoon.getIcon();
                String theme = cartoon.getTheme();
                String ranking = cartoon.getRanking();
                String introduction = cartoon.getIntroduction();
                intent.putExtra("id", id_);//715
                intent.putExtra("name", name);
                intent.putExtra("author", author);
                intent.putExtra("state", state);
                intent.putExtra("icon", icon);
                intent.putExtra("theme", theme);
                intent.putExtra("ranking", ranking);
                intent.putExtra("introduction", introduction);
                intent.putExtra("tag", 4);
                startActivity(intent);
            }
        });

        search_change_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNum++;
                new DownloadJsonTask2(SearchFragment.this).execute(Url.getRecommendMore(pageNum, 4));
            }
        });
    }

    @Override
    public void jsonCallBack(String json) {
        nameList.clear();
        List<String> name = ParseJson.parseSearchItem(json);
        nameList.addAll(name);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void jsonCallBack2(String json) {
        //每次加载时都先清空列表
        cartoonList.clear();
        List<Cartoon> cartoons = ParseJson.parseCartoon(json);
        cartoonList.addAll(cartoons);
        boutiqueAdapter.notifyDataSetChanged();
    }
}
