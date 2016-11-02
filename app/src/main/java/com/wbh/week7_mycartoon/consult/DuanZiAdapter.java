package com.wbh.week7_mycartoon.consult;

import android.content.Context;
import android.widget.TextView;

import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.javabean.DuanBean;
import com.wbh.week7_mycartoon.myadapter.MyBaseAdapter;
import com.wbh.week7_mycartoon.myadapter.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */
public class DuanZiAdapter extends MyBaseAdapter<DuanBean> {
    private List<DuanBean> list;
    private Context mContext;

    public DuanZiAdapter(List<DuanBean> list, Context mContext, int resId) {
        super(list, mContext, resId);
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public void setDate(ViewHolder holder, int position) {
        TextView con_duanzi_title = (TextView) holder.findView(R.id.con_duanzi_title);
        TextView con_duanzi_time = (TextView) holder.findView(R.id.con_duanzi_time);
        TextView con_duanzi_text = (TextView) holder.findView(R.id.con_duanzi_text);
        TextView con_duanzi_comment = (TextView) holder.findView(R.id.con_duanzi_comment);
        con_duanzi_title.setText(list.get(position).getName());
        con_duanzi_time.setText(list.get(position).getCreatetime());
        con_duanzi_text.setText(list.get(position).getText());
        con_duanzi_comment.setText(list.get(position).getCommend());

    }
}
