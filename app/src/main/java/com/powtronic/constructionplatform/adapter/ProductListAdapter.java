package com.powtronic.constructionplatform.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.powtronic.constructionplatform.bean.Constants;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.bean.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pp on 2017/1/10.
 */

public class ProductListAdapter extends HolderAdapter<Product, ProductListAdapter.ViewHolder> {


    private View view;

    public ProductListAdapter(Context context, List<Product> listData) {
        super(context, listData);
    }

    @Override
    public View buildConvertView(LayoutInflater layoutInflater, Product product, int position) {
        view = layoutInflater.inflate(R.layout.item_product_off, null);

        return view;
    }

    @Override
    public ViewHolder buildHolder(View convertView, Product product, int position) {
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void bindViewDatas(ViewHolder holder, Product product, int position) {
//        viewHolder.iv;
        if (!TextUtils.isEmpty(product.getImgUrl())) {
            Picasso.with(context).load(Constants.IMAGE_URL_ + product.getImgUrl().replace("\\", "/")).resize(112, 80).into(holder.iv);
        } else {
            Picasso.with(context).load(Constants.IMAGE_URL_ + "upLoading\\photo\\new.jpg".replace("\\", "/")).resize(112, 80).into(holder.iv);
        }
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("￥" + product.getPrice());
    }

    public class ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.iv_product)
        ImageView iv;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }

    }
}
