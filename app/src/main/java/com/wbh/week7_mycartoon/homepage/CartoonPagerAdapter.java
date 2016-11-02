package com.wbh.week7_mycartoon.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.CartoonPage;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
public class CartoonPagerAdapter extends BaseAdapter{
    private final int heightPixels;
    private List<CartoonPage> list;
    private Context mContext;
    private LayoutInflater inflater;

    public CartoonPagerAdapter(List<CartoonPage> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        heightPixels = mContext.getResources().getDisplayMetrics().heightPixels;
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
        ViewHolder holer = null;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.cartoon_page_item, parent, false);
            holer = new ViewHolder();
            holer.cartoon_page_iv = (ImageView) convertView.findViewById(R.id.cartoon_page_iv);
            convertView.setTag(holer);
        } else{
            holer = (ViewHolder) convertView.getTag();

        }
        Picasso.with(mContext).load(list.get(position).getIcon()).into(holer.cartoon_page_iv);


        convertView.getLayoutParams().width=ViewGroup.LayoutParams.MATCH_PARENT;
        convertView.getLayoutParams().height=heightPixels;

        return convertView;
    }

    static class ViewHolder{
        ImageView cartoon_page_iv;
    }
}
