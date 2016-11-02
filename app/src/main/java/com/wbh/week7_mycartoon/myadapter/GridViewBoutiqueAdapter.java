package com.wbh.week7_mycartoon.myadapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.Cartoon;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class GridViewBoutiqueAdapter extends MyBaseAdapter<Cartoon> {
    private List<Cartoon> list;
    private Context mContext;

    public GridViewBoutiqueAdapter(List<Cartoon> list, Context mContext, int resId) {
        super(list, mContext, resId);
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public void setDate(ViewHolder holder, int position) {
        ImageView gridImage_iv = (ImageView) holder.findView(R.id.gridImage_iv);
        TextView grid_carName_tv = (TextView) holder.findView(R.id.grid_carName_tv);
        Picasso.with(mContext).load(list.get(position).getIcon()).into(gridImage_iv);
        grid_carName_tv.setText(list.get(position).getName());
    }
}
