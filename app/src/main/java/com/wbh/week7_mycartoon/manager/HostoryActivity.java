package com.wbh.week7_mycartoon.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.homepage.CartoonActivity;
import com.wbh.week7_mycartoon.javabean.Cartoon;
import com.wbh.week7_mycartoon.myadapter.RankAdapter;
import com.wbh.week7_mycartoon.utils.MyOpenHelper;
import com.wbh.week7_mycartoon.utils.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class HostoryActivity extends Activity {

    private List<Cartoon> list = new ArrayList<>();
    private MyOpenHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView rank_listView;
    private RefreshLayout refreshLayout;
    private RankAdapter adapter;
    private Intent intent;
    private int current;
    private AlertDialog.Builder builder;
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
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_rank);
        rank_listView = ((ListView) findViewById(R.id.rank_listView));
        refreshLayout = ((RefreshLayout) findViewById(R.id.refreshLayout));
        helper = new MyOpenHelper(this, "cartoon.db");
        db = helper.getReadableDatabase();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        checkedData();

    }

    private void checkedData() {
        list.clear();
        cursor = db.rawQuery("select * from cartoontb", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
//                cartoon = new Cartoon(id, name, icon, author, theme, ranking, state, introduction);
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String icon = cursor.getString(cursor.getColumnIndex("icon"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                String theme = cursor.getString(cursor.getColumnIndex("theme"));
                String ranking = cursor.getString(cursor.getColumnIndex("ranking"));
                String state = cursor.getString(cursor.getColumnIndex("state"));
                String introduction = cursor.getString(cursor.getColumnIndex("introduction"));
                Cartoon cartoon = new Cartoon(id, name, icon, author, theme, ranking, state, introduction);
                list.add(cartoon);
            }
        } else{
            Toast.makeText(HostoryActivity.this, "暂时没有浏览", Toast.LENGTH_SHORT).show();
        }
        adapter = new RankAdapter(list, this, R.layout.home_rank_item_layout);
        rank_listView.setAdapter(adapter);
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

    private void initListView() {
        rank_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(HostoryActivity.this, CartoonActivity.class);
                Cartoon cartoon = list.get(position);
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
                startActivity(intent);
                finish();
            }
        });

        rank_listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                builder.setMessage("取消收藏");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = list.get(current).getId();
                        list.remove(current);
                        db.delete("cartoontb", "id=?", new String[]{id});
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                return true;
            }
        });
    }
}