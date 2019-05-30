package com.yapp14th.yappapp.view.alarm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.yapp14th.yappapp.Base.BaseFragment;
import com.yapp14th.yappapp.R;
import com.yapp14th.yappapp.adapter.alarm.AlarmAdapter;
import com.yapp14th.yappapp.common.RetrofitClient;
import com.yapp14th.yappapp.model.AlarmInfo;
import com.yapp14th.yappapp.model.AlarmResponse;
import com.yapp14th.yappapp.model.UserIdModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_alarm)
    RecyclerView rv;
    @BindView(R.id.txt_empty)
    TextView txtEmpty;
    @BindView(R.id.txt_alarm)
    TextView txtAlarm;

    private ArrayList<AlarmResponse.AlarmInfo> modelList = new ArrayList<>();
    private AlarmAdapter adapter;
    private View mRootLayout;
    private boolean isFirst = true;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @Override
    protected int getLayout() {
        return R.layout.fragment_alarm;
    }

    public static AlarmFragment newInstance() {
        return new AlarmFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (isFirst) {

            mRootLayout = inflater.inflate(getLayout(), container, false);
            ButterKnife.bind(this, mRootLayout);
            swipe.setOnRefreshListener(this);

            YoYo.with(Techniques.FadeIn).playOn(txtAlarm);

        }

        return mRootLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isFirst) setRecyclerView();

    }

    @Override
    public void onStart() {
        super.onStart();

        if (isFirst) getAlarmDatas();

        isFirst = false;

    }

    private void getAlarmDatas(){

        RetrofitClient.getInstance().getService().GetAlarmDatas(new UserIdModel("dnjsgml")).enqueue(new Callback<AlarmResponse>() {
            @Override
            public void onResponse(Call<AlarmResponse> call, Response<AlarmResponse> response) {

                if (response.isSuccessful()){

                    if (response.code() == 200){

                        modelList.addAll(response.body().list);

                        adapter.notifyDataSetChanged();

                    }
                }

                if (modelList.size() == 0) txtEmpty.setVisibility(View.VISIBLE);
                else txtEmpty.setVisibility(View.INVISIBLE);

                swipe.setRefreshing(false);

                rv.scheduleLayoutAnimation();

            }

            @Override
            public void onFailure(Call<AlarmResponse> call, Throwable t) {

            }
        });

    }

    private void setRecyclerView(){

        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        lm.scrollToPositionWithOffset(0,0);
        rv.setLayoutManager(lm);

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(rv.getContext(), R.anim.layout_animation_from_bottom);

        rv.setLayoutAnimation(animation);

        setAdapter();

    }

    private void setAdapter(){

        adapter = new AlarmAdapter(getActivity(), modelList);

        rv.setAdapter(adapter);

    }

    @Override
    public void onRefresh() {
        modelList.clear();
        getAlarmDatas();
    }
}
