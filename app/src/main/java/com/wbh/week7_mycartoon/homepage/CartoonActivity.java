package com.wbh.week7_mycartoon.homepage;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.Cartoon;
import com.wbh.week7_mycartoon.javabean.CartoonChapter;
import com.wbh.week7_mycartoon.myadapter.ChapterAdapter;
import com.wbh.week7_mycartoon.myasynctask.DownloadJsonTask;
import com.wbh.week7_mycartoon.myinterface.MyInterface;
import com.wbh.week7_mycartoon.utils.MyOpenHelper;
import com.wbh.week7_mycartoon.utils.ParseJson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartoonActivity extends AppCompatActivity implements MyInterface.JsonCallBack {

    String url = "http://a121.baopiqi.com/api/mh/getCartoonInfo.php?&appname=%E7%88%B1%E6%83%85%E6%BC%AB%E7%94%BB%E7%B2%BE%E9%80%89&pkgname=com.platform.cartoonl&imei=863191020203140&versionname=1.2.7&id=";
    private ImageView cartoon_icon_iv, actionBar_iv, detail_iv;
    private TextView cartoon_name_tv, cartoon_author_tv, cartoon_state_tv, cartoon_intro_tv;
    public static ListView cartoon_listView;
    private ArrayList<CartoonChapter> totalList = new ArrayList<>();
    private ChapterAdapter adapter;
    private Intent intent;
    private String id, name, icon, author, theme, ranking, state, introduction;
    private TextView cartoon_reading_bt, cartoon_collect_bt;
    private Cartoon cartoon = null;
    private List<Cartoon> list = new ArrayList<>();
    private MyOpenHelper helper;
    private Cursor cursor;
    private SQLiteDatabase db;
    private boolean isAdded = false;
    public static int prePosition, currposition;
    private int tag;
    private boolean isVisible = false;
    private int numbers[];
    private ContentValues values;
    private SQLiteDatabase db_number;
    private Cursor cursor_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon);

        initView();

        helper = new MyOpenHelper(this, "cartoon.db");
        db = helper.getReadableDatabase();
        values = new ContentValues();
        cursor = db.rawQuery("select * from cartoontb", null);

        db_number = openOrCreateDatabase("number.db", MODE_PRIVATE, null);
        String sql_ = "Create table if not exists numbertb(_id integer primary key autoincrement, id text, number text, position text)";
        db_number.execSQL(sql_);
        cursor_number = db_number.rawQuery("select * from numbertb", null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        while (cursor.moveToNext()) {
            String id_ = cursor.getString(cursor.getColumnIndex("id"));
            if (id_.equals(id)) {
                //数据库中已存在
                isAdded = true;
                cartoon_collect_bt.setText("取消收藏");
                cartoon_collect_bt.setBackgroundResource(R.drawable.button_red_bg);
            }
        }
        while (cursor_number.moveToNext()) {
            String id_number = cursor_number.getString(cursor_number.getColumnIndex("id"));
            if (id_number.equals(id)) {
                isAdded = true;
//                String position = cursor_number.getString(cursor_number.getColumnIndex("position"));
                cartoon_reading_bt.setText("继续阅读");
                cartoon_reading_bt.setBackgroundColor(Color.YELLOW);
            }
        }
    }

    //初始化控件
    private void initView() {
        cartoon_listView = (ListView) findViewById(R.id.cartoon_listView);
        cartoon_icon_iv = (ImageView) findViewById(R.id.cartoon_icon_iv);
        actionBar_iv = (ImageView) findViewById(R.id.actionBar_iv);
        detail_iv = (ImageView) findViewById(R.id.detail_iv);
        cartoon_name_tv = (TextView) findViewById(R.id.cartoon_name_tv);
        cartoon_author_tv = (TextView) findViewById(R.id.cartoon_author_tv);
        cartoon_state_tv = (TextView) findViewById(R.id.cartoon_state_tv);
        cartoon_intro_tv = (TextView) findViewById(R.id.cartoon_intro_tv);
        cartoon_reading_bt = ((TextView) findViewById(R.id.cartoon_reading_bt));
        cartoon_collect_bt = ((TextView) findViewById(R.id.cartoon_collect_bt));
        initIntent();
        initListView();
        initClick();

    }


    //接收传过来的值,启动异步下载
    private void initIntent() {
        intent = getIntent();
        tag = intent.getIntExtra("tag", 1);
        actionBar_iv.setTag(tag);

        id = intent.getStringExtra("id");//715
        cartoon_listView.setTag(id);
        name = intent.getStringExtra("name");//伪恋
        author = intent.getStringExtra("author");
        state = intent.getStringExtra("state");
        icon = intent.getStringExtra("icon");
        theme = intent.getStringExtra("theme");
        ranking = intent.getStringExtra("ranking");
        introduction = intent.getStringExtra("introduction");
        //将接收到的数据存起来,以便存入数据库
        cartoon = new Cartoon(id, name, icon, author, theme, ranking, state, introduction);

        cartoon_name_tv.setText(name);
        cartoon_author_tv.setText("作者: " + author);
        cartoon_state_tv.setText(state);
        cartoon_intro_tv.setText(introduction);
        cartoon_intro_tv.setMaxLines(2);
        Picasso.with(this).load(icon).into(cartoon_icon_iv);

        adapter = new ChapterAdapter(totalList, this);
        cartoon_listView.setAdapter(adapter);
        new DownloadJsonTask(this).execute(url + id);

    }

    //    倒序
    public void onClick(View view) {
        Collections.reverse(totalList);
        adapter.notifyDataSetChanged();
    }

    //实现漫画列表的点击
    private void initListView() {
        cartoon_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                totalList.get(prePosition).flag = false;
                totalList.get(position).flag = true;
                adapter.notifyDataSetChanged();

                CartoonChapter cartoonChapter = totalList.get(position);
                String number = cartoonChapter.getNumber();
                String title = cartoonChapter.getTitle();
                intent = new Intent(CartoonActivity.this, CartoonPageActivity.class);
                currposition = position;
                cartoon_listView.getChildAt(position).setBackgroundColor(Color.YELLOW);
                cartoon_listView.getChildAt(prePosition).setBackgroundColor(Color.WHITE);
                cartoon_reading_bt.setText("继续阅读");
                cartoon_reading_bt.setBackgroundColor(Color.YELLOW);
                prePosition = position;
                String id_ = (String) cartoon_listView.getTag();
                intent.putExtra("id", id_);//715
                intent.putExtra("number", number);//25415
                intent.putExtra("CartoonChapter", totalList);
                intent.putExtra("title", title);
                intent.putExtra("position", position);
                intent.putExtra("numbers", numbers);
                //点击阅读存存储
                values.put("id", id_);
                values.put("number", number);
                values.put("position", position);
                db_number.insert("numbertb", null, values);
                startActivity(intent);
            }
        });
    }

    //点击事件
    private void initClick() {
        //点击开始阅读
        cartoon_reading_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdded) {
                    Cursor cursor = db_number.rawQuery("select * from numbertb where id = ?", new String[]{id});
                    while (cursor.moveToNext()) {
                        String position = cursor.getString(cursor.getColumnIndex("position"));
                        intent = new Intent(CartoonActivity.this, CartoonPageActivity.class);
                        String number = totalList.get(Integer.parseInt(position)).getNumber();
                        String id_ = (String) cartoon_listView.getTag();
                        intent.putExtra("CartoonChapter", totalList);
                        intent.putExtra("id", id_);//715
                        intent.putExtra("number", number);//25415
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                } else {
//                    第一次阅读
                    cartoon_reading_bt.setText("继续阅读");
                    cartoon_reading_bt.setBackgroundColor(Color.YELLOW);
                    cartoon_listView.getChildAt(0).setBackgroundColor(Color.YELLOW);
                    intent = new Intent(CartoonActivity.this, CartoonPageActivity.class);
                    String number = totalList.get(0).getNumber();
                    String id_ = (String) cartoon_listView.getTag();
                    intent.putExtra("CartoonChapter", totalList);
                    intent.putExtra("id", id_);//715
                    intent.putExtra("number", number);//25415
                    intent.putExtra("position", 0);
                    //第一次阅读存储
                    values.put("id", id_);
                    values.put("number", number);
                    values.put("position", 0);
                    db_number.insert("numbertb", null, values);
                    startActivity(intent);
                }
            }
        });


        //点击收藏
        cartoon_collect_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 做数据库收藏
                while (cursor.moveToNext()) {
                    String id_ = cursor.getString(cursor.getColumnIndex("id"));
                    if (id_.equals(id)) {
                        //已经添加过了
                        isAdded = true;
                    }
                }
                if (isAdded) {
                    //已经被收藏过
                    // TODO 做数据库删除
                    db.delete("cartoontb", "id=?", new String[]{id});
                    Toast.makeText(CartoonActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    cartoon_collect_bt.setEnabled(true);
                    cartoon_collect_bt.setText("添加收藏");
                    cartoon_collect_bt.setBackgroundResource(R.color.colorBlue);
                    isAdded = false;
                } else {
                    cartoon_collect_bt.setEnabled(true);
                    values.put("id", id);
                    values.put("name", name);
                    values.put("icon", icon);
                    values.put("author", author);
                    values.put("theme", theme);
                    values.put("ranking", ranking);
                    values.put("state", state);
                    values.put("introduction", introduction);
                    db.insert("cartoontb", null, values);
                    values.clear();
                    Toast.makeText(CartoonActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    cartoon_collect_bt.setText("取消收藏");
                    cartoon_collect_bt.setEnabled(true);
                    cartoon_collect_bt.setBackgroundResource(R.drawable.button_red_bg);
                    isAdded = true;
                }
            }
        });

        //点击返回
        actionBar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //点击作者
        cartoon_author_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CartoonActivity.this, SearchKeyActivity.class);
                intent.putExtra("key", author);
                startActivity(intent);
            }
        });

        //点击显示全部内容简介
        detail_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVisible) {
                    isVisible = true;
                    cartoon_intro_tv.setMaxLines(10);
                    detail_iv.setImageResource(R.mipmap.icon_comic_detail_visible);
                } else {
                    isVisible = false;
                    cartoon_intro_tv.setMaxLines(2);
                    detail_iv.setImageResource(R.mipmap.icon_comic_detail_disvisible);
                }
            }
        });
    }


    @Override
    public void jsonCallBack(String json) {
        List<CartoonChapter> cartoonChapters = ParseJson.parseCartoonChapter(json);
        if (cartoonChapters != null && cartoonChapters.size() > 0) {
            totalList.addAll(cartoonChapters);
            adapter.notifyDataSetChanged();
        }

    }

}
