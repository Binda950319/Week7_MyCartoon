package com.wbh.week7_mycartoon.mainfragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.consult.CartoonFragment;
import com.wbh.week7_mycartoon.consult.DuanFragment;
import com.wbh.week7_mycartoon.consult.HeadlineFragment;
import com.wbh.week7_mycartoon.consult.PicFragment;
import com.wbh.week7_mycartoon.myadapter.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultFragment extends Fragment {

    private ViewPager consult_viewPager;
    private RadioGroup consult_group;
    private RadioButton[] buttons;
    private List<Fragment> list = new ArrayList<>();
    private int prePosition;
    private TextView consult_view;
    private int width;

    public ConsultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consult, container, false);
        consult_viewPager = ((ViewPager) view.findViewById(R.id.consult_viewPager));
        consult_group = (RadioGroup) view.findViewById(R.id.consult_group);
        consult_view = (TextView) view.findViewById(R.id.consult_view);

        width = getResources().getDisplayMetrics().widthPixels;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //防止fragment移走的时候listView会重复加载数据
        consult_viewPager.setOffscreenPageLimit(3);
        initRadioButton();
        initFragment();
    }

    private void initRadioButton() {
        buttons = new RadioButton[consult_group.getChildCount()];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = (RadioButton) consult_group.getChildAt(i);
        }
        consult_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < buttons.length; i++) {
                    if (buttons[i].getId() == checkedId){
                        consult_viewPager.setCurrentItem(i);
                    }
                }
            }
        });
    }

    private void initFragment() {
        HeadlineFragment headFragment = new HeadlineFragment();
        CartoonFragment cartoonFragment = new CartoonFragment();
        PicFragment picFragment = new PicFragment();
        DuanFragment duanFragment = new DuanFragment();
        list.add(headFragment);
        list.add(cartoonFragment);
        list.add(picFragment);
        list.add(duanFragment);
        consult_viewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(), list));

        consult_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                consult_view.setX(position * width / 4 + positionOffsetPixels / 4);
            }

            @Override
            public void onPageSelected(int position) {
                buttons[position].setChecked(true);
                buttons[prePosition].setChecked(false);
                buttons[position].setTextColor(getResources().getColor(R.color.colorBlue));
                buttons[prePosition].setTextColor(Color.BLACK);
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
