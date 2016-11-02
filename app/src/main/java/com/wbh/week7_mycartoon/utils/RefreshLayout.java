package com.wbh.week7_mycartoon.utils;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.wbh.week7_mycartoon.R;

/**
 * Created by ${Mr.Zhao} on 2016/9/21.
 */
public class RefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {

    private OnLoadingListener listener;
    private View footerView;
    private ListView listView;
    private boolean isLoading = false;


    //    1.定义一个接口 用来实现上拉加载更多时候的 触发方法
    public interface OnLoadingListener {
        public void onLoading();
    }

    //  2. 创建一个 设置监听器的方法
    public void setOnLoadingListener(OnLoadingListener onLoadingListener) {
        //  3.实例化当前监听器的引用
        this.listener = onLoadingListener;
    }


    public RefreshLayout(Context context) {
        super(context);
        initFooterView(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFooterView(context);
    }

    // 4. 初始化 底布局用来添加与移除
    public void initFooterView(Context context) {
        footerView = LayoutInflater.from(context).inflate(R.layout.rank_footer_layout, null);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 5.获取 添加到当前View 内部的  ListView
        int count = getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                View view = getChildAt(i);
                if (view instanceof ListView) {
                    listView = (ListView) view;
                    listView.setOnScrollListener(this);
                }
            }
        }
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //6. 判断滑动到底部  且  当前的 View 没有正在加载
        if (firstVisibleItem + visibleItemCount == totalItemCount && isLoading == false) {
            // 设置   添加底布局 开始加载
            setLoadingState(true);
            listener.onLoading();
        }
    }

    //7.创建设置滑动完成的 方法
    public void setLoadingState(boolean loading) {
        isLoading = loading;
        if (loading) {
            listView.addFooterView(footerView);
        } else {
            listView.removeFooterView(footerView);
        }

    }


}