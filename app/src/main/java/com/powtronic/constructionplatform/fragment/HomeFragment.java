package com.powtronic.constructionplatform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.activity.ModuleDisplayActivity;
import com.powtronic.constructionplatform.activity.SearchActivity;
import com.powtronic.constructionplatform.adapter.ModuleAdapter;
import com.powtronic.constructionplatform.bean.Banner;
import com.powtronic.constructionplatform.bean.Module;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pp on 2017/1/9.
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    private View mView;
    private ArrayList<Module> modules;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    private void initView() {
        initSlider();
        initRecyclerView();
    }

    private void initSlider() {
        List<Banner> mBanner = new ArrayList<>();
        mBanner.add(new Banner("置创明匠", R.drawable.ad1, "ad1"));
        mBanner.add(new Banner("搅拌车", R.drawable.ad2, "ad2"));
        mBanner.add(new Banner("塔吊", R.drawable.ad3, "ad3"));

        if (mBanner != null) {
            for (Banner banner : mBanner) {
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra", banner.getName());

                slider.addSlider(textSliderView);
            }
        }

        // mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomAnimation(new DescriptionAnimation());
        slider.setPresetTransformer(SliderLayout.Transformer.Default);
        slider.setDuration(3000);
    }

    private void initRecyclerView() {
        final String[] strings = {"sale","rent","lease","part","supervise","safe"};
        //设置固定大小
        rvMain.setHasFixedSize(true);
        //创建网格布局
        GridLayoutManager girdLayoutManager = new GridLayoutManager(getActivity(), 4);
        //给RecyclerView设置布局管理器
        rvMain.setLayoutManager(girdLayoutManager);
        //创建适配器，并且设置

        modules = new ArrayList<>();
        modules.add(new Module("设备出售", R.drawable.sale, null));
        modules.add(new Module("设备出租", R.drawable.rent, null));
        modules.add(new Module("设备求租", R.drawable.lease, null));
        modules.add(new Module("配套件出售", R.drawable.part, null));
        modules.add(new Module("监管部门", R.drawable.supervise, null));
        modules.add(new Module("安检单位", R.drawable.safe, null));

        ModuleAdapter mAdapter = new ModuleAdapter(getActivity(), modules);
        mAdapter.setmOnItemClickListener(new ModuleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                Intent intent = new Intent(getActivity(), ModuleDisplayActivity.class);
                intent.putExtra("tag",strings[position]);
                startActivity(intent);
            }
        });
        rvMain.setAdapter(mAdapter);
    }

    @OnClick(R.id.ll_search)
    public void search(View v){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("type",201);
        startActivity(intent);
    }
}
