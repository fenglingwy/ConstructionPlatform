package com.powtronic.constructionplatform.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.powtronic.constructionplatform.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pp on 2017/1/9.
 */

public class MaintainAddFragment extends Fragment {


    @BindView(R.id.et_company_name)
    EditText etCompanyName;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_people)
    EditText etPeople;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_maintainer)
    TextView etMaintainer;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add_maintain, container, false);
        ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    private void initView() {

    }


    @OnClick(R.id.et_maintainer)
    public void onClick() {
        String[] ss = {"赵云", "甄姬", "黄忠"};
        showSelectDialog(ss);
    }

    private void showSelectDialog(final String[] strings) {
        new AlertDialog.Builder(getActivity())
                .setTitle("请选择")
                .setMultiChoiceItems(strings, null, null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog alertDialog = (AlertDialog) dialog;
                        ListView listView = alertDialog.getListView();
                        //通过listView取得稀疏布尔数组
                        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
                        /**SparseBooleanArray：好比hashmap ：key(integer:position)  value(boolean:复选项的状态)
                         * 遍历稀疏布尔数组
                         */
                        int size = checkedItemPositions.size();
                        StringBuilder sb = new StringBuilder("");
                        for (int i = 0; i < size; i++) {
                            if (checkedItemPositions.valueAt(i)) {
                                //依据保存的位置position来获取复选项复选文本
                                int position = checkedItemPositions.keyAt(i);
                                String selectStr = listView.getAdapter().getItem(position).toString();
                                if (TextUtils.isEmpty(sb)) sb.append(selectStr);
                                else sb.append("/" + selectStr);
                            }
                        }
                        etMaintainer.setText(sb);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
