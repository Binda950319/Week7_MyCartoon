package com.wbh.week7_mycartoon.consult;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.PicBean;
import com.wbh.week7_mycartoon.myadapter.MyBaseAdapter;
import com.wbh.week7_mycartoon.myadapter.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/25.
 */
public class PicAdapter extends MyBaseAdapter<PicBean> {
    private List<PicBean> list;
    private Context mContext;

    public PicAdapter(List<PicBean> list, Context mContext, int resId) {
        super(list, mContext, resId);
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public void setDate(ViewHolder holder, int position) {
        ImageView consult_pic_iv = (ImageView) holder.findView(R.id.consult_pic_iv);
        Picasso.with(mContext).load(list.get(position).getPicture_x()).into(consult_pic_iv);
    }
}
