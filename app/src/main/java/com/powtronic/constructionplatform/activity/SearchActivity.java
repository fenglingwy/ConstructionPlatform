package com.powtronic.constructionplatform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.powtronic.constructionplatform.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class SearchActivity extends AppCompatActivity implements TextWatcher {
    ArrayList<String> historys;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.lv_history)
    ListView lvHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.iv_empty_history)
    ImageView iveEmpry;
    private int product_type;
    private int sales_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        product_type = getIntent().getIntExtra("product_type", -1);
        sales_type = getIntent().getIntExtra("sales_type", -1);

        initView();
    }

    private void initView() {
        historys = new ArrayList<>();
        showHistory();
        etSearch.addTextChangedListener(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_search, R.id.iv_delete, R.id.iv_empty_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                onClickSearch();
                break;
            case R.id.iv_delete:
                etSearch.setText(null);
                ivDelete.setVisibility(View.GONE);
                showHistory();
                break;
            case R.id.iv_empty_history:
                historys.clear();
                showHistory();
                break;
        }
    }

    /**
     * 显示历史纪录
     */
    private void showHistory() {
        llHistory.setVisibility(View.VISIBLE);
        if (historys.size() == 0) {
            iveEmpry.setVisibility(View.GONE);
            tvHint.setVisibility(View.VISIBLE);
            lvHistory.setVisibility(View.GONE);
        } else {
            iveEmpry.setVisibility(View.VISIBLE);
            tvHint.setVisibility(View.GONE);
            lvHistory.setVisibility(View.VISIBLE);
            lvHistory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historys));
        }
    }

    /**
     * 隐藏历史纪录
     */
    private void hideHistory() {
        llHistory.setVisibility(View.GONE);
    }

    /**
     * 点击搜索
     */
    private void onClickSearch() {
        String keyword = etSearch.getText().toString();
        if (TextUtils.isEmpty(keyword)) return;
        addHistory(keyword);
        Intent intent = new Intent(this, SearchDisplayActivity.class);
        intent.putExtra("keyword", keyword);
        if (product_type != -1) intent.putExtra("product_type", product_type);
        if (sales_type != -1) intent.putExtra("sales_type", sales_type);
        showHistory();
        startActivity(intent);
    }


    private void addHistory(String content) {
        historys.add(0, content);
    }

    @OnItemClick(R.id.lv_history)
    public void onItemClick(int position) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())) {
            showHistory();
            ivDelete.setVisibility(View.GONE);
        } else {
//            hideHistory();
            showSearchHint();
            ivDelete.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示搜索关联提示
     */
    private void showSearchHint() {
    }
}
