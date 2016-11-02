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
public class RankAdapter extends MyBaseAdapter<Cartoon> {

    private List<Cartoon> list;
    private Context mContext;

    public RankAdapter(List<Cartoon> list, Context mContext, int resId) {
        super(list, mContext, resId);
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public void setDate(ViewHolder holder, int position) {
        ImageView rand_icon_iv = (ImageView) holder.findView(R.id.rand_icon_iv);
        TextView rank_name_tv = (TextView) holder.findView(R.id.rank_name_tv);
        TextView rank_ranking_tv = (TextView) holder.findView(R.id.rank_ranking_tv);
        TextView rank_state_tv = (TextView) holder.findView(R.id.rank_state_tv);
        TextView rank_theme_tv = (TextView) holder.findView(R.id.rank_theme_tv);
        Picasso.with(mContext).load(list.get(position).getIcon()).into(rand_icon_iv);
        rank_name_tv.setText(list.get(position).getName());
        rank_ranking_tv.setText("人气: "+list.get(position).getRanking());
        rank_state_tv.setText("状态: "+list.get(position).getState());
        rank_theme_tv.setText("类别: "+list.get(position).getTheme());
    }
}
