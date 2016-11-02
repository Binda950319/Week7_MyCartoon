package com.wbh.week7_mycartoon.myadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by ${Mr.Zhao} on 2016/9/22.
 */
public class ViewHolder {
    //  定义一个引用  作为  getView 方法 返回的对象
    private View convertView;

    //    private Map<Integer, View> map = new HashMap<>();
    //  更高效的使用内存
    private SparseArray<View> array = new SparseArray<>();


    //  ViewHolder的 构造
    public ViewHolder(Context context, int resId) {
        // 实例化布局
        convertView = LayoutInflater.from(context).inflate(resId, null);
        //  设置标签
        convertView.setTag(this);
    }

    // 将 convertView 返回的方法
    public View getView() {
        return convertView;
    }

    //  定义一个静态方法 作为  Holder的 实例化方法    convertView 的 重复使用
    public static ViewHolder getHolder(Context context, int resId, View view) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder(context, resId);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return holder;
    }

    //   根据Id 查找View 的 方法
    public View findView(int viewId) {
        // 先去 集合中查找是否有这个View
        View view = array.get(viewId);
        if (view == null) {
            // 没有的话 FindViewById
            view = convertView.findViewById(viewId);
            // 并添加到集合中
            array.append(viewId, view);
        }
        // 返回
        return view;
    }
}