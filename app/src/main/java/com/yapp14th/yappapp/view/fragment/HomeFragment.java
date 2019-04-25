package com.yapp14th.yappapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.home.GroupCardAdpater;
import com.yapp14th.yappapp.model.GroupCardInfo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
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

        lm = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        lm.scrollToPositionWithOffset(0,0);

        rvRealTime.setLayoutManager(lm);

    }

    private void setNearGroupDatas(){

        nearGroupModelList = new ArrayList<GroupCardInfo>();

        nearGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));
        nearGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));
        nearGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));
        nearGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));
        nearGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));

    }

    private void setRealTimeGroupDatas(){

        realTimeGroupModelList = new ArrayList<>();

        realTimeGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));
        realTimeGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));
        realTimeGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));
        realTimeGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));
        realTimeGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));

    }

    private void setAdapter(){

        adapterNear = new GroupCardAdpater(getActivity(), nearGroupModelList, 0);

        rvNearGroup.setAdapter(adapterNear);

        adapterRealTime = new GroupCardAdpater(getActivity(), realTimeGroupModelList, 1);

        rvRealTime.setAdapter(adapterRealTime);

    }
}
