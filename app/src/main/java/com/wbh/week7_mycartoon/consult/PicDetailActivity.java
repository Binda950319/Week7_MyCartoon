package com.wbh.week7_mycartoon.consult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.myadapter.BoutiqueTitleAdapter;

import java.util.ArrayList;
import java.util.List;

public class PicDetailActivity extends AppCompatActivity {

    private Intent intent;
    private ViewPager picDetail_viewPager;
    private List<View> list = new ArrayList<>();
    private BoutiqueTitleAdapter adapter;
    private ImageView video_return_iv;
    private LinearLayout pic_bottom_layout;
    private boolean isVisibity = false;
    private ArrayList<String> picses;
    private TextView picNum_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_detail);
        picNum_tv = (TextView) findViewById(R.id.picNum_tv);
        video_return_iv = ((ImageView) findViewById(R.id.video_return_iv));
        picDetail_viewPager = (ViewPager) findViewById(R.id.picDetail_viewPager);
        pic_bottom_layout = (LinearLayout) findViewById(R.id.pic_bottom_layout);
        initViewPager();
    }

    private void initViewPager() {
        intent = getIntent();
        picses = (ArrayList<String>) intent.getSerializableExtra("pics");
        int position = intent.getIntExtra("position", 0);
        for (int i = 0; i < picses.size(); i++) {
            ImageView image = new ImageView(this);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(this).load(picses.get(i)).into(image);
            list.add(image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isVisibity) {
                        isVisibity = true;
                        pic_bottom_layout.setVisibility(View.VISIBLE);
                    } else {
                        pic_bottom_layout.setVisibility(View.INVISIBLE);
                        isVisibity = false;
                    }
                }
            });
        }
        adapter = new BoutiqueTitleAdapter(list);
        picDetail_viewPager.setAdapter(adapter);
        picDetail_viewPager.setCurrentItem(position);
        picNum_tv.setText((position+1)+"/"+picses.size());
        picDetail_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                picNum_tv.setText((position+1)+"/"+picses.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    public void onClick(View view) {
        finish();
    }
}
