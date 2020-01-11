package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class LvDemoAdatper extends BaseAdapter {
    private Context mContext;

    private int[] resIds = {
            R.drawable.item_lv_1,
            R.drawable.item_lv_2,
            R.drawable.item_lv_3,
            R.drawable.item_lv_4,
            R.drawable.item_lv_5,
            R.drawable.item_lv_6,
            R.drawable.item_lv_7,
            R.drawable.item_lv_8,
            R.drawable.item_lv_9,
            R.drawable.item_lv_10,
            R.drawable.item_lv_11,
            R.drawable.item_lv_12,
            R.drawable.item_lv_13,
            R.drawable.item_lv_14,
            R.drawable.item_lv_15,
            R.drawable.item_lv_16,
            R.drawable.item_lv_17,
            R.drawable.item_lv_18,
            R.drawable.item_lv_19,
            R.drawable.item_lv_20,
    };

    public LvDemoAdatper(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return resIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_lv_demo, null);

            holder.ivIcon = convertView.findViewById(R.id.iv_icon);
            holder.tvTitle = convertView.findViewById(R.id.tv_title);;
            holder.tvDesc = convertView.findViewById(R.id.tv_description);

            convertView.setTag(holder);
        } else {
            // 复用holder，通过tag找到holder
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ivIcon.setImageResource(resIds[position]);
        holder.tvTitle.setText("标题" + (position+1));
        holder.tvDesc.setText("我就是标题的描述信息：" + (position+1));

        return convertView;
    }

    public final class ViewHolder {
        public ImageView ivIcon;
        public TextView tvTitle;
        public TextView tvDesc;
    }
}
