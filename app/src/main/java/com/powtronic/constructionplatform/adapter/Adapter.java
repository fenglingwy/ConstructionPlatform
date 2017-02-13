package com.powtronic.constructionplatform.adapter;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.Product;
import com.powtronic.constructionplatform.view.ETWachter;

import java.util.List;

/**
 * Created by pp on 2017/1/24.
 */

public class Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<Product.Param> params;

    public Adapter(Context context, List<Product.Param> params) {
        this.context = context;
        this.params = params;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return params.size();
    }

    @Override
    public Product.Param getItem(int position) {
        return params.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        convertView = inflater.inflate(R.layout.item_ware_add, parent, false);
        holder = new ViewHolder();
        holder.etParamName = (EditText) convertView.findViewById(R.id.et_param_name);
        holder.etParamValue = (EditText) convertView.findViewById(R.id.et_param_value);
        holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
        convertView.setTag(holder);
        holder.etParamName.addTextChangedListener(
                new ETWachter() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        params.get(position).setName(s.toString());
                        Log.d("TAG", "afterTextChanged: "+position);
                        Log.d("TAG", "afterTextChanged: " + params.get(position));
                    }
                }
        );
        holder.etParamValue.addTextChangedListener(new ETWachter() {
            @Override
            public void afterTextChanged(Editable s) {
                params.get(position).setValue(s.toString());
                Log.d("TAG", "afterTextChanged: "+position);
                Log.d("TAG", "afterTextChanged: " + params.get(position));
            }
        });

        holder.etParamName.setText(params.get(position).getName());
        holder.etParamValue.setText(params.get(position).getValue());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: " + position);
                params.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        EditText etParamName;
        EditText etParamValue;
        ImageView ivDelete;
    }


}
