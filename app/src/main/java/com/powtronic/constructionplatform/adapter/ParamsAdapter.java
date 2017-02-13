package com.powtronic.constructionplatform.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.Product;

import java.util.List;

/**
 * Created by pp on 2017/1/22.
 */
public class ParamsAdapter extends HolderAdapter<Product.Param, ParamsAdapter.ViewHolder> {



    public ParamsAdapter(Context context, List<Product.Param> listData) {
        super(context, listData);
    }

    @Override
    public View buildConvertView(LayoutInflater layoutInflater, Product.Param param, int position) {
        View view = layoutInflater.inflate(R.layout.item_ware_add, null);
        return view;
    }


    @Override
    public ViewHolder buildHolder(View convertView, Product.Param param,  int position) {
        ViewHolder holder = new ViewHolder();
        holder.etParamName = (EditText) convertView.findViewById(R.id.et_param_name);
        holder.etParamValue = (EditText) convertView.findViewById(R.id.et_param_value);
        holder.ivDelete   = (ImageView) convertView.findViewById(R.id.iv_delete);

        return holder;
    }

    @Override
    public void bindViewDatas(ViewHolder holder, Product.Param param,  int position) {
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("TAG", "onClick: "+position);
//                listData.remove(position);
//                notifyDataSetChanged();
            }
        });
        Log.d("TAG", "param: "+param);
        holder.etParamName.setText(param.getName());
        holder.etParamValue.setText(param.getValue());
        holder.etParamName.addTextChangedListener(new ETWatcher(position));
    }


    public class ViewHolder {
        EditText etParamName;
        EditText etParamValue;
        ImageView ivDelete;
    }

class ETWatcher implements TextWatcher{
    int position;

    public ETWatcher(int position) {
        this.position = position;
    }

    @Override
    public void afterTextChanged(Editable s) {
        listData.get(position).setName(s.toString());
        Log.d("TAG", "afterTextChanged: "+position);
        Log.d("TAG", "afterTextChanged: " + listData.get(position));
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


}
}
