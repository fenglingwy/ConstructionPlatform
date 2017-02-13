package com.powtronic.constructionplatform.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powtronic.constructionplatform.R;

/**
 * Created by pp on 2017/1/13.
 */

public class SaleViewHolder extends BaseViewHolder {

    public  TextView tvName;
    public final TextView tvPrice;
    public final ImageView ivProduct;

    public SaleViewHolder(View itemView, BaseAdapter.OnItemClickListener onItemClickListener) {
        super(itemView, onItemClickListener);

        tvName = (TextView)itemView.findViewById(R.id.tv_name);
        tvPrice = (TextView)itemView.findViewById(R.id.tv_price);
        ivProduct = (ImageView)itemView.findViewById(R.id.iv_product);
    }
}
