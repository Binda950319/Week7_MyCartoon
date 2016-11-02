package com.wbh.week7_mycartoon.consult;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.HeadLine;
import com.wbh.week7_mycartoon.myadapter.MyBaseAdapter;
import com.wbh.week7_mycartoon.myadapter.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/25.
 */
public class HeadlineAdapter extends MyBaseAdapter<HeadLine> {
    private List<HeadLine> list;
    private Context mContext;

    public HeadlineAdapter(List<HeadLine> list, Context mContext, int resId) {
        super(list, mContext, resId);
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public void setDate(ViewHolder holder, int position) {
        ImageView headline_iv = (ImageView) holder.findView(R.id.headline_iv);
        TextView headine_title_tv = (TextView) holder.findView(R.id.headine_title_tv);
        TextView headline_type_tv = (TextView) holder.findView(R.id.headline_type_tv);
        TextView headline_author_tv = (TextView) holder.findView(R.id.headline_author_tv);
        Picasso.with(mContext).load(list.get(position).getL_picture()).into(headline_iv);
        headine_title_tv.setText(list.get(position).getTitle());
        headline_type_tv.setText(list.get(position).getNewstype_content());
        headline_author_tv.setText(list.get(position).getNewsauthor_content());

    }
}
