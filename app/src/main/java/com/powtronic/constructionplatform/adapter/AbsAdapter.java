package com.powtronic.constructionplatform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by pp on 2016/10/25.
 */

/**
 * 抽象适配器（免去一些通用的代码）
 *
 * @param <T>
 * @author Jenly
 */
public abstract class AbsAdapter<T> extends BaseAdapter {

    protected Context context;

    protected List<T> listData;

    protected LayoutInflater layoutInflater;

    public AbsAdapter(Context context, List<T> listData) {
        this.context = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData == null ? null : listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getListData() {
        return listData;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
    }


    public View inflate(int layoutId) {
        return layoutInflater.inflate(layoutId, null);
    }

}
