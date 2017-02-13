package com.powtronic.constructionplatform.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pp on 2017/1/13.
 */


public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.MyViewHolder> {
    private final LayoutInflater inflater;
    public List<Product> data;
    private OnItemClickListener mOnItemClickListener = null;
    private Context context;

    public SaleAdapter(Context context, List<Product> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }


    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_product_off_grid, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvPrice.setText(data.get(position).getPrice());
//        holder.iv.setBackgroundResource(R.drawable.ad1);
        Picasso.with(context).load(data.get(position).getImgUrl()).into(holder.iv);
        holder.tvName.setText(data.get(position).getName()
        );
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv;
        TextView tvPrice;
        TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_product);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


}
