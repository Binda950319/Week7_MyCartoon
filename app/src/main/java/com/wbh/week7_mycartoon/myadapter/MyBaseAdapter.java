package com.wbh.week7_mycartoon.myadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ${Mr.Zhao} on 2016/9/22.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    //1 定义泛型 数据源
    private List<T> list;
    // 2.上下文对象
    private Context mContext;
    //3 .每个 Item的 布局Id
    private int resId;

    //4  构造
    public MyBaseAdapter(List<T> list, Context mContext, int resId) {
        this.list = list;
        this.mContext = mContext;
        this.resId = resId;
    }

    // 5.  抽取一下三个方法 固定写法
    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
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
        //6.1   获取实例化 ViewHolder
        ViewHolder holder = ViewHolder.getHolder(mContext, resId, convertView);
        //6.2   填充数据到View
        setDate(holder, position);
        //6.3   将View 返回
        return holder.getView();
    }

    //  7 .定义抽象方法
    public abstract void setDate(ViewHolder holder, int position);
}