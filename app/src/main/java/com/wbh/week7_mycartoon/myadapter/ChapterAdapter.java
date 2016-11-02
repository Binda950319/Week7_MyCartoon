package com.wbh.week7_mycartoon.myadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.CartoonChapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class ChapterAdapter extends BaseAdapter {
    private List<CartoonChapter> list;
    private Context context;
    private LayoutInflater inflater;
    private View preView;

    public ChapterAdapter(List<CartoonChapter> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        if (convertView == null) {
            if (position % 2 == 0) {
                holder2 = new ViewHolder2();
                convertView = inflater.inflate(R.layout.chapter_item2_layout, parent, false);
                holder2.chapter2_tv = (TextView) convertView.findViewById(R.id.chapter2_tv);
                convertView.setTag(holder2);
            } else {
                holder1 = new ViewHolder1();
                convertView = inflater.inflate(R.layout.chapter_item_layout, parent, false);
                holder1.chapter1_tv = (TextView) convertView.findViewById(R.id.chapter1_tv);
                convertView.setTag(holder1);
            }
        } else {
            if (convertView.getTag() instanceof ViewHolder1) {
                if (position % 2 == 0) {
                    holder2 = new ViewHolder2();
                    convertView = inflater.inflate(R.layout.chapter_item2_layout, parent, false);
                    holder2.chapter2_tv = (TextView) convertView.findViewById(R.id.chapter2_tv);
                    convertView.setTag(holder2);
                } else {
                    holder1 = (ViewHolder1) convertView.getTag();
                }
            } else if (convertView.getTag() instanceof ViewHolder2) {
                if (position % 2 == 0) {
                    holder2 = (ViewHolder2) convertView.getTag();
                } else {
                    holder1 = new ViewHolder1();
                    convertView = inflater.inflate(R.layout.chapter_item_layout, parent, false);
                    holder1.chapter1_tv = (TextView) convertView.findViewById(R.id.chapter1_tv);
                    convertView.setTag(holder1);
                }
            }

        }
        if (position % 2 == 0) {
            holder2.chapter2_tv.setText(list.get(position).getTitle());
        } else {
            holder1.chapter1_tv.setText(list.get(position).getTitle());
        }

        if (list.get(position).isFlag()) {
            convertView.setBackgroundColor(Color.YELLOW);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }

    static class ViewHolder1 {
        TextView chapter1_tv;
    }

    static class ViewHolder2 {
        TextView chapter2_tv;
    }

//    @Override
//    public void setDate(ViewHolder holder, int position) {
//        TextView chapter_tv = (TextView) holder.findView(R.id.chapter_tv);
//        chapter_tv.setText(list.get(position).getTitle());
//
//    }


}
