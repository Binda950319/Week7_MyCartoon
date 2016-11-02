package com.wbh.week7_mycartoon;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wbh.week7_mycartoon.mainfragment.ConsultFragment;
import com.wbh.week7_mycartoon.mainfragment.HomePageFragment;
import com.wbh.week7_mycartoon.mainfragment.ManagerFragment;
import com.wbh.week7_mycartoon.mainfragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup bottom_group;
    private RadioButton[] buttons;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private FrameLayout main_container;
    private int prePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_container = (FrameLayout) findViewById(R.id.main_container);
        initView();
        initFragment();
    }

    //TODO 将Button上的标志做成图片,添加进去
    private void initView() {
        bottom_group = (RadioGroup) findViewById(R.id.bootom_group);
        buttons = new RadioButton[bottom_group.getChildCount()];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = (RadioButton) bottom_group.getChildAt(i);
        }
        bottom_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < buttons.length; i++) {
                    if(buttons[i].getId() == checkedId){
                        buttons[i].setTextColor(getResources().getColor(R.color.colorGreen));
                        buttons[prePosition].setTextColor(Color.BLACK);
                        switchFragment(i);
                        prePosition = i;
                    }
                }
            }
        });
    }

    private void switchFragment(int i) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currFragment = fragmentList.get(i);
        Fragment preFragment = fragmentList.get(prePosition);
        if (currFragment.isAdded()){
            transaction.show(currFragment).hide(preFragment).commit();
        } else{
            transaction.add(R.id.main_container,currFragment).hide(preFragment).commit();
        }
        prePosition = i;
    }

    private void initFragment() {
        HomePageFragment homePageFragment = new HomePageFragment();
        SearchFragment searchFragment = new SearchFragment();
        ConsultFragment consultFragment = new ConsultFragment();
        ManagerFragment managerFragment = new ManagerFragment();
        fragmentList.add(homePageFragment);
        fragmentList.add(searchFragment);
        fragmentList.add(consultFragment);
        fragmentList.add(managerFragment);
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,fragmentList.get(0)).commit();
    }
}
