package com.powtronic.constructionplatform.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.RepairOrder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pp on 2017/1/9.
 */

public class MaintainListFragment extends Fragment {


    @BindView(R.id.ll)
    ListView ll;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_list_execute, container, false);
        ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    private void initView() {

        List<RepairOrder> orders = new ArrayList<>();
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setCompanyAddress("上海");
        repairOrder.setCompanyName("万达");
        repairOrder.setPhone("021-888888");
        repairOrder.setMaintainer("赵云");
        repairOrder.setStatus("待接单");
        orders.add(repairOrder);
        RepairOrder repairOrder1 = new RepairOrder();
        repairOrder1.setCompanyAddress("上海");
        repairOrder1.setCompanyName("一建");
        repairOrder1.setPhone("021-888888");
        repairOrder1.setMaintainer("赵云");
        repairOrder1.setStatus("维修中");
        orders.add(repairOrder1);
        RepairOrder repairOrder2 = new RepairOrder();
        repairOrder2.setCompanyAddress("上海");
        repairOrder2.setCompanyName("二建");
        repairOrder2.setPhone("021-888888");
        repairOrder2.setMaintainer("赵云");
        repairOrder2.setStatus("完成");
        orders.add(repairOrder2);

        MyAdapter adapter = new MyAdapter(getActivity(), orders);
        ll.setAdapter(adapter);


    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private List<RepairOrder> datas;

        public MyAdapter(Context context, List<RepairOrder> datas) {
            this.datas = datas;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public RepairOrder getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_repair_order, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvAddress.setText(getItem(position).getCompanyAddress());
            holder.tvMaintainer.setText(getItem(position).getMaintainer());
            holder.tvName.setText(getItem(position).getCompanyName());
            holder.tvPhone.setText(getItem(position).getPhone());
            String status = getItem(position).getStatus();
            if("待接单".equals(status)) holder.ivIcon.setImageResource(R.drawable.wait);
            if("维修中".equals(status)) holder.ivIcon.setImageResource(R.drawable.repairing);
            if("完成".equals(status)) holder.ivIcon.setImageResource(R.drawable.end);
            return convertView;
        }

         class ViewHolder {
            @BindView(R.id.iv_icon)
            ImageView ivIcon;
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_phone)
            TextView tvPhone;
            @BindView(R.id.tv_address)
            TextView tvAddress;
            @BindView(R.id.tv_maintainer)
            TextView tvMaintainer;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }

}
