package com.wbh.week7_mycartoon.homepage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.CartoonChapter;
import com.wbh.week7_mycartoon.javabean.CartoonPage;
import com.wbh.week7_mycartoon.myasynctask.DownloadJsonTask;
import com.wbh.week7_mycartoon.myinterface.MyInterface;
import com.wbh.week7_mycartoon.url_path.Url;
import com.wbh.week7_mycartoon.utils.ParseJson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CartoonPageActivity extends AppCompatActivity implements MyInterface.JsonCallBack {

    private Intent intent;
    private RelativeLayout nextPage_layout, previewPage_layout;
    private List<CartoonPage> totalList = new ArrayList<>();
    private int position;
    private String number, title;
    private TextView carpage_tv, carpage_title_tv, time_tv;
    private int width;
    private int height;
    private int prePosition;
    private ListView cartoon_page_lv;
    private CartoonPagerAdapter adapter;
    private ArrayList<CartoonChapter> list;
    private boolean isLast = false;
    private boolean isStart = false;
    private BatteryReceiver batteryReceiver;
    private ImageView battery_iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon_page);
        nextPage_layout = (RelativeLayout) findViewById(R.id.nextPage_layout);
        previewPage_layout = (RelativeLayout) findViewById(R.id.previewPage_layout);
        carpage_tv = (TextView) findViewById(R.id.carpage_tv);
        carpage_title_tv = (TextView) findViewById(R.id.carpage_title_tv);
        cartoon_page_lv = (ListView) findViewById(R.id.cartoon_page_lv);
        battery_iv = (ImageView) findViewById(R.id.battery_iv);
        time_tv = (TextView) findViewById(R.id.time_tv);
        width = getResources().getDisplayMetrics().widthPixels;
        height = getResources().getDisplayMetrics().heightPixels;
        intent = getIntent();
        String id = intent.getStringExtra("id");//715
        nextPage_layout.setTag(id);
        String number = intent.getStringExtra("number");//25415

        list = (ArrayList<CartoonChapter>) intent.getSerializableExtra("CartoonChapter");
        title = intent.getStringExtra("title");
        position = intent.getIntExtra("position", 0);
        carpage_title_tv.setText(list.get(position).getTitle());
        cartoon_page_lv.setDividerHeight(0);
        adapter = new CartoonPagerAdapter(totalList, this);
        cartoon_page_lv.setAdapter(adapter);
        new DownloadJsonTask(this).execute(Url.getCartoonPage(number, id));
        initClick();
        initScroll();
        initBattery();
        initTime();
    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        String time = hour / 10 + hour % 10 + ":" + minute / 10 + minute % 10;
        time_tv.setText(time);
    }


    private void initBattery() {
        batteryReceiver = new BatteryReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }

    private void initScroll() {
        cartoon_page_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLast && scrollState == SCROLL_STATE_IDLE) {
                    nextPage_layout.setVisibility(View.VISIBLE);
                }
                if (isStart && scrollState == SCROLL_STATE_IDLE) {
                    previewPage_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                carpage_tv.setText(" " + (firstVisibleItem + 1) + "/" + totalList.size());
                isLast = firstVisibleItem + visibleItemCount == totalItemCount;
                isStart = totalItemCount - visibleItemCount == firstVisibleItem;
            }
        });
    }

    private void initClick() {
        nextPage_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage_layout.setVisibility(View.INVISIBLE);
                previewPage_layout.setVisibility(View.INVISIBLE);
                position++;
                //记录传过来的number
                number = list.get(position).getNumber();
                String id = (String) nextPage_layout.getTag();
                carpage_title_tv.setText(list.get(position).getTitle());
                CartoonActivity.cartoon_listView.getChildAt(position).setBackgroundColor(Color.YELLOW);
                CartoonActivity.cartoon_listView.getChildAt(prePosition).setBackgroundColor(Color.WHITE);
                prePosition = position;
                new DownloadJsonTask(CartoonPageActivity.this).execute(Url.getCartoonPage(number, id));

            }
        });
        previewPage_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage_layout.setVisibility(View.INVISIBLE);
                previewPage_layout.setVisibility(View.INVISIBLE);
                if (position > 0) {
                    position--;
                    number = list.get(position).getNumber();
                    String id = (String) nextPage_layout.getTag();
                    carpage_title_tv.setText(list.get(position).getTitle());
                    CartoonActivity.cartoon_listView.getChildAt(position).setBackgroundColor(Color.YELLOW);
                    CartoonActivity.cartoon_listView.getChildAt(prePosition).setBackgroundColor(Color.WHITE);
                    prePosition = position;
                    new DownloadJsonTask(CartoonPageActivity.this).execute(Url.getCartoonPage(number, id));

                } else {
                    previewPage_layout.setVisibility(View.VISIBLE);
                    Toast.makeText(CartoonPageActivity.this, "已经是第一话", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void jsonCallBack(String json) {
        totalList.clear();
        List<CartoonPage> cartoonPages = ParseJson.parseCartoonPage(json);
        totalList.addAll(cartoonPages);
        adapter.notifyDataSetChanged();
    }

    class BatteryReceiver extends BroadcastReceiver {
        //        BatteryDB batteryDB;
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
                //   计算当前电量百分比
                int i = level * 100 / scale;
                battery_iv.setImageLevel(i);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }
}
