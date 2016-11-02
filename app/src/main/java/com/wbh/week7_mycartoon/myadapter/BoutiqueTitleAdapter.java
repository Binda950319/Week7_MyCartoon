package com.wbh.week7_mycartoon.myadapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class BoutiqueTitleAdapter extends PagerAdapter {
    private List<View> list;

    public BoutiqueTitleAdapter(List<View> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position ));
        return list.get(position );
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position ));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
