package com.wbh.week7_mycartoon.search;

import android.content.Context;
import android.widget.TextView;

import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.myadapter.MyBaseAdapter;
import com.wbh.week7_mycartoon.myadapter.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/25.
 */
public class HotSearchAdapter extends MyBaseAdapter<String> {
    private List<String> list;

    public HotSearchAdapter(List<String> list, Context mContext, int resId) {
        super(list, mContext, resId);
        this.list = list;
    }

    @Override
    public void setDate(ViewHolder holder, int position) {
        TextView hotSearch_tv = (TextView) holder.findView(R.id.hotSearch_tv);
        hotSearch_tv.setText(list.get(position));
    }
}
