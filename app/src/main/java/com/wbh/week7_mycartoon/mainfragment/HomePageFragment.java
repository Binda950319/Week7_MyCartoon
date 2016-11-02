package com.wbh.week7_mycartoon.mainfragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.homepage.BoutiqueFragment;
import com.wbh.week7_mycartoon.homepage.RankFragment;
import com.wbh.week7_mycartoon.myadapter.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {


    private ViewPager home_viewPager;
    private List<Fragment> list = new ArrayList<>();
    private RadioButton boutique_rb;
    private RadioButton rank_rb;
    private List<RadioButton> buttonsList = new ArrayList<>();
    private int prePosition;
    private TextView view2;
    private int width;

    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page_, container, false);
        home_viewPager = ((ViewPager) view.findViewById(R.id.home_viewPager));
        boutique_rb = ((RadioButton) view.findViewById(R.id.boutique_rb));
        rank_rb = ((RadioButton) view.findViewById(R.id.rank_rb));
        view2 = (TextView) view.findViewById(R.id.view2);
        width = getResources().getDisplayMetrics().widthPixels;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRadioButton();
        initFragment();
    }

    private void initRadioButton() {
        buttonsList.add(boutique_rb);
        buttonsList.add(rank_rb);
        boutique_rb.setOnCheckedChangeListener(this);
        rank_rb.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.boutique_rb:
                changeButtonState(0);
                break;
            case R.id.rank_rb:
                changeButtonState(1);
                break;
        }
    }

    private void changeButtonState(int i) {
        buttonsList.get(i).setChecked(true);
        buttonsList.get(i).setTextColor(getResources().getColor(R.color.colorBlue));
        buttonsList.get(prePosition).setChecked(false);
        buttonsList.get(prePosition).setTextColor(getResources().getColor(R.color.colorGray));
        home_viewPager.setCurrentItem(i);
        prePosition = i;
    }

    private void initFragment() {
        BoutiqueFragment boutiqueFragment = new BoutiqueFragment();
        RankFragment rankFragment = new RankFragment();
        list.add(boutiqueFragment);
        list.add(rankFragment);
        home_viewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(), list));

        home_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float location = position * width / 2 + positionOffsetPixels / 2;
                view2.setX(location);
            }

            @Override
            public void onPageSelected(int position) {
                buttonsList.get(position).setTextColor(getResources().getColor(R.color.colorBlue));
                buttonsList.get(prePosition).setTextColor(Color.GRAY);
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


}
