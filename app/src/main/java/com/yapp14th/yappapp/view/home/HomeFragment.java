package com.yapp14th.yappapp.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.rubensousa.gravitysnaphelper.GravityPagerSnapHelper;
import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.GroupCardAdpater;
import com.yapp14th.yappapp.model.GroupCardInfo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_home_near_group)
    RecyclerView rvNearGroup;

    @BindView(R.id.rv_home_realtime_group)
    RecyclerView rvRealTime;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    private GroupCardAdpater adapterNear, adapterRealTime;

    private ArrayList<GroupCardInfo> nearGroupModelList, realTimeGroupModelList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRecyclerView();

        setNearGroupDatas();

        setRealTimeGroupDatas();

        setAdapter();

    }

    private void setRecyclerView(){

        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        lm.scrollToPositionWithOffset(0,0);

        rvNearGroup.setLayoutManager(lm);

        new LinearSnapHelper().attachToRecyclerView(rvNearGroup);

        lm = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        lm.scrollToPositionWithOffset(0,0);

        rvRealTime.setLayoutManager(lm);
        rvRealTime.setNestedScrollingEnabled(false);
        rvRealTime.setHasFixedSize(false);

    }

    private void setNearGroupDatas(){

        nearGroupModelList = new ArrayList<GroupCardInfo>();

        for (int i=0; i<40;i ++){

            ArrayList<String> strs = new ArrayList<>();

            for(int j=0; j<i+1; j++) strs.add("s");

            nearGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",strs));

        }

    }

    private void setRealTimeGroupDatas(){

        realTimeGroupModelList = new ArrayList<GroupCardInfo>();

        for (int i=0; i<40;i ++){

            ArrayList<String> strs = new ArrayList<>();

            for(int j=0; j<i+1; j++) strs.add("s");

            realTimeGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",strs));

        }

    }

    private void setAdapter(){

        adapterNear = new GroupCardAdpater(getActivity(), nearGroupModelList, 0);

        adapterNear.setOnItemClickListener(itemClickListener);

        rvNearGroup.setAdapter(adapterNear);

        adapterRealTime = new GroupCardAdpater(getActivity(), realTimeGroupModelList, 1);

        adapterRealTime.setOnItemClickListener(itemClickListener);

        rvRealTime.setAdapter(adapterRealTime);

    }

    private GroupCardAdpater.ItemOnCickListener itemClickListener = (model, sharedView) -> {

        Intent intent = new Intent(getActivity(), HomeDetailActivity.class);
        intent.putExtra(getString(R.string.intent_str_transition_view), ViewCompat.getTransitionName(sharedView));
        intent.putExtra("groupInfo", model);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                Pair.create(sharedView, ViewCompat.getTransitionName(sharedView)));
        startActivityForResult(intent, 1, options.toBundle());


    };


}
