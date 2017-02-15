package com.powtronic.constructionplatform.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.ManagerDepartment;

import java.util.List;

/**
 * Created by pp on 2017/2/15.
 */

public class ManagerAdapter extends android.widget.BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<ManagerDepartment> datas;

    public ManagerAdapter(Context context, List<ManagerDepartment> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public ManagerDepartment getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_manager, parent, false);
            holder = new ViewHolder();
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPhone = (TextView) convertView.findViewById(R.id.tv_phone);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tv_address);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.ivIcon.setImageResource(datas.get(position).getIcon());
        holder.tvName.setText(datas.get(position).getName());
        holder.tvPhone.setText(datas.get(position).getPhone());
        holder.tvAddress.setText(datas.get(position).getAddress());
        return convertView;
    }
    public class ViewHolder {
        TextView tvName;
        TextView tvPhone;
        TextView tvAddress;
        ImageView ivIcon;
    }

}