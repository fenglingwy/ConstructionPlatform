package com.powtronic.constructionplatform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.adapter.ManagerAdapter;
import com.powtronic.constructionplatform.bean.ManagerDepartment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pp on 2017/1/20.
 */

public class LeaseFragment extends BaseFragment {

    @BindView(R.id.lv)
    ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supervise, container, false);
        ButterKnife.bind(this, view);
        setTitleBar(view, "求租信息", TITLEBAR_FRAGMENT_ONLY_BACK);
        initView();
        return view;
    }

    private void initView() {
        ArrayList<ManagerDepartment> datas = new ArrayList<>();
        datas.add(new ManagerDepartment(R.drawable.zu, "求租人1", "021-95533", "上海市徐汇区中核浦原科技园"));
        datas.add(new ManagerDepartment(R.drawable.zu, "求租人2", "021-95544", "上海市徐汇区中核浦原科技园"));
        datas.add(new ManagerDepartment(R.drawable.zu, "求租人3", "021-95566", "上海市徐汇区中核浦原科技园"));
        datas.add(new ManagerDepartment(R.drawable.zu, "求租人4", "021-95531", "上海市徐汇区中核浦原科技园"));
        datas.add(new ManagerDepartment(R.drawable.zu, "求租人5", "021-95531", "上海市徐汇区中核浦原科技园"));
        datas.add(new ManagerDepartment(R.drawable.zu, "求租人6", "021-95531", "上海市徐汇区中核浦原科技园"));
        datas.add(new ManagerDepartment(R.drawable.zu, "求租人7", "021-95531", "上海市徐汇区中核浦原科技园"));
        datas.add(new ManagerDepartment(R.drawable.zu, "求租人8", "021-95531", "上海市徐汇区中核浦原科技园"));
        datas.add(new ManagerDepartment(R.drawable.zu, "求租人9", "021-95531", "上海市徐汇区中核浦原科技园"));
        lv.setAdapter(new ManagerAdapter(getActivity(), datas));
    }
}
