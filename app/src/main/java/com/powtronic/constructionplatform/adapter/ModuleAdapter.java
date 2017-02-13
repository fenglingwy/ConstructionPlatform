package com.powtronic.constructionplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.Module;

import java.util.List;


/**
 * Created by pp on 2016/10/20.
 */
public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.MyViewHolder> {
    private final LayoutInflater inflater;
    private List<Module> data;
    private OnItemClickListener mOnItemClickListener = null;

    public ModuleAdapter(Context context, List<Module> data){
        this.data = data;
        inflater = LayoutInflater.from(context);
    }


    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout. item_main_recycler,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(data.get(position).getName());
        holder.iv.setBackgroundResource(data.get(position).getResId());
        holder.itemView.setTag(data.get(position));
    }



    @Override
    public int getItemCount() {
        return data.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView iv;
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv=(TextView) view.findViewById(R.id.tv_module_name);
            iv = (ImageView)view.findViewById(R.id.iv_role);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }


    }

    public static interface OnItemClickListener {
        void onItemClick(View view,int position);
    }


}
