package com.wbh.week7_mycartoon.myadapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.SortBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class GridViewSortAdapter extends MyBaseAdapter<SortBean> {
    private List<SortBean> list;
    private Context mContext;

    public GridViewSortAdapter(List<SortBean> list, Context mContext, int resId) {
        super(list, mContext, resId);
        this.list = list;
        this.mContext = mContext;
    }


    @Override
    public void setDate(ViewHolder holder, int position) {
        ImageView gridImage_iv = (ImageView) holder.findView(R.id.cirgridImage_iv);
        TextView grid_carName_tv = (TextView) holder.findView(R.id.cirgrid_carName_tv);
        Picasso.with(mContext).load(list.get(position).getIcon()).into(gridImage_iv);
        grid_carName_tv.setText(list.get(position).getName());
    }
}
