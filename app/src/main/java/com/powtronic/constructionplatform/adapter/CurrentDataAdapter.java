package com.powtronic.constructionplatform.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.CurrentData;

import java.util.List;

/**
 * Created by pp on 2017/2/15.
 */

public class CurrentDataAdapter extends android.widget.BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<CurrentData> datas;

    public CurrentDataAdapter(Context context, List<CurrentData> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CurrentData getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_data_current, parent, false);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvValue = (TextView) convertView.findViewById(R.id.tv_value);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvName.setText(datas.get(position).getName());
        holder.tvValue.setText(datas.get(position).getValue());
        return convertView;
    }
    public class ViewHolder {
        TextView tvName;
        TextView tvValue;
    }

}