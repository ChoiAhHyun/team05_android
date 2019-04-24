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

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    private GroupCardAdpater adpater;

    private ArrayList<GroupCardInfo> nearGroupModelList, realTimeGroupModelList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRecyclerView();

        setModelDatas();

        setAdapter();

    }

    private void setRecyclerView(){

        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        lm.scrollToPositionWithOffset(0,0);

        rvNearGroup.setLayoutManager(lm);

    }

    private void setModelDatas(){

        nearGroupModelList = new ArrayList<GroupCardInfo>();

        nearGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));

        nearGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));
        nearGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));
        nearGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));
        nearGroupModelList.add(new GroupCardInfo("asd","ads","asd","asd","asd",new ArrayList<>()));

    }

    private void setAdapter(){

        adpater = new GroupCardAdpater(getActivity(), nearGroupModelList, 0);

        rvNearGroup.setAdapter(adpater);
    }
}
