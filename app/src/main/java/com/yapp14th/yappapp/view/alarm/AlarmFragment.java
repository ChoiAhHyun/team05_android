package com.yapp14th.yappapp.view.alarm;

import android.os.Bundle;
import android.view.View;

import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.alarm.AlarmAdapter;
import com.yapp14th.yappapp.model.AlarmInfo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class AlarmFragment extends BaseFragment {

    @BindView(R.id.rv_alarm)
    RecyclerView rv;

    private ArrayList<AlarmInfo> modelList;
    private AlarmAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_alarm;
    }

    public static AlarmFragment newInstance() {
        return new AlarmFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDummyData();

        setRecyclerView();

    }

    private void setDummyData(){

        modelList = new ArrayList<>();

        modelList.add(new AlarmInfo("",1, 0, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",2, 1, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",0, 2, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",2, 1, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",0, 2, "ads","ad","asdsad", true));
        modelList.add(new AlarmInfo("",1, 0, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",2, 0, "ads","ad","asdsad", true));
        modelList.add(new AlarmInfo("",1, 0, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",1, 0, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",1, 0, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",1, 1, "ads","ad","asdsad", true));
        modelList.add(new AlarmInfo("",1, 0, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",2, 1, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",0, 2, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",2, 1, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",0, 2, "ads","ad","asdsad", true));
        modelList.add(new AlarmInfo("",1, 0, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",2, 0, "ads","ad","asdsad", true));
        modelList.add(new AlarmInfo("",1, 0, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",1, 0, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",1, 0, "ads","ad","asdsad", false));
        modelList.add(new AlarmInfo("",1, 1, "ads","ad","asdsad", true));


    }

    private void setRecyclerView(){

        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        lm.scrollToPositionWithOffset(0,0);
        rv.setLayoutManager(lm);
        setAdapter();

    }

    private void setAdapter(){

        adapter = new AlarmAdapter(getActivity(), modelList);

        rv.setAdapter(adapter);

    }
}
